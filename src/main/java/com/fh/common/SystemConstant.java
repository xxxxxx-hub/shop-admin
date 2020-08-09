package com.fh.common;

import java.util.UUID;

public class SystemConstant {
    //导出pdf模板存放的文件夹
    public static  final  String    PDF_TEMPLATE="/template/";
    //导出pdf的商品模板
    public static  final  String    PDF_PRODUCT_TEMPLATE_HTML="/product.html";
    //保存硬盘的路径
    public static  final  String    FILE_PATH="E:/1906B/";
    //导出word 的文件路径
    public static  final  String    WORD_FILE_PATH="/product.xml";
    //图片上传保存的文件夹
    public static  final  String    FILE_IMAGE_PATH="/uploaded/";
    //导出word的前缀
    public static  final  String    WORD_SAVE_PATH="E:/";
    //导出word的后缀
    public static  final  String    WORD_SUFFIX=".docx";
    //导出pdf的用户模板
    public static  final  String    PDF_USER_TEMPLATE_HTML="/user.html";
    //UUID
    public static  final  String    UUID_STRING=UUID.randomUUID().toString();
    //登录后的用户
   // public static final String SESSION_LOGIN_NAME = ("loginUser");
   public static  final  String    LOGIN_USER="loginUser";
    //返回到登录页面
    public static  final  String    LOGIN_JSP="/login.jsp";
    //导出用户Excel的表头
    public static  final  String[]  USER_HEADERS={"Id","用户名","真实姓名","生日","电话","邮箱","地区"};
    //导出用户Excel的行数据
    public static  final  String[]  USER_PROPS={"id","userName","realName","birthday","phone","eml","areaName"};
    //导出用户Excel大标题
    public static  final  String    USER_TITLE="用户列表";
    //导出商品Excel的表头
    public static  final  String[]  Home_HEADERS={"小区名"};
    //导出商品Excel的行数据
    public static  final  String[]  Home_PROPS={"xiaoquName","price"};
    //导出商品Excel大标题
    public static  final  String    Home_TITLE="房屋列表";
    //登录的方法
    public static  final  String    LOGIN_METHOD="login";

    public static final String GOODS_REDIS_INFO="cateList";
    //存放redis的过期时间
    public static final int GOODS_REDIS_TIME=10*60;

    public static final String GOODS_GoodsListHot_INFO="goodsList";

    public static final String GOODS_Brand_INFO="brandList";

}
