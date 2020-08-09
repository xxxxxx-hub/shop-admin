package com.fh.common;

public enum  ReposeEnum {


    SPCE_INFO_NULL(2000,"规格值为空!!!"),

    PASSWORD_LANJIE(1003,"今日输入错误次数过多！！请24小时后尝试登陆！！"),
    PASSWORD_IS_ERROR(1002,"密码错误"),
    USERNAME_IS_NOT_EXISTS(1001,"用户名不存在"),
    USERNAME_PASSWORD_IS_NULL(1000,"用户名或密码为空");

    private int code;

    private String msg;

    ReposeEnum(int code,String msg){
        this.code=code;
        this.msg=msg;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
