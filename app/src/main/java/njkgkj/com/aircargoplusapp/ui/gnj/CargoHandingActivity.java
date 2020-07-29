package njkgkj.com.aircargoplusapp.ui.gnj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import njkgkj.com.aircargoplusapp.ui.base.CaptureActivity;
import njkgkj.com.aircargoplusapp.ui.base.NavBar;
import njkgkj.com.aircargoplusapp.ui.dialog.LoadingDialog;

import static android.R.style.Widget;
import static com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView.newSection;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.CargoHandingActivity_CAMERA_REQUEST;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.CargoHandingActivity_CAMERA_RESULT;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.GNJ_CargoHandingActivity;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.gnj_CargoHandingActivity_model;

public class CargoHandingActivity extends Activity {
    //region 自定义变量
    private Context mContext;
    private Activity mAct;
    private NavBar navBar;
    private List<View> views;
    private final String TAG = "CargoHandingLog";
    private List<CargoHandingApp> cargoHandingApps;
    //endregion

    //region 未预设XML控件
    private LoadingDialog Ldialog;
    private QMUITipDialog tipDialog;
    //endregion

    //region 其他控件
    @BindView(R.id.CargoHandingActivity_groupListView)
    QMUIGroupListView mGroupListView;
    //endregion

    //region Layout控件

    //endregion


    //region Button控件
    @BindView(R.id.CargoHanding_Btn_ChaXun)
    Button Btn_ChaXun;
    @BindView(R.id.CargoHanding_Btn_QinKong)
    Button Btn_QinKong;
    @BindView(R.id.CargoHanding_Btn_SaoMiao)
    Button Btn_SaoMiao;
    //endregion

    //region EditText控件
    @BindView(R.id.CargoHanding_EdTxt_YunDanHao)
    EditText EdTxt_YunDanHao;
    //endregion

    //region 滚动View控件

    //endregion

    //region TextView控件

    //endregion

    //region 初始化

