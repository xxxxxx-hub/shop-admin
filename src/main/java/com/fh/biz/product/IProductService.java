package com.fh.biz.product;

import com.fh.common.DataTableResult;
import com.fh.common.ServerResponse;
import com.fh.param.ProductWhere;
import com.fh.po.product.Product;

import java.util.List;

public interface IProductService {
    void addProduct(Product product);

    DataTableResult findPageList(ProductWhere productWhere);

    void deleteProduct(Long id);

    ServerResponse findById(Long id);

    void updateProduct(Product product);

    void batchDelete(List<Long> idList);

    void updateIsHotStatus(Long id, int status);

    void updateIsupStatus(Long id, int status);

    void batchAdd(List<Product> list);

    Product findBProductyId(Long id);
}
