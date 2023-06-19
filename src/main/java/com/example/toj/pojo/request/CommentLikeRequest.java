package com.example.toj.pojo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentLikeRequest {
    @JsonProperty("comment_id")
    Integer commentId = -1;
    @JsonProperty("is_like")
    Boolean isLike = false;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Boolean getLike() {
        return isLike;
    }

    public void setLike(Boolean like) {
        isLike = like;
    }
}
