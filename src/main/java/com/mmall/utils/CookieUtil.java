package com.mmall.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author:Chengkangxin
 * @Description:
 * @Date:Create in 10:17 2018/11/20
 */
@Slf4j
public class CookieUtil {

    private final static String COOKIE_DOMAIN = "";
    private final static String COOKIE_NAME = "";

    public static void writeLoginToken(HttpServletResponse response,String token){
        Cookie cookie = new Cookie(COOKIE_NAME,token);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath("/");

        cookie.setMaxAge(60*60*24*365);
        response.addCookie(cookie);

    }
}
