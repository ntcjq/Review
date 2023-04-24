package com.sea.review.consumer;


import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RocketMQMessageListener(consumerGroup = "my-consumer-group-2", topic = "topic-str-delay")
public class RocketDelayConsumer implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt msg) {
        System.out.println("-------msgId=" + msg.getMsgId());
        System.out.printf("------%s,consume-group-2 message : %s %n", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), msg);
        System.out.printf("------%s,consume-group-2 messageBody : %s %n", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), new String(msg.getBody()));
    }
}
