package com.simplemall.micro.serv.common.bean.offer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PmOffer implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private String offerId;

    @Excel(name = "商品名称", width = 100)
    private String offerName;

    @Excel(name = "商品描述", width = 500)
    private String offerDesc;

    @Excel(name = "商品类型", width = 10)
    private String offerType;

    @Excel(name = "商品价格/单位分", width = 12)
    private Integer offerPrice;

    @Excel(name = "状态", width = 2)
    private Integer status;

    @Excel(name = "创建时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @Excel(name = "创建人员编号", width = 32)
    private String createUserName;

    @Excel(name = "生效时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validDate;

    @Excel(name = "失效时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expireDate;


}
