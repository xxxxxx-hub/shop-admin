package com.fh.param;


import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class MemberWhere extends Page implements Serializable {

    private  String memberName;

    private  String realName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private   Date   minDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maxDate;

    private Long   shengId;

    private Long   shiId;

    private Long   xianId;


    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public Long getShengId() {
        return shengId;
    }

    public void setShengId(Long shengId) {
        this.shengId = shengId;
    }

    public Long getShiId() {
        return shiId;
    }

    public void setShiId(Long shiId) {
        this.shiId = shiId;
    }

    public Long getXianId() {
        return xianId;
    }

    public void setXianId(Long xianId) {
        this.xianId = xianId;
    }
}
