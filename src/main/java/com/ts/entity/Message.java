package com.ts.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Message extends Model implements Serializable {

    @TableId(value = "message_id",type = IdType.AUTO)
    private Integer messageId;


    @TableField("author_id")
    private Integer authorId;

    @TableField("forum_id")
    private Integer forumId;

    @TableField("author_name")
    private String authorName;

    private String content;

    @TableField("create_time")
    private Date createTime;
    @Override
    protected Serializable pkVal() {
        return messageId;
    }
}
