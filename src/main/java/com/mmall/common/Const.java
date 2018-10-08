package com.mmall.common;

/**
 * @Author:Chengkangxin
 * @Description:
 * @Date:Create in 16:00 2018/10/8
 */
public class Const {
    public static final String CURRENT_USER = "current_user";

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    public interface Role{
        final int ROLE_CUSTOMER = 0;
        final int ROLE_ADMIN = 1;
    }
}
