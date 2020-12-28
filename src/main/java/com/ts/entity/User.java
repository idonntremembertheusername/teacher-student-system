package com.ts.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

@Data
public class User extends Model implements Serializable {

    @TableId(value = "user_id",type = IdType.AUTO)
    private Integer userId;

    private String account;

    private String password;

    private String name;

    private Integer status;

    private Integer role;

    @TableField(exist = false)
    private String token;

    @Override
    protected Serializable pkVal() {
        return userId;
    }
}
