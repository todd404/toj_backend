package com.example.toj.pojo.response.commentResponse;

import com.example.toj.pojo.response.BaseResponse;
import com.example.toj.pojo.response.object.MessageItem;

import java.util.ArrayList;
import java.util.List;

public class MessagesResponse extends BaseResponse {
    List<MessageItem> messages = new ArrayList<>();

    public List<MessageItem> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageItem> messages) {
        this.messages = messages;
    }
}
