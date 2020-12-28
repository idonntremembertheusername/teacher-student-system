package com.ts.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.entity.BaseResp;
import com.ts.entity.Homework;
import com.ts.service.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Homework")
public class HomeworkController {

    @Autowired
    private HomeworkService homeworkService;

    @GetMapping
    public BaseResp getAll(@RequestParam(required = false) String courseName,
                           @RequestParam(required = false) String studentName,
                           @RequestParam(required = false) Integer userId,
                           @RequestParam(required = false) Integer role,
                           Page page){
        return this.homeworkService.getAll(page,courseName,studentName,userId,role);
    }

    @PostMapping
    public BaseResp addHomework(@RequestBody Homework homework){
        this.homeworkService.addHomework(homework);
        return BaseResp.success("上传作业成功");
    }

    @DeleteMapping
    public BaseResp deleteHomework(@RequestParam(required = false) Integer homeworkId){
        this.homeworkService.deleteHomework(homeworkId);
        return BaseResp.success("删除资料成功");
    }

    @PatchMapping
    public BaseResp setScore(@RequestParam(required = false) Integer homeworkId,
                             @RequestParam(required = false) Integer score){
        this.homeworkService.setScore(homeworkId,score);
        return BaseResp.success("评分成功");
    }
}
