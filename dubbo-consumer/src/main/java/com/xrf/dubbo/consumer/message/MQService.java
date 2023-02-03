package com.xrf.dubbo.consumer.message;

import cn.hutool.core.util.IdUtil;
import com.xrf.dubbo.common.MessageBody;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.MessageDecoder;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class MQService {
    private final static Logger logger = LoggerFactory.getLogger(MQService.class);
    private static enum MSG_TYPE{ ONEWAY, ASYNC, SYNC };
    @Autowired
    public RocketMQTemplate rocketMQTemplate;

    /**
     * 发送消息，通用
     * @param msg_type
     * @param destination
     * @param payload
     */
    private void sendMsg(MSG_TYPE msg_type, String destination, Object payload, String msgSource){
        String msgKey = IdUtil.simpleUUID();
        MessageBody msgBody = new MessageBody(msgKey, payload , msgSource);
        Message<MessageBody> message = MessageBuilder.withPayload(msgBody).setHeader("KEYS",msgKey ).build();
        logger.info(String.format("消息发送 MQService 开始: %s %s", destination, message));
        SendResult result = null;
        switch (msg_type) {
            case ONEWAY:
                rocketMQTemplate.sendOneWay(destination, message);
                break;
            case ASYNC:
                rocketMQTemplate.asyncSend(destination, message,new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                    }
                    @Override
                    public void onException(Throwable throwable) {
                        logger.error("MQService:" + ExceptionUtils.getStackTrace(throwable));
                        throw new RuntimeException(String.format("消息发送失败 topic_tag:%s", destination ));
                    }
                });
                break;
            case SYNC:
                result = rocketMQTemplate.syncSend(destination, message);
                break;
        }
        logger.info(String.format("消息发送 MQService 结束: msgId: %s dest: %s msg: %s",result != null ? result.getMsgId() : "", destination, message));
    }

    /**
     * 同步发送消息,会确认应答
     * @param destination
     * @param payload
     */
    public void syncSendMsg(String destination, Object payload, String msgSource){
        sendMsg(MSG_TYPE.SYNC,destination, payload,msgSource) ;
    }

    public static void main(String[] args) {
//        String json = "KEYS\u000146611a4c1305446084b2569e39fc3f27\u0002id\u00012e88e16c-a46b-aa65-1423-224121f6d1e9\u0002UNIQ_KEY\u00017F000001655C18B4AAC29060768E000D\u0002WAIT\u0001true\u0002contentType\u0001application/json\u0002TAGS\u0001test-tag\u0002timestamp\u00011674924640908\u0002sw8\u00011-YzdmYTQ0OTA1ZDcxNDBmNDg1YjczMTQwYTE1Mzg1NWEuMTQzLjE2NzQ5MjQ2NDA5MTAwMDAz-YzdmYTQ0OTA1ZDcxNDBmNDg1YjczMTQwYTE1Mzg1NWEuMTQzLjE2NzQ5MjQ2NDA5MTAwMDAy-0-Y29uc3VtZXItYXBw-Y2M5NTYxYjBkNjkzNDJmOGJiOTQyNzdiOTFmYzBlZWFAMTkyLjE2OC4xMzcuMQ==-Um9ja2V0TVEvdGVzdC10b3BpYy9Qcm9kdWNlcg==-MTkyLjE2OC40NC4xMzU6OTg3Ng==\u0002sw8-x\u00010-1674924640910";
        String json = "KEYS\u0001f90ffa374c104b6eb97e040f9a0abd34\u0002id\u000119a41bc0-6f78-903d-7de5-8dec71751747\u0002UNIQ_KEY\u00017F0000016A2818B4AAC290AB6FC80001\u0002WAIT\u0001true\u0002contentType\u0001application/json\u0002TAGS\u0001test-tag\u0002timestamp\u00011674929443082\u0002sw8\u00011-M2RmNGVjYTE0ZWFkNGRmMWFhYjI3NWNiYzc1ZWQwYzAuMTI3LjE2NzQ5Mjk1NTQzNzYwMDAx-M2RmNGVjYTE0ZWFkNGRmMWFhYjI3NWNiYzc1ZWQwYzAuMTI3LjE2NzQ5Mjk1NTQzNzYwMDAw-0-Y29uc3VtZXItYXBw-ZWM1OWQ4MTc4OWRiNDJlOTkwOGMwNmE1ZWUyZGRkNmFAMTkyLjE2OC4xMzcuMQ==-Um9ja2V0TVEvdGVzdC10b3BpYy9Qcm9kdWNlcg==-MTkyLjE2OC40NC4xMzU6OTg3Ng==\u0002sw8-x\u00010-1674929554376";
        System.out.println(MessageDecoder.string2messageProperties(json));
    }
    /**
     * 同步发送消息,会确认应答
     * @param topic
     * @param tag
     * @param payload
     */
    public void syncSendMsg(String topic, String tag, Object payload, String msgSource){
        // 发送的消息体，消息体必须存在
        // 业务主键作为消息key
        String destination = topic + ":" + tag;
        syncSendMsg(destination, payload,msgSource);
    }

    /**
     * 异步消息发送,异步日志确认异常
     * @param destination
     * @param payload
     */
    public void asyncSendMsg(String destination, Object payload, String msgSource){
        sendMsg(MSG_TYPE.ASYNC,destination, payload,msgSource);
    }
    /**
     * 异步消息发送,异步日志确认异常
     * @param topic
     * @param tag
     * @param payload
     * @return
     */
    public void asyncSendMsg(String topic, String tag, Object payload, String msgSource){
        // 发送的消息体，消息体必须存在
        // 业务主键作为消息key
        String destination = topic + ":" + tag;
        asyncSendMsg(destination, payload,msgSource);
    }

    /**
     * 单向发送消息，不关注结果
     * @param destination
     * @param payload
     */
    public void oneWaySendMsg(String destination, Object payload, String msgSource){
        sendMsg(MSG_TYPE.ONEWAY,destination, payload,msgSource);
    }
    /**
     * 单向发送消息，不关注结果
     * @param topic
     * @param tag
     * @param payload
     */
    public void oneWaySendMsg(String topic, String tag, Object payload, String msgSource){
        // 发送的消息体，消息体必须存在
        // 业务主键作为消息key
        String destination = topic + ":" + tag;
        oneWaySendMsg(destination, payload,msgSource);
    }
}
