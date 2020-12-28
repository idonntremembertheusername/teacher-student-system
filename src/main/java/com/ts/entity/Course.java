package com.ts.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Course extends Model implements Serializable {

    @TableId(value = "course_id",type = IdType.AUTO)
    private Integer courseId;

    @TableField("teacher_id")
    private Integer teacherId;

    private Integer status;

    private String name;

    private String code;

    @TableField("teacher_name")
    private String teacherName;

    private String content;

    @TableField("create_time")
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return courseId;
    }
}
