package com.example.toj.pojo.response.object;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubmitCommentData {
    @JsonProperty("parent_id")
    private Integer parentId = -1;
    @JsonProperty("comment_id")
    private Integer commentId = -1;

    public SubmitCommentData() {
    }

    public SubmitCommentData(Integer parentId, Integer commentId) {
        this.parentId = parentId;
        this.commentId = commentId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }
}
