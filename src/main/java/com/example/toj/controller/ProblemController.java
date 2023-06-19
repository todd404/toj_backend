package com.example.toj.controller;

import com.example.toj.pojo.Problem;
import com.example.toj.pojo.User;
import com.example.toj.pojo.response.ProblemResponse;
import com.example.toj.pojo.response.ProblemSetResponse;
import com.example.toj.service.ProblemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProblemController {
    final ProblemService problemService;

    @Autowired
    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @GetMapping("/problemset")
    public ProblemSetResponse problemSet(HttpSession session){
        User user = (User) session.getAttribute("user");
        user = (user == null) ? new User() : user;

        return problemService.getProblemSet(user);
    }

    @GetMapping("/problem")
    public ProblemResponse problem(@RequestParam("problem_id") Integer problemId){
        return problemService.getProblem(problemId);
    }
}