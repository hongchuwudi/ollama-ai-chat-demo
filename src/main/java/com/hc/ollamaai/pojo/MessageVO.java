package com.hc.ollamaai.pojo;

import lombok.Data;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;

@Data
public class MessageVO {
    private String role;
    private String content;

    public MessageVO(Message message) {
        MessageType type = message.getMessageType();
        this.role = switch (type){
            case USER -> "user";
            case ASSISTANT -> "assistant";
            case SYSTEM -> "system";
            default -> "unknow";
        };
        this.content = message.getText();
        System.out.println("MessageVO: " + this.role + ": " + this.content);
    }
}
