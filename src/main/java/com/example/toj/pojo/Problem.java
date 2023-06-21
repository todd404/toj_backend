package com.example.toj.pojo;

public class Problem {
    private Integer id = 0;
    private String content = "";
    private String title = "";
    private Integer difficulty = 1;

    public Problem(){

    }
    public Problem(Problem problem) {
        this.id = problem.id;
        this.content = problem.content;
        this.title = problem.title;
        this.difficulty = problem.difficulty;
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
}
