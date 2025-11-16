package com.hc.ollamaai.controller;

import com.hc.ollamaai.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor            //有参构造
@RestController
@RequestMapping("/ai")
public class GameChatController {
    private final ChatClient gameChatClient;
    private final ChatMemory chatMemory;
    //  流式记忆
    @RequestMapping(value = "/game",produces = "text/html;charset=utf-8")
    public  Flux<String> chat(@RequestParam String prompt, @RequestParam String chatId) {
        return gameChatClient.prompt()
                .user(prompt)
                .advisors(a -> a.param(chatMemory.CONVERSATION_ID, chatId))
                .stream()
                .content();
    }
}
