package com.ts.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Forum extends Model implements Serializable {

    @TableId(value = "forum_id",type = IdType.AUTO)
    private  Integer forumId;

    @TableField("author_id")
    private  Integer authorId;

    private  Integer role;

    private String title;

    private String author;

    private String content;

    @TableField("create_time")
    private Date createTime;

    @TableField("course_id")
    private Integer courseId;

    @TableField(exist = false)
    private Integer answer;

    @Override
    protected Serializable pkVal() {
        return forumId;
    }
}
