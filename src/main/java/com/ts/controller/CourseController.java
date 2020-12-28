package com.ts.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.entity.BaseResp;
import com.ts.entity.Course;
import com.ts.entity.Homework;
import com.ts.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/{id}")
    public BaseResp getStudentCourse(@PathVariable(value = "id") Integer userId){
        return this.courseService.getStudentCourse(userId);
    }

    @GetMapping("/teacher/{id}")
    public BaseResp getTeacherCourse(@PathVariable(value = "id") Integer userId){
        return this.courseService.getTeacherCourse(userId);
    }

    @GetMapping
    public BaseResp getAll(@RequestParam(required = false) String courseName,
                           @RequestParam(required = false) String teacherName,
                           @RequestParam(required = false) Integer userId,
                           @RequestParam(required = false) Integer role,
                           Page page){
        return this.courseService.getAll(page,courseName,teacherName,userId,role);
    }

    @PostMapping
    public BaseResp addCourse(@RequestBody Course course){
        this.courseService.addCourse(course);
        return BaseResp.success("新建课程成功");
    }

    @PatchMapping
    public BaseResp changeStatus(@RequestParam(required = false) Integer courseId){
        return this.courseService.changeStatus(courseId);
    }

    @PatchMapping("/join")
    public BaseResp joinCourse(@RequestParam(required = false) Integer userId,
                               @RequestParam(required = false) String code){
        return this.courseService.joinCourse(userId,code);
    }
}
