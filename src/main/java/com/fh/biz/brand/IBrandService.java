package com.fh.biz.brand;


import com.fh.po.brand.Brand;


import java.util.List;

public interface IBrandService {
    List<Brand> findBrand();

    void addBrand(Brand brand);

    void deleteBrand(Long id);

    Brand getById(Long id);

    void updateBrand(Brand brand);

    void addBrandName(Brand b);
}
