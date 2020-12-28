package com.ts.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.entity.BaseResp;
import com.ts.entity.Homework;
import com.ts.entity.Material;
import com.ts.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping
    public BaseResp getAll(@RequestParam(required = false) Integer status,
                           @RequestParam(required = false) String courseName,
                           @RequestParam(required = false) String teacherName,
                           @RequestParam(required = false) Integer userId,
                           @RequestParam(required = false) Integer role,
                           Page page){

        return this.materialService.getAll(page,role,userId,teacherName,courseName,status);
    }

    @PostMapping
    public BaseResp addMaterial(@RequestBody Material material){
        this.materialService.addMaterial(material);
        return BaseResp.success("上传作业成功");
    }

    @DeleteMapping
    public BaseResp deleteMaterial(@RequestParam(required = false) Integer materialId){
        this.materialService.deleteMaterial(materialId);
        return BaseResp.success("删除作业成功");
    }

}
