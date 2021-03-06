package com.ts.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Homework extends Model implements Serializable {

    @TableId(value = "homework_id",type = IdType.AUTO)
    private Integer homeworkId;

    @TableField("course_id")
    private Integer courseId;

    private Integer score;

    private String name;

    @TableField("course_name")
    private String courseName;

    private String author;

    @TableField("create_time")
    private Date createTime;

    @TableField("user_id")
    private Integer userId;

    @Override
    protected Serializable pkVal() {
        return homeworkId;
    }
}
