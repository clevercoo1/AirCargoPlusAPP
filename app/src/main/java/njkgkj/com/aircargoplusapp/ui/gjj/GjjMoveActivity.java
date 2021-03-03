package njkgkj.com.aircargoplusapp.ui.gjj;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ReplacementTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.io.File;
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
import njkgkj.com.aircargoplusapp.model.gjjm.GjjMoveModel;
import njkgkj.com.aircargoplusapp.model.gnjm.CargoHandingApp;
import njkgkj.com.aircargoplusapp.ui.base.BaseDetailsActivity;
import njkgkj.com.aircargoplusapp.ui.base.CaptureActivity;
import njkgkj.com.aircargoplusapp.ui.base.NavBar;
import njkgkj.com.aircargoplusapp.ui.dialog.LoadingDialog;
import njkgkj.com.aircargoplusapp.view.AutofitTextView;

import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.CargoHandingActivity_CAMERA_REQUEST;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.GJJ_GjjMoveActivity_Load;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.GNJ_CargoHandingActivity;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.GjjMoveActivity_CAMERA_REQUEST;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.PickUpSignatureActivity_REQUEST;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.StoragePath;

public class GjjMoveActivity extends Activity {
    //region 自定义变量
    private Context mContext;
    private Activity mAct;
    private NavBar navBar;
    private List<View> views;
    private final String TAG = "GjjMoveActivity";
    private List<GjjMoveModel> gjjMoveModelList;
    private GjjMoveAdapter myAdapter;
    private HashMap<String,String> MoveStore = new HashMap();
    //endregion

    //region 未预设XML控件
    private LoadingDialog Ldialog;
    private QMUITipDialog tipDialog;
    //endregion

    //region 其他控件
    @BindView(R.id.GjjMove_pulltorefreeshviw)
    SwipeRefreshLayout pulltorefreshview;
    //endregion

    //region Layout控件

    //endregion

    //region Button控件
    @BindView(R.id.GjjMove_Btn_chaxun)
    Button Btn_chaxun;
    @BindView(R.id.GjjMove_Btn_shencheng)
    Button Btn_shencheng;
    //endregion

    //region EditText控件
    @BindView(R.id.GjjMove_EdTxt_AgentCode)
    EditText EdTxt_AgentCode;
    @BindView(R.id.GjjMove_EdTxt_YunDanHao)
    EditText EdTxt_YunDanHao;
    //endregion

    //region 滚动View控件
    @BindView(R.id.GjjMove_in_home_lv)
    ListView GjjMoveLv;
    //endregion

    //region TextView控件
    @BindView(R.id.GjjMove_txt_YunDanLeiXin)
    AutofitTextView txt_YunDanLeiXin;
    //endregion

    //region ImgView控件

    //endregion

    //region 初始化

    //region 入口函数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gjj_move);

