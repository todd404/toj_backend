package com.example.toj.pojo.request.commentRequest;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class SubmitCommentRequest {
    @JsonProperty("problem_id")
    private Integer problemId = -1;

    @JsonProperty("parent_id")
    private Integer parentId = -1;

    @JsonProperty("comment")
    private String content = "";

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @JsonGetter("comment")
    public String getContent() {
        return content;
    }

    @JsonSetter("comment")
    public void setContent(String content) {
        this.content = content;
    }
}
