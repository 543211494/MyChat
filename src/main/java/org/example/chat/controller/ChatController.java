package org.example.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.chat.util.ChatGPT;
import org.example.chat.util.bean.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
public class ChatController {

    @Autowired
    private ChatGPT chatGPT;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/chat",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String chat(@RequestParam("messages")String messages) {
        List<Message> messageList;
        String response;
        try{
            messageList = (List<Message>) objectMapper.readValue(messages,new TypeReference<List<Message>>(){});
            response = chatGPT.chat(messageList);
        }catch (Exception e){
            e.printStackTrace();
            response = e.getMessage();
        }
        return response;
    }
}
