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

    private static final String COOKIE_DOMAIN = "localhost";
    private static final String COOKIE_NAME = "login_token";

    /** 读取Cookie **/
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

    /** 写入Cookie **/
    public static void writeLoginToken(HttpServletResponse httpServletResponse, String token){
        Cookie ck = new Cookie(COOKIE_NAME, token);
        ck.setDomain(COOKIE_DOMAIN);
        //代表设置在根目录
        ck.setPath("/");
        ck.setHttpOnly(true);
        //单位是秒
        //如果这个MaxAge不设置的话，cookie就不会写入硬盘，而是写在内存。只在当前页面有效。
        //如果是-1，代表永久
        ck.setMaxAge(60 * 60 * 24 * 365);
        httpServletResponse.addCookie(ck);
    }

    /** 删除Cookie **/
    public static void delLoginToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        Cookie[] cks = httpServletRequest.getCookies();
        if (cks != null){
            for (Cookie ck : cks){
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)){
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");

                    //设置成0，代表删除此Cookie
                    ck.setMaxAge(0);
                    httpServletResponse.addCookie(ck);
                    return;
                }
            }
        }
    }

}
