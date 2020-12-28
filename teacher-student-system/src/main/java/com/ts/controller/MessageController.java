package com.ts.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.entity.BaseResp;
import com.ts.entity.Forum;
import com.ts.entity.Message;
import com.ts.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    public BaseResp getAll(@RequestParam(required = false) Integer forumId,
                           Page page){
        IPage list = this.messageService.getAll(page,forumId);
        return BaseResp.success(page);
    }

    @PostMapping
    public BaseResp addMessage(@RequestBody Message message){
        this.messageService.messageService(message);
        return BaseResp.success("回复话题成功");
    }

    @DeleteMapping
    public BaseResp deleteMessage(@RequestParam(required = false) Integer messageId){
        this.messageService.deleteMessage(messageId);
        return BaseResp.success("删除回复成功");
    }
}
