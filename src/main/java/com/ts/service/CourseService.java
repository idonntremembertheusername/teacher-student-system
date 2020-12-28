package com.ts.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.entity.BaseResp;
import com.ts.entity.Course;
import com.ts.entity.Cs;
import com.ts.entity.Homework;
import com.ts.mapper.CourseMapper;
import com.ts.util.RandomUtil;
import io.netty.util.internal.StringUtil;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    public BaseResp getStudentCourse(Integer userId) {
        return BaseResp.success(this.courseMapper.getStudentCourse(userId));
    }

    public BaseResp getAll(Page page, String courseName, String teacherName, Integer userId, Integer role) {
        if(role == 1){
            return BaseResp.success(this.courseMapper.getCoursePage(page,
                    new QueryWrapper<Course>().like(!StringUtil.isNullOrEmpty(teacherName),"teacher_name",teacherName)
                            .like(!StringUtil.isNullOrEmpty(courseName),"name",courseName))
            );

        }else if(role ==2&&userId !=null){
            return BaseResp.success(this.courseMapper.getCoursePage(page,
                    new QueryWrapper<Course>().like(!StringUtil.isNullOrEmpty(teacherName),"teacher_name",teacherName)
                            .like(!StringUtil.isNullOrEmpty(courseName),"name",courseName)
                            .eq(userId!=null,"teacher_id",userId))
            );

        }else if(role ==3 &&userId !=null){
            return BaseResp.success(this.courseMapper.getStudentCoursePage(page,userId,
                    new QueryWrapper<Course>().like(!StringUtil.isNullOrEmpty(teacherName),"teacher_name",teacherName)
                                            .like(!StringUtil.isNullOrEmpty(courseName),"name",courseName)
                                            .gt("status",0)
            ));
        }
        return BaseResp.fail("01","服务器错误");
    }

    public BaseResp getTeacherCourse(Integer userId) {
        return BaseResp.success(this.courseMapper.getTeacherCourse(userId));
    }

    public void addCourse(Course course) {
        course.setStatus(1);
        course.setCode(RandomUtil.getRandomString(8));
        course.insert();
    }

    public BaseResp changeStatus(Integer courseId) {
        Course course = this.courseMapper.selectById(courseId);
        if(course.getStatus() == 1){
            course.setStatus(0);
            course.updateById();
            return BaseResp.success("停用课程成功");
        }else if(course.getStatus() == 0) {
            course.setStatus(1);
            course.updateById();
            return BaseResp.success("启用课程成功");
        }
        return BaseResp.fail("01","服务器错误");
    }

    public BaseResp joinCourse(Integer userId, String code) {
        Course course =this.courseMapper.selectOne(new QueryWrapper<Course>().eq("code",code));
        Cs cs = new Cs();
        cs.setCourseId(course.getCourseId());
        cs.setStudentId(userId);
        cs.insert();
        return BaseResp.success("加入课程成功");
    }
}
