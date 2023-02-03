package com.xrf.dubbo.consumer.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xrf.dubbo.common.User;
import com.xrf.dubbo.common.UserEntity;
import com.xrf.dubbo.consumer.mapper.UserMapper;
import com.xrf.dubbo.itf.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Controller
@Slf4j
public class ConsumerController {

    private static int COUNT = 0;

//    @Reference(version = "${demo.service.version}",
//            application = "${dubbo.application.id}",
//            url = "dubbo://localhost:20880", timeout = 60000)
//    private UserService userService;
//
//    @Resource
//    private UserMapper userMapper;
//
//    @RequestMapping("/user/{id}")
//    @ResponseBody
//    public User getUser(@PathVariable Integer id){
//        //调用服务
//        if ((COUNT++) % 3 == 0){
//            throw new RuntimeException();
//        }
//        LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery(UserEntity.class);
//        queryWrapper.like(UserEntity::getNickName, "5s");
//        List<UserEntity> userEntities = userMapper.selectList(queryWrapper);
//        log.info("userEntities:{}", userEntities);
//        return userService.findUserById(id);
//    }
    
}