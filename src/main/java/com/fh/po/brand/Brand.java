package com.fh.po.brand;


import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("fh_brand")
public class Brand implements Serializable {

    private Long id;

    private String brandName;

    private String logo;

    private String oldLogo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getOldLogo() {
        return oldLogo;
    }

    public void setOldLogo(String oldLogo) {
        this.oldLogo = oldLogo;
    }
}
