package com.ts.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.config.Sha256;
import com.ts.entity.BaseResp;
import com.ts.entity.DashboardVo;
import com.ts.entity.User;
import com.ts.mapper.UserMapper;
import io.netty.util.internal.StringUtil;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Base64;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    public IPage getAll(Integer status, Integer role, String name, Integer current, Integer size) {
        return this.userMapper.selectPageCount(new Page(current, size), new QueryWrapper<User>().eq(status != null, "status", status)
                .eq(role != null, "role", role)
                .like(!StringUtil.isNullOrEmpty(name), "name", name));
    }


    public String changeStatus(Integer userID) {
        User user = (User)new User().selectById(userID);
        if(user.getStatus() == 2){
            user.setStatus(1);
            user.updateById();
            return "账户启用成功";
        }else if(user.getStatus() == 1){
            user.setStatus(2);
            user.updateById();
            return "账户已注销";
        }
        return "服务器错误";
    }

    public String resetPassword(Integer id) {
        User user = this.userMapper.selectById(id);
        String account = Sha256.getSHA256(user.getAccount());
        String newPassWord = DigestUtils.md5DigestAsHex(account.getBytes());
        user.setPassword(newPassWord);
        user.updateById();
        return "重置密码成功";
    }

    public User selectByAccount(String account) {
        return this.userMapper.selectOne(new QueryWrapper<User>().eq(!StringUtil.isNullOrEmpty(account),"account",account));
    }

    public String saveToken(User member) {
        String token = initToken(member.getAccount());
        redisService.setValue(token,member);
        return token;
    }

    private String initToken(String userName) {
        String str = System.currentTimeMillis() + userName + System.currentTimeMillis();
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public void logout(String token) {
        if(redisService.hasKey(token)){
            redisService.delete(token);
        }
    }

    public BaseResp ChangePassword(Integer userId, String oldPassword, String newPassword) {
        User user = this.userMapper.selectById(userId);
//        if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(oldPassword.getBytes()))){
//            return BaseResp.fail("01","旧密码错误");
//        }
        user.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
        user.updateById();
        return BaseResp.success("密码修改成功");

    }

    public BaseResp Changename(Integer userId, String name) {
        User user = this.userMapper.selectById(userId);
        user.setName(name);
        user.updateById();
        return BaseResp.success("修改名称成功");
    }

    public IPage getStudent(Page page, Integer courseId, String studentName, Integer userId) {
        if(courseId == null) {
            return this.userMapper.selectStudent(page, userId, new QueryWrapper<User>()
                    .eq("status", 1)
                    .like(!StringUtil.isNullOrEmpty(studentName), "name", studentName));
        }
        if(courseId!=null){
            return this.userMapper.selectCourseStudent(page, userId,courseId, new QueryWrapper<User>()
                    .eq("status", 1)
                    .like(!StringUtil.isNullOrEmpty(studentName), "name", studentName));
        }
        return null;
    }

    public DashboardVo getCount(Integer userId, Integer role) {
        DashboardVo dashboardVo = this.userMapper.selectDashboardCount(userId, role);
        dashboardVo.setForumNoAnswer(dashboardVo.getForumPublish()-dashboardVo.getForumAnswer());
        return dashboardVo;
    }
}
