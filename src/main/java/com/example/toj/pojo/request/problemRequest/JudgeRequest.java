package com.example.toj.pojo.request.problemRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JudgeRequest {
    @JsonProperty("problem_id")
    private Integer problemId;
    private String code;
    private String language;
    @JsonProperty("execute_time")
    private Double executeTime;
    private Integer memory;
    @JsonProperty("history_id")
    private Integer historyId;

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }
}
