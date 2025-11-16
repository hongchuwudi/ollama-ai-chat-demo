package com.hc.ollamaai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hc")
@MapperScan("com.hc.**.mapper")
public class OllamaAiApplication {
    public static void main(String[] args) {
        SpringApplication.run(OllamaAiApplication.class, args);
    }
}
