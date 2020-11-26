package njkgkj.com.aircargoplusapp.ui.gnj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import njkgkj.com.aircargoplusapp.R;
import njkgkj.com.aircargoplusapp.Utils.AviationCommons;
import njkgkj.com.aircargoplusapp.Utils.FastjsonUtils;
import njkgkj.com.aircargoplusapp.Utils.PreferenceUtils;
import njkgkj.com.aircargoplusapp.Utils.PublicFun;
import njkgkj.com.aircargoplusapp.Utils.ToastUtils;
import njkgkj.com.aircargoplusapp.Utils.UploadDataUtils;
import njkgkj.com.aircargoplusapp.http.HttpCommons;
import njkgkj.com.aircargoplusapp.http.HttpRoot;
import njkgkj.com.aircargoplusapp.model.ResData;
import njkgkj.com.aircargoplusapp.model.UploadData;
import njkgkj.com.aircargoplusapp.model.gnjm.CargoHandingApp;
import njkgkj.com.aircargoplusapp.model.gnjm.ImpPickUpAppModel;
import njkgkj.com.aircargoplusapp.tools.TanChuPaiXu.DragSortDialog;
import njkgkj.com.aircargoplusapp.tools.freegridview.AbsCommonAdapter;
import njkgkj.com.aircargoplusapp.tools.freegridview.AbsViewHolder;
import njkgkj.com.aircargoplusapp.tools.freegridview.SyncHorizontalScrollView;
import njkgkj.com.aircargoplusapp.tools.freegridview.TableModel;
import njkgkj.com.aircargoplusapp.ui.base.CaptureActivity;
import njkgkj.com.aircargoplusapp.ui.base.NavBar;
import njkgkj.com.aircargoplusapp.ui.dialog.LoadingDialog;

import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.CargoHandingActivity_CAMERA_REQUEST;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.FILE_NAME;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.GNJ_ImpPickUpActivity;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.ImpPickUp_CAMERA_REQUEST;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.LANGUAGE_FILE_NAME;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.LANGUAGE_NAME;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.PickUpSignatureActivity_REQUEST;

public class ImpPickUpActivity extends Activity {

    //region 自定义变量
    private Context mContext;
    private Activity mAct;
    private NavBar navBar;
    private List<View> views;
    private final String TAG = "ImpPickUpLog";
    private List<ImpPickUpAppModel> PickUpAppModels;
    private String text = "";
    private AbsCommonAdapter<TableModel> mLeftAdapter, mRightAdapter;
    private SparseArray<TextView> mTitleTvArray;
    private HashMap<String,String[]> PickUpstore = new HashMap();

    //endregion

    //region 未预设XML控件

    //endregion

    //region 其他控件
    private LoadingDialog Ldialog;
    private QMUITipDialog tipDialog;
    private  DragSortDialog dialog;

    @BindView(R.id.ImpPickUp_left_container_listview)
    ListView leftListView;
    @BindView(R.id.ImpPickUp_right_container_listview)
    ListView rightListView;
    //endregion

    //region Layout控件

    //endregion

    //region ImgView控件
    @BindView(R.id.ImpPickUp_Img_SaoMaYunDan)
    AppCompatImageView Img_SaoMaYunDan;
    //endregion

    //region Button控件
    @BindView(R.id.ImpPickUp_Btn_ChaXun)
    Button Btn_ChaXun;
    @BindView(R.id.ImpPickUp_Btn_QinKong)
    Button Btn_QinKong;
    @BindView(R.id.ImpPickUp_Btn_TiQu)
    Button Btn_TiQu;
    //endregion

    //region EditText控件
    @BindView(R.id.ImpPickUp_EdTxt_ShenFenZheng)
    EditText EdTxt_ShenFenZheng;
    @BindView(R.id.ImpPickUp_EdTxt_YunDanHao)
    EditText EdTxt_YunDanHao;
    //endregion

    //region 滚动View控件
    @BindView(R.id.ImpPickUp_pulltorefreshview)
    SwipeRefreshLayout pulltorefreshview;
    @BindView(R.id.ImpPickUp_title_horsv)
    SyncHorizontalScrollView titleHorScv;
    @BindView(R.id.ImpPickUp_content_horsv)
    SyncHorizontalScrollView contentHorScv;
    //endregion

    //region TextView控件
    @BindView(R.id.ImpPickUp_tv_table_title_left)
    TextView txt_RightTitle;
    //endregion

    //region 初始化

    //region 入口函数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imp_pick_up);

