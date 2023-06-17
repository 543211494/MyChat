package org.example.chat.util;

import org.example.chat.util.bean.Message;
import org.example.chat.util.bean.Model;
import org.example.chat.util.bean.RequestBody;
import org.example.chat.util.bean.ResponseBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.example.chat.util.bean.Constant.*;

/**
 * 使用官方API的Java套壳类
 */
@Slf4j
@Builder
public class ChatGPT {

    /**
     * api key
     */
    private final String apiKey;

    /**
     * api url
     */
    private String apiHost = DEFAULT_CHAT_COMPLETION_API_URL;

    /**
     * 用户发起https请求
     */
    protected OkHttpClient client;

    /**
     * json格式序列化/反序列化工具
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 读取超时
     */
    private static final int READ_TIME_OUT = 120;

    /**
     * 写入超时
     */
    private static final int WRITE_TIME_OUT = 120;

    /**
     * 连接超时
     */
    private static final int CONNECT_TIME_OUT = 60;


    public ChatGPT(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT,TimeUnit.SECONDS)
                .build();
    }

    public ChatGPT(String apiKey, OkHttpClient client) {
        this.apiKey = apiKey;
        this.client = client;
    }

    public ChatGPT(String apiHost, String apiKey) {
        this.apiHost = apiHost;
        this.apiKey = apiKey;
        this.client = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT,TimeUnit.SECONDS)
                .build();
    }

    public ChatGPT(String apiHost, String apiKey, OkHttpClient client) {
        this.apiHost = apiHost;
        this.apiKey = apiKey;
        this.client = client;
    }

    /**
     * 发送单条提问，无上下文
     * @param input 对话输入内容
     * @return 回复
     */
    public String chat(String input) {
        return chat(DEFAULT_MODEL.getName(), DEFAULT_USER, input);
    }

    /**
     * 发送单条提问，无上下文
     * @param user 输入对话消息的role
     * @param input 对话输入内容
     * @return 回复
     */
    public String chat(String user, String input) {
        return chat(DEFAULT_MODEL.getName(), user, input);
    }

    /**
     * 发送单条提问，无上下文
     * @param model 选择使用的模型
     * @param input 对话输入内容
     * @return 回复
     */
    public String chat(Model model, String input) {
        return chat(model.getName(), DEFAULT_USER, input);
    }

    /**
     * 发送多条消息，有上下文
     * @param messages 历史消息记录列表
     * @return 回复
     */
    public String chat(List<Message> messages) {
        return chat(DEFAULT_MODEL.getName(), messages);
    }

    /**
     * 发送多条消息，有上下文
     * @param model 选择的语言模型
     * @param messages 历史消息记录列表
     * @return 回复
     */
    public String chat(Model model, List<Message> messages) {
        return chat(model.getName(), messages);
    }

    /**
     * 构造请求体
     * @param model 选择的语言模型
     * @param messages 历史消息记录列表
     * @return 回复
     */
    private String buildRequestBody(String model, List<Message> messages) {
        try {
            RequestBody requestBody = RequestBody.builder()
                    .model(model)
                    .messages(messages)
                    .build();
            return objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送请求获取回复
     *
     * @param model    选择的语言模型
     * @param messages 历史消息记录列表
     * @return ResponseBody回复对象
     */
    public ResponseBody chatOriginal(String model, List<Message> messages) {

        /**
         * 构造请求体
         */
        okhttp3.RequestBody body = okhttp3.RequestBody.create(buildRequestBody(model, messages), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(apiHost)
                .header("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();
        /**
         * 获取回复
         */
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                if (response.body() == null) {
                    log.error("Request failed: {}, please try again", response.message());
                    throw new RuntimeException("Request failed");
                } else {
                    log.error("Request failed: {}, please try again", response.body().string());
                    throw new RuntimeException("Request failed");
                }
            } else {
                assert response.body() != null;
                String bodyString = response.body().string();
                return objectMapper.readValue(bodyString, ResponseBody.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对chatOriginald的封装，无上下文版
     * @param model 选择的语言模型
     * @param role 消息作者
     * @param content 消息内容
     * @return 回复
     */
    public String chat(String model, String role, String content) {
        ResponseBody chatCompletionResponseBody = chatOriginal(model, Collections.singletonList(Message.builder()
                .role(role)
                .content(content)
                .build()));
        List<ResponseBody.Choice> choices = chatCompletionResponseBody.getChoices();
        StringBuilder result = new StringBuilder();
        for (ResponseBody.Choice choice : choices) {
            result.append(choice.getMessage().getContent());
        }
        return result.toString();
    }

    /**
     * 对chatOriginald的封装，有上下文版
     * @param model 选择的语言模型
     * @param message 历史消息记录列表
     * @return 回复
     */
    public String chat(String model, List<Message> message) {
        ResponseBody chatCompletionResponseBody = chatOriginal(model, message);
        List<ResponseBody.Choice> choices = chatCompletionResponseBody.getChoices();
        StringBuilder result = new StringBuilder();
        for (ResponseBody.Choice choice : choices) {
            result.append(choice.getMessage().getContent());
        }
        return result.toString();
    }

}
