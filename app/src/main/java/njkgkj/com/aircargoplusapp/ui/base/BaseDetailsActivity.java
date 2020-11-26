package njkgkj.com.aircargoplusapp.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.widget.ListView;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import njkgkj.com.aircargoplusapp.R;
import njkgkj.com.aircargoplusapp.Utils.PublicFun;
import njkgkj.com.aircargoplusapp.model.BaseDetailModel;
import njkgkj.com.aircargoplusapp.model.adapter.BaseDetailAdapter;
import njkgkj.com.aircargoplusapp.model.gjjm.GjjMoveModel;

public class BaseDetailsActivity extends Activity {
    //region 自定义变量
    private Context mContext;
    private Activity mAct;
    private NavBar navBar;
    private String ModelType = "";
    private BaseDetailAdapter myAdapter;
    private List<BaseDetailModel> bdModelList;
    //endregion

    //region 未预设XML控件
    private QMUITipDialog tipDialog;
    //endregion

    //region 其他控件
    @BindView(R.id.BaseDetailsActivity_in_home_lv)
    ListView BaseDetailsActivityLv;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_details);

        mContext = BaseDetailsActivity.this;
        mAct = PublicFun.getActivityByContext(mContext);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        navBar = new NavBar(this);
        navBar.setTitle("详情");
        ModelType = getIntent().getSerializableExtra("ModelType").toString();
        bdModelList = new ArrayList<BaseDetailModel>();
        boolean flag = true;

        switch (ModelType) {
            case "GjjMove":
                gjjmove();
                break;
            default:
                flag = false;
                break;
        }

        if (flag) {
            myAdapter = new BaseDetailAdapter(bdModelList,mContext);
            BaseDetailsActivityLv.setAdapter(myAdapter);
        } else {
            tipDialog = new QMUITipDialog.Builder(mContext)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                    .setTipWord("解析错误")
                    .create();

            tipDialog.show();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tipDialog.dismiss();
                }
            }, 900);
        }



    }
    //endregion

    private void gjjmove() {
        GjjMoveModel gjjMoveModel =(GjjMoveModel) getIntent().getSerializableExtra("Details");

        BaseDetailModel newModel = new BaseDetailModel();
        newModel.setBasename("运单号:");
        newModel.setBasevalue(gjjMoveModel.getAwbno());
        bdModelList.add(newModel);

        newModel = new BaseDetailModel();
        newModel.setBasename("件数:");
        newModel.setBasevalue(gjjMoveModel.getPc());
        bdModelList.add(newModel);

        newModel = new BaseDetailModel();
        newModel.setBasename("重量:");
        newModel.setBasevalue(gjjMoveModel.getWeight());
        bdModelList.add(newModel);

        newModel = new BaseDetailModel();
        newModel.setBasename("品名:");
        newModel.setBasevalue(gjjMoveModel.getGoods());
        bdModelList.add(newModel);

        newModel = new BaseDetailModel();
        newModel.setBasename("特码:");
        newModel.setBasevalue(gjjMoveModel.getSpCode());
        bdModelList.add(newModel);

        newModel = new BaseDetailModel();
        newModel.setBasename("代理:");
        newModel.setBasevalue(gjjMoveModel.getAgent());
        bdModelList.add(newModel);

        newModel = new BaseDetailModel();
        newModel.setBasename("航班号:");
        newModel.setBasevalue(gjjMoveModel.getFlight());
        bdModelList.add(newModel);

        newModel = new BaseDetailModel();
        newModel.setBasename("始发港:");
        newModel.setBasevalue(gjjMoveModel.getOrigin());
        bdModelList.add(newModel);

        newModel = new BaseDetailModel();
        newModel.setBasename("目的港:");
        newModel.setBasevalue(gjjMoveModel.getDest());
        bdModelList.add(newModel);

        newModel = new BaseDetailModel();
        newModel.setBasename("运单类型:");
        newModel.setBasevalue(gjjMoveModel.getAwbtype());
        bdModelList.add(newModel);
    }

    //region 句柄监听
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            return false;
        }
    });
    //endregion

}
