package njkgkj.com.aircargoplusapp.Utils;

import android.text.TextUtils;

import java.util.List;

import njkgkj.com.aircargoplusapp.model.UploadData;
import njkgkj.com.aircargoplusapp.model.gjjm.GjjMoveModel;
import njkgkj.com.aircargoplusapp.model.gnjm.CargoHandingApp;
import njkgkj.com.aircargoplusapp.model.gnjm.ImpPickUpAppModel;
import njkgkj.com.aircargoplusapp.model.gnjm.PickUpPicModel;

/**
 * Created by 46296 on 2020/7/13.
 */

public class UploadDataUtils {
    public static UploadData getUploadData(Object ob, String flag,String method) {
        UploadData res = new UploadData();
        String json = "";
        switch(flag){
            case AviationCommons.gnj_CargoHandingActivity_model :
                CargoHandingApp cargoHandingApp = (CargoHandingApp) ob;
                String num = cargoHandingApp.getNo();
                if (num.length() == 11) {
                    cargoHandingApp.setPrefix(num.substring(0,3));
                    cargoHandingApp.setNo(num.substring(3));
                }
                json = FastjsonUtils.toJson(cargoHandingApp);
                break;
            case AviationCommons.gnj_PickUP_model:
                ImpPickUpAppModel ipum = (ImpPickUpAppModel) ob;
                json = FastjsonUtils.toJson(ipum);
                break;
            case AviationCommons.gnj_PickUpSignature_model:
                PickUpPicModel picM = (PickUpPicModel)ob;
                json = FastjsonUtils.toJson(picM);
                break;
            case AviationCommons.gjj_Move_model:
                GjjMoveModel gjjMoveModel = (GjjMoveModel)ob;
                json = FastjsonUtils.toJson(gjjMoveModel);
                break;
            case AviationCommons.gjj_Move_modelList:
                List<GjjMoveModel> moveModelList = (List<GjjMoveModel>)ob;
                json = FastjsonUtils.toJson(moveModelList);
                break;
            default : //可选
                break;
        }

        if (!TextUtils.isEmpty(json)) {
            res.setMethod(method);
            res.setData(json);
        }

        return res;
    }
}