    //region 入口函数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargo_handing);
        mContext = CargoHandingActivity.this;
        mAct = PublicFun.getActivityByContext(mContext);
        ButterKnife.bind(this);
        initView();
//        setContentView(root);
    }
    //endregion

    //region 设置初始化
    private void initView() {
        navBar = new NavBar(this);
        navBar.setTitle("国内进港理货");

        Ldialog = new LoadingDialog(mContext);
        cargoHandingApps = new ArrayList<>();

        TxtViewSetEmpty();
        setListener();

        views = new ArrayList<View>();
        views.add(EdTxt_YunDanHao);
    }
    //endregion

    //region 输入框置空
    private void TxtViewSetEmpty() {
        EdTxt_YunDanHao.setText("");
    }
    //endregion

    //region 类的Intent

    //endregion

    //endregion

    //region 控件事件

    //region 其他界面返回值处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle bundle = new Bundle();
        String re = "";
        switch (requestCode) {
            case CargoHandingActivity_CAMERA_REQUEST:
                if (resultCode == AviationCommons.CargoHandingActivity_CAMERA_RESULT) {
                    bundle = data.getExtras();
                    re = bundle.getString("result");

                    if (!TextUtils.isEmpty(re)) {
                        Btn_QinKong.performClick();
                        PublicFun.KeyBoardHide(mAct, views);
                        EdTxt_YunDanHao.setText(re);
                        EdTxt_YunDanHao.setSelection(re.length());
                        Btn_ChaXun.performClick();
                    }
                }


                break;
            case AviationCommons.CargoHandingInfoActivity_REQUEST:
                if (resultCode == AviationCommons.CargoHandingInfoActivity_RESULT) {
                    bundle = data.getExtras();
                    if (bundle.getBoolean("result")) {
                        tipDialog = new QMUITipDialog.Builder(mContext)
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                                .setTipWord("理货成功")
                                .create();

                        tipDialog.show();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tipDialog.dismiss();
                            }
                        }, 1500);
                    }
                }

                break;
        }
    }
    //endregion


    //region 页面上所有的点击事件
    private void setListener() {
        Btn_ChaXun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargoHandingApp cargoHandingApp = new CargoHandingApp();
                mGroupListView.removeAllViews();
                String YunDanHao = EdTxt_YunDanHao.getText().toString().trim();

                if (!TextUtils.isEmpty(YunDanHao)) {
                    cargoHandingApp.setNo(YunDanHao);
                    UploadData mData = UploadDataUtils.getUploadData(cargoHandingApp, AviationCommons.gnj_CargoHandingActivity_model, HttpCommons.gnj_CargoHandingActivity_load);
                    Ldialog.show();

                    if (!TextUtils.isEmpty(mData.getMethod())) {
                        GetInfo(mData);
                    } else {
                        ToastUtils.showToast(mContext, "数据转换错误！", Toast.LENGTH_SHORT);
                    }

                } else {
                    ToastUtils.showToast(mContext, "请填写运单号！", Toast.LENGTH_SHORT);
                }
            }
        });

        Btn_QinKong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TxtViewSetEmpty();
                mGroupListView.removeAllViews();
            }
        });

        Btn_SaoMiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useCamera();
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
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == GNJ_CargoHandingActivity) {
                if (cargoHandingApps != null && cargoHandingApps.size() > 0) {

                    View.OnClickListener onClickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (v instanceof QMUICommonListItemView) {
                                String mFno = ((QMUICommonListItemView) v).getText().toString();
                                String mPrefix = ((QMUICommonListItemView) v).getDetailText().toString().split("-")[0];
                                String mNo = ((QMUICommonListItemView) v).getDetailText().toString().split("-")[1];

                                for (CargoHandingApp ch:cargoHandingApps) {
                                    if (!TextUtils.isEmpty(ch.getFno()) && !TextUtils.isEmpty(ch.getPrefix()) && !TextUtils.isEmpty(ch.getNo())) {
                                        if (ch.getFno().equals(mFno) && ch.getPrefix().equals(mPrefix) && ch.getNo().equals(mNo)) {
                                            Bundle mBundle = new Bundle();
                                            mBundle.putSerializable("Info", ch);
                                            mBundle.putInt("id",AviationCommons.CargoHandingInfoActivity_REQUEST);

                                            Intent intent = new Intent(mContext, CargoHandingInfoActivity.class);
                                            intent.putExtras(mBundle);
                                            startActivityForResult(intent,AviationCommons.CargoHandingInfoActivity_REQUEST);
                                        }
                                    }
                                }
                            }
                        }
                    };

                    for (int i = 0;i < cargoHandingApps.size();i++) {
                        QMUICommonListItemView qmuiListItem = mGroupListView.createItemView(cargoHandingApps.get(i).getFno());
                        qmuiListItem.setOrientation(QMUICommonListItemView.VERTICAL);
                        qmuiListItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
                        qmuiListItem.setDetailText(cargoHandingApps.get(i).getPrefix() + "-" + cargoHandingApps.get(i).getNo());
                        QMUIGroupListView.newSection(mContext).addItemView(qmuiListItem, onClickListener).addTo(mGroupListView);
                    }


                } else {
                    ToastUtils.showToast(mContext, "数据不存在或已发放！", Toast.LENGTH_SHORT);
                }
            }
            return false;
        }
    });
    //endregion

    //region 请求数据
    private void GetInfo(UploadData p) {
        HttpRoot.getInstance().requstAync(mContext, p,
                new HttpRoot.CallBack() {
                    @Override
                    public void onSucess(Object result) {
                        Ldialog.dismiss();
                        ResData resData = (ResData) result;
                        String json = resData.getData().toString();
                        cargoHandingApps = FastjsonUtils.fromJsonList(json, CargoHandingApp.class);
                        handler.sendEmptyMessage(GNJ_CargoHandingActivity);

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
    //endregion

    //region 文本框赋值

    //endregion

    //region 调用相机
    private void useCamera() {
        Intent intent = new Intent(mContext, CaptureActivity.class);
        intent.putExtra("id", CargoHandingActivity_CAMERA_REQUEST);
        startActivityForResult(intent, CargoHandingActivity_CAMERA_REQUEST);
    }
    //endregion

    //endregion
}
