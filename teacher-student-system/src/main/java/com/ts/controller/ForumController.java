package com.ts.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.entity.BaseResp;
import com.ts.entity.Forum;
import com.ts.entity.Homework;
import com.ts.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Forum")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @GetMapping
    public BaseResp getAll(@RequestParam(required = false) Integer role,
                           @RequestParam(required = false) String title,
                           Page page){
        IPage list = this.forumService.getAll(page,role,title);
        return BaseResp.success(page);
    }

    @PostMapping
    public BaseResp addForum(@RequestBody Forum forum){
        this.forumService.addForum(forum);
        return BaseResp.success("发布话题成功");
    }

    @DeleteMapping
    public BaseResp deleteForum(@RequestParam(required = false) Integer forumId){
        this.forumService.deleteForum(forumId);
        return BaseResp.success("删除话题成功");
    }

    @PostMapping("/update")
    public BaseResp updateForum(@RequestBody Forum forum){
        this.forumService.updateForum(forum);
        return BaseResp.success("更新话题成功");
    }

    @GetMapping("{forumId}")
    public BaseResp getOne(@PathVariable(required = false) Integer forumId){
        Forum forum = this.forumService.getOne(forumId);
        return BaseResp.success(forum);
    }

}
