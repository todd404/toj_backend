package com.example.toj.pojo.response.problemResponse;

import com.example.toj.pojo.History;
import com.example.toj.pojo.response.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class HistoryResponse extends BaseResponse {
    List<History> data = new ArrayList<>();

    public List<History> getData() {
        return data;
    }

    public void setData(List<History> data) {
        this.data = data;
    }
}
