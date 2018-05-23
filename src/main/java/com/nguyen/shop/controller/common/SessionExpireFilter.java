package com.nguyen.shop.controller.common;

import com.nguyen.shop.common.custom.Const;
import com.nguyen.shop.pojo.User;
import com.nguyen.shop.utils.CookieUtil;
import com.nguyen.shop.utils.JsonUtil;
import com.nguyen.shop.utils.RedisSharedPoolUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author RWM
 * @date 2018/3/12
 *
 * @description 重置Session有效期过滤器
 */
public class SessionExpireFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isNotEmpty(loginToken)){
            String userJsonStr = RedisSharedPoolUtil.get(loginToken);
            User user = JsonUtil.string2Obj(userJsonStr, User.class);
            if (user != null){
                // 重置session有效期
                RedisSharedPoolUtil.expire(loginToken, Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
            }
        }
        // web.xml里面配置的有可能是一个接一个的filter链。
        // 将请求转发给过滤器链上下一个filter，如果没有filter那就到请求的资源。
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
