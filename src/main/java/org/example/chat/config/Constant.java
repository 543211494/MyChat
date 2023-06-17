package org.example.chat.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 常量类
 */
@Configuration
@Data
public class Constant {

    @Value("${chat.apikey}")
    private String key;
}
