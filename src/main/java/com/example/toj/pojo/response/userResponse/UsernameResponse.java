package com.example.toj.pojo.response.userResponse;

import com.example.toj.pojo.response.BaseResponse;

public class UsernameResponse extends BaseResponse {
    String username = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
