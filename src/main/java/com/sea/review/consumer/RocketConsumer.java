package com.sea.review.consumer;


import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RocketMQMessageListener(consumerGroup = "my-consumer-group-1", topic = "topic-str")
public class RocketConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        System.out.printf("------%s,consume-group-1 message : %s %n", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), s);
    }
}
