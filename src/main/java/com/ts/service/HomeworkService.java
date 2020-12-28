package com.ts.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.entity.BaseResp;
import com.ts.entity.Homework;
import com.ts.entity.Material;
import com.ts.mapper.HomeworkMapper;
import io.netty.util.internal.StringUtil;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeworkService {

    @Autowired
    private HomeworkMapper homeworkMapper;

    public BaseResp getAll(Page page, String courseName, String studentName, Integer userId, Integer role) {
        if(role == 1){
            return BaseResp.success(this.homeworkMapper.selectPage(page,
                    new QueryWrapper<Homework>().like(!StringUtil.isNullOrEmpty(studentName),"author",studentName)
                                                .like(!StringUtil.isNullOrEmpty(courseName),"course_name",courseName))
            );

        }else if(role ==2&&userId !=null){
            return BaseResp.success(this.homeworkMapper.selectHomeworkStudent(page,userId,
                    new QueryWrapper<Homework>().gt("homework_id",0)
                                                .like(!StringUtil.isNullOrEmpty(studentName),"author",studentName)
                                                .like(!StringUtil.isNullOrEmpty(courseName),"course_name",courseName))
            );

        }else if(role ==3 &&userId !=null){
            return BaseResp.success(this.homeworkMapper.selectPage(page,
                    new QueryWrapper<Homework>().like(!StringUtil.isNullOrEmpty(studentName),"author",studentName)
                                                .like(!StringUtil.isNullOrEmpty(courseName),"course_name",courseName)
                                                .eq(userId!=null,"user_id",userId)));
        }
        return BaseResp.fail("01","服务器错误");
    }

    public void addHomework(Homework homework) {
        homework.insert();
    }

    public void deleteHomework(Integer homeworkId) {
        this.homeworkMapper.deleteById(homeworkId);
    }

    public void setScore(Integer homeworkId, Integer score) {
        Homework homework = this.homeworkMapper.selectById(homeworkId);
        homework.setScore(score);
        homework.updateById();
    }
}
