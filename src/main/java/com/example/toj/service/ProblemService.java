package com.example.toj.service;

import com.example.toj.mapper.ProblemMapper;
import com.example.toj.pojo.History;
import com.example.toj.pojo.PassRate;
import com.example.toj.pojo.Problem;
import com.example.toj.pojo.User;
import com.example.toj.pojo.request.problemRequest.JudgeReportRequest;
import com.example.toj.pojo.request.problemRequest.JudgeRequest;
import com.example.toj.pojo.request.problemRequest.ProblemRequest;
import com.example.toj.pojo.response.BaseResponse;
import com.example.toj.pojo.response.problemResponse.*;
import com.example.toj.pojo.response.object.ProblemSetItem;
import com.example.toj.service.storage.TempFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProblemService {
    final ProblemMapper problemMapper;
    final TempFileStorageService tempFileStorageService;

    @Autowired
    public ProblemService(ProblemMapper problemMapper, TempFileStorageService tempFileStorageService) {
        this.problemMapper = problemMapper;
        this.tempFileStorageService = tempFileStorageService;
    }

    public ProblemResponse getProblem(Integer problemId){
        var problem = problemMapper.queryProblem(problemId);
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

        List<Problem> allProblem = problemMapper.queryAllProblems();
        List<History> userPassedHistory = problemMapper.queryUserPassedHistory(user.getId());
        List<PassRate> allProblemPassRate = problemMapper.queryAllProblemPassRate();

        Map<Integer, ProblemSetItem> problemSetItemMap = new HashMap<>();
        for(var p : allProblem){
            problemSetItemMap.put(p.getId(), new ProblemSetItem(p));
        }

        for(var history : userPassedHistory){
            var id = history.getProblemId();
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
        problemSetItemMap.forEach((key, value)-> problemSet.add(value));

        response.setProblemSetItemList(problemSet);
        response.setSuccess(true);

        return response;
    }

    public HistoryResponse getSubmitHistory(Integer problemId, User user){
        List<History> historyList = problemMapper.querySubmitHistory(problemId, user.getId());
        HistoryResponse response = new HistoryResponse();

        if(historyList == null){
            response.setSuccess(false);
            response.setMessage("获取提交记录失败: 未知原因");
            return response;
        }

        response.setSuccess(true);
        response.setData(historyList);

        return response;
    }

    public JudgeResponse judge(JudgeRequest judgeRequest, User user){
        JudgeResponse response = new JudgeResponse();

        History history = new History();
        history.setProblemId(judgeRequest.getProblemId());
        history.setCode(judgeRequest.getCode());
        history.setLanguage(judgeRequest.getLanguage());
        history.setUserId(user.getId());
        history.setExecuteTime(-1.0);
        history.setMemory(-1);
        history.setResult("判题中...");

        Integer result = problemMapper.insertHistory(history);

        if(result == 0){
            response.setSuccess(false);
            response.setMessage("判题提交失败: 未知原因");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }

        judgeRequest.setHistoryId(history.getId());
        //TODO: 问题添加运行时间、内存限制列
        judgeRequest.setExecuteTime(5000.0);
        judgeRequest.setMemory(20000);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<JudgeRequest> requestHttpEntity = new HttpEntity<>(judgeRequest);
        response = restTemplate.postForObject("http://localhost/judge", requestHttpEntity, JudgeResponse.class);

        if(response == null){
            response = new JudgeResponse();
            response.setSuccess(false);
            response.setMessage("判题提交失败: 未知原因");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
        response.setSuccess(true);
        return response;
    }

    public StateResponse getState(String uuid){
        String url = "http://localhost/state?uuid=" + uuid;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, StateResponse.class);
    }

    public BaseResponse reportResult(JudgeReportRequest judgeReportRequest){
        History history = new History();
        history.setId(judgeReportRequest.getHistoryId());
        history.setExecuteTime(judgeReportRequest.getExecuteTime());
        history.setMemory(judgeReportRequest.getMemory());
        history.setResult(judgeReportRequest.getResult());

        Integer result = problemMapper.updateHistory(history);
        if(result == null || result == 0){
            return new BaseResponse(false, "");
        }

        return new BaseResponse(true, "");
    }

    @Transactional(rollbackFor = Exception.class)
    public BaseResponse adminAddProblem(ProblemRequest problemRequest) {
        Integer result = problemMapper.insertProblem(problemRequest);
        BaseResponse response = new BaseResponse();

        if(result == null || result == 0){
            response.setMessage("添加问题失败: 未知原因");
            response.setSuccess(false);
            return response;
        }

        try{
            tempFileStorageService.copyToTest(problemRequest.getTestFileUuid(), problemRequest.getId());
            tempFileStorageService.copyToAnswer(problemRequest.getAnswerFileUuid(), problemRequest.getId());
        } catch (IOException e) {
            response.setSuccess(false);
            response.setMessage("添加问题失败: 添加答案、测试文件失败");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }

        response.setSuccess(true);
        return response;
    }

    public BaseResponse adminEditProblem(ProblemRequest problemRequest){
        Integer result = problemMapper.updateProblem(problemRequest);
        BaseResponse response = new BaseResponse();

        if(result == null || result == 0){
            response.setMessage("编辑问题失败: 未知原因");
            response.setSuccess(false);
            return response;
        }
        if(!problemRequest.getAnswerFileUuid().isEmpty() && !problemRequest.getTestFileUuid().isEmpty()){
            try{
                tempFileStorageService.copyToTest(problemRequest.getTestFileUuid(), problemRequest.getId());
                tempFileStorageService.copyToAnswer(problemRequest.getAnswerFileUuid(), problemRequest.getId());
            } catch (IOException e) {
                response.setSuccess(false);
                response.setMessage("编辑问题失败: 修改答案、测试文件失败");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return response;
            }
        }

        response.setSuccess(true);
        return response;
    }

    public BaseResponse adminDeleteProblem(Integer problemId){
        Integer result = problemMapper.deleteProblem(problemId);
        BaseResponse response = new BaseResponse();

        if(result == null || result == 0){
            response.setSuccess(false);
            response.setMessage("删除问题失败: 未知原因");
            return response;
        }

        response.setSuccess(true);
        return response;
    }
}
