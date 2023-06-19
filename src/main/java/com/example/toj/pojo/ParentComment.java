package com.example.toj.pojo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class ParentComment {
    private Integer id = -1;
    @JsonProperty("user_id")
    private Integer userId = -1;
    @JsonProperty("user_name")
    private String username = "";
    @JsonProperty("is_user_like")
    private Boolean isLike = false;
    private String content = "";
    @JsonProperty("like")
    private Integer likeCount = 0;
    @JsonProperty("sub_comment")
    private Integer subCommentCount = 0;
    private String avatar = "";

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
        this.avatar = "/avatar/%d.png".formatted(id);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonGetter("is_user_like")
    public Boolean getLike() {
        return isLike;
    }

    @JsonSetter("is_user_like")
    public void setLike(Boolean like) {
        isLike = like;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonGetter("like")
    public Integer getLikeCount() {
        return likeCount;
    }

    @JsonSetter("like")
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getSubCommentCount() {
        return subCommentCount;
    }

    public void setSubCommentCount(Integer subCommentCount) {
        this.subCommentCount = subCommentCount;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
