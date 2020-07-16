package njkgkj.com.aircargoplusapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import njkgkj.com.aircargoplusapp.R;
import njkgkj.com.aircargoplusapp.Utils.AviationCommons;
import njkgkj.com.aircargoplusapp.Utils.PreferenceUtils;
import njkgkj.com.aircargoplusapp.model.adapter.HomePageAdapter;
import njkgkj.com.aircargoplusapp.model.home.HomeMessage;
import njkgkj.com.aircargoplusapp.model.home.PrefereceHomeMessage;
import njkgkj.com.aircargoplusapp.Utils.DateUtils;
import njkgkj.com.aircargoplusapp.Utils.AviationCommons;
import njkgkj.com.aircargoplusapp.Utils.PreferenceUtils;
import njkgkj.com.aircargoplusapp.ui.base.NavBar;
import njkgkj.com.aircargoplusapp.ui.gnj.CargoHandingActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 首页fragment
 */
public class HomePageFragment extends Fragment{
    // 用户权限
    private String userPermission = "";

    // 锁屏密码
    private String lockPass = "";

    private GridView grid_home;
    private BaseAdapter mAdapter = null;

    private String xml = "";

    private View view;
    private List<HomeMessage> list;

    // 时间比较
    private String time;
    private String splitBeginTime;
    private  Date beginTime;
    private  Date endTime;
    private String todayTime;// 获得当前时间
    private boolean result;// 时间返回结果（true表示在使用周期，false表示不在使用周期）

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.homepage_fragment, container, false);
        initView();
        return view;
    }
    private void initView() {
        NavBar navBar = new NavBar(getActivity());
        navBar.setTitle("首页");
        navBar.hideRight();
        navBar.hideLeft();

        // 初始化gridView
        grid_home = (GridView) view.findViewById(R.id.home_gridView);

        // 得到锁屏密码
        lockPass = PreferenceUtils.getLockPass(getActivity());

        // 得到登录传递过来的xml数据
//        xml = getActivity().getIntent().getStringExtra(AviationCommons.LOGIN_XML);
//        list = PrefereceHomeMessage.pullXml(xml,getActivity());
        HomeMessage ho = new HomeMessage();
        ho.setName(AviationCommons.APP_GNJ_CargoHanding);
        ho.setNameCN("出港理货");
        List<HomeMessage> holist = new ArrayList<>();
        holist.add(ho);
        list = holist;

        // 判断是否设置过锁屏密码
//        if (!TextUtils.isEmpty(lockPass)) {
//            // 解锁用户名
//            String userName = PreferenceUtils.getUserName(getActivity());
//            userPermission = getActivity().getIntent().getStringExtra("userPermission");
//        }

        mAdapter = new HomePageAdapter<HomeMessage>((ArrayList<HomeMessage>) list, R.layout.homepage_item) {

            @Override
            public void bindView(ViewHolder holder, HomeMessage obj) {
                if (obj.getName().equals(AviationCommons.APP_GNJ_CargoHanding)) {
                    holder.setImageResource(R.id.image_iv,  R.drawable.gnj_cargohanding);
                } else {
                    holder.setImageResource(R.id.image_iv,  R.drawable.domawb);
                };

                Log.d("guoji", obj.getName());

                String title = "";
                if (obj.getNameCN().contains("国内")) {
                    title = obj.getNameCN().substring(2);
                } else {
                    title = obj.getNameCN();
                }
                holder.setText(R.id.image_tv, title);
            }
        };
        grid_home.setAdapter(mAdapter);

        grid_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                time = list.get(position).getActiveDate();
//                splitBeginTime = time.split("T")[0];
//                todayTime = DateUtils.getTodayDateTime();
//                beginTime = DateUtils.convertFromStrYMD(splitBeginTime);
//                endTime = DateUtils.convertFromStrYMD(todayTime);
//
//                result = DateUtils.compareDate(beginTime, endTime);
                result = true;
                if (result && list.get(position).getName().equals(AviationCommons.APP_GNJ_CargoHanding)) {
                    Intent CargoHanding = new Intent(getActivity(), CargoHandingActivity.class);
                    startActivity(CargoHanding);
                }  else {
                    Toast.makeText(getActivity(), "功能尚未开发", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
