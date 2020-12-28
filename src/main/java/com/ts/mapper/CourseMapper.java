package com.ts.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ts.entity.Course;
import com.ts.entity.CourseVo;
import com.ts.entity.Homework;
import com.ts.entity.Material;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapper extends BaseMapper<Course> {


    List<Course> getStudentCourse(@Param("userId") Integer userId);

    List<Course> getTeacherCourse(@Param("userId") Integer userId);
    /**
     * 自定义sql分页
     * @param page
     * @param queryWrapper 看这里看这里，如果自定义的方法中需要用到wrapper查询条件，需要这样写
     * @return
     */
    IPage<CourseVo> getStudentCoursePage(IPage<Course> page, @Param("userId") Integer userId, @Param(Constants.WRAPPER) Wrapper<Course> queryWrapper);

    IPage<CourseVo> getCoursePage(IPage<Course> page, @Param(Constants.WRAPPER) Wrapper<Course> queryWrapper);



}
