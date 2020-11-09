package com.simplemall.micro.serv.common.bean.account;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * 准康农惠微信村庄详情
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ZknhVillageDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "村庄ID", width = 64)
    private String modelId;

    @Excel(name = "模块详情介绍字段1", width = 1000)
    private String content1;

    @Excel(name = "模块详情介绍字段2", width = 1000)
    private String content2;

    @Excel(name = "模块详情介绍字段3", width = 1000)
    private String content3;

    @Excel(name = "模块详情介绍字段4", width = 1000)
    private String content4;

    @Excel(name = "模块详情介绍字段5", width = 1000)
    private String content5;

    @Excel(name = "模块详情介绍字段6", width = 1000)
    private String content6;

    @Excel(name = "模块详情介绍字段7", width = 1000)
    private String content7;

    @Excel(name = "模块详情介绍字段8", width = 1000)
    private String content8;

    @Excel(name = "模块详情介绍字段9", width = 1000)
    private String content9;

    @Excel(name = "模块详情介绍字段10", width = 1000)
    private String content10;
}
