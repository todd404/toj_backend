package com.example.toj.pojo.response.commentResponse;

import com.example.toj.pojo.response.BaseResponse;
import com.example.toj.pojo.response.object.SubmitCommentData;

public class SubmitCommentResponse extends BaseResponse {
    private SubmitCommentData data = new SubmitCommentData();

    public SubmitCommentData getData() {
        return data;
    }

    public void setData(SubmitCommentData data) {
        this.data = data;
    }
}
