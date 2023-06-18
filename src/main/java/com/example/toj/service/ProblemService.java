package com.example.toj.service;

import com.example.toj.mapper.ProblemMapper;
import com.example.toj.pojo.History;
import com.example.toj.pojo.PassRate;
import com.example.toj.pojo.Problem;
import com.example.toj.pojo.User;
import com.example.toj.pojo.response.ProblemResponse;
import com.example.toj.pojo.response.ProblemSetResponse;
import com.example.toj.pojo.response.object.ProblemSetItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProblemService {
    final ProblemMapper problemMapper;

    @Autowired
    public ProblemService(ProblemMapper problemMapper) {
        this.problemMapper = problemMapper;
    }

    public ProblemResponse getProblem(Integer problemId){
        var problem = problemMapper.getProblem(problemId);
        ProblemResponse response = new ProblemResponse();
        if(problem == null){
            response.setSuccess(false);
            return response;
        }

        response = new ProblemResponse(problem);
        response.setSuccess(true);

        return response;
    }

    public ProblemSetResponse getProblemSet(User user){
        ProblemSetResponse response = new ProblemSetResponse();

        List<Problem> allProblem = problemMapper.getAllProblems();
        List<History> userPassedHistory = problemMapper.getUserPassedHistory(user.getId());
        List<PassRate> allProblemPassRate = problemMapper.getAllProblemPassRate();

        Map<Integer, ProblemSetItem> problemSetItemMap = new HashMap<>();
        for(var p : allProblem){
            problemSetItemMap.put(p.getId(), new ProblemSetItem(p));
        }

        for(var history : userPassedHistory){
            var id = history.getId();
            ProblemSetItem item = problemSetItemMap.get(id);

            if(item != null){
                item.setState(true);
            }
        }

        for(var passRate : allProblemPassRate){
            var id = passRate.getProblemId();
            ProblemSetItem item = problemSetItemMap.get(id);

            if(item != null){
                //转换为百分比
                item.setAcceptanceRate(passRate.getPassRate() * 100);
            }
        }

        List<ProblemSetItem> problemSet = new ArrayList<>();
        problemSetItemMap.forEach((key, value)->{
            problemSet.add(value);
        });

        response.setProblemSetItemList(problemSet);
        response.setSuccess(true);

        return response;
    }
}
