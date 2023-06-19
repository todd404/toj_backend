package com.example.toj.pojo.response;

import com.example.toj.pojo.SubComment;

import java.util.ArrayList;
import java.util.List;

public class SubCommentResponse extends BaseResponse{
    List<SubComment> data = new ArrayList<>();

    public List<SubComment> getData() {
        return data;
    }

    public void setData(List<SubComment> data) {
        this.data = data;
    }
}
