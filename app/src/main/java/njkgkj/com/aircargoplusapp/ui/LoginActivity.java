package njkgkj.com.aircargoplusapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import njkgkj.com.aircargoplusapp.R;
import njkgkj.com.aircargoplusapp.Utils.AviationCommons;
import njkgkj.com.aircargoplusapp.Utils.ToastUtils;
import njkgkj.com.aircargoplusapp.http.HttpRoot;
import njkgkj.com.aircargoplusapp.model.ResData;
import njkgkj.com.aircargoplusapp.model.UploadData;

import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.Comany_txt;

public class LoginActivity extends Activity {
    //region 自定义变量
    private Context mContext;
    private Activity mAct;
    private final String TAG = "LoginActivity";

    //endregion

    //region 未预设XML控件

    //endregion

    //region 其他控件

    //endregion

    //region Layout控件

    //endregion

    //region Button控件
    @BindView(R.id.login_btn)
    Button login_btn;
    //endregion

    //region EditText控件

    //endregion

    //region 滚动View控件

    //endregion

    //region TextView控件
    @BindView(R.id.company_tv)
    TextView txt_company_tv;
    //endregion

    //region 初始化

    //region 入口函数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = LoginActivity.this;
        mAct = (Activity) mContext;
        ButterKnife.bind(this);
        init();
    }
    //endregion

    //region 设置初始化
    public void init() {
        txt_company_tv.setText(AviationCommons.Comany_txt);
        setListener();
    }
    //endregion

    //region 输入框置空

    //endregion

    //region 类的Intent

    //endregion

    //endregion

    //region 控件事件

    //region 页面上所有的点击事件
    private void setListener() {
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mAct, UserHomePageActivity.class);

                // 网络请求获取的xml值传递
                mAct.startActivity(intent);
                mAct.finish();
            }
        });
    }
    //endregion

    //endregion

    //region 功能方法

    //region 打开编辑区

    //endregion

    //region 关闭编辑区

    //endregion

    //region 句柄监听
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            try {

                if (msg.what == 1) {



                }

            }catch (Exception ee) {

            }
            return false;
        }
    });
    //endregion

    //region 请求数据
    private void GetInfo(UploadData param) {
        HttpRoot.getInstance().requstAync(mContext, param,
                new HttpRoot.CallBack() {
                    @Override
                    public void onSucess(Object result) {
                        ResData res = (ResData)result;

                        mHandler.sendEmptyMessage(1);
                    }

                    @Override
                    public void onFailed(String message) {

                        ToastUtils.showToast(mContext,message, Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onError() {
                        ToastUtils.showToast(mContext,"返回实体为Null",Toast.LENGTH_SHORT);
                    }
                });
    }
    //endregion

    //region 文本框赋值

    //endregion

    //endregion
}
