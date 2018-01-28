package com.nguyen.service;

import com.nguyen.common.ServerResponse;
import com.nguyen.pojo.User;

/**
 * @author RWM
 * @date 2018/1/28
 */
public interface IUserService {
    ServerResponse<User> login(String username, String password);
    ServerResponse<String> register(User user);
    ServerResponse<String> checkValid(String str, String type);
}
