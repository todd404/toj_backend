package com.example.toj.controller;

import com.example.toj.pojo.User;
import com.example.toj.pojo.request.userRequset.AccountRequest;
import com.example.toj.pojo.request.userRequset.EditUserInfoRequest;
import com.example.toj.pojo.response.BaseResponse;
import com.example.toj.pojo.response.userResponse.UserInfoResponse;
import com.example.toj.pojo.response.object.UserInfo;
import com.example.toj.pojo.response.userResponse.UserListResponse;
import com.example.toj.pojo.response.userResponse.UsernameResponse;
import com.example.toj.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public BaseResponse login(@RequestBody AccountRequest accountRequest,
                              HttpSession session,
                              HttpServletResponse servletResponse)
    {
        BaseResponse res = new BaseResponse();
        User user = userService.login(accountRequest.getUsername(),
                                      accountRequest.getPassword());

        if(user != null){
            session.setAttribute("user", user);
            Cookie cookie = new Cookie("JSESSIONID", session.getId());
            cookie.setMaxAge(24 * 60);
            servletResponse.addCookie(cookie);

            res.setSuccess(true);
            res.setMessage("登录成功!");
        }else{
            res.setSuccess(false);
            res.setMessage("登录失败：用户名或密码错误！");
        }

        return res;
    }

    @GetMapping("/userinfo")
    public UserInfoResponse userInfo(HttpSession session){
        UserInfoResponse res = new UserInfoResponse();
        User user = (User) session.getAttribute("user");
        if(user != null){
            res.setSuccess(true);
            UserInfo userInfo = new UserInfo(user);
            userInfo.setAvatar("/avatar/%d.png".formatted(user.getId()));
            res.setUserInfo(userInfo);
        }else{
            res.setSuccess(false);
            UserInfo userInfo = new UserInfo();
            userInfo.setAvatar("/avatar/default.png");
            res.setUserInfo(userInfo);
            res.setMessage("未登录");
        }

        return res;
    }

    @GetMapping("/logout")
    public BaseResponse logout(HttpSession session, HttpServletResponse servletResponse){
        BaseResponse res = new BaseResponse();

        User user = (User) session.getAttribute("user");
        if(user != null){
            session.removeAttribute("user");
            Cookie cookie = new Cookie("JSESSIONID", "");
            cookie.setMaxAge(0);
            servletResponse.addCookie(cookie);

            res.setSuccess(true);
            res.setMessage("退出成功!");
        }else{
            res.setSuccess(false);
            res.setMessage("退出失败!");
        }

        return res;
    }

    @PostMapping("/register")
    public BaseResponse register(@RequestBody AccountRequest accountRequest){
        return userService.newUser(accountRequest.getUsername(), accountRequest.getPassword());
    }

    @PostMapping("/edit-userinfo")
    public BaseResponse editUserInfo(@RequestBody EditUserInfoRequest editUserInfoRequest,
                                     HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if(user != null){
            return userService.editUserInfo(editUserInfoRequest, user);
        }else{
            return new BaseResponse(false, "未登录！");
        }
    }

    //=👇=Admin=👇=//
    @GetMapping("/username")
    public UsernameResponse adminUsername(@RequestParam("userid") Integer userId,
                                          HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if(user == null || !user.getAdmin()){
            UsernameResponse response = new UsernameResponse();
            response.setSuccess(false);
            response.setMessage("获取用户名失败: 请登录管理员账户");
            return response;
        }

        return userService.adminGetUsernameById(userId);
    }

    @GetMapping("/userlist")
    public UserListResponse adminUserList(HttpSession session){
        User user = (User) session.getAttribute("user");

        if(user == null || !user.getAdmin()){
            UserListResponse response = new UserListResponse();
            response.setSuccess(false);
            response.setMessage("获取列表: 请登录管理员账户");
            return response;
        }

        return userService.adminGetUserList();
    }

    @PostMapping("/edit-user")
    public BaseResponse adminEditUser(@RequestBody User userInfo,
                                      HttpSession session)
    {
        User user = (User) session.getAttribute("user");

        if(user == null || !user.getAdmin()){
            return new BaseResponse(false, "修改用户信息失败: 请登录管理员账户");
        }

        return userService.adminEditUser(userInfo);
    }

    @GetMapping("/delete-user")
    public BaseResponse adminDeleteUser(@RequestParam("id") Integer userId,
                                        HttpSession session)
    {
        User user = (User) session.getAttribute("user");

        if(user == null || !user.getAdmin()){
            return new BaseResponse(false, "删除用户失败: 请登录管理员账户");
        }

        return userService.adminDeleteUser(userId);
    }

}