        mContext = GjjMoveActivity.this;
        mAct = PublicFun.getActivityByContext(mContext);
        ButterKnife.bind(this);
        initView();
    }
    //endregion

    //region 设置初始化
    private void initView() {
        navBar = new NavBar(this);
        navBar.setTitle("国际进港移库");
        navBar.setRight(R.drawable.saoma_two);

        Ldialog = new LoadingDialog(mContext);
        gjjMoveModelList = new ArrayList<>();

        //设置下拉的距离和动画颜色
        pulltorefreshview.setProgressViewEndTarget (true,100);
        pulltorefreshview.setDistanceToTriggerSync(150);
        pulltorefreshview.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);


        TxtViewSetEmpty();
        setListener();

        views = new ArrayList<View>();
        views.add(EdTxt_YunDanHao);
        views.add(EdTxt_AgentCode);
    }
    //endregion


    //region 输入框置空
    private void TxtViewSetEmpty() {
        EdTxt_YunDanHao.setText("");
        EdTxt_AgentCode.setText("");
        txt_YunDanLeiXin.setText("转国内出港");
    }
    //endregion

    //region 类的Intent

    //endregion

    //endregion

    //region 控件事件

    //region 页面上所有的控件事件
    private void setListener() {
        //region 下拉刷新
        pulltorefreshview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GjjMoveModel serEntity = new GjjMoveModel();
                if (txt_YunDanLeiXin.getText().toString().trim().equals("转国内出港")) {
                    serEntity.setAwbtype("IICO");
                } else if (txt_YunDanLeiXin.getText().toString().trim().equals("转国际出港")) {
                    serEntity.setAwbtype("IIIO");
                } else {
                    ToastUtils.showToast(mContext,"请选择正确的‘运单类型’！", Toast.LENGTH_SHORT);
                    return;
                }

                UploadData mData = UploadDataUtils.getUploadData(serEntity, AviationCommons.gjj_Move_model, HttpCommons.gjj_Move_load);
                GetInfo(mData);

                pulltorefreshview.setRefreshing(false);
            }
        });
        //endregion

        //region 扫码
        navBar.getRightImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useCamera();
            }
        });
        //endregion

        //region 查询按钮
        Btn_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublicFun.KeyBoardHide(mContext,views);
                GjjMoveModel serEntity = new GjjMoveModel();
                if (txt_YunDanLeiXin.getText().toString().trim().equals("转国内出港")) {
                    serEntity.setAwbtype("IICO");
                } else if (txt_YunDanLeiXin.getText().toString().trim().equals("转国际出港")) {
                    serEntity.setAwbtype("IIIO");
                } else {
                    ToastUtils.showToast(mContext,"请选择正确的‘运单类型’！", Toast.LENGTH_SHORT);
                    return;
                }
                serEntity.setCarrier(EdTxt_AgentCode.getText().toString().toUpperCase().trim());

                String awbno = EdTxt_YunDanHao.getText().toString().trim();
                if (awbno.length() > 0 && awbno.length() < 4 ) {
                    ToastUtils.showToast(mContext,"运单号不少于后4位!", Toast.LENGTH_SHORT);
                    return;
                }

                if (awbno.length() == 11) {
                    serEntity.setPrefix(awbno.substring(0, 3));
                    serEntity.setNo(awbno.substring(3));
                } else {
                    serEntity.setNo(awbno);
                }

                UploadData mData = UploadDataUtils.getUploadData(serEntity, AviationCommons.gjj_Move_model, HttpCommons.gjj_Move_load);
                GetInfo(mData);

                EdTxt_YunDanHao.setText("");
                EdTxt_AgentCode.setText("");
            }
        });
        //endregion

        //region 生成二维码
        Btn_shencheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MoveStore.size() > 0) {
                    Intent intent = new Intent(mContext, GjjMoveQRcodeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Info", MoveStore);
                    if (txt_YunDanLeiXin.getText().toString().toString().trim().equals("转国内出港")) {
                        bundle.putSerializable("MoveType", AviationCommons.Gjj_To_Gnc);
                    } else if (txt_YunDanLeiXin.getText().toString().toString().trim().equals("转国际出港")) {
                        bundle.putSerializable("MoveType", AviationCommons.Gjj_To_Gjc);
                    } else {
                        ToastUtils.showToast(mContext,"移库类型解析错误!", Toast.LENGTH_SHORT);
                        return;
                    }

                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    ToastUtils.showToast(mContext,"请勾选需要移库的条目!", Toast.LENGTH_SHORT);
                }

            }
        });
        //endregion

        //region 运单类型点击
        txt_YunDanLeiXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = {"转国内出港", "转国际出港"};
                new QMUIDialog.CheckableDialogBuilder(mAct)
                        .addItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                txt_YunDanLeiXin.setText(items[which]);
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        //endregion

        //region 代理人代码自动变大写
        EdTxt_AgentCode.setTransformationMethod(new ReplacementTransformationMethod() {
            @Override
            protected char[] getOriginal() {
                char[] originalCharArr = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z' };
                return originalCharArr;
            }

            @Override
            protected char[] getReplacement() {
                char[] replacementCharArr = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z' };
                return replacementCharArr;
            }
        });
        //endregion

    }
    //endregion

    //endregion

    //region 功能方法

    //region 调用相机
    private void useCamera() {
        Intent intent = new Intent(mContext, CaptureActivity.class);
        intent.putExtra("id", GjjMoveActivity_CAMERA_REQUEST);
        startActivityForResult(intent, GjjMoveActivity_CAMERA_REQUEST);
    }
    //endregion

    //region 句柄监听
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == GJJ_GjjMoveActivity_Load) {
                MoveStore.clear();

                if (gjjMoveModelList.size() == 0) {
                    GjjMoveLv.setAdapter(null);
                } else {
                    myAdapter = new GjjMoveAdapter(gjjMoveModelList,mContext);
                    GjjMoveLv.setAdapter(myAdapter);
                }
            }
            return false;
        }
    });
    //endregion

    //region 请求数据
    private void GetInfo(UploadData p) {
        Ldialog.show();

        HttpRoot.getInstance().requstAync(mContext, p,
                new HttpRoot.CallBack() {
                    @Override
                    public void onSucess(Object result) {
                        Ldialog.dismiss();
                        ResData resData = (ResData) result;
                        String json = resData.getData().toString();
                        gjjMoveModelList = FastjsonUtils.fromJsonList(json, GjjMoveModel.class);
                        mHandler.sendEmptyMessage(GJJ_GjjMoveActivity_Load);

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

    //region activity界面回调事件
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle bundle = new Bundle();
        String re = "";

        switch (requestCode) {
            case AviationCommons.GjjMoveActivity_CAMERA_REQUEST:
                if (resultCode == AviationCommons.GjjMoveActivity_CAMERA_RESULT) {
                    bundle = data.getExtras();
                    re = bundle.getString("result");
                    List<GjjMoveModel> mList = new ArrayList<GjjMoveModel>();
                    String[] strings = re.split("_");

                    if (TextUtils.isEmpty(re)) {
                        return;
                    }

                    if (re.contains("_")) {
                        for(String str:strings ){
                            if (str.length() == 11) {
                                GjjMoveModel gjjm = new GjjMoveModel();
                                gjjm.setPrefix(str.substring(0,3));
                                gjjm.setNo(str.substring(3));
                                mList.add(gjjm);
                            }
                        }

                        if (mList.size() == strings.length - 1) {
                            CargoToMove(mList,strings[0]);
                        } else {
                            ToastUtils.showToast(mContext, "运单号解析错误！", Toast.LENGTH_SHORT);
                        }
                    } else {
                        ToastUtils.showToast(mContext, "二维码解析错误！", Toast.LENGTH_SHORT);
                    }
                }
                break;
            default:
                break;
        }
    }
    //endregion

    //region 移库上传
    private void CargoToMove(List<GjjMoveModel> mList,String MoveType) {
        String method = "";

        if (MoveType.equals(AviationCommons.Gnj_To_Gjj)) {
            method = HttpCommons.gnj_Move_toMove;
        } else {
            ToastUtils.showToast(mContext,"非移库到国际进港的货物!", Toast.LENGTH_SHORT);
            return;
        }

        UploadData mData = UploadDataUtils.getUploadData(mList, AviationCommons.gjj_Move_modelList, method);

        Ldialog.show();
        HttpRoot.getInstance().requstAync(mContext, mData,
                new HttpRoot.CallBack() {
                    @Override
                    public void onSucess(Object result) {
                        Ldialog.dismiss();
                        tipDialog = new QMUITipDialog.Builder(mContext)
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                                .setTipWord("移库成功")
                                .create();

                        tipDialog.show();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tipDialog.dismiss();
                                Btn_chaxun.performClick();
                            }
                        }, 900);

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

    //endregion

    //region 数据适配器
    private class GjjMoveAdapter extends BaseAdapter {
        private List<GjjMoveModel> GjjMoveInfoList;
        private Context context;

        public GjjMoveAdapter(List<GjjMoveModel> gjjMoveInfoList, Context context) {
            GjjMoveInfoList = gjjMoveInfoList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return GjjMoveInfoList.size();
        }

        @Override
        public GjjMoveModel getItem(int position) {
            return GjjMoveInfoList.get(position);
        }

        public void remove(int pos) {
            if (GjjMoveInfoList != null && GjjMoveInfoList.size() > 0) {
                GjjMoveInfoList.remove(pos);
                notifyDataSetChanged();
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            final GjjMoveModel gmove = (GjjMoveModel) getItem(position);
            final int tip = position;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.gjjmove_search_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.cb = (CheckBox) convertView.findViewById(R.id.GjjMove_checkbox);
                viewHolder.inTxt_yundanhao = (AutofitTextView) convertView.findViewById(R.id.GjjMove_inTxt_yundanhao);
                viewHolder.inTxt_tema = (AutofitTextView) convertView.findViewById(R.id.GjjMove_inTxt_tema);
                viewHolder.inTxt_daili = (AutofitTextView) convertView.findViewById(R.id.GjjMove_inTxt_daili);
                viewHolder.inTxt_hangban = (AutofitTextView) convertView.findViewById(R.id.GjjMove_inTxt_hangban);
                viewHolder.showLy = (LinearLayout) convertView.findViewById(R.id.GjjMove_in_layout);
                viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        String ke = gmove.getAwbno().toString().replace("-", "").replace("P", "").trim();
                        if (isChecked) {
                            boolean flag = MoveStore.containsKey(ke);
                            if (!flag) {
                                MoveStore.put(ke, "");
                            }
                        } else {
                            MoveStore.put(ke, "");
                        }
                    }
                });

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final String awbno = GjjMoveInfoList.get(position).getAwbno();
            if (!TextUtils.isEmpty(awbno)) {
                viewHolder.inTxt_yundanhao.setText(awbno);
            } else {
                viewHolder.inTxt_yundanhao.setText("");
            }
            String spcode = GjjMoveInfoList.get(position).getSpCode();
            if (!TextUtils.isEmpty(spcode)) {
                viewHolder.inTxt_tema.setText(spcode);
            } else {
                viewHolder.inTxt_tema.setText("");
            }
            String agent = GjjMoveInfoList.get(position).getAgent();
            if (!TextUtils.isEmpty(agent)) {
                viewHolder.inTxt_daili.setText(agent);
            } else {
                viewHolder.inTxt_daili.setText("");
            }

            String flightno = GjjMoveInfoList.get(position).getFlight();
            if (!TextUtils.isEmpty(flightno)) {
                viewHolder.inTxt_hangban.setText(flightno);
            } else {
                viewHolder.inTxt_hangban.setText("");
            }


            viewHolder.showLy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GjjMoveModel gjjMoveModel = getItem(tip);
                    Intent intent = new Intent(mContext, BaseDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Details", gjjMoveModel);
                    bundle.putSerializable("ModelType", "GjjMove");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            return convertView;
        }

        class ViewHolder {
            CheckBox cb;
            AutofitTextView inTxt_yundanhao;
            AutofitTextView inTxt_tema;
            AutofitTextView inTxt_daili;
            AutofitTextView inTxt_hangban;
            LinearLayout showLy;
        }
    }
    //endregion
}
