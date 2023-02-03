package com.xrf.dubbo.consumer.controller;

import com.xrf.dubbo.common.UserEntity;
import com.xrf.dubbo.consumer.message.MQProducerService;
import com.xrf.dubbo.consumer.message.MQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rocketmq")
@Slf4j
public class RocketMQController {

    @Autowired
    private MQProducerService mqProducerService;

    @Autowired
    private MQService mqService;

    @GetMapping("/send")
    public void send() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUserName("username1");
        user.setBlockId("bbb");
        log.info("sssss");
//        mqProducerService.send(user);
//        mqService.asyncSendMsg("test-topic", "test-tag", user, "appSource");
        mqService.asyncSendMsg("test-topic", "test-tag", user, "appSource");
    }
    
//    @GetMapping("/sendTag")
//    public Result<SendResult> sendTag() {
//        SendResult sendResult = mqProducerService.sendTagMsg("带有tag的字符消息");
//        return Result.success(sendResult);
//    }

}
