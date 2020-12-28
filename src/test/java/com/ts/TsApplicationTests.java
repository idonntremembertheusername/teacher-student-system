package com.ts;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.config.Sha256;
import com.ts.entity.*;
import com.ts.mapper.MaterialMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.util.List;

@SpringBootTest
class TsApplicationTests {

    @Autowired
    private MaterialMapper materialMapper;

    @Test
    void contextLoads() {
        IPage list = materialMapper.selectMaterialStudent(new Page<>(1, 10), 21, new QueryWrapper<Material>());
        System.out.println(list.getRecords());
    }

    @Test
    void demo1(){
//        val list = new User().selectAll();
//        val list = new Cs().selectAll();
//        val list = new Forum().selectAll();
//        val list = new Homework().selectAll();
//        val list = new Material().selectAll();
//        List<User> list = new User().selectAll();
//        list.forEach(user -> {
//            user.deleteById();
//        });

        for(int i=10;i<16;i++){
            Course course = (Course) new Course().selectById(i);
            Cs cs = new Cs();
            cs.setCourseId(course.getCourseId());

            for (int j=51;j<60;j++){
                cs.setStudentId(j);
                cs.insert();
            }
        }
    }


    @Test
    public void demo2(){
        for (int i=0;i<10;i++) {
            User user = new User();
            user.setAccount("admin"+i);
            user.setName("管理员"+i);
            user.setPassword("123");
            user.setRole(1);
            user.setStatus(1);
            user.insertOrUpdate();
        }
        for (int i=0;i<10;i++) {
            User user = new User();
            user.setAccount("教师"+i);
            user.setName("teacher"+i);
            user.setPassword("123");
            user.setRole(2);
            user.setStatus(1);
            user.insertOrUpdate();
        }
        for (int i=0;i<10;i++) {
            User user = new User();
            user.setAccount("student"+i);
            user.setName("学生"+i);
            user.setPassword("123");
            user.setRole(3);
            user.setStatus(1);
            user.insertOrUpdate();
        }
    }

    @Test
    public void demo3(){
        List<User> list = new User().selectAll();
        for (User user: list
             ) {
            user.deleteById();

        }
    }



    @Test
    void contextLoads9() {
        List<User> list = new User().selectAll();
        list.forEach(user -> {
            String password = Sha256.getSHA256(user.getPassword());
            String newPassWord = DigestUtils.md5DigestAsHex(password.getBytes());
            user.setPassword(newPassWord);
            user.updateById();

        });
    }

}
