package njkgkj.com.aircargoplusapp.Utils;

import android.os.Environment;

import lombok.val;

/**
 * 主要存全局常用值
 */

public class AviationCommons {

    //常用公共变量
    public static String[] SPfenlei = new String[]{"DZHA:大宗货(无锂电池的电子产品、纸质品、塑料制品、特殊货物)",
            "WYXH:无氧鲜活" ,
            "DZHB:大宗货(有油机械、航材、工艺品、生物制品)",
            "YYXH:有氧鲜活", "KJ:快件", "YJ:邮件",
            "SP:食品",
            "CPY:成品药",
            "HGP:化工品",
            "WXP:危险品",
            "SPFZ:散拼,回收类服装",
            "FEP:著名企业电子产品",
            "FZB:服装包",
            "WYJX:无油机械",
            "LZK:陆转空",
            "PH:普货"};

    public static String TAG = "ACTAG";
    public static final String DST_FOLDER_NAME = "AirCargoPlusApp";
    public static final String StoragePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/" + DST_FOLDER_NAME;
    public static final int Camera_Quality = 30;

    public static String Comany_txt = "南京禄口国际机场定制版";
    /**
     * 登录得到服务器成功返回的ID（每次注销得到的ID值不一样）
     */
    public static String LoginFlag = "";
    public static float MyDPI = 0;

    //解析时 错误信息的TAG
    public static final String Log_TAG = "ErrorText";

    // intent传递xml的标识
    public static final String LOGIN_XML = "loginxml";

    // bundle传递list项
    public static final String AWB_ITEM_INFO = "awbItemInfo";
    public static final String HOUSE_ITEM_INFO = "housedetail";
    public static final String INT_CHILD_INFO = "fenyeshuju";
    public static final String INT_GROUP_INFO = "zhufendanshuju";
    public static final String INT_AWB_HOUSE = "intawbhousexml";
    public static final String INT_ONEKEY_DECLARE = "decalrexml";
    public static final String DECLARE_INFO = "declareinfo";
    public static final String DECLARE_MAWB = "mawb";
    public static final String DECLARE_REARCHID = "rearchID";
    public static final String DECLARE_INFO_DEATIL = "declareinfoxml";
    public static final String DECLAREINFO_DEATIL = "declareinfo";
    public static final String IMP_CARGO_INFO = "cargoinfoxml";
    public static final String IMP_CARGO_INFO_ITEM = "cargoInfoMessage";
    public static final String IMP_CARGO_INFO_BUSINESSTYPE = "businessType";
    public static final String IMP_CARGO_INFO_HAWBID = "hawbID";
    public static final String FLIGHT_INFO = "flightinfo";
    public static final String FLIGHT_XML="xml";
    public static final String FLIGHT_DETAIL = "flightdetail";
    public static final String EDECLARE_INFO = "edeclareinfo";

    public static final String Gnj_To_Gjj = "CIII";
    public static final String  Gjj_To_Gnc = "IICO";
    public static final String Gjj_To_Gjc = "IIIO";

    // Intent传值
    public static final String INT_GROUP_MAWB = "mawb";
    public static final String HIDE_INT_AWB_UPDATE = "hidehouse";
    public static final String MANAGE_HOUSE_MAWAB = "housemawb";
    public static final String MANAGE_HOUSE_BEGAIN_TIME = "beagintime";
    public static final String MANAGE_HOUSE_END_TIME = "endtime";
    public static final String SPLITE_REARCHID = "rearchid";
    public static final String SPLITE_PC = "pc";
    public static final String SPLITE_WEIGHT = "weight";
    public static final String SPLITE_VOLUME = "volume";
    public static final String IMP_HAWBID = "hawbid";
    public static final String IMP_MAWB = "mawb";
    public static final String IMP_TYPE = "businessType";

    // HomePageFragment内容
    public static final String APP_GNJ_CargoHanding = "appGnjCargoHanding";
    public static final String APP_GNJ_ImpPickUp = "appGnjImpPickUp";
    public static final String APP_GJJ_Move = "appGjjMove";

    //国内进港
    //进港理货
    public static final String gnj_CargoHandingActivity_model = "CargoHandingApp";
    //
    public static final String gnj_PickUP_model = "ImpPickUP";

    public static final String gnj_PickUpSignature_model = "PickUpSignature";

    public static final String gjj_Move_model = "GjjMove";
    public static final String gjj_Move_modelList = "GjjMoveList";

    // handler传递的值（int型）
    public static final int GNJ_CargoHandingActivity = 0x11;
    public static final int GNJ_CargoHandingInfoActivity = 0x22;
    public static final int GNJ_ImpPickUpActivity = 0x33;
    public static final int GNJ_PickUpSignatureActivity = 0x44;
    public static final int GNJ_PickUpSignatureActivity_update = 0x50;
    public static final int GJJ_GjjMoveActivity_Load = 0x51;

    //权限申请
    public static final int REQUEST_CODE_CAMERA_PERMISSIONS  = 0x100;
    public static final int REQUEST_CODE_WRITE_PERMISSIONS  = 0x200;
    public static final int REQUEST_CODE_READ_PERMISSIONS  = 0x300;

    //回调参数
    public static final int CargoHandingActivity_CAMERA_REQUEST = 10;
    public static final int CargoHandingActivity_CAMERA_RESULT = 11;

    public static final int CargoHandingInfoActivity_REQUEST = 12;
    public static final int CargoHandingInfoActivity_RESULT = 13;

    public static final int ImpPickUp_CAMERA_REQUEST = 14;
    public static final int ImpPickUp_CAMERA_RESULT = 15;

    public static final int PickUpSignatureActivity_REQUEST = 16;
    public static final int PickUpSignatureActivity_RESULT = 17;

    public static final int GjjMoveActivity_CAMERA_REQUEST = 18;
    public static final int GjjMoveActivity_CAMERA_RESULT = 19;








    //上拉加载更多
    public static final int LOAD_DATA = 2;
    //下拉刷新
    public static final int REFRESH_DATA = 1;

    //tesstwo
    public static final String FILE_NAME = "tessdata";
    public static final String LANGUAGE_NAME = "eng.traineddata";
    public static final String LANGUAGE_FILE_NAME = "eng";
    public static final int PERMISSION_REQUEST_CODE = 0;
}
