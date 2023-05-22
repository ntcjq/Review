package com.sea.review.controller;

import com.alibaba.fastjson.JSON;
import com.sea.review.bean.Person;
import com.sea.review.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mq")
public class RocketProducerController {

    private static final String TOPIC_STR = "topic_str";
    private static final String TOPIC_STR_DELAY = "topic_str_delay";
    private static final String TOPIC_STR_ORDERLY = "topic_str_orderly";

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @RequestMapping("send")
    @ResponseBody
    public String send(String str, String type) {
        if (StringUtils.isBlank(str)) {
            str = "Hello, World!";
        }
        if (StringUtils.isBlank(type)) {
            type = "normal";

        }
        switch (type) {
            case "normal":
                sendNormal(str);
                break;
            case "delay":
                sendDelay(str);
                break;
            case "orderly":
                sendOrderly(str);
                break;

        }
        return "success";
    }

    /**
     * 普通消息
     *
     * @param msg
     */
    private void sendNormal(String msg) {
        SendResult sendResult = null;
        // 同步发送消息。默认重复两次。不指定超时时间会拿producer 全局的默认超时时间(默认3s)
        System.out.printf("------%s,%s,send normal msg:%s %n", DateUtil.curDateFmt(), Thread.currentThread().getName(), msg);
        sendResult = rocketMQTemplate.syncSend(TOPIC_STR, msg);
        System.out.printf("------%s,%s,send normal result:%s %n", DateUtil.curDateFmt(), Thread.currentThread().getName(), sendResult);

//        // 发送消息并且指定tag
//        sendResult = rocketMQTemplate.syncSend(TOPIC_STR + ":tag0", "Hello, World! tag0!");
//
//        // 发送自定义的对象，默认会转为JSON串进行发送
//        Person person = new Person();
//        person.setName("cjq");
//        sendResult = rocketMQTemplate.syncSend(TOPIC_STR, person);
//        System.out.printf("------%s,%s,send normal result:%s %n", DateUtil.curDateFmt(), Thread.currentThread().getName(), sendResult);
//
//        // 单方向发送消息
//        rocketMQTemplate.sendOneWay(TOPIC_STR, msg);
//
//        // 异步发送, 指定回调与超时时间
//        System.out.printf("------%s,%s,send async msg:%s %n", DateUtil.curDateFmt(), Thread.currentThread().getName(), msg);
//        rocketMQTemplate.asyncSend(TOPIC_STR, msg, new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                System.out.printf("------%s,%s,send async result:%s %n", DateUtil.curDateFmt(), Thread.currentThread().getName(), sendResult);
//            }
//
//            @Override
//            public void onException(Throwable throwable) {
//                System.out.printf("------%s,%s,send async error:%s %n", DateUtil.curDateFmt(), Thread.currentThread().getName(), throwable.getMessage());
//
//            }
//        }, 10 * 1000);
    }


    /**
     * 延时消息
     *
     * @param msg
     */
    private void sendDelay(String msg) {
        SendResult sendResult = null;
        // 延迟消息,设置超时时间10s，指定延迟等级3(10s)
        System.out.printf("------%s,%s,sendDelay msg:%s %n", DateUtil.curDateFmt(), Thread.currentThread().getName(), msg);
        Person person = new Person();
        person.setName(msg);
        sendResult = rocketMQTemplate.syncSend(TOPIC_STR_DELAY, MessageBuilder.withPayload(
                JSON.toJSONString(person)).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE).build(), 10 * 1000, 3);
        System.out.printf("------%s,%s,sendDelay result:%s %n", DateUtil.curDateFmt(), Thread.currentThread().getName(), sendResult);
    }

    /**
     * 顺序消息
     *
     * @param msg
     */
    private void sendOrderly(String msg) {
        for (int i = 0; i < 10; i++) {
            // 模拟有序
            // 发送消息有序消息的发送比普通消息的发送多了一类参数“select_queue_key”，在rocketmq-spring中叫做hashKey，
            // 该参数的左右即是在发送消息的时候，固定发送到一个队列（默认情况下rocketmq中的topic有4个队列）以保证顺序。
            SendResult sendResult = rocketMQTemplate.syncSendOrderly(TOPIC_STR_ORDERLY, msg + i, "select_queue_key");
            System.out.printf("------%s,%s,sendOrderly result:%s %n", DateUtil.curDateFmt(), Thread.currentThread().getName(), sendResult);
        }
    }

}


