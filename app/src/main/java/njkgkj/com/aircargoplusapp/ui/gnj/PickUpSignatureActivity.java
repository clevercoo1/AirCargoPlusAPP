package njkgkj.com.aircargoplusapp.ui.gnj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import njkgkj.com.aircargoplusapp.R;
import njkgkj.com.aircargoplusapp.Utils.AviationCommons;
import njkgkj.com.aircargoplusapp.Utils.FastjsonUtils;
import njkgkj.com.aircargoplusapp.Utils.PublicFun;
import njkgkj.com.aircargoplusapp.Utils.ToastUtils;
import njkgkj.com.aircargoplusapp.Utils.UploadDataUtils;
import njkgkj.com.aircargoplusapp.http.HttpCommons;
import njkgkj.com.aircargoplusapp.http.HttpRoot;
import njkgkj.com.aircargoplusapp.model.ResData;
import njkgkj.com.aircargoplusapp.model.UploadData;
import njkgkj.com.aircargoplusapp.model.gnjm.CargoHandingApp;
import njkgkj.com.aircargoplusapp.model.gnjm.ImpPickUpAppModel;
import njkgkj.com.aircargoplusapp.model.gnjm.PickUpPicModel;
import njkgkj.com.aircargoplusapp.ui.base.NavBar;
import njkgkj.com.aircargoplusapp.ui.base.RecognizeCardActivity;
import njkgkj.com.aircargoplusapp.ui.dialog.LoadingDialog;
import njkgkj.com.aircargoplusapp.view.LinePathView;

import static android.R.attr.data;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.GNJ_PickUpSignatureActivity;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.GNJ_PickUpSignatureActivity_update;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.PickUpSignatureActivity_REQUEST;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.StoragePath;
import static njkgkj.com.aircargoplusapp.Utils.PublicFun.getLoacalBitmap;

public class PickUpSignatureActivity extends Activity {

    //region 自定义变量
    private Context mContext;
    private Activity mAct;
    private NavBar navBar;
    private List<PickUpPicModel> PickUpstore;
    private final String SignerjpegFile = StoragePath + "/" + "Signer" + ".jpg";
    private final String CardjpegFile = StoragePath + "/" + "card" +".jpg";
    private String ResultStr = "";
    private int intFlag = 0;
    //endregion

    //region 未预设XML控件
    private LoadingDialog Ldialog;
    private QMUITipDialog tipDialog;
    //endregion

    //region 其他控件
    @BindView(R.id.PickUpSignature_LinePathView)
    LinePathView LinePathView_HuaBu;
    @BindView(R.id.PickUpSignature_ImageView)
    AppCompatImageView ImageView;
    //endregion

    //region Layout控件

    //endregion

    //region Button控件
    @BindView(R.id.PickUpSignature_Btn_qingchu)
    Button Btn_qingchu;
    @BindView(R.id.PickUpSignature_Btn_PaiZhao)
    Button Btn_PaiZhao;
    @BindView(R.id.PickUpSignature_Btn_Shangchuan)
    Button Btn_Shangchuan;
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
        setContentView(R.layout.activity_pick_up_signature);

