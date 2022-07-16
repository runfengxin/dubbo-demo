package com.xrf.dubbo.provider.service;

import com.xrf.dubbo.common.User;
import com.xrf.dubbo.itf.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.apache.skywalking.apm.toolkit.trace.CallableWrapper;
import org.apache.skywalking.apm.toolkit.trace.RunnableWrapper;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Service(version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}", timeout = 60000)
@Slf4j
public class UserServiceImpl implements UserService {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2,
            Runtime.getRuntime().availableProcessors() * 2, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100),
            new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public User findUserById(Integer id) {
        User user = new User();
        user.setId(id);
        user.setUsername("chy");
        log.info("bbb");
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        executor.execute(RunnableWrapper.of(() -> {
            log.info("cc");
        }));
        return user;
    }
}