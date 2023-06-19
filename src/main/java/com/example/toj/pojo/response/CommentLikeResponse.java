package com.example.toj.pojo.response;

public class CommentLikeResponse extends BaseResponse{
    Integer count = 0;

    public CommentLikeResponse(){

    }

    public CommentLikeResponse(boolean success, String message) {
        super(success, message);
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
