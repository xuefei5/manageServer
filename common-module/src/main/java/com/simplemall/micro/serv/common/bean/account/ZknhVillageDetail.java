package com.simplemall.micro.serv.common.bean.account;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

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

    @Excel(name = "创建时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @Excel(name = "排序")
    private int sort;

    public String get(int key){
        String ret = "";
        switch (key){
            case 1:
                ret = getContent1();
                break;
            case 2:
                ret = getContent2();
                break;
            case 3:
                ret = getContent3();
                break;
            case 4:
                ret = getContent4();
                break;
            case 5:
                ret = getContent5();
                break;
            case 6:
                ret = getContent6();
                break;
            case 7:
                ret = getContent7();
                break;
            case 8:
                ret = getContent8();
                break;
            case 9:
                ret = getContent9();
                break;
            case 10:
                ret = getContent10();
                break;
            default:
                ret = "";
                break;
        }
        return ret;
    }

    public void set(int key,String value){
        switch (key){
            case 1:
                content1 = value;
                break;
            case 2:
                content2 = value;
                break;
            case 3:
                content3 = value;
                break;
            case 4:
                content4 = value;
                break;
            case 5:
                content5 = value;
                break;
            case 6:
                content6 = value;
                break;
            case 7:
                content7 = value;
                break;
            case 8:
                content8 = value;
                break;
            case 9:
                content9 = value;
                break;
            case 10:
                content10 = value;
                break;
            default:
                break;
        }
    }
}
