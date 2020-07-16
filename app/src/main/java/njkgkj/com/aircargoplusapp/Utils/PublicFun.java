package njkgkj.com.aircargoplusapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import njkgkj.com.aircargoplusapp.R;

/**
 * Created by 46296 on 2020/7/13.
 */

public class PublicFun {
    //region 是否有网络
    public static int getNetype(Context context) {
           int netType = -1;
           ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
           NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
           if (networkInfo == null) {
               return netType;
           }
           int nType = networkInfo.getType();
           if (nType == ConnectivityManager.TYPE_MOBILE) {
                netType = 2;
           } else if (nType == ConnectivityManager.TYPE_WIFI) {
                netType = 1;
           }
           return netType;
    }

    //endregion

    //region 遍历子控件
    public static void ElementSwitch (ViewGroup viewGroup, boolean bool) {
        if (viewGroup == null) {
            return;
        }

        if(viewGroup instanceof ViewGroup) {
            LinkedList<ViewGroup> queue = new LinkedList<ViewGroup>();
            queue.add(viewGroup);
            while(!queue.isEmpty()) {
                ViewGroup current = queue.removeFirst();
                current.setEnabled(bool);

                for(int i = 0; i < current.getChildCount(); i ++) {
                    if(current.getChildAt(i) instanceof ViewGroup) {
                        queue.addLast((ViewGroup) current.getChildAt(i));
                    }else {
                        View view = current.getChildAt(i);
                        view.setEnabled(bool);
                        if (view instanceof Button) {
                            Button newBtn = (Button) view;
                            if (bool) {
                                newBtn.setBackgroundResource(R.drawable.button_selector);
                            } else {
                                newBtn.setBackgroundColor(Color.parseColor("#979797"));
                            }
                        }
                    }
                }
            }
        }else {
            viewGroup.setEnabled(bool);
        }
    }
    //endregion

    //region 软键盘状态切换
    public static void KeyBoardSwitch(Context context) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive()) {
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }
    //endregion

    //region 隐藏软键盘
    public static void KeyBoardHide(Activity act, Context context) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive() && act.getCurrentFocus()!=null){
            if (act.getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
    //endregion

    //region 计算ListView的高度
    public static int CalcListHeigh(ListView listView) {
        ListAdapter mAdapter = listView.getAdapter();
        if (mAdapter == null) {
            return 0;
        }
        View mView = mAdapter.getView(0, null, listView);
        mView.measure(0, 0);
        int res = mView.getMeasuredHeight() + listView.getDividerHeight();
        return res;
    }
    //endregion

    //region 判断字符串是否是数字
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
    //endregion

    //region 浮点数转字符
    public static String DoubleToStr(Double n,int m){
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumFractionDigits(m);
        String dou_str = nf.format(n);
        return  dou_str;
    }
    //endregion

    //region 转换平板号
    public static String getPinBanHao(String x){
        String result = "";
        String first = x.substring(0,1);

        if (isNumeric(first)){
            if (x.length() == 1) {
                result = "00" + x;
            }else if (x.length() == 2) {
                result = "0" + x;
            } else {
                result = x;
            }
        }else if (x.length() > 1){
            result = first.toUpperCase();
            String two = x.substring(1);
            if (two.length() == 1){
                result += "000" + two;
            }else if (two.length() == 2) {
                result += "00" + two;
            }else if (two.length() == 3) {
                result += "0" + two;
            }else {
                result = x;
            }
        } else {
            result = x;
        }

        return result;
    }
    //endregion

    //region 判断首字符是否为字母
    public static boolean  CheckFirstLetter(String   fstrData)
    {
        char   c   =   fstrData.charAt(0);
        if(((c>='a'&&c<='z')   ||   (c>='A'&&c<='Z')))
        {
            return   true;
        }else{
            return   false;
        }
    }
    //endregion

    //region 对象转字符串
    public static String ObjectToString(Object obj) {
        try {
            String value1 = "";

            if (null != obj) {
                if (obj instanceof BigDecimal) {
                    value1 = String.format("%.2f", (BigDecimal) obj);
                } else if (obj instanceof Float) {
                    value1 = String.format("%.2f", (Float)obj);
                } else if (obj instanceof Double) {
                    value1 = String.format("%.2f", (Double) obj);
                }else if (obj instanceof Date) {
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    value1 = sdf.format((Date) obj);
                }else {
                    value1 = obj.toString();
                }

                return value1;
            }

            return "";
        }  catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    //endregion

    //region 字符串转时间
    public static Date StringToDate(String str) {
        try {
            if (str != null && str.length() != 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date a = sdf.parse(str);
                return a;
            }
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    //endregion

    //region 判断字符串中是否包含中文
    /**
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        if (null != str && str != "") {
            Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
            Matcher m = p.matcher(str);
            if (m.find()) {
                return true;
            }
        }

        return false;
    }
    //endregion

    //region 二进制字符串BASE64编码
    public static String EncodeBase64(Object code)  {
        try {
            if (code!=null){
                if (code instanceof Blob) {
                    int size = (int) ((Blob) code).length();
                    byte[] msgContent = ((Blob) code).getBytes(1, size);
                    return Base64.encodeToString(msgContent, Base64.NO_WRAP);
                } else {
                    return Base64.encodeToString((byte[]) code, Base64.NO_WRAP);
                }
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    //endregion

    //region BASE64编码转二进制字符串
    public static Object DecodeBase64(String code){
        if (code!=null){
            return Base64.decode(code,Base64.DEFAULT);
        }
        else return null;
    }
    //endregion

}
