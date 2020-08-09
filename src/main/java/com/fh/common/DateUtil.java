package com.fh.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final  String Y_M_D="yyyy-MM-dd";

    public static final  String FULL_TIME="yyyy-MM-dd HH:mm:ss";


   public static  String se2te(Date date, String patten){
       if(date==null){
           return "";
       }
       SimpleDateFormat sim = new SimpleDateFormat(patten);
       String format = sim.format(date);
       return  format;
   }
}
