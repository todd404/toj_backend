package com.example.toj.pojo.response.problemResponse;

import com.example.toj.pojo.response.object.LanguageConfigItem;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class LanguageConfigResponse {
    @JsonProperty("language_server_uri")
    private String languageServerUri = "";
    @JsonProperty("language_list")
    private List<LanguageConfigItem> languageList = new ArrayList<>();

    public String getLanguageServerUri() {
        return languageServerUri;
    }

    public void setLanguageServerUri(String languageServerUri) {
        this.languageServerUri = languageServerUri;
    }

    public List<LanguageConfigItem> getLanguageList() {
        return languageList;
    }

    public void setLanguageList(List<LanguageConfigItem> languageList) {
        this.languageList = languageList;
    }
}
