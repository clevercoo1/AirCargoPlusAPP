package njkgkj.com.aircargoplusapp.ui.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;


import njkgkj.com.aircargoplusapp.R;
import njkgkj.com.aircargoplusapp.ui.base.NavBar;
import njkgkj.com.aircargoplusapp.Utils.PreferenceUtils;

/**
 * 我的fragment(这里主要包括用户的一些设置)
 */

public class PersonFragment extends Fragment {
    private View view;

    // 设置锁屏
    private RelativeLayout seetingPassLayout;

    // 修改密码
    private RelativeLayout changePassLayout;

    // 版本更新
    private RelativeLayout versionUpdateLayout;

    // 退出登录
    private RelativeLayout exitLoginLayout;

    // 版本更新（版本号 提示消息 下载地址）
    private ProgressDialog pBar;
    private String version;
    private String LocalVersion;
    private String describe;
    private String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.person_fragment, container, false);
        initView();
        return view;
    }

    private void initView() {
        NavBar navBar = new NavBar(getActivity());
        navBar.setTitle("我的");
        navBar.hideRight();
        navBar.hideLeft();
        seetingPassLayout = (RelativeLayout) view.findViewById(R.id.setting_pass_layout);

        changePassLayout = (RelativeLayout) view.findViewById(R.id.change_pass_layout);


        versionUpdateLayout = (RelativeLayout) view.findViewById(R.id.version_update_layout);


        exitLoginLayout = (RelativeLayout) view.findViewById(R.id.exit_login_layout);


        // 获取版本更新信息
        version = PreferenceUtils.getAPPVersion(getActivity());
        describe = PreferenceUtils.getAPPDescribe(getActivity());
        url = PreferenceUtils.getAppUrl(getActivity());

    }

}
