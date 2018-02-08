package com.nguyen.shop.service;

import com.nguyen.shop.common.ServerResponse;
import com.nguyen.shop.pojo.User;

/**
 * @author RWM
 * @date 2018/1/28
 */
public interface IUserService {
    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str, String type);

    ServerResponse<String> selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username, String question, String answer);

    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    ServerResponse<User> updateInformation(User user);

    ServerResponse<User> getInformation(Integer  userId);

    ServerResponse<Object> checkAdminRole(User user);
}
