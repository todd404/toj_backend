package com.example.toj.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Problem {
    private Integer id = 0;
    private String content = "";
    private String title = "";
    private Integer difficulty = 1;
    @JsonProperty("time_limit")
    private Double timeLimit = -1.0;
    @JsonProperty("memory_limit")
    private Integer memoryLimit = -1;

    public Problem(){

    }
    public Problem(Problem problem) {
        this.id = problem.id;
        this.content = problem.content;
        this.title = problem.title;
        this.difficulty = problem.difficulty;
        this.timeLimit = problem.timeLimit;
        this.memoryLimit = problem.memoryLimit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Double getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Double timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Integer getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(Integer memoryLimit) {
        this.memoryLimit = memoryLimit;
    }
}
