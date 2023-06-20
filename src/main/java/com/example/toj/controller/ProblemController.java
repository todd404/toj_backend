package com.example.toj.controller;

import com.example.toj.pojo.User;
import com.example.toj.pojo.request.problemRequest.ProblemRequest;
import com.example.toj.pojo.response.BaseResponse;
import com.example.toj.pojo.response.problemResponse.HistoryResponse;
import com.example.toj.pojo.response.problemResponse.ProblemResponse;
import com.example.toj.pojo.response.problemResponse.ProblemSetResponse;
import com.example.toj.service.ProblemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/history")
    public HistoryResponse history(@RequestParam("problem_id") Integer problemId,
                                   HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if(user == null){
            HistoryResponse response = new HistoryResponse();
            response.setSuccess(false);
            response.setMessage("获取提交记录失败: 未登录");
            return response;
        }

        return problemService.getSubmitHistory(problemId, user);
    }

    @PostMapping("/add-problem")
    public BaseResponse adminAddProblem(@RequestBody ProblemRequest problem,
                                        HttpSession session)
    {
        User user = (User) session.getAttribute("user");

        if(user == null || !user.getAdmin()){
            return new BaseResponse(false, "添加问题失败: 请登录管理员账户");
        }

        return problemService.adminAddProblem(problem);
    }

    @PostMapping("/edit-problem")
    public BaseResponse adminEditProblem(@RequestBody ProblemRequest problem,
                                         HttpSession session)
    {
        User user = (User) session.getAttribute("user");

        if(user == null || !user.getAdmin()){
            return new BaseResponse(false, "修改问题失败: 请登录管理员账户");
        }

        return problemService.adminEditProblem(problem);
    }

    @GetMapping("delete-problem")
    public BaseResponse adminDeleteProblem(@RequestParam("id") Integer problemId,
                                           HttpSession session)
    {
        User user = (User) session.getAttribute("user");

        if(user == null || !user.getAdmin()){
            return new BaseResponse(false, "删除问题失败: 请登录管理员账户");
        }

        return problemService.adminDeleteProblem(problemId);
    }
}
