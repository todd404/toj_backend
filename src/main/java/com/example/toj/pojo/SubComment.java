package com.example.toj.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubComment {
    private Integer id = -1;
    @JsonProperty("user_id")
    private Integer userId = -1;
    @JsonProperty("user_name")
    private String userName = "";
    @JsonProperty("user_avatar")
    private String userAvatar = "";
    private String content = "";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
        this.userAvatar = "/avatar/%d.png".formatted(userId);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
