package com.simplemall.micro.serv.common.bean.account;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 准康农惠微信村集体下属村庄配置
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ZknhVillageModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Excel(name = "村庄ID", width = 64)
    private String villageId;

    @Excel(name = "模块名称", width = 255)
    private String modelName;

    @Excel(name = "模块标题图片", width = 255)
    private String modelImg;

    @Excel(name = "创建时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @Excel(name = "排序")
    private int modelSort;
}
