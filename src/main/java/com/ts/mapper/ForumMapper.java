package com.ts.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ts.entity.Forum;
import com.ts.entity.Homework;
import org.apache.ibatis.annotations.Param;

public interface ForumMapper extends BaseMapper<Forum> {
    /**
     * 自定义sql分页
     * @param page
     * @param queryWrapper 看这里看这里，如果自定义的方法中需要用到wrapper查询条件，需要这样写
     * @return
     */
    IPage<Forum> selectForum(IPage<Forum> page, @Param(Constants.WRAPPER) Wrapper<Forum> queryWrapper);

}
