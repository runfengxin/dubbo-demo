package com.xrf.dubbo.provider.message;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xrf.dubbo.common.UserEntity;
import com.xrf.dubbo.provider.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class MQConsumerService {

    @Resource
    private UserMapper userMapper;

    // topic需要和生产者的topic一致，consumerGroup属性是必须指定的，内容可以随意
    // selectorExpression的意思指的就是tag，默认为“*”，不设置的话会监听所有消息
    @Service
    @RocketMQMessageListener(topic = "RLT_TEST_TOPIC", selectorExpression = "tag1", consumerGroup = "CONSUMER_GROUP")
    public class ConsumerSend implements RocketMQListener<UserEntity> {
        // 监听到消息就会执行此方法
        @Override
        @Trace
        public void onMessage(UserEntity user) {
            log.info("监听到消息：user={}", JSON.toJSONString(user));
            LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery(UserEntity.class);
            queryWrapper.like(UserEntity::getNickName, "0935sda");
            List<UserEntity> userEntities = userMapper.selectList(queryWrapper);
            log.info("userEntities:{}", userEntities);
        }
    }

    // 注意：这个ConsumerSend2和上面ConsumerSend在没有添加tag做区分时，不能共存，
    // 不然生产者发送一条消息，这两个都会去消费，如果类型不同会有一个报错，所以实际运用中最好加上tag，写这只是让你看知道就行
    @Service
    @RocketMQMessageListener(topic = "RLT_TEST_TOPIC", consumerGroup = "Con_Group_Two")
    public class ConsumerSend2 implements RocketMQListener<String> {
        @Override
        public void onMessage(String str) {
            log.info("监听到消息：str={}", str);
        }
    }

	// MessageExt：是一个消息接收通配符，不管发送的是String还是对象，都可接收，当然也可以像上面明确指定类型（我建议还是指定类型较方便）
    @Service
    @RocketMQMessageListener(topic = "RLT_TEST_TOPIC", selectorExpression = "tag2", consumerGroup = "Con_Group_Three")
    public class Consumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {



        @Override
        public void onMessage(MessageExt messageExt) {
            byte[] body = messageExt.getBody();
            String msg = new String(body);
            log.info("监听到消息：msg={}", msg);
        }

        @Override
        public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {

        }
    }

}
