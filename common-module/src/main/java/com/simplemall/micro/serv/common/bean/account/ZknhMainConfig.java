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
 * 准康农惠微信主页配置表
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ZknhMainConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private int id;

    @Excel(name = "模块名称", width = 255)
    private String modalName;

    @Excel(name = "模块类型")
    private int modalType;

    @Excel(name = "图标", width = 255)
    private String modalIcon;

    @Excel(name = "图标类型")
    private int iconType;

    @Excel(name = "链接地址", width = 255)
    private String modalUrl;

    @Excel(name = "修改人员", width = 255)
    private String doneUserName;

    @Excel(name = "修改时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date doneDate;

    @Excel(name = "排序")
    private int sort;

}