        mContext = PickUpSignatureActivity.this;
        mAct = PublicFun.getActivityByContext(mContext);
        ButterKnife.bind(this);
        initView();
    }
    //endregion

    //region 设置初始化
    private void initView() {
        navBar = new NavBar(this);
        navBar.setTitle("提取签名");

        Ldialog = new LoadingDialog(mContext);
        tipDialog = new QMUITipDialog.Builder(mContext)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord("成功")
                .create();

        PickUpstore = new ArrayList<>();

        HashMap<String,String[]> p = (HashMap<String, String[]>) getIntent().getSerializableExtra("Info");
        for (String key : p.keySet()){
            PickUpPicModel pick = new PickUpPicModel();
            pick.setPkid(p.get(key)[0]);
            pick.setPrefix(p.get(key)[1]);
            pick.setNo(p.get(key)[2]);
            pick.setExno(p.get(key)[3]);
            PickUpstore.add(pick);
        }

        setListener();
    }
    //endregion

    @Override
    public void finish() {
        Intent intent = new Intent();
        int num = 0;

        Integer req = (Integer) getIntent().getSerializableExtra("id");
        if (req == PickUpSignatureActivity_REQUEST) {
            intent.setClass(mContext, ImpPickUpActivity.class);
            num = AviationCommons.PickUpSignatureActivity_RESULT;
        }

        setResult(num,intent);
        super.finish();
    }

    //region activity界面回调事件
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PickUpSignatureActivity_REQUEST:
                if (resultCode == AviationCommons.PickUpSignatureActivity_RESULT) {
                    File f = new File(StoragePath + "/" + "card" +".jpg");

                    if (f.exists()) {
                        Bitmap bitmap = PublicFun.getLoacalBitmap(StoragePath + "/" + "card" +".jpg");
                        ImageView.setImageBitmap(bitmap);
                    } else {
                        ToastUtils.showToast(mContext, "照片拍摄未成功!", Toast.LENGTH_SHORT);
                    }
                }
                break;
            default:
                break;
        }
    }
    //endregion

    //endregion

    //region 控件事件

    //region 页面上所有的点击事件
    private void setListener() {
        //region 清除签名按钮
        Btn_qingchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinePathView_HuaBu.clear();
            }
        });
        //endregion

        //region 拍照按钮
        Btn_PaiZhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecognizeCardActivity.class);
                intent.putExtra("id", PickUpSignatureActivity_REQUEST);
                startActivityForResult(intent, PickUpSignatureActivity_REQUEST);
            }
        });
        //endregion

        //region 上传按钮
        Btn_Shangchuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (PickUpPicModel p:PickUpstore) {
                    UploadData mData = UploadDataUtils.getUploadData(p, AviationCommons.gnj_PickUpSignature_model , HttpCommons.gnj_PickUpSignature_update);
                    updateInfo(mData);
                }

            }
        });
        //endregion


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
            if (msg.what == GNJ_PickUpSignatureActivity) {
                if (Boolean.parseBoolean(ResultStr)) {
                    finish();
                }

            } else if (msg.what == GNJ_PickUpSignatureActivity_update) {
                if (intFlag == PickUpstore.size()) {
                    LinePathView_HuaBu.saveJPEG();
                    File mFile= new File(SignerjpegFile);
                    File cFile = new File(StoragePath + "/" + "card" +".jpg");

                    if (mFile.exists() && cFile.exists()) {
                        byte[] SignerJepgByte = PublicFun.FileToByte(SignerjpegFile);
                        String SignerJepgStr = PublicFun.EncodeBase64(SignerJepgByte);

                        byte[] CardJepgByte = PublicFun.FileToByte(CardjpegFile);
                        String CardJepgStr = PublicFun.EncodeBase64(CardJepgByte);

                        mFile.delete();
                        cFile.delete();

                        PickUpPicModel picMode = new PickUpPicModel();
                        picMode.setPkid(PickUpstore.get(0).getPkid());
                        picMode.setSigner(SignerJepgStr);
                        picMode.setIdcard(CardJepgStr);

                        UploadData mData = UploadDataUtils.getUploadData(picMode, AviationCommons.gnj_PickUpSignature_model , HttpCommons.gnj_PickUpSignature_save);
                        updatePic(mData);
                    } else {
                        ToastUtils.showToast(mContext, "未拍照或图片未找到!", Toast.LENGTH_SHORT);
                    }
                }
            }

            return false;
        }
    });
    //endregion


    //region 请求数据
    private void updatePic(UploadData p) {
        HttpRoot.getInstance().requstAync(mContext, p,
                new HttpRoot.CallBack() {
                    @Override
                    public void onSucess(Object result) {
                        Ldialog.dismiss();
                        ResData resData = (ResData) result;
                        ResultStr = resData.getData().toString();
                        mHandler.sendEmptyMessage(GNJ_PickUpSignatureActivity);

                    }

                    @Override
                    public void onFailed(String message) {
                        Ldialog.dismiss();
                        ToastUtils.showToast(mContext, message, Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onError() {
                        Ldialog.dismiss();
                        ToastUtils.showToast(mContext, "数据获取出错", Toast.LENGTH_SHORT);
                    }
                });

    }

    private void updateInfo(UploadData p) {
        HttpRoot.getInstance().requstAync(mContext, p,
                new HttpRoot.CallBack() {
                    @Override
                    public void onSucess(Object result) {
                        Ldialog.dismiss();
                        intFlag += 1;
                        ResData resData = (ResData) result;
                        ResultStr = resData.getData().toString();
                        mHandler.sendEmptyMessage(GNJ_PickUpSignatureActivity_update);

                    }

                    @Override
                    public void onFailed(String message) {
                        Ldialog.dismiss();
                        intFlag += 1;
                        ResultStr = "false";
                        mHandler.sendEmptyMessage(GNJ_PickUpSignatureActivity_update);
                    }

                    @Override
                    public void onError() {
                        Ldialog.dismiss();
                        intFlag += 1;
                        ResultStr = "false";
                        mHandler.sendEmptyMessage(GNJ_PickUpSignatureActivity_update);
                    }
                });
    }
    //endregion

    //region 文本框赋值

    //endregion

    //endregion

}
