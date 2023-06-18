package com.example.toj;

import com.example.toj.mapper.ProblemMapper;
import com.example.toj.pojo.User;
import com.example.toj.service.ProblemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProblemTest {
    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    ProblemService problemService;

    @Test
    void getUserAllPassedHistoryTest(){
        var result = problemMapper.getUserPassedHistory(2);
        return;
    }

    @Test
    void getAllProblemPassRate(){
        var result = problemMapper.getAllProblemPassRate();
        return;
    }

    @Test
    void getProblemSet(){
        User user = new User();
        user.setId(2);
        var result = problemService.getProblemSet(user);
        return;
    }

}
