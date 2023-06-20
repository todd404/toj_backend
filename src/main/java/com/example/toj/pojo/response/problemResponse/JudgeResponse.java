package com.example.toj.pojo.response.problemResponse;

import com.example.toj.pojo.response.BaseResponse;

public class JudgeResponse extends BaseResponse {
    String uuid = "";

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
