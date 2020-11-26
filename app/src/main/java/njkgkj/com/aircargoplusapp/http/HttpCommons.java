package njkgkj.com.aircargoplusapp.http;

/**
 * Created by 46296 on 2020/6/30.
 */

public class HttpCommons {
    // 版本升级的url（目前是从txt文件中获取版本升级的url和版本的versionCode）https://github.com/mxzs1314/.github.io/raw/master
    public static final String UPDATE_VERSION_URL = "https://github.com/mxzs1314/Aviations/raw/master/app";
    // apk下载地址
    public static final String APK_URL = "https://github.com/mxzs1314/.github.io/raw/master/app/app-release.apk";

    // EndPoint 正式版本
    public static final String END_POINT = "http://192.168.1.15:8080/api/gateway/cgo";

    //国内进港理货-查询待理货列表
    public static final String gnj_CargoHandingActivity_load = "CargoHandingApp.loadList";
    //国内进港理货-上传理货信息
    public static final String gnj_CargoHandingActivity_update = "CargoHandingApp.update";
    //国内进港提取-查询待提取列表
    public static final String gnj_ImpPickUp_load = "gnjPickUp.load";
    //国内进港提取-保存图片
    public static final String gnj_PickUpSignature_save = "PickUpPic.save";
    //国内进港提取-上传提取状态
    public static final String gnj_PickUpSignature_update = "PickUpPic.updateIsPickUp";
    //查询待移库列表
    public static final String gjj_Move_load = "GjjMove.GetGjjCargoMoveToShow";
    //
    public static final String gjj_Move_toMove = "GjjMove.GjjWarehouseToMove";

}
