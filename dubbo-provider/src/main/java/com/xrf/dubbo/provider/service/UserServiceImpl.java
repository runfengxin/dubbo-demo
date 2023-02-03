package com.xrf.dubbo.provider.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xrf.dubbo.common.User;
import com.xrf.dubbo.common.UserEntity;
import com.xrf.dubbo.itf.UserService;
import com.xrf.dubbo.provider.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.shenyu.client.dubbo.common.annotation.ShenyuDubboClient;
import org.apache.skywalking.apm.toolkit.trace.CallableWrapper;
import org.apache.skywalking.apm.toolkit.trace.RunnableWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@DubboService
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2,
            Runtime.getRuntime().availableProcessors() * 2, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100),
            new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    @ShenyuDubboClient(path = "/findUserById")
    public User findUserById(Integer id) {
        User user = new User();
        user.setId(id);
        user.setUsername("chy");
        log.info("bbb");
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        executor.execute(RunnableWrapper.of(() -> {
            log.info("cc");
        }));

        LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery(UserEntity.class);
        queryWrapper.like(UserEntity::getNickName, "8s");
        List<UserEntity> userEntities = userMapper.selectList(queryWrapper);
        log.info("userEntities:{}", userEntities);
        return user;
    }
}