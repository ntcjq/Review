package com.sea.review.consumer;


import com.sea.review.util.DateUtil;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 延时消息消费者
 */
@Component
@RocketMQMessageListener(consumerGroup = "my-consumer-group-delay", topic = "topic_str_delay")
public class RocketDelayConsumer implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt msg) {
        System.out.printf("------%s,%s,come consumer group delay msgId:%s %n", DateUtil.curDateFmt(), Thread.currentThread().getName(), msg.getMsgId());
        System.out.printf("------%s,%s,come consumer group delay message:%s %n", DateUtil.curDateFmt(), Thread.currentThread().getName(), msg);
        System.out.printf("------%s,%s,come consumer group delay messageBody:%s %n", DateUtil.curDateFmt(), Thread.currentThread().getName(), new String(msg.getBody()));
    }
}
