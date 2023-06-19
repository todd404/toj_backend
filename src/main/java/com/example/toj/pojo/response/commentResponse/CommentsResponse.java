package com.example.toj.pojo.response.commentResponse;

import com.example.toj.pojo.ParentComment;
import com.example.toj.pojo.response.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class CommentsResponse extends BaseResponse {
    List<ParentComment> data = new ArrayList<>();

    public List<ParentComment> getData() {
        return data;
    }

    public void setData(List<ParentComment> data) {
        this.data = data;
    }
}
