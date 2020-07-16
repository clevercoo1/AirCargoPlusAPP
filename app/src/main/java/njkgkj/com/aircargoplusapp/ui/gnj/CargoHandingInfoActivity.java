package njkgkj.com.aircargoplusapp.ui.gnj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import njkgkj.com.aircargoplusapp.R;
import njkgkj.com.aircargoplusapp.Utils.AviationCommons;
import njkgkj.com.aircargoplusapp.Utils.PublicFun;
import njkgkj.com.aircargoplusapp.Utils.SerializableUtils;
import njkgkj.com.aircargoplusapp.Utils.ToastUtils;
import njkgkj.com.aircargoplusapp.Utils.UploadDataUtils;
import njkgkj.com.aircargoplusapp.http.HttpCommons;
import njkgkj.com.aircargoplusapp.http.HttpRoot;
import njkgkj.com.aircargoplusapp.model.ResData;
import njkgkj.com.aircargoplusapp.model.UploadData;
import njkgkj.com.aircargoplusapp.model.gnjm.CargoHandingApp;
import njkgkj.com.aircargoplusapp.ui.base.NavBar;
import njkgkj.com.aircargoplusapp.ui.dialog.LoadingDialog;

public class CargoHandingInfoActivity extends Activity {

    //region 自定义变量
    private Context mContext;
    private Activity mAct;
    private NavBar navBar;
    boolean bool;
    BigDecimal WeightDefualt;
    BigDecimal VolumeDefault;

    private final String TAG = "CargoHandingInfoLog";
    private CargoHandingApp cargoHandingApp;
    //endregion

    //region 未预设XML控件
    private LoadingDialog Ldialog;
    //endregion

    //region 其他控件
    @BindView(R.id.CargoHandingInfoBar)
    ViewGroup CargoHandingInfo_navBar;
    //endregion

    //region Layout控件
    @BindView(R.id.lay_CargoHandingInfo_12)
    LinearLayout Lay_TiJiao;
    //endregion

    //region Button控件
    @BindView(R.id.CargoHandingInfo_btn_QueDin)
    Button btn_QueDin;
    @BindView(R.id.CargoHandingInfo_btn_QuXiao)
    Button btn_QuXiao;
    //endregion

    //region EditText控件
    @BindView(R.id.chInfo_EdTxt_shidaojianshu)
    EditText EdTxt_shidaojianshu;
    @BindView(R.id.chInfo_EdTxt_shidaozhongliang)
    EditText EdTxt_shidaozhongliang;
    @BindView(R.id.chInfo_EdTxt_shidaozhongtiji)
    EditText EdTxt_shidaozhongtiji;
    @BindView(R.id.chInfo_EdTxt_cangkuhuowei)
    EditText EdTxt_cangkuhuowei;
    @BindView(R.id.chInfo_EdTxt_beizhu)
    EditText EdTxt_beizhu;
    //endregion

    //region 滚动View控件
    @BindView(R.id.CargoHandingInfo_Scrll)
    ScrollView ch_Scrll;
    //endregion

    //region TextView控件
    @BindView(R.id.chInfo_txt_hangbanriqi)
    TextView txt_hangbanriqi;
    @BindView(R.id.chInfo_txt_hangbanhao)
    TextView txt_hangbanhao;
    @BindView(R.id.chInfo_txt_shifagang)
    TextView txt_shifagang;
    @BindView(R.id.chInfo_txt_mudigang)
    TextView txt_mudigang;
    @BindView(R.id.chInfo_txt_dailiren)
    TextView txt_dailiren;
    @BindView(R.id.chInfo_txt_yundanhao)
    TextView txt_yundanhao;
    @BindView(R.id.chInfo_txt_pinming)
    TextView txt_pinming;
    @BindView(R.id.chInfo_txt_zongjianshu)
    TextView txt_zongjianshu;
    @BindView(R.id.chInfo_txt_Prefix)
    TextView txt_Prefix;
    //endregion

    //region 初始化

