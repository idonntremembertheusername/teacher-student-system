package com.ts.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.entity.BaseResp;
import com.ts.entity.DashboardVo;
import com.ts.entity.User;
import com.ts.service.RedisService;
import com.ts.service.UserService;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    public UserService userService;

    @Autowired
    private RedisService redisService;

    @PostMapping("/Login")
    public BaseResp login(@RequestBody User user){
        if (StringUtil.isNullOrEmpty(user.getAccount()) || StringUtil.isNullOrEmpty(user.getPassword())) {
            return BaseResp.fail("01","用户名和密码不能为空");
        }

        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        User loginUser = this.userService.selectByAccount(user.getAccount());
        if (loginUser == null) {
            return BaseResp.fail("02", "用户名或密码错误");
        }

        String token = userService.saveToken(loginUser);
        loginUser.setToken(token);
        return BaseResp.success(loginUser,"登陆成功");
    }

    @RequestMapping("/Logout")
    public BaseResp logout(HttpServletRequest request) {
        String token = request.getHeader("x-auth-token");
        if (token != null) {
            userService.logout(token);
        }
        return BaseResp.success("成功退出");
    }

    @GetMapping
    public BaseResp getAll(@RequestParam(required = false) Integer status,
                           @RequestParam(required = false) Integer role,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) Integer current,
                           @RequestParam(required = false) Integer size){
        IPage page = this.userService.getAll(status,role,name,current,size);
        return BaseResp.success(page);
    }

    @PatchMapping
    public BaseResp changeStatus(@RequestParam(required = false) Integer userId){
        return BaseResp.success(this.userService.changeStatus(userId));
    }



    @PatchMapping("/{id}")
    public BaseResp resetPassword(@PathVariable Integer id){
        return BaseResp.success(this.userService.resetPassword(id));
    }

    @PatchMapping("/ChangePassword")
    public BaseResp ChangePassword(@RequestParam(required = false) String newPassword,
                                   @RequestParam(required = false) String oldPassword,
                                   @RequestParam(required = false) Integer userId){
        return this.userService.ChangePassword(userId,oldPassword,newPassword);
    }

    @PatchMapping("/ChangeName")
    public BaseResp ChangeName(@RequestParam(required = false) String name,
                                   @RequestParam(required = false) Integer userId){
        return this.userService.Changename(userId,name);
    }

    @GetMapping("student")
    public BaseResp getStudent(@RequestParam(required = false) Integer userId,
                           @RequestParam(required = false) Integer courseId,
                           @RequestParam(required = false) String studentName,
                           Page page){
        IPage list = this.userService.getStudent(page,courseId,studentName,userId);
        return BaseResp.success(list);
    }

    @GetMapping("count")
    public BaseResp getCount(@RequestParam(required = false) Integer userId,
                               @RequestParam(required = false) Integer role){
        DashboardVo dashboardVo = this.userService.getCount(userId,role);
        return BaseResp.success(dashboardVo);
    }

}
