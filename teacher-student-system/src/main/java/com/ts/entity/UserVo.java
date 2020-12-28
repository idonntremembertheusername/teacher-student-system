package com.ts.entity;

import lombok.Data;

@Data
public class UserVo extends User {

    private  Integer fNumber;

    private  Integer mNumber;

    private String courseName;
}
