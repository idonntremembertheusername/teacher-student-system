package com.ts.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.entity.Forum;
import com.ts.entity.User;
import com.ts.mapper.ForumMapper;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForumService {
    @Autowired
    private ForumMapper forumMapper;


    public IPage getAll(Page page, Integer role, String title) {
        return this.forumMapper.selectForum(page, new QueryWrapper<Forum>().eq(role != null, "role", role)
                                                                     .like(!StringUtil.isNullOrEmpty(title), "title", title));
    }

    public void addForum(Forum forum) {
        forum.insert();
    }

    public void deleteForum(Integer forumId) {
        this.forumMapper.deleteById(forumId);
    }

    public void updateForum(Forum forum) {
        forum.updateById();
    }

    public Forum getOne(Integer forumId) {
        return this.forumMapper.selectOne(new QueryWrapper<Forum>().eq("forum_id",forumId));
    }
}
