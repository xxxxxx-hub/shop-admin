package com.fh.po.product;

import com.baomidou.mybatisplus.annotation.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Product implements Serializable {

    private Long id;

    private String name;

    private BigDecimal price;

    private Long brandId;

    private String brandName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    private Date showTime;

    private Date updateTime;

    private String filePath;
    @TableField(exist = false)
    private String oldImg;

    private Integer isHot;

    private Integer isup;

    private Long type1;

    private Long type2;

    private Long type3;

    private String typeName1;

    private String typeName2;

    private String typeName3;

    public String getTypeName1() {
        return typeName1;
    }

    public void setTypeName1(String typeName1) {
        this.typeName1 = typeName1;
    }

    public String getTypeName2() {
        return typeName2;
    }

    public void setTypeName2(String typeName2) {
        this.typeName2 = typeName2;
    }

    public String getTypeName3() {
        return typeName3;
    }

    public void setTypeName3(String typeName3) {
        this.typeName3 = typeName3;
    }

    public Long getType1() {
        return type1;
    }

    public void setType1(Long type1) {
        this.type1 = type1;
    }

    public Long getType2() {
        return type2;
    }

    public void setType2(Long type2) {
        this.type2 = type2;
    }

    public Long getType3() {
        return type3;
    }

    public void setType3(Long type3) {
        this.type3 = type3;
    }

    public Integer getIsup() {
        return isup;
    }

    public void setIsup(Integer isup) {
        this.isup = isup;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public String getOldImg() {
        return oldImg;
    }

    public void setOldImg(String oldImg) {
        this.oldImg = oldImg;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getShowTime() {
        return showTime;
    }

    public void setShowTime(Date showTime) {
        this.showTime = showTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
