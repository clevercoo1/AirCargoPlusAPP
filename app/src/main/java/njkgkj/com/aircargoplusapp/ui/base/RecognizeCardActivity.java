package njkgkj.com.aircargoplusapp.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import njkgkj.com.aircargoplusapp.R;
import njkgkj.com.aircargoplusapp.Utils.AviationCommons;
import njkgkj.com.aircargoplusapp.Utils.ToastUtils;
import njkgkj.com.aircargoplusapp.tools.recognize.view.FacadeView;
import njkgkj.com.aircargoplusapp.ui.gnj.PickUpSignatureActivity;

import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.GNJ_ImpPickUpActivity;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.PickUpSignatureActivity_REQUEST;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.StoragePath;
import static njkgkj.com.aircargoplusapp.Utils.PublicFun.isFastDoubleClick;

public class RecognizeCardActivity extends Activity{
    //region 自定义变量
    private Context mContext;
    private Activity mAct;
    private NavBar navBar;
    private int takePic = 0;
    //endregion


    @BindView(R.id.RecognizeCard_btn_shutter)
    ImageButton btn_shutter;
    @BindView(R.id.RecognizeCard_facade)
    FacadeView facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognize_card);
        mContext = RecognizeCardActivity.this;
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        navBar = new NavBar(this);
        navBar.setTitle("身份证识别");
        AviationCommons.MyDPI = mContext.getResources().getDisplayMetrics().density;
        facade.setTransParentRectWH(948,1200);

        File f = new File(StoragePath + "/" + "card" +".jpg");
        if (f.exists()) {
            f.delete();
        }

        setListener();
    }

    private void setListener() {
        btn_shutter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFastDoubleClick()) {
                    return;
                }

                facade.takeRectPicture(true,AviationCommons.MyDPI);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        finish();
                    }
                },1000);


            }
        });
    }

    //region 句柄监听
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            return false;
        }
    });
    //endregion

    @Override
    public void finish() {
        Intent intent = new Intent();
        int num = 0;

        Integer req = (Integer) getIntent().getSerializableExtra("id");
        if (req == PickUpSignatureActivity_REQUEST) {
            intent.setClass(RecognizeCardActivity.this, PickUpSignatureActivity.class);
            num = AviationCommons.PickUpSignatureActivity_RESULT;
        }

        setResult(num,intent);
        super.finish();
    }

}
