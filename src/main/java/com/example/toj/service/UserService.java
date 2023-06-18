package com.example.toj.service;

import com.example.toj.mapper.UserMapper;
import com.example.toj.pojo.User;
import com.example.toj.pojo.request.EditUserInfoRequest;
import com.example.toj.pojo.response.BaseResponse;
import com.example.toj.service.storage.TempFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
public class UserService {
    final UserMapper userMapper;
    final TempFileStorageService tempFileStorageService;

    @Autowired
    public UserService(UserMapper userMapper, TempFileStorageService tempFileStorageService) {
        this.userMapper = userMapper;
        this.tempFileStorageService = tempFileStorageService;
    }

    public User login(String username, String password){
        User loginUser = new User();
        loginUser.setUsername(username);
        loginUser.setPassword(password);

        return userMapper.login(loginUser);
    }

    public BaseResponse newUser(String username, String password){
        BaseResponse response = new BaseResponse();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAdmin(false);

        try{
            userMapper.insertUser(user);
            response.setSuccess(true);
        }catch (DataAccessException e){
            response.setSuccess(false);
            if(e.getCause() instanceof SQLIntegrityConstraintViolationException){
                response.setMessage("注册失败：用户名已被占用");
            }else{
                response.setMessage("注册失败：未知错误");
            }
        }

        return response;
    }


    public BaseResponse editUserInfo(EditUserInfoRequest request, User loginUser){
        BaseResponse response = new BaseResponse();

        if(!request.getOldPassword().isEmpty() && !request.getNewPassword().isEmpty()){
            var count = userMapper.updatePassword(loginUser.getId(), request.getOldPassword(), request.getNewPassword());
            if(count > 0){
                response.setSuccess(true);
                response.setMessage("修改密码成功！");
            }
        }if(!request.getAvatarUuid().isEmpty()){
            tempFileStorageService.copyToAvatar(request.getAvatarUuid(), String.valueOf(loginUser.getId()));
            response.setSuccess(true);
            var message = response.getMessage() + "\n头像修改成功";
            response.setMessage(message.trim());
        }

        return response;
    }
}
