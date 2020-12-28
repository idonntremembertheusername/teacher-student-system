package com.ts.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ts.entity.Material;
import org.apache.ibatis.annotations.Param;

public interface MaterialMapper extends BaseMapper<Material> {
    /**
     * 自定义sql分页
     * @param page
     * @param queryWrapper 看这里看这里，如果自定义的方法中需要用到wrapper查询条件，需要这样写
     * @return
     */
    IPage<Material> selectMaterialStudent(IPage<Material> page,@Param("userId") Integer userId, @Param(Constants.WRAPPER) Wrapper<Material> queryWrapper);

}
