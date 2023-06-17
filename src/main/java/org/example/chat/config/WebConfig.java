package org.example.chat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.chat.util.ChatGPT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 */
@Configuration
public class WebConfig {

    @Autowired
    private Constant constant;

    @Bean
    public ChatGPT initChatGPT(){
        return new ChatGPT(constant.getKey());
    }

    @Bean
    public ObjectMapper initObjectMapper(){
        return new ObjectMapper();
    }
}
