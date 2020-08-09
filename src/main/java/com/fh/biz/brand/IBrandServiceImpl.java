package com.fh.biz.brand;


import com.fh.mapper.IBrandMapper;
import com.fh.po.brand.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("brandService")
public class IBrandServiceImpl implements  IBrandService{

    @Autowired
    private IBrandMapper brandMapper;

    @Override
    public List<Brand> findBrand() {
        return brandMapper.findBrand();
    }

    @Override
    public void addBrand(Brand brand) {
        brandMapper.addBrand(brand);
    }

    @Override
    public void deleteBrand(Long id) {
        brandMapper.deleteBrand(id);
    }

    @Override
    public Brand getById(Long id) {
        return brandMapper.getById(id);
    }

    @Override
    public void updateBrand(Brand brand) {
        brandMapper.updateBrand(brand);
    }


    @Override
    public void addBrandName(Brand b) {
        brandMapper.addBrandName(b);
    }


}
