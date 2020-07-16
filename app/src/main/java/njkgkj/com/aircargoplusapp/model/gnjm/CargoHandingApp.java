package njkgkj.com.aircargoplusapp.model.gnjm;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class CargoHandingApp implements Serializable {
    /**
     * 航班流水号
     */
    private Long fid;

    /**
     * 航班日期
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date fdate;

    /**
     * 航班号
     */
    private String fno;
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
    private Long awbPC;

    /**
     * 实到件数
     */
    private Long pc;
    /**
     * 实到重量
     */
    private BigDecimal weight;
    /**
     * 实到体积
     */
    private BigDecimal volume;
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
     * 仓库发放货位
     */
    private String whslocation;
    /**
     * 运单备注信息
     */
    private String remark;
    /**
     * 舱单发放标识(0:未发放;1:已发放)
     */
    private String ref;
    /**
     * 允许提取标识(0:不可提取;1:允许提取)
     */
    private String allowpickup;
    /**
     * 理货人代码
     */
    private String tallyID;
    /**
     * 理货人姓名
     */
    private String tallyName;
    /**
     * 操作时间
     */
    private Date tallyDate;
}
