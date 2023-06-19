package com.example.toj.pojo.response.object;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageItem {
    @JsonProperty("comment_id")
    private Integer id = -1;
    @JsonProperty("parent_id")
    private Integer parentId = -1;
    @JsonProperty("problem_id")
    private Integer problemId = -1;
    @JsonProperty("problem_title")
    private String title = "";
    @JsonProperty("comment_content")
    private String content = "";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
