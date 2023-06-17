package org.example.chat.util.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 回复类
 * 取值参考https://platform.openai.com/docs/api-reference/chat/create
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBody {

    /**
     * 每个对话请求的唯一标识符
     */
    @JsonProperty(value = "id")
    public String id;

    /**
     * 响应对象的类型，通常为chat.completion
     */
    @JsonProperty(value = "object")
    public String object;

    /**
     * 回复生成的时间
     */
    @JsonProperty(value = "created")
    public Long created;

    /**
     * 指定使用的模型
     */
    @JsonProperty(value = "model")
    public String model;

    /**
     * 生成的回复结果数组
     */
    @JsonProperty(value = "choices")
    public List<Choice> choices;

    /**
     * 有关API使用情况的信息
     */
    @JsonProperty(value = "usage")
    public Usage usage;

    @Data
    public static class Choice {

        /**
         * 生成回复的索引
         */
        @JsonProperty(value = "index")
        public Integer index;

        /**
         * 回复的消息
         */
        @JsonProperty(value = "message")
        public Message message;

        /**
         * 生成回复的原因，例如"stop"表示达到停止条件。
         */
        @JsonProperty(value = "finish_reason")
        public String finishReason;
    }


    @Data
    public static class Usage {

        /**
         * API请求中的prompt文本
         */
        @JsonProperty(value = "prompt_tokens")
        public Integer promptTokens;

        /**
         * 生成回复的长度
         */
        @JsonProperty(value = "completion_tokens")
        public Integer completionTokens;

        /**
         * prompt_tokens+completion_tokens的值
         */
        @JsonProperty(value = "total_tokens")
        public Integer totalTokens;
    }
}
