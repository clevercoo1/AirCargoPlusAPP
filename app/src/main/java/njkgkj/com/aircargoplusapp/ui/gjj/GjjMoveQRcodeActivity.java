package njkgkj.com.aircargoplusapp.ui.gjj;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import njkgkj.com.aircargoplusapp.R;
import njkgkj.com.aircargoplusapp.Utils.PublicFun;
import njkgkj.com.aircargoplusapp.Utils.ToastUtils;
import njkgkj.com.aircargoplusapp.ui.base.NavBar;

import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.StoragePath;
import static njkgkj.com.aircargoplusapp.Utils.createQRCodeBitmap.createQRCodeBitmap;

public class GjjMoveQRcodeActivity extends Activity {
    //region 自定义变量
    private Context mContext;
    private Activity mAct;
    private NavBar navBar;
    private List<View> views;
    private String QRstring;
    //endregion

    //region 未预设XML控件

    //endregion

    //region 其他控件
    @BindView(R.id.GjjMove_ImageView)
    AppCompatImageView img;
    //endregion

    //region Layout控件

    //endregion

    //region Button控件

    //endregion

    //region EditText控件

    //endregion

    //region 滚动View控件

    //endregion

    //region TextView控件

    //endregion

    //region ImgView控件

    //endregion

    //region 初始化

    //region 入口函数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gjj_move_qrcode);

        mContext = GjjMoveQRcodeActivity.this;
        mAct = PublicFun.getActivityByContext(mContext);
        ButterKnife.bind(this);
        initView();
    }
    //endregion

    //region 设置初始化
    private void initView() {
        navBar = new NavBar(this);
        navBar.setTitle("待移库信息");
        QRstring = "";

        HashMap<String,String[]> p = (HashMap<String, String[]>) getIntent().getSerializableExtra("Info");
        for (String key : p.keySet()){
            QRstring += key + "_";
        }

        if (!TextUtils.isEmpty(QRstring)) {
            Bitmap bitmap = createQRCodeBitmap(QRstring, 800, 800, "UTF-8", "H", "1", Color.BLACK, Color.WHITE);
            img.setImageBitmap(bitmap);
        } else {
            ToastUtils.showToast(mContext, "未获取数据！", Toast.LENGTH_SHORT);
        }



    }
    //endregion

    //region 输入框置空

    //endregion

    //region 类的Intent

    //endregion

    //endregion

    //region 控件事件

    //region 页面上所有的控件事件

    //endregion

    //endregion

    //region 功能方法

    //region 打开编辑区

    //endregion

    //region 关闭编辑区

    //endregion

    //region 句柄监听

    //endregion

    //region 请求数据

    //endregion

    //region 文本框赋值

    //endregion

    //endregion


}
