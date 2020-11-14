package com.simplemall.account.dal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZknhVillageModelMapper extends BaseMapper<ZknhVillageModel> {

    public List<ZknhVillageModel> getVillageModelByVillageId(@Param("villageId") String villageId);

}
