package com.fh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.po.brand.Brand;

import java.util.List;


public interface IBrandMapper extends BaseMapper<Brand> {
    List<Brand> findBrand();


    void addBrand(Brand brand);

    void deleteBrand(Long id);

    Brand getById(Long id);

    void updateBrand(Brand brand);

    void addBrandName(Brand b);

    Long getBrandName(String brandName);
}
