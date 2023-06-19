package com.example.toj.pojo.response;

import com.example.toj.pojo.ParentComment;

import java.util.ArrayList;
import java.util.List;

public class CommentsResponse extends BaseResponse{
    List<ParentComment> data = new ArrayList<>();

    public List<ParentComment> getData() {
        return data;
    }

    public void setData(List<ParentComment> data) {
        this.data = data;
    }
}
