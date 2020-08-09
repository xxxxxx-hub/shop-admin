package com.fh.mapper;


import com.fh.param.ProductWhere;
import com.fh.po.product.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IProductMapper {
    void addProduct(Product product);

    Long findCount(ProductWhere productWhere);

    List<Product> findPageList(ProductWhere productWhere);

    void deleteProduct(Long id);

    Product findById(Long id);


    void updateProduct(Product product);

    void batchDelete(List<Long> idList);


    void updateIsHotStatus(@Param("id") Long id, @Param("status") int status);

    void updateIsupStatus(@Param("id") Long id, @Param("status") int status);

    void batchAdd(List<Product> list);
}
