package com.simplemall.account.dal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simplemall.micro.serv.common.bean.account.SysStaticData;
import com.simplemall.micro.serv.common.vo.DuplicateCheckVo;

public interface SysStaticDataMapper extends BaseMapper<SysStaticData> {
    /**
     *  重复检查SQL
     * @return
     */
    public Long duplicateCheckCountSql(DuplicateCheckVo duplicateCheckVo);
    public Long duplicateCheckCountSqlNoDataId(DuplicateCheckVo duplicateCheckVo);
}
