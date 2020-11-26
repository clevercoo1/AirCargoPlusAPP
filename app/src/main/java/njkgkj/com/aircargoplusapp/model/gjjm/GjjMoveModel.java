package njkgkj.com.aircargoplusapp.model.gjjm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * Created by 46296 on 2020/11/24.
 */
@Data
public class GjjMoveModel implements Serializable {
    /**
     * 企业组织机构代码
     */
    private String organizationCode;
    /**
     * 移库编号
     (对应GNJ_Warehouse. VoucherNumber)
     */
    private String moveId;
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
     * 提运单总件数
     */
    private String awbPC;
    /**
     * 提取件数
     */
    private String pc;
    /**
     * 提取重量
     */
    private String weight;
    /**
     * 特种货物代码
     */
    private String spCode;
    /**
     * 特种货物子代码
     */
    private String subCode;
    /**
     * 货物品名
     */
    private String goods;
    /**
     * 始发港
     */
    private String origin;
    /**
     * 启运港
     */
    private String dep;
    /**
     * 目的港
     */
    private String dest;
    /**
     * 航班流水号
     */
    private String fid;
    /**
     * 航班日期
     */
    private String fdate;
    /**
     * 航班号
     */
    private String fno;
    /**
     * 运单备注信息
     */
    private String remark;
    /**
     * 接收人代码
     */
    private String rcvId;
    /**
     * 接收人姓名
     */
    private String rcvName;
    /**
     * 接收时间
     */
    private Date rcvDate;
    /**
     * 移库人代码
     */
    private String opId;
    /**
     * 移库人姓名
     */
    private String opName;
    /**
     * 移库时间
     */
    private Date opDate;

    /**
     * 拼接运单号
     */
    private String awbno;

    /**
     * 拼接航班号
     */
    private String flight;

    /**
     * 运单类型
     */
    private String awbtype;

    /**
     * 承运人
     */
    private String carrier;

    /**
     * 运单总件数
     */
    private String totalpc;

    /**
     * 体积
     */
    private String volume;

    /**
     * 业务类型
     */
    private String businesstype;

    /**
     * 结束时间
     */
    private String opDateEnd;

    /**
     * 重置状态
     */
    private String moveStatus;

}
