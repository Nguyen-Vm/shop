package com.nguyen.shop.config;

import com.nguyen.shop.common.ResponseCode;
import com.nguyen.shop.dto.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author RWM
 * @date 2018/3/14
 *
 * @description: 全局异常处理类
 */
@Slf4j
@Component
public class ExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         Object o,
                                         Exception e) {
        log.error("{} Exception", httpServletRequest.getRequestURI(), e);
        ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());

        //当使用Jackson2.x的时候使用MappingJackson2JsonView，课程中使用的是1.9。
        modelAndView.addObject("status", e instanceof AppException ? ((AppException) e).code.code(): ResponseCode.ERROR.getCode());
        modelAndView.addObject("msg", e instanceof AppException ? ((AppException) e).code.msg() : "接口异常，详情请查看服务端日志的信息");
        modelAndView.addObject("data", e.toString());
        return modelAndView;
    }
}
