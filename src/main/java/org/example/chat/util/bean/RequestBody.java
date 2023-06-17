package org.example.chat.util.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * chatGPT API请求体类
 * 取值参考https://platform.openai.com/docs/api-reference/chat/create
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestBody {

    /**
     * 必填
     * 使用的模型id，只能填gpt-3.5-turbo或gpt-3.5-turbo-0301
     */
    @JsonProperty(value = "model")
    private String model;

    /**
     * 必填
     * 用于完成聊天的消息列表
     * 聊天模型将消息列表作为输入，并返回模型生成的消息作为输出。
     * 尽管聊天格式旨在简化多回合对话，但它对没有任何对话的单回合任务同样有用。
     */
    @JsonProperty(value = "messages")
    private List<Message> messages;

    /**
     * 选填
     * 默认值为1
     * 值的范围为0-2
     * 高的值如0.8会使输出更加随机
     * 低的值如0.2会使输出更加确定
     * 通常建议调整该值或top_p，但不要同时调整
     */
    @JsonProperty(value = "temperature")
    private Float temperature;

    /**
     * 选填
     * 默认值为1
     * 一种temperature值的替代方案，叫nucleus sampling
     */
    @JsonProperty(value = "top_p")
    private Float topP;

    /**
     * 选填，为每个消息生成多少个聊天
     * 默认值为1
     */
    @JsonProperty(value = "n")
    private Integer n;

    /**
     * 选填
     * 默认false
     * 当设置为true时，启用流式输入模式。流式输入允许您逐步构建对话，逐个消息地发送给API，而不需要等待完整的对话列表
     */
    @JsonProperty(value = "stream")
    private Boolean stream;

    /**
     * 选填
     * 默认为null
     * 指定终止标记，用于告诉模型停止生成文本。当模型生成的文本中包含终止标记时，生成过程将停止
     * 将多个终止标记作为一个数组
     */
    @JsonProperty(value = "stop")
    private List<String> stop;

    /**
     * 选填
     * 指定生成的回复的最大长度。
     * 长度单位可以是一个单词或一个字符，取决于输入文本的长度和复杂性。
     * 默认值为2048
     */
    @JsonProperty(value = "max_tokens")
    private Integer maxTokens;

    /**
     * 选填
     * 默认值为0
     * 取值为-2.0至2.0
     * 存在惩罚参数用于控制模型生成的回复是否遵循给定的上下文。
     * 较高的存在惩罚值会使模型生成的回复更加关注和遵循之前提供的消息
     * 而较低的值则允许更自由的生成。
     * 通过调整此参数，可以平衡生成回复的创造性和上下文的连贯性。
     */
    @JsonProperty(value = "presence_penalty")
    private Float presencePenalty;

    /**
     * 选填
     * 默认值为0
     * 取值为-2.0至2.0
     * 频率惩罚参数用于控制模型生成的回复中特定词汇或短语的重复频率。
     * 较高的频率惩罚值会降低模型生成重复词汇的概率，而较低的值则允许更多的重复。
     * 通过调整此参数，可以控制生成回复中的重复度。
     * 参考https://platform.openai.com/docs/api-reference/parameter-details
     */
    @JsonProperty(value = "frequency_penalty")
    private Float frequencyPenalty;

    /**
     * 选填
     * 逻辑偏置
     * 逻辑偏置参数用于通过调整模型的输出概率分布来引导生成结果。
     * 可以提供一个逻辑偏置对象，其中包含目标词汇和对应的偏置值。
     * 偏置值较高的词汇更可能出现在生成结果中，而偏置值较低的词汇则可能会被削弱或减少。
     * 逻辑偏置可以用于引导模型生成特定风格、内容或回答的文本。
     * 例如：
     * "logit_bias": {
     *   "positive": 2.0,
     *   "negative": -1.0
     * }
     * 模型在生成回答时会更倾向于选择积极的词汇，并减少选择消极的词汇
     */
    @JsonProperty(value = "logit_bias")
    private Map<Object, Object> logitBias;


    /**
     * 选填
     * 代表最终用户的唯一标识符，可以帮助OpenAI监控和检测滥用。
     * 参考https://platform.openai.com/docs/guides/safety-best-practices/end-user-ids
     */
    private String user;

}
