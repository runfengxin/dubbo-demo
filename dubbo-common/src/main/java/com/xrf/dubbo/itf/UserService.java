package com.xrf.dubbo.itf;

import com.xrf.dubbo.common.User;

public interface UserService {
    User findUserById(Integer id);
}