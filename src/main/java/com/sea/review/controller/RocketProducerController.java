package com.sea.review.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mq")
public class RocketProducerController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @RequestMapping("send")
    public String send(String str) {

        if (StringUtils.isBlank(str)) {
            str = "Hello, World!";
        }
        sendString(str);
        return "success";
    }


    private void sendString(String str) {
        String springTopic = "spring-string";

        rocketMQTemplate.convertAndSend(springTopic, str);

        /***********第一种发送方式*********/
        // 同步发送消息。默认重复两次。不指定超时时间会拿producer 全局的默认超时时间(默认3s)
        SendResult sendResult = rocketMQTemplate.syncSend(springTopic, str);
        System.out.printf("syncSend1 to topic %s sendResult=%s %n", springTopic, sendResult);
//        // 指定超时时间是10 s
//        sendResult = rocketMQTemplate.syncSend(springTopic, "Hello, World2!", 10 * 1000);
//        System.out.printf("syncSend2 to topic %s sendResult=%s %n", springTopic, sendResult);
//        // 发送消息并且指定tag
//        sendResult = rocketMQTemplate.syncSend(springTopic + ":tag0", "Hello, World! tag0!");
//        System.out.printf("syncSend1 to topic %s sendResult=%s %n", springTopic, sendResult);
//        // 发送自定义的对象，默认会转为JSON串进行发送
//        sendResult = rocketMQTemplate.syncSend(userTopic, new User().setUserAge((byte) 18).setUserName("Kitty"));
//        System.out.printf("syncSend3 to topic %s sendResult=%s %n", userTopic, sendResult);
//        // 单方向发送消息
//        rocketMQTemplate.sendOneWay(springTopic, "Hello, World! sendOneWay!");
//        // 延迟消息。发送 spring message 对象， 指定超时时间是10 s, 并且指定延迟等级
//        sendResult = rocketMQTemplate.syncSend(delayTopic, MessageBuilder.withPayload(
//                new User().setUserAge((byte) 21).setUserName("Delay")).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE).build(), 10 * 1000, 3);
//        System.out.printf("syncSend5 to topic %s sendResult=%s %n", delayTopic, sendResult);
//        // 异步发送, 指定回调与超时时间
//        rocketMQTemplate.asyncSend(springTopic, new User().setUserAge((byte) 180).setUserName("asyncSend"), new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                System.out.println("asyncSend onSuccess:" + sendResult);
//            }
//
//            @Override
//            public void onException(Throwable throwable) {
//                System.out.println("asyncSend error:" + throwable.getMessage());
//            }
//        }, 10 * 1000);

    }
}
