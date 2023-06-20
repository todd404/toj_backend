package com.example.toj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public class History {
    private Integer id = -1;
    private Integer userId = -1;
    @JsonProperty("problem_id")
    private Integer problemId = -1;
    private String language = "";
    @JsonIgnore
    private String code = "";
    @JsonProperty("execute_time")
    private Double executeTime = -1.0;
    private Integer memory = -1;
    @JsonProperty("status")
    private String result = "";
    @JsonProperty("submit_time")
    private Date createdAt = new Date(0);

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
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Double executeTime) {
        this.executeTime = executeTime;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
