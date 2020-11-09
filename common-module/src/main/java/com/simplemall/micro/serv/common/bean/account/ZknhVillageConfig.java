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
 * 准康农惠微信村集体配置表
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ZknhVillageConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Excel(name = "村庄名称", width = 255)
    private String villageName;

    @Excel(name = "村庄背景图片", width = 255)
    private int villageBack;

    @Excel(name = "村庄logo", width = 255)
    private String villageMainImg;

    @Excel(name = "链接地址", width = 520)
    private String villageContent;

    @Excel(name = "排序")
    private int sort;
}
