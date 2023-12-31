package org.example.chat.util.bean;

import lombok.Getter;

/**
 * 模型枚举，存储可选择的语言模型值
 * 取值参考https://platform.openai.com/docs/api-reference/models
 */
@Getter
public enum Model {

    GPT_4("gpt-4"),
    GPT_4_0314("gpt-4-0314"),
    GPT_4_32K("gpt-4-32k"),
    GPT_4_32K_0314("gpt-4-32k-0314"),
    GPT_3_5_TURBO("gpt-3.5-turbo"),
    GPT_3_5_TURBO_0301("gpt-3.5-turbo-0301"),
    TEXT_DAVINCI_003("text-davinci-003"),
    TEXT_DAVINCI_002("text-davinci-002"),
    TEXT_DAVINCI_001("text-davinci-001"),
    ;

    private final String name;

    Model(String name) {
        this.name = name;
    }
}
