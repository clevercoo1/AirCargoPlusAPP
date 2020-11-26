package njkgkj.com.aircargoplusapp.model.gnjm;

import lombok.Data;

/**
 * Created by 46296 on 2020/8/11.
 */

@Data
public class PickUpPicModel {

    public PickUpPicModel() {
        organizationcode = "";
        pkid = "";
        signer = "";
        idcard = "";
        prefix = "";
        no = "";
        exno = "";
    }

    /**
     * 企业组织机构代码
     */
    private String organizationcode;
    /**
     * 提取记录编号
     (对应GNJ_Warehouse. VoucherNumber)
     */
    private String pkid;

    /**
     * 签名图片
     */
    private String signer;

    /**
     * 身份证图片
     */
    private String idcard;

    /**
     * 主提运单前缀(承运人运单三字码)
     */
    private String prefix;
    /**
     * 主提运单号
     */
    private String no;

    /**
     * (预留)主提运单号扩展码(Default ‘0’)
     */
    private String exno;
}
