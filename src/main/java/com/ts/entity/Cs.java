package com.ts.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
public class Cs extends Model implements Serializable {

    @TableId(value = "cs_id",type = IdType.AUTO)
    private Integer csId;

    @TableField("course_id")
    private Integer courseId;

    @TableField("student_id")
    private Integer studentId;
    @Override
    protected Serializable pkVal() {
        return csId;
    }
}
