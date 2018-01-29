package com.nguyen.controller.backend;

import com.nguyen.common.Const;
import com.nguyen.common.ServerResponse;
import com.nguyen.pojo.User;
import com.nguyen.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author RWM
 * @date 2018/1/29
 */
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()){
            User user = response.getData();
            if (user.getRole() == Const.Role.ROLE_ADMIN){
                // 管理员
                session.setAttribute(Const.CURRENT_USER, user);
                return response;
            }
        }else {
            return ServerResponse.createByErrorMessage("不是管理员，无法登录");
        }
        return response;
    }

}
