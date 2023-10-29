package com.example.toj.service;

import com.example.toj.mapper.ProblemMapper;
import com.example.toj.pojo.History;
import com.example.toj.pojo.PassRate;
import com.example.toj.pojo.Problem;
import com.example.toj.pojo.User;
import com.example.toj.pojo.config.JudgeServerConfig;
import com.example.toj.pojo.request.problemRequest.JudgeReportRequest;
import com.example.toj.pojo.request.problemRequest.JudgeRequest;
import com.example.toj.pojo.request.problemRequest.ProblemRequest;
import com.example.toj.pojo.response.BaseResponse;
import com.example.toj.pojo.response.problemResponse.*;
import com.example.toj.pojo.response.object.ProblemSetItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProblemService {
    @Resource
    private Environment environment;

    final ProblemMapper problemMapper;
    final FileService fileService;

    final JudgeServerConfig judgeServerConfig;

    @Autowired
    public ProblemService(ProblemMapper problemMapper, FileService fileService, JudgeServerConfig judgeServerConfig) {
        this.problemMapper = problemMapper;
        this.fileService = fileService;
        this.judgeServerConfig = judgeServerConfig;
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

    public LanguageConfigResponse getLanguageConfig(){
        File jsonFile = null;
        LanguageConfigResponse response = new LanguageConfigResponse();
        try {
            String jsonFilePath = environment.getProperty("language-config-path", "");
            jsonFilePath = jsonFilePath.isEmpty() ? "classpath:language_config.json" : jsonFilePath;

            jsonFile = ResourceUtils.getFile(jsonFilePath);

            ObjectMapper objectMapper = new ObjectMapper();
            response = objectMapper.readValue(jsonFile, LanguageConfigResponse.class);
        } catch (IOException e) {
            return response;
        }

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

        Problem problemLimit = problemMapper.queryProblemLimit(judgeRequest.getProblemId());
        if(problemLimit == null){
            response.setSuccess(false);
            response.setMessage("判题提交失败: 问题不存在");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
        judgeRequest.setExecuteTime(problemLimit.getTimeLimit());
        judgeRequest.setMemory(problemLimit.getMemoryLimit());

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<JudgeRequest> requestHttpEntity = new HttpEntity<>(judgeRequest);
        response = restTemplate.postForObject("http://%s/judge".formatted(judgeServerConfig.getHost()), requestHttpEntity, JudgeResponse.class);

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
        String url = "http://%s/state?uuid=".formatted(judgeServerConfig.getHost()) + uuid;
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
            String test_file_uuid = problemRequest.getTestFileUuid();
            String answer_file_uuid = problemRequest.getAnswerFileUuid();
            String problem_id = String.valueOf(problemRequest.getId());

            var res_test = fileService.setTest(test_file_uuid, problem_id);
            var res_answer = fileService.setAnswer(answer_file_uuid, problem_id);

            if(!res_test.getSuccess() || !res_answer.getSuccess()){
                throw new Exception(res_test.getMessage() + " " + res_answer.getMessage());
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("添加问题失败: 添加答案、测试文件失败");
            e.printStackTrace();
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
                String test_file_uuid = problemRequest.getTestFileUuid();
                String answer_file_uuid = problemRequest.getAnswerFileUuid();
                String problem_id = String.valueOf(problemRequest.getId());

                var res_test = fileService.setTest(test_file_uuid, problem_id);
                var res_answer = fileService.setAnswer(answer_file_uuid, problem_id);

                if(!res_test.getSuccess() || !res_answer.getSuccess()){
                    throw new Exception(res_test.getMessage() + " " + res_answer.getMessage());
                }
            } catch (Exception e) {
                response.setSuccess(false);
                response.setMessage("编辑问题失败: 修改答案、测试文件失败");
                e.printStackTrace();
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
