package com.ts.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ts.entity.DashboardVo;
import com.ts.entity.Material;
import com.ts.entity.User;
import com.ts.entity.UserVo;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {

    /**
     * 自定义sql分页
     * @param page
     * @param queryWrapper 看这里看这里，如果自定义的方法中需要用到wrapper查询条件，需要这样写
     * @return
     */
    IPage<UserVo> selectStudent(IPage<UserVo> page, @Param("userId") Integer userId, @Param(Constants.WRAPPER) Wrapper<User> queryWrapper);


    /**
     * 自定义sql分页
     * @param page
     * @param queryWrapper 看这里看这里，如果自定义的方法中需要用到wrapper查询条件，需要这样写
     * @return
     */
    IPage<UserVo> selectCourseStudent(IPage<UserVo> page, @Param("userId") Integer userId,  @Param("courseId") Integer courseId,@Param(Constants.WRAPPER) Wrapper<User> queryWrapper);

    /**
     * 自定义sql分页
     * @param page
     * @param queryWrapper 看这里看这里，如果自定义的方法中需要用到wrapper查询条件，需要这样写
     * @return
     */
    IPage<UserVo> selectPageCount(IPage<UserVo> page, @Param(Constants.WRAPPER) Wrapper<User> queryWrapper);


    DashboardVo selectDashboardCount(@Param("userId") Integer userId, @Param("userId") Integer role);

}
