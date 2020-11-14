package com.simplemall.account.dal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZknhVillageDetailMapper extends BaseMapper<ZknhVillageDetail> {

    public List<ZknhVillageDetail> getVillageDetailByModelId(@Param("modelId") String modelId);

}
