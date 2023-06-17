# 1.介绍

本项目是使用java封装调用ChatGPT官方API接口的简易demo。

主要功能是根据ChatGPT官方文档构建请求，并解析回复实现ChatGPT对话，参数详情参考<a href="https://platform.openai.com/docs/api-reference/chat/create">官方文档</a>。

主要参数为model(选择使用的语言模型)和messages(对话记录列表)，每次请求都会将历史对话记录发送给ChatGPT使其回复具有上下文。封装ChatGPT接口的具体实现均在org.example.chat.util下。

# 2.运行方法

后端部分为典型的SpringBoot应用，在application.properties文件中填写ChatGPT的API KEY后运行即可。

前端部分基于Vue开发，拉取代码后

```shell
#进入目录
cd html
#安装依赖包
npm install
#运行项目
npm run serve
```

成功运行后通过http://127.0.0.1:8080访问即可