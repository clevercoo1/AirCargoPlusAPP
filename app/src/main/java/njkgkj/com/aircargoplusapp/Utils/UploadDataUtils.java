package njkgkj.com.aircargoplusapp.Utils;

import android.text.TextUtils;

import njkgkj.com.aircargoplusapp.model.UploadData;
import njkgkj.com.aircargoplusapp.model.gnjm.CargoHandingApp;

import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.gnj_CargoHandingActivity_model;
import static njkgkj.com.aircargoplusapp.http.HttpCommons.gnj_CargoHandingActivity_load;

/**
 * Created by 46296 on 2020/7/13.
 */

public class UploadDataUtils {
    public static UploadData getUploadData(Object ob, String flag,String method) {
        UploadData res = new UploadData();
        switch(flag){
            case gnj_CargoHandingActivity_model :
                CargoHandingApp cargoHandingApp = (CargoHandingApp) ob;
                String num = cargoHandingApp.getNo();
                if (num.length() == 11) {
                    cargoHandingApp.setPrefix(num.substring(0,3));
                    cargoHandingApp.setNo(num.substring(3));
                }
                String json = FastjsonUtils.toJson(cargoHandingApp);
                if (!TextUtils.isEmpty(json)) {
                    res.setMethod(method);
                    res.setData(json);
                }
                break;

            default : //可选
                break;
        }

        return res;
    }
}
