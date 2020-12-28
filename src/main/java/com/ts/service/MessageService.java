package com.ts.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.entity.Message;
import com.ts.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    public IPage getAll(Page page, Integer forumId) {
        return this.messageMapper.selectPage(page,new QueryWrapper<Message>().eq("forum_id",forumId));
    }

    public void messageService(Message message) {
        message.insert();
    }

    public void deleteMessage(Integer messageId) {
        this.messageMapper.deleteById(messageId);
    }
}
