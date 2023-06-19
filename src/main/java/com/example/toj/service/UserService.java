package com.example.toj.service;

import com.example.toj.mapper.UserMapper;
import com.example.toj.pojo.User;
import com.example.toj.pojo.request.userRequset.EditUserInfoRequest;
import com.example.toj.pojo.response.BaseResponse;
import com.example.toj.pojo.response.userResponse.UserListResponse;
import com.example.toj.pojo.response.userResponse.UsernameResponse;
import com.example.toj.service.storage.TempFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

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
        StringBuilder resultMessage = new StringBuilder();
        boolean resultSuccess = true;

        if(!request.getOldPassword().isEmpty() && !request.getNewPassword().isEmpty()){
            var count = userMapper.updatePassword(loginUser.getId(), request.getOldPassword(), request.getNewPassword());
            if(count > 0){
                resultMessage.append("修改密码成功!\n");
            }else{
                resultSuccess = false;
                resultMessage.append("修改密码失败：旧密码不正确\n");
            }
        }if(!request.getAvatarUuid().isEmpty()){
            try {
                tempFileStorageService.copyToAvatar(request.getAvatarUuid(), String.valueOf(loginUser.getId()));
            } catch (IOException e) {
                resultSuccess = false;
                resultMessage.append("修改头像失败!\n");
            }
            resultMessage.append("修改头像成功!\n");
        }

        response.setSuccess(resultSuccess);
        response.setMessage(resultMessage.toString().trim());
        return response;
    }

    public UsernameResponse adminGetUsernameById(Integer userId){
        String username = userMapper.queryUsernameById(userId);

        UsernameResponse response = new UsernameResponse();
        if(username == null){
            response.setSuccess(false);
            response.setMessage("获取用户名失败: 未知错误");
            return response;
        }

        response.setSuccess(true);
        response.setUsername(username);

        return response;
    }

    public UserListResponse adminGetUserList(){
        List<User> userList = userMapper.queryAllUser();
        UserListResponse response = new UserListResponse();

        if(userList == null){
            response.setSuccess(false);
            response.setMessage("获取用户列表失败: 未知错误");
            return response;
        }

        response.setUserList(userList);
        response.setSuccess(true);

        return response;
    }

    public BaseResponse adminEditUser(User userInfo){
        Integer result = userMapper.updateUser(userInfo);
        BaseResponse response = new BaseResponse();

        if(result == null || result == 0){
            response.setSuccess(false);
            response.setMessage("修改用户信息失败: 未知原因");
            return response;
        }

        response.setSuccess(true);
        return response;
    }
}
