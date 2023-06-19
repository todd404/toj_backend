package com.example.toj.pojo.request.problemRequest;

import com.example.toj.pojo.Problem;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProblemRequest extends Problem {
    @JsonProperty("problem_id")
    Integer id = 0;
    @JsonProperty("test_file_uuid")
    String testFileUuid = "";
    @JsonProperty("answer_file_uuid")
    String answerFileUuid = "";

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTestFileUuid() {
        return testFileUuid;
    }

    public void setTestFileUuid(String testFileUuid) {
        this.testFileUuid = testFileUuid;
    }

    public String getAnswerFileUuid() {
        return answerFileUuid;
    }

    public void setAnswerFileUuid(String answerFileUuid) {
        this.answerFileUuid = answerFileUuid;
    }
}
