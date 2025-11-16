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

@RequiredArgsConstructor            // 有参构造
@RestController
@RequestMapping("/ai")
public class CustomerServiceController {
    private final ChatClient serviceChatClient;  // 确保注入的是 serviceChatClient
    private final ChatMemory chatMemory;
    private final ChatHistoryRepository chatHistoryRepository;

    @RequestMapping(value = "/service", produces = "text/html;charset=utf-8")
    public Flux<String> chat(@RequestParam String prompt, @RequestParam String chatId) {
        chatHistoryRepository.save(chatId, "service");
        return serviceChatClient.prompt()  // 使用 serviceChatClient
                .user(prompt)
                .advisors(a -> a.param("chat_memory_conversation_id", chatId))  // 使用字符串常量
                .stream()
                .content();
    }
}
