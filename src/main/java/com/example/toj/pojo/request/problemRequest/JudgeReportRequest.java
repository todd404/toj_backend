package com.example.toj.pojo.request.problemRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JudgeReportRequest {
    @JsonProperty("history_id")
    Integer historyId = -1;
    @JsonProperty("execute_time")
    Double executeTime = -1.0;
    Integer memory = -1;
    String result = "";

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
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
}
