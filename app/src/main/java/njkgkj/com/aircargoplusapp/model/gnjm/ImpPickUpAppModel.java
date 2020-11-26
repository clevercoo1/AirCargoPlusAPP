package njkgkj.com.aircargoplusapp.model.gnjm;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * Created by 46296 on 2020/8/3.
 */

@Data
public class ImpPickUpAppModel {
    /**
     * 提取记录编号
     (对应GNJ_Warehouse. VoucherNumber)
     */
    private String pkId;

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
    private String exNo;

    /**
     * 代理人代码
     */
    private String agent;

    /**
     * 提取件数
     */
    private Long pc;

    /**
     * 提取重量
     */
    private BigDecimal weight;

    /**
     * 特种货物代码
     */
    private String spCode;

    /**
     * 货物品名
     */
    private String goods;

    /**
     * 提货人名称(单位名称 或 个人姓名)
     */
    private String dlvName;

    /**
     * 提货人(企业组织机构代码 或 个人身份证)
     */
    private String dlvNumber;

    /**
     * 提货人电话
     */
    private String dlvTelephone;

    /**
     * 提取时间
     */
    private Date dlvTime;

    /**
     * 提取标识(0:未提取;1:已提取)
     */
    private String isPickUp;
}
