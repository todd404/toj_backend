package com.example.toj.pojo.response.userResponse;

import com.example.toj.pojo.User;
import com.example.toj.pojo.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class UserListResponse extends BaseResponse {
    @JsonProperty("userlist")
    List<User> userList = new ArrayList<>();

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
