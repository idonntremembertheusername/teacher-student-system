package com.ts.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.entity.BaseResp;
import com.ts.entity.Material;
import com.ts.mapper.MaterialMapper;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialService {
    @Autowired
    private MaterialMapper materialMapper;

    public BaseResp getAll(Page page, Integer role, Integer userId, String teacherName, String courseName, Integer status) {
        if(role == 1){
            return BaseResp.success(this.materialMapper.selectPage(page,
                                                    new QueryWrapper<Material>().eq(status!=null,"status",status)
                                                    .like(!StringUtil.isNullOrEmpty(teacherName),"teacher",teacherName)
                                                    .like(!StringUtil.isNullOrEmpty(courseName),"course_name",courseName))
            );

        }else if(role ==2&&userId !=null){
            return BaseResp.success(this.materialMapper.selectPage(page,
                                                    new QueryWrapper<Material>().eq(status!=null,"status",status)
                                                    .like(!StringUtil.isNullOrEmpty(teacherName),"teacher",teacherName)
                                                    .like(!StringUtil.isNullOrEmpty(courseName),"course_name",courseName)
                                                    .eq(userId!=null,"teacher_id",userId))
            );

        }else if(role ==3 &&userId !=null){
            return BaseResp.success(this.materialMapper.selectMaterialStudent(page,userId,new QueryWrapper<Material>()
                            .gt("material_id",0)
                            .eq(status!=null,"status",status)
                            .like(!StringUtil.isNullOrEmpty(teacherName),"teacher",teacherName)
                            .like(!StringUtil.isNullOrEmpty(courseName),"course_name",courseName)
                    ));
        }
        return BaseResp.fail("01","服务器错误");
    }

    public void addMaterial(Material material) {
        material.insert();
    }

    public void deleteMaterial(Integer materialId) {
        this.materialMapper.deleteById(materialId);
    }
}
