package com.fh.common;

public class ServerResponse {

    private int code;

    private String msg;

    private Object data;

    private ServerResponse(int code, String msg, Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ServerResponse(){

    }

    public static ServerResponse  success(){
        return new ServerResponse(200,"ok",null);
    }

    public static ServerResponse  success(Object data){
        return new ServerResponse(200,"ok",data);
    }


    public static  ServerResponse error(){
        return  new ServerResponse(500,"error",null);
    }
    public  static  ServerResponse error(ReposeEnum reposeEnum){
        return  new ServerResponse(reposeEnum.getCode(),reposeEnum.getMsg(),null);
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
