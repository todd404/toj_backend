package com.example.toj.pojo.response.object;

import com.example.toj.pojo.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInfo {
    @JsonProperty("user_id")
    private Integer userId = -1;

    @JsonProperty("user_name")
    private String userName = "";

    @JsonProperty("is_admin")
    private Boolean isAdmin = false;

    @JsonProperty("avatar")
    private String avatar = "";

    public UserInfo(){

    }

    public UserInfo(User user){
        this.userName = user.getUsername();
        this.userId = user.getId();
        this.isAdmin = user.getAdmin();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
