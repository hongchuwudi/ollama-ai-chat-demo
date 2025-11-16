package com.hc.ollamaai.config;

import com.hc.ollamaai.constants.SystemConstants;
import com.hc.ollamaai.tools.CourseTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
@Configuration
@Slf4j
public class AiConfig {

    @Bean
    public ChatMemoryRepository chatMemoryRepository() {
        return new InMemoryChatMemoryRepository();
    }

    // 为普通聊天创建独立的记忆
    @Bean
    public ChatMemory chatMemory(ChatMemoryRepository repository) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(repository)
                .maxMessages(10)
                .build();
    }

    // 为游戏创建独立的记忆
    @Bean
    public ChatMemory gameChatMemory(ChatMemoryRepository repository) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(repository)
                .maxMessages(10)
                .build();
    }

    // 为客服创建独立的记忆（特别重要）
    @Bean
    public ChatMemory serviceChatMemory(ChatMemoryRepository repository) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(repository)
                .maxMessages(10)
                .build();
    }

    // 普通聊天客户端
    @Bean
    public ChatClient chatClient(OllamaChatModel model, ChatMemory chatMemory) {
        log.info("开始配置聊天model: {}", model);
        return ChatClient.builder(model)
                .defaultSystem("你是一个热心、可爱的智能助手，你的名字叫小团团，请以小团团的身份和语气回答问题。")
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .build();
    }

    // 游戏客户端
    @Bean
    public ChatClient gameChatClient(OpenAiChatModel model, ChatMemory gameChatMemory) {
        log.info("开始配置哄哄模拟器model: {}", model);
        return ChatClient.builder(model)
                .defaultSystem(SystemConstants.GAME_SYSTEM_PROMPT)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(gameChatMemory).build()
                )
                .build();
    }

    // 客服客户端（使用独立的记忆）
    @Bean
    public ChatClient serviceChatClient(OpenAiChatModel model,
                                        ChatMemory serviceChatMemory,
                                        CourseTools courseTools) {
        log.info("开始配置客服model: {}", model);
        return ChatClient.builder(model)
                .defaultSystem(SystemConstants.SERVICE_SYSTEM_PROMPT)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(serviceChatMemory).build()
                )
                .defaultTools(courseTools)
                .build();
    }
}