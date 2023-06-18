package com.example.toj.pojo.response.object;

import com.example.toj.pojo.Problem;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProblemSetItem {
    private Integer id = -1;
    private Boolean state = false;
    private String title = "";
    @JsonProperty("acceptance_rate")
    private Double acceptanceRate = 0.0;
    private Integer difficulty = 1;

    public ProblemSetItem(){

    }

    public ProblemSetItem(Problem problem){
        this.id = problem.getId();
        this.title = problem.getTitle();
        this.difficulty = problem.getDifficulty();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getAcceptanceRate() {
        return acceptanceRate;
    }

    public void setAcceptanceRate(Double acceptanceRate) {
        this.acceptanceRate = acceptanceRate;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }
}
