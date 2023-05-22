package com.sea.review.consumer;


import com.sea.review.util.DateUtil;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 普通消息消费者
 */
@Component
@RocketMQMessageListener(consumerGroup = "my-consumer-group-normal", topic = "topic_str")
public class RocketConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        System.out.printf("------%s,%s,come consumer group normal %n", DateUtil.curDateFmt(), Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("------%s,%s,come consumer group normal message:%s %n", DateUtil.curDateFmt(), Thread.currentThread().getName(), s);
    }
}
