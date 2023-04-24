package com.sea.review.controller;

import com.alibaba.fastjson.JSON;
import com.sea.review.bean.Person;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

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
        String topicStr = "topic-str";
        String topicStrDelay = "topic-str-delay";

        SendResult sendResult = null;
        // 同步发送消息。默认重复两次。不指定超时时间会拿producer 全局的默认超时时间(默认3s)
        System.out.printf("------%s,syncSend %n", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        sendResult = rocketMQTemplate.syncSend(topicStr, "hello word");
        System.out.printf("------%s,syncSend to topic=%s, sendResult=%s %n", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), topicStr, sendResult);

        // 发送消息并且指定tag
//        System.out.printf("------%s,syncSend—tag %n", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//        sendResult = rocketMQTemplate.syncSend(topicStr + ":tag0", "Hello, World! tag0!");
//        System.out.printf("------%s,syncSend to topic=%s, tag=tag0, sendResult=%s %n", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), topicStr, sendResult);

        // 发送自定义的对象，默认会转为JSON串进行发送
//        sendResult = rocketMQTemplate.syncSend(userTopic, new User().setUserAge((byte) 18).setUserName("Kitty"));
//        System.out.printf("syncSend3 to topic %s sendResult=%s %n", userTopic, sendResult);

        // 单方向发送消息
//        rocketMQTemplate.sendOneWay(topicStr, "Hello, World! sendOneWay!");

        // 延迟消息。发送 spring message 对象， 指定超时时间是10 s, 并且指定延迟等级
        System.out.printf("------%s,delaySend %n", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

        Person person = new Person();
        person.setName("cui");
        person.setAge(31);
        String uuid = UUID.randomUUID().toString();
        System.out.println("------uuid=" + uuid);
        sendResult = rocketMQTemplate.syncSend(topicStrDelay, MessageBuilder.withPayload(
                JSON.toJSONString(person)).setHeader("id", uuid).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE).build(), 10 * 1000, 3);
        System.out.printf("------%s,syncSend to topic=%s, sendResult=%s %n", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), topicStrDelay, sendResult);

        // 异步发送, 指定回调与超时时间
//        System.out.printf("------%s,asyncSend %n", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//        rocketMQTemplate.asyncSend(topicStr, "hello world,async", new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                System.out.printf("------%s,asyncSend onSuccess, sendResult=%s %n", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), sendResult);
//            }
//
//            @Override
//            public void onException(Throwable throwable) {
//                System.out.printf("------%s,asyncSend error:%s %n", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), throwable.getMessage());
//            }
//        }, 10 * 1000);

    }
}
