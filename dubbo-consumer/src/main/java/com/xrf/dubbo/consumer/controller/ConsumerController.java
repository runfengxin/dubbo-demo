package com.xrf.dubbo.consumer.controller;

import com.xrf.dubbo.common.User;
import com.xrf.dubbo.itf.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Controller
@Slf4j
public class ConsumerController {

    private static int COUNT = 0;

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}",
            url = "dubbo://localhost:20880", timeout = 60000)
    private UserService userService;

    @RequestMapping("/user/{id}")
    @ResponseBody
    public User getUser(@PathVariable Integer id){
        //调用服务
        if ((COUNT++) % 3 == 0){
            throw new RuntimeException();
        }
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
        User user= userService.findUserById(id);
        log.info("aaa");
        return user;
    }
    
}