package com.xrf.dubbo.provider.message;

import com.xrf.dubbo.common.MessageBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RocketMQMessageListener(topic = "test-topic",nameServer = "${rocketmq.name-server}",consumerGroup = "${rocketmq.consumer.group}", selectorExpression = "test-tag")
@Component
@Slf4j
public class ComsumerListener implements RocketMQListener<MessageBody> {

    @Override
    public void onMessage(MessageBody messageBody) {
        log.info("{}", messageBody);
    }
}
