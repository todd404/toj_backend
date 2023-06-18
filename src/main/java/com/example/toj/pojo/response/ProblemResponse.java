package com.example.toj.pojo.response;

import com.example.toj.pojo.Problem;

public class ProblemResponse extends Problem{
    Boolean success = false;

    public ProblemResponse(){

    }
    public ProblemResponse(Problem problem){
        super(problem);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
