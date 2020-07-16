package njkgkj.com.aircargoplusapp.http;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import njkgkj.com.aircargoplusapp.Utils.FastjsonUtils;
import njkgkj.com.aircargoplusapp.Utils.PublicFun;
import njkgkj.com.aircargoplusapp.model.ResData;
import njkgkj.com.aircargoplusapp.model.UploadData;

/**
 * Created by 46296 on 2020/6/29.
 */

public class HttpRoot {
    private static HttpRoot instance;
    private Handler handler;
    private CallBack callBack;

    private HttpRoot() {
        handler = new Handler();
    }

    public static HttpRoot getInstance() {
        if (instance == null) {
            synchronized (HttpRoot.class) {
                if (instance == null) {
                    instance = new HttpRoot();
                }
            }
        }
        return instance;
    }

    public interface CallBack {
        void onSucess(Object result);//服务器成功返回数据
        void onFailed(String message);//服务器返回了错误的消息
        void onError();//没有网络
    }

    public void requstAync(final Context context, final UploadData params, final CallBack callBack) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                if (isCancelled()) {
                    return null;
                }


                String req = FastjsonUtils.toJson(params);
                final ResData Res = callService(context, req);

                if (callBack != null) {
                    if (Res == null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onError();
                            }
                        });
                    } else {
                        String result = Res.getCode();
                        if (result.equals("1")) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onSucess(Res);
                                }
                            });
                        }else {
                            final String errString = Res.getText();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if  (null != errString){
                                        callBack.onFailed(errString);
                                    }
                                }
                            });
                        }
                        return result;
                    }
                }
                return null;
            }
        }.execute();
    }

    private ResData callService(Context context, String jsonParam) {

        if (PublicFun.getNetype(context ) == -1) {
            ResData err = new ResData();
            err.setCode("2");
            err.setText("请检查网络连接！");
            return err;
        }

        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(HttpCommons.END_POINT);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置为true
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Connection", "Keep-Alive");

            out = new OutputStreamWriter(conn.getOutputStream());
            // POST的请求参数写在正文中
            out.write(jsonParam);

            out.flush();
            out.close();

            // 建立实际的连接
            conn.connect();
            // 获取所有响应头字段
            Map<String, List<String>> headers = conn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : headers.keySet()) {
                System.out.println(key + "--->" + headers.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            if (HttpCommons.END_POINT.contains("nlp"))
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));
            else
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String getLine;
            while ((getLine = in.readLine()) != null) {
                result.append(getLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

        return FastjsonUtils.fromJson(result.toString(),ResData.class) ;
    }
    //endregion
}
