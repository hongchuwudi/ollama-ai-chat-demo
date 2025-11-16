package com.hc.ollamaai.controller;

import com.hc.ollamaai.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor            //有参构造
@RestController
@RequestMapping("/ai")
public class ChatController {
    private final ChatClient chatClient;
    private final ChatMemory chatMemory;
    private final ChatHistoryRepository chatHistoryRepository;

    //  报式
    @PostMapping("/callChat")
    public String callChat(String prompt){
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    // 流式
    @PostMapping(value = "/fluxChat",produces = "text/html;charset=utf-8")
    public Flux<String> fluxChat(String prompt){
        return chatClient.prompt()
                .user(prompt)
                .stream()
                .content();
    }

    //  流式记忆
    @PostMapping(value = "/chat")
    public  Flux<String> chat(@RequestParam String prompt, @RequestParam String chatId) {
        // 保存会话
        chatHistoryRepository.save(chatId, "chat");
        //  返回聊天结果
        return chatClient.prompt()
                .user(prompt)
                .advisors(a -> a.param(chatMemory.CONVERSATION_ID, chatId))
                .stream()
                .content();
    }


}
