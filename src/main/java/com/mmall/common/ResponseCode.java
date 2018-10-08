package com.mmall.common;

/**
 * @Author:Chengkangxin
 * @Description:
 * @Date:Create in 10:28 2018/10/8
 */
public enum  ResponseCode {
    SUCCESS(0,"SUCCESS"),ERROR(1,"ERROR"), NEED_LOGIN(10,"NEED_LOGIN"), ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;

    ResponseCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }
    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }
}
