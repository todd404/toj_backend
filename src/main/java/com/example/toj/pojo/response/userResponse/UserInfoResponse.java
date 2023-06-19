package com.example.toj.pojo.response.userResponse;

import com.example.toj.pojo.response.BaseResponse;
import com.example.toj.pojo.response.object.UserInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInfoResponse extends BaseResponse {
    @JsonProperty("userinfo")
    UserInfo userInfo = new UserInfo();

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
