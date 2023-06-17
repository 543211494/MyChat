package org.example.chat.util.bean;

/**
 * 常量类
 */
public class Constant {
    private Constant() {
    }

    /**
     * 默认API URL，第一个为openai官方url，第二个为国内代理
     */
    //public static final String DEFAULT_CHAT_COMPLETION_API_URL = "https://api.openai.com/v1/chat/completions";
    public static final String DEFAULT_CHAT_COMPLETION_API_URL = "https://api.openai-proxy.com/v1/chat/completions";

    /**
     * 默认消息作者
     */
    public static final String DEFAULT_USER = "user";

    /**
     * 默认语言模型
     */
    public static final Model DEFAULT_MODEL = Model.GPT_3_5_TURBO;
}
