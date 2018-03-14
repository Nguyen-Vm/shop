package com.nguyen.shop.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author RWM
 * @date 2018/3/6
 */
@Slf4j
public class CookieUtil {

    private static final String COOKIE_DOMAIN = ".nguyen.com";
    private static final String COOKIE_NAME = "mmall_login_token";

    public static String readLoginToken(HttpServletRequest request){
        Cookie[] cks = request.getCookies();
        if (cks != null){
            for (Cookie ck : cks){
                log.info("read cookieName:{}, cookieValue:{}", ck.getName(), ck.getValue());
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)){
                    log.info("read cookieName:{}, cookieValue:{}", ck.getName(), ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    public static void writeLoginToken(HttpServletResponse httpServletResponse, String token){
        Cookie ck = new Cookie(COOKIE_NAME, token);
        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/");//代表设置在根目录

        //单位是秒
        //如果这个MaxAge不设置的话，cookie就不会写入硬盘，而是写在内存。只在当前页面有效。
        ck.setMaxAge(60 * 60 * 24 * 365);//如果是-1，代表永久
        httpServletResponse.addCookie(ck);
    }

    public static void delLoginToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        Cookie[] cks = httpServletRequest.getCookies();
        if (cks != null){
            for (Cookie ck : cks){
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)){
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);//设置成0，代表删除此Cookie
                    httpServletResponse.addCookie(ck);
                    return;
                }
            }
        }
    }

}