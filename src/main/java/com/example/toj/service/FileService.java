package com.example.toj.service;

import com.example.toj.pojo.config.FileServiceServerConfig;
import com.example.toj.pojo.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class FileService {
    @Autowired
    FileServiceServerConfig fileServiceServerConfig;

    private BaseResponse setFile(String url, String uuid, String filename) throws JSONException {
        BaseResponse response = new BaseResponse();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        JSONObject request = new JSONObject();

        headers.setContentType(MediaType.APPLICATION_JSON);
        request.put("file_uuid", uuid);
        request.put("file_name", filename);

        HttpEntity<String> requestEntity = new HttpEntity<>(request.toString());
        response = restTemplate.postForObject(url, requestEntity, BaseResponse.class);

        return response;
    }

    public BaseResponse setAvatar(String uuid, String filename) throws JSONException {
        String url = "http://%s/set_avatar".formatted(fileServiceServerConfig.getHost());

        return setFile(url, uuid, filename);
    }

    public BaseResponse setTest(String uuid, String filename) throws JSONException {
        String url = "http://%s/set_test".formatted(fileServiceServerConfig.getHost());

        return setFile(url, uuid, filename);
    }

    public BaseResponse setAnswer(String uuid, String filename) throws JSONException {
        String url = "http://%s/set_answer".formatted(fileServiceServerConfig.getHost());

        return setFile(url, uuid, filename);
    }
}