        mContext = ImpPickUpActivity.this;
        mAct = PublicFun.getActivityByContext(mContext);
        ButterKnife.bind(this);
        initView();
    }
    //endregion

    //region 设置初始化
    private void initView() {
        navBar = new NavBar(this);
        navBar.setTitle("国内进港提取");

        Ldialog = new LoadingDialog(mContext);
        dialog = new DragSortDialog(mContext);

        String  model = android.os.Build.MODEL;
        if (model.equals("HDN-L09")){dialog.setTxSize(8.0f);}

        PickUpAppModels = new ArrayList<>();

        txt_RightTitle.setText("提货编号");

        //设置下拉的距离和动画颜色
        pulltorefreshview.setProgressViewEndTarget (true,100);
        pulltorefreshview.setDistanceToTriggerSync(150);
        pulltorefreshview.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.ImpPickUp_right_title_container);
        layoutInflater.inflate(R.layout.gnjpickup_right_title,linearLayout,true);


        // 设置两个水平控件的联动
        titleHorScv.setScrollView(contentHorScv);
        contentHorScv.setScrollView(titleHorScv);

        findTitleTextViewIds();
        initTableView();
        TxtViewSetEmpty();
        clearTableView();
        setListener();

        views = new ArrayList<View>();
        views.add(EdTxt_YunDanHao);
        views.add(EdTxt_ShenFenZheng);
    }
    //endregion

    //region 输入框置空
    private void TxtViewSetEmpty() {
        EdTxt_ShenFenZheng.setText("");
        EdTxt_YunDanHao.setText("");
    }
    //endregion

    //region 清空tableview
    private void clearTableView(){
        mLeftAdapter.clearData(true);
        mRightAdapter.clearData(true);
        PickUpstore.clear();
        titleHorScv.scrollTo(0,0);
    }
    //endregion

    //region 利用反射初始化标题的TextView的item引用
    private void findTitleTextViewIds() {
        mTitleTvArray = new SparseArray<>();
        for (int i = 0; i < 11; i++) {
            try {
                Field field = R.id.class.getField("ImpPickUp_tv_table_title_" + i);
                int key = field.getInt(new R.id());
                TextView textView = (TextView) findViewById(key);

                mTitleTvArray.put(key, textView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
    //endregion

    //region 初始化表格的view
    private void initTableView() {
        mLeftAdapter = new AbsCommonAdapter<TableModel>(mContext, R.layout.table_left_item) {
            @Override
            public void convert(AbsViewHolder helper, TableModel item, int pos) {
                TextView tv_table_content_left = helper.getView(R.id.tv_table_content_item_left);
                CheckBox cb = helper.getView(R.id.item_cb);
                tv_table_content_left.setText(item.getLeftTitle());
                cb.setChecked(false);
            }
        };

        mRightAdapter = new AbsCommonAdapter<TableModel>(mContext, R.layout.gnjpickup_right_item) {
            @Override
            public void convert(AbsViewHolder helper, final TableModel item, int pos) {
                TextView tv_table_content_right_item0 = helper.getView(R.id.ImpPickUp_tv_table_content_right_item0);
                TextView tv_table_content_right_item1 = helper.getView(R.id.ImpPickUp_tv_table_content_right_item1);
                TextView tv_table_content_right_item2 = helper.getView(R.id.ImpPickUp_tv_table_content_right_item2);
                TextView tv_table_content_right_item3 = helper.getView(R.id.ImpPickUp_tv_table_content_right_item3);
                TextView tv_table_content_right_item4 = helper.getView(R.id.ImpPickUp_tv_table_content_right_item4);
                TextView tv_table_content_right_item5 = helper.getView(R.id.ImpPickUp_tv_table_content_right_item5);
                TextView tv_table_content_right_item6 = helper.getView(R.id.ImpPickUp_tv_table_content_right_item6);
                TextView tv_table_content_right_item7 = helper.getView(R.id.ImpPickUp_tv_table_content_right_item7);
                TextView tv_table_content_right_item8 = helper.getView(R.id.ImpPickUp_tv_table_content_right_item8);
                TextView tv_table_content_right_item9 = helper.getView(R.id.ImpPickUp_tv_table_content_right_item9);
                TextView tv_table_content_right_item10 = helper.getView(R.id.ImpPickUp_tv_table_content_right_item10);

                tv_table_content_right_item0.setText(item.getText0());
                tv_table_content_right_item1.setText(item.getText1());
                tv_table_content_right_item2.setText(item.getText2());
                tv_table_content_right_item3.setText(item.getText3());
                tv_table_content_right_item4.setText(item.getText4());
                tv_table_content_right_item5.setText(item.getText5());
                tv_table_content_right_item6.setText(item.getText6());
                tv_table_content_right_item7.setText(item.getText7());
                tv_table_content_right_item8.setText(item.getText8());
                tv_table_content_right_item9.setText(item.getText9());
                tv_table_content_right_item10.setText(item.getText10());

                for (int i = 0; i < 11; i++) {
                    View view = ((LinearLayout) helper.getConvertView()).getChildAt(i);
                    view.setVisibility(View.VISIBLE);
                }
            }
        };

        leftListView.setAdapter(mLeftAdapter);
        rightListView.setAdapter(mRightAdapter);
    }
    //endregion

    //region 类的Intent

    //endregion

    //endregion

    //region 控件事件

    //region 页面上所有的点击事件
    private void setListener() {

        //region 查询按钮
        Btn_ChaXun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImpPickUpAppModel ipum = new ImpPickUpAppModel();
                String ydh = EdTxt_YunDanHao.getText().toString().trim();
                if (TextUtils.isEmpty(EdTxt_YunDanHao.getText().toString().trim()) && TextUtils.isEmpty(EdTxt_ShenFenZheng.getText().toString().trim())) {
                    ToastUtils.showToast(mContext, "请填写查询条件！", Toast.LENGTH_SHORT);
                } else {
                    ipum.setPrefix(PublicFun.getYunDanHao(ydh)[0]);
                    ipum.setNo(PublicFun.getYunDanHao(ydh)[1]);
                    ipum.setDlvNumber(EdTxt_ShenFenZheng.getText().toString().trim());
                    ipum.setIsPickUp("0");
                    UploadData mData = UploadDataUtils.getUploadData(ipum, AviationCommons.gnj_PickUP_model, HttpCommons.gnj_ImpPickUp_load);

                    if (!TextUtils.isEmpty(mData.getMethod())) {
                        Ldialog.show();
                        GetInfo(mData);
                    } else {
                        ToastUtils.showToast(mContext, "数据转换错误！", Toast.LENGTH_SHORT);
                    }
                }
            }
        });
        //endregion

        //region 左侧标题列点击转跳事件
        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mAct != null){
                    CheckBox checkBox = (CheckBox) view.findViewById(R.id.item_cb);
                    TableModel ta = (TableModel) parent.getItemAtPosition(position);
                    String ke = ta.getLeftTitle().toString() + "/" + ta.getText0().toString() + ta.getText1().toString() + ta.getText2().toString();
                    if (checkBox.isChecked()) {
                        checkBox.setChecked(false);
                        PickUpstore.remove(ke);
                    } else {
                        checkBox.setChecked(true);
                        boolean flag = PickUpstore.containsKey(ke);
                        if (!flag) {
                            PickUpstore.put(ke,new String[]{ta.getLeftTitle().toString(),ta.getText0().toString(),ta.getText1().toString(),ta.getText2().toString()});
                        }
                    }
                }
            }
        });
        //rendregion

        //region 清空按钮
        Btn_QinKong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TxtViewSetEmpty();
                clearTableView();
            }
        });
        //endregion

        //region 提取按钮
        Btn_TiQu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PickUpstore.size() > 0) {

                    String flagStr = "";
                    int num = 0;
                    boolean tf = true;
                    for (String key : PickUpstore.keySet()){
                        if (num == 0) {
                            flagStr = PickUpstore.get(key)[0];
                            num += 1;
                        } else {
                            if (!flagStr.equals(PickUpstore.get(key)[0])) {
                                tf = false;
                                break;
                            }
                        }
                    }

                    if (tf) {
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("Info", PickUpstore);
                        mBundle.putInt("id", PickUpSignatureActivity_REQUEST);

                        Intent intent = new Intent(mContext, PickUpSignatureActivity.class);
                        intent.putExtras(mBundle);
                        startActivityForResult(intent, PickUpSignatureActivity_REQUEST);
                    } else {
                        ToastUtils.showToast(mContext,"多选请保持提取编号相同！",Toast.LENGTH_SHORT);
                    }



                } else {
                    ToastUtils.showToast(mContext,"请勾选待提取的货物信息！",Toast.LENGTH_SHORT);
                }
            }
        });
        //endregion

        //region 扫描运单按钮
        Img_SaoMaYunDan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useCamera();
            }
        });
        //endregion

        //region 下拉刷新
        pulltorefreshview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });
        //endregion
    }
    //endregion

    //region 其他界面返回值处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle bundle = new Bundle();
        String re = "";
        switch (requestCode) {
            case ImpPickUp_CAMERA_REQUEST:
                if (resultCode == AviationCommons.ImpPickUp_CAMERA_RESULT) {
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
            case PickUpSignatureActivity_REQUEST:
                if (resultCode == AviationCommons.PickUpSignatureActivity_RESULT) {
                    tipDialog = new QMUITipDialog.Builder(mContext)
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                            .setTipWord("提取成功")
                            .create();

                    tipDialog.show();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Btn_QinKong.performClick();
                            tipDialog.dismiss();
                        }
                    }, 1000);
                }
                break;
            default:
                break;
        }
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
            if (msg.what == GNJ_ImpPickUpActivity) {

                if (PickUpAppModels.size() == 0) {
                    ToastUtils.showToast(mContext,"数据为空",Toast.LENGTH_SHORT);
                    Ldialog.dismiss();
                }else {
                    setDatas(PickUpAppModels,AviationCommons.REFRESH_DATA);
                    mHandler.postDelayed(new Runnable(){
                        public void run() {
                            //execute the task
                            Ldialog.dismiss();
                        }
                    }, 1000);
                }
            }
            return false;
        }
    });
    //endregion

    //region 请求数据
    private void GetInfo(UploadData p) {
        clearTableView();

        HttpRoot.getInstance().requstAync(mContext, p,
                new HttpRoot.CallBack() {
                    @Override
                    public void onSucess(Object result) {
                        Ldialog.dismiss();
                        ResData resData = (ResData) result;
                        String json = resData.getData().toString();
                        PickUpAppModels = FastjsonUtils.fromJsonList(json, ImpPickUpAppModel.class);
                        mHandler.sendEmptyMessage(GNJ_ImpPickUpActivity);

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

    //region 文本框赋值

    //endregion

    //region 把数据绑定到Model
    private void setDatas(List<ImpPickUpAppModel> impPickUp, int type) {
        if (impPickUp.size() > 0) {
            List<TableModel> mDatas = new ArrayList<>();
            for (int i = 0; i < impPickUp.size(); i++) {
                ImpPickUpAppModel cc = impPickUp.get(i);
                TableModel tableMode = new TableModel();
                tableMode.setOrgCode("");
                tableMode.setLeftTitle(cc.getPkId() + "");
                tableMode.setText0(cc.getPrefix() + "");//列0内容
                tableMode.setText1(cc.getNo() + "");//列1内容
                tableMode.setText2(cc.getExNo() + "");//列2内容
                tableMode.setText3(cc.getAgent() + "");
                tableMode.setText4(cc.getPc() + "");
                tableMode.setText5(cc.getWeight() + "");//
                tableMode.setText6(cc.getSpCode() + "");//
                tableMode.setText7(cc.getGoods() + "");//
                tableMode.setText8(cc.getDlvName() + "");//
                tableMode.setText9(cc.getDlvNumber() + "");//
                tableMode.setText10(cc.getDlvTelephone()                                                                                                                                                     + "");//

                mDatas.add(tableMode);
            }
            boolean isMore;
            if (type == AviationCommons.LOAD_DATA) {
                isMore = true;
            } else {
                isMore = false;
                pulltorefreshview.setRefreshing(false);
            }
            mLeftAdapter.addData(mDatas, isMore);
            mRightAdapter.addData(mDatas, isMore);

            mDatas.clear();
        } else {
            mLeftAdapter.clearData(true);
            mRightAdapter.clearData(true);
        }
    }
    //endregion

    //region 调用相机
    private void useCamera() {
        Intent intent = new Intent(mContext, CaptureActivity.class);
        intent.putExtra("id", ImpPickUp_CAMERA_REQUEST);
        startActivityForResult(intent, ImpPickUp_CAMERA_REQUEST);
    }
    //endregion

    //region 拷贝识别数据集
    private void copyToSdCard(String path, String name) {
        Log.i(TAG, "copyToSD: "+ path);
        Log.i(TAG, "copyToSD: "+ name);

        //如果存在就删掉
        File f = new File(path);
        if (f.exists()){
            f.delete();
        }
        if (!f.exists()){
            File p = new File(f.getParent());
            if (!p.exists()){
                p.mkdirs();
            }
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        InputStream is=null;
        OutputStream os=null;
        try {
            is = this.getAssets().open(name);
            File file = new File(path);
            os = new FileOutputStream(file);
            byte[] bytes = new byte[2048];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if (os != null)
                    os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //endregion

    //region 识别证件号
    private void load(final Bitmap bitmap) {
        File outFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/" + FILE_NAME +"/" +LANGUAGE_NAME);
        if (!outFile.exists()) {
            ToastUtils.showToast(mContext, "找不到tessdata", Toast.LENGTH_SHORT);
            return;
        }
        final TessBaseAPI baseApi = new TessBaseAPI();
        //关闭测试
        baseApi.setDebug(true);
        //载入数据集
        baseApi.init(Environment.getExternalStorageDirectory().getAbsolutePath(), LANGUAGE_FILE_NAME);
        //白名单
        baseApi.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "X0123456789");
        //黑名单
        baseApi.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&*()_+=-[]}{;:'\"\\|~`,./<>?ABCDEFGHIJKLMNOPQRSTUVWYZabcdefghijklmnopqrstuvwxyz");
        //识别模式
        baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO_OSD);


        new Thread(new Runnable() {
            @Override
            public void run() {
                baseApi.setImage(bitmap);
                text = baseApi.getUTF8Text();
                baseApi.end();
                mHandler.sendEmptyMessage(12);
            }
        }).start();

    }
    //endregion

    //endregion

}
