package com.example.toj.pojo.response.problemResponse;

import com.example.toj.pojo.response.BaseResponse;
import com.example.toj.pojo.response.object.ProblemSetItem;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ProblemSetResponse extends BaseResponse {
    @JsonProperty("problemset")
    List<ProblemSetItem> problemSetItemList = new ArrayList<>();

    public List<ProblemSetItem> getProblemSetItemList() {
        return problemSetItemList;
    }

    public void setProblemSetItemList(List<ProblemSetItem> problemSetItemList) {
        this.problemSetItemList = problemSetItemList;
    }
}
