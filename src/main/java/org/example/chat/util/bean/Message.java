package org.example.chat.util.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 消息类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    /**
     * 消息的作者，只能是system, user, assistant三者之一
     */
    @JsonProperty(value = "role")
    public String role;

    /**
     * 消息内容
     */
    @JsonProperty(value = "content")
    public String content;

}
