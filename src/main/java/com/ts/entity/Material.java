package com.ts.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Material extends Model implements Serializable {

    @TableId(value = "material_id",type = IdType.AUTO)
    private Integer materialId;

    @TableField("teacher_id")
    private Integer teacherId;

    private Integer status;

    private String name;

    private String teacher;

    private String path;

    @TableField("create_time")
    private Date createTime;

    @TableField("course_name")
    private String courseName;

    @TableField("course_id")
    private String courseId;

    @Override
    protected Serializable pkVal() {
        return materialId;
    }
}
