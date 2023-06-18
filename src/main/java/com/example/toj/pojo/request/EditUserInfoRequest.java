package com.example.toj.pojo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EditUserInfoRequest {
    @JsonProperty("user_name")
    String userName = "";

    @JsonProperty("avatar_file_uuid")
    String avatarUuid = "";

    @JsonProperty("old_password")
    String oldPassword = "";

    @JsonProperty("new_password")
    String newPassword = "";

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarUuid() {
        return avatarUuid;
    }

    public void setAvatarUuid(String avatarUuid) {
        this.avatarUuid = avatarUuid;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