    //region 入口函数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargo_handing_info);
        mContext = CargoHandingInfoActivity.this;
        mAct = (Activity) mContext;
        ButterKnife.bind(this);
        initView();
    }
    //endregion

    //region 设置初始化
    private void initView() {
        cargoHandingApp = new CargoHandingApp();
        cargoHandingApp = (CargoHandingApp) getIntent().getSerializableExtra("Info");

        WeightDefualt = cargoHandingApp.getWeight().divide(new BigDecimal(cargoHandingApp.getPc()),2,BigDecimal.ROUND_HALF_UP);
        VolumeDefault = cargoHandingApp.getVolume().divide(new BigDecimal(cargoHandingApp.getPc()),2,BigDecimal.ROUND_HALF_UP);

        navBar = new NavBar(this);
        navBar.setTitle("舱单详情");
        navBar.setRight(R.drawable.bianji);

        Ldialog = new LoadingDialog(mContext);
        Lay_TiJiao.setVisibility(View.GONE);

        TxtViewSetEmpty();
        setListener();
        TextSetVaule();
    }
    //endregion

    //region 输入框置空
    private void TxtViewSetEmpty() {
        txt_hangbanriqi.setText("");
        txt_hangbanhao.setText("");
        txt_shifagang.setText("");
        txt_mudigang.setText("");
        txt_dailiren.setText("");
        txt_yundanhao.setText("");
        txt_pinming.setText("");
        txt_zongjianshu.setText("");
        txt_Prefix.setText("");

        EdTxt_shidaojianshu.setText("");
        EdTxt_shidaozhongliang.setText("");
        EdTxt_shidaozhongtiji.setText("");
        EdTxt_cangkuhuowei.setText("");
        EdTxt_beizhu.setText("");

    }
    //endregion

    //region finish前清空消息队列，并回收垃圾
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        System.gc();
    }
    //endregion

    //region 类的Intent

    //endregion

    //endregion

    //region 控件事件

    //region 页面上所有的点击事件
    private void setListener() {

        //region bar右侧编辑按钮点击事件
        navBar.getRightImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenWri();
                EdTxt_shidaojianshu.setSelection(EdTxt_shidaojianshu.getText().length());
            }
        });
        //endregion

        //region 确定按钮
        btn_QueDin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(EdTxt_shidaojianshu.getText().toString().trim())) {
                    ToastUtils.showToast(mContext, "实到件数不能为空！", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(EdTxt_shidaozhongtiji.getText().toString().trim())) {
                    ToastUtils.showToast(mContext, "实到体积不能为空！", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(EdTxt_shidaozhongliang.getText().toString().trim())) {
                    ToastUtils.showToast(mContext, "实到重量不能为空！", Toast.LENGTH_SHORT);
                    return;
                }

                CargoHandingApp chapp = (CargoHandingApp) SerializableUtils.copy(cargoHandingApp);
                ;
                chapp.setPc(Long.parseLong(EdTxt_shidaojianshu.getText().toString().trim()));
                chapp.setVolume(new BigDecimal(EdTxt_shidaozhongtiji.getText().toString().trim()));
                chapp.setWeight(new BigDecimal(EdTxt_shidaozhongliang.getText().toString().trim()));
                chapp.setWhslocation(EdTxt_cangkuhuowei.getText().toString().trim());
                chapp.setRemark(EdTxt_beizhu.getText().toString().trim());

                UploadData mData = UploadDataUtils.getUploadData(chapp, AviationCommons.gnj_CargoHandingActivity_model, HttpCommons.gnj_CargoHandingActivity_update);
                Ldialog.show();

                if (!TextUtils.isEmpty(mData.getMethod())) {
                    GetInfo(mData);
                } else {
                    ToastUtils.showToast(mContext, "数据转换错误！", Toast.LENGTH_SHORT);
                }
            }
        });
        //endregion

        //region 取消按钮
        btn_QuXiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloseWri();
            }
        });
        //endregion

        // region 实到重量变化联动
        EdTxt_shidaojianshu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString()) && StringUtils.isNumeric(s.toString()) && Integer.valueOf(s.toString()) > 0) {
                    EdTxt_shidaozhongtiji.setText(VolumeDefault.multiply(new BigDecimal(s.toString())).toString());
                    EdTxt_shidaozhongliang.setText(WeightDefualt.multiply(new BigDecimal(s.toString())).toString());
                } else {
                    EdTxt_shidaozhongtiji.setText("0");
                    EdTxt_shidaozhongliang.setText("0");
                }


            }
        });
        //endregion
    }

    //endregion

    //endregion

    //region 功能方法

    //region 打开编辑区
    private void OpenWri() {
        Lay_TiJiao.setVisibility(View.VISIBLE);

        EdTxt_shidaojianshu.setBackgroundResource(R.drawable.edit_bg);
        EdTxt_shidaojianshu.setFocusable(true);
        EdTxt_shidaojianshu.setFocusableInTouchMode(true);

        EdTxt_shidaozhongliang.setBackgroundResource(R.drawable.edit_bg);
        EdTxt_shidaozhongliang.setFocusable(true);
        EdTxt_shidaozhongliang.setFocusableInTouchMode(true);

        EdTxt_shidaozhongtiji.setBackgroundResource(R.drawable.edit_bg);
        EdTxt_shidaozhongtiji.setFocusable(true);
        EdTxt_shidaozhongtiji.setFocusableInTouchMode(true);

        EdTxt_cangkuhuowei.setBackgroundResource(R.drawable.edit_bg);
        EdTxt_cangkuhuowei.setFocusable(true);
        EdTxt_cangkuhuowei.setFocusableInTouchMode(true);

        EdTxt_beizhu.setBackgroundResource(R.drawable.edit_bg);
        EdTxt_beizhu.setFocusable(true);
        EdTxt_beizhu.setFocusableInTouchMode(true);

        PublicFun.ElementSwitch(CargoHandingInfo_navBar,false);

        handler.post(new Runnable() {
            @Override
            public void run() {
                ch_Scrll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
    //endregion

    //region 关闭编辑区
    private void CloseWri() {
        Lay_TiJiao.setVisibility(View.GONE);

        EdTxt_shidaojianshu.setBackgroundResource(0);
        EdTxt_shidaojianshu.setFocusable(false);
        EdTxt_shidaojianshu.setFocusableInTouchMode(false);

        EdTxt_shidaozhongliang.setBackgroundResource(0);
        EdTxt_shidaozhongliang.setFocusable(false);
        EdTxt_shidaozhongliang.setFocusableInTouchMode(false);

        EdTxt_shidaozhongtiji.setBackgroundResource(0);
        EdTxt_shidaozhongtiji.setFocusable(false);
        EdTxt_shidaozhongtiji.setFocusableInTouchMode(false);

        EdTxt_cangkuhuowei.setBackgroundResource(0);
        EdTxt_cangkuhuowei.setFocusable(false);
        EdTxt_cangkuhuowei.setFocusableInTouchMode(false);

        EdTxt_beizhu.setBackgroundResource(0);
        EdTxt_beizhu.setFocusable(false);
        EdTxt_beizhu.setFocusableInTouchMode(false);

        PublicFun.ElementSwitch(CargoHandingInfo_navBar,true);

        TextSetVaule();

        handler.post(new Runnable() {
            @Override
            public void run() {
                ch_Scrll.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }
    //endregion

    //region 句柄监听
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == AviationCommons.GNJ_CargoHandingInfoActivity) {
                if (bool) {
                    Integer req = (Integer) getIntent().getSerializableExtra("id");
                    Intent intent = new Intent(CargoHandingInfoActivity.this,CargoHandingActivity.class);
                    if (req == AviationCommons.CargoHandingInfoActivity_REQUEST) {
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("result",bool);

                        intent.putExtras(bundle);
                        setResult(AviationCommons.CargoHandingInfoActivity_RESULT, intent);
                        mAct.finish();
                    } else {
                        ToastUtils.showToast(mContext,"上层请求码错误，无法回调！",Toast.LENGTH_SHORT);
                    }
                } else {
                    ToastUtils.showToast(mContext,"返回值异常！",Toast.LENGTH_SHORT);
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
                        ResData resData = (ResData) result;
                        bool = Boolean.parseBoolean(resData.getData().toString());

                        handler.sendEmptyMessage(AviationCommons.GNJ_CargoHandingInfoActivity);
                        Ldialog.dismiss();
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
    private void TextSetVaule() {
        TxtViewSetEmpty();
        txt_hangbanriqi.setText(PublicFun.ObjectToString(cargoHandingApp.getFdate()).split(" ")[0]);
        txt_hangbanhao.setText(cargoHandingApp.getFno());
        txt_shifagang.setText(cargoHandingApp.getOrigin());
        txt_mudigang.setText(cargoHandingApp.getDest());
        txt_dailiren.setText(cargoHandingApp.getAgent());
        txt_yundanhao.setText(cargoHandingApp.getNo());
        txt_pinming.setText(cargoHandingApp.getGoods());
        txt_zongjianshu.setText(cargoHandingApp.getAwbPC().toString());
        txt_Prefix.setText(cargoHandingApp.getPrefix());
        EdTxt_shidaojianshu.setText(cargoHandingApp.getPc().toString());
        EdTxt_shidaozhongliang.setText(cargoHandingApp.getWeight().toString());
        EdTxt_shidaozhongtiji.setText(cargoHandingApp.getVolume().toString());
        EdTxt_cangkuhuowei.setText(cargoHandingApp.getWhslocation());
        EdTxt_beizhu.setText(cargoHandingApp.getRemark());
    }
    //endregion

    //endregion

}
