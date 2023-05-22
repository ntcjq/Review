package com.sea.review.consumer;


import com.sea.review.util.DateUtil;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 顺序消息消费者
 */
@Component
@RocketMQMessageListener(consumerGroup = "my-consumer-group-orderly", topic = "topic_str_orderly", consumeMode = ConsumeMode.ORDERLY)
public class RocketOrderlyConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String msg) {
        System.out.printf("------%s,%s,come consumer group orderly msg:%s %n", DateUtil.curDateFmt(), Thread.currentThread().getName(), msg);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("------%s,%s,complete consumer group orderly msg:%s %n", DateUtil.curDateFmt(), Thread.currentThread().getName(), msg);
    }
}
