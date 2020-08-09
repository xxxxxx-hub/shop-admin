package com.fh.biz.product;


import com.fh.common.DataTableResult;
import com.fh.common.DateUtil;
import com.fh.common.ServerResponse;
import com.fh.common.SystemFinal;
import com.fh.mapper.IBrandMapper;
import com.fh.mapper.IProductMapper;
import com.fh.param.ProductWhere;
import com.fh.po.product.Product;
import com.fh.util.RedisUitl;
import com.fh.vo.product.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service("productService")
public class IProductServiceImpl implements  IProductService{

    @Autowired
    private IProductMapper productMapper;

    @Autowired
    private IBrandMapper brandtMapper;

    @Override
    public void addProduct(Product product) {
        product.setShowTime(new Date());
        productMapper.addProduct(product);
    }

    @Override
    public DataTableResult findPageList(ProductWhere productWhere) {
        // 获取总条数
        Long totalCount = productMapper.findCount(productWhere);
        // 获取分页列表
       List<Product> productList = productMapper.findPageList(productWhere);
       List<ProductVo>productVoList = new ArrayList();
       for(Product product:productList){
           ProductVo productVo  = new ProductVo();
           productVo.setId(product.getId());
           productVo.setName(product.getName());
           productVo.setPrice(product.getPrice().toString());
           productVo.setBrandName(product.getBrandName());
           productVo.setCreateDate(DateUtil.se2te(product.getCreateDate(),DateUtil.Y_M_D));
           productVo.setShowTime(DateUtil.se2te(product.getShowTime(),DateUtil.FULL_TIME));
           productVo.setUpdateTime(DateUtil.se2te(product.getUpdateTime(),DateUtil.FULL_TIME));
           productVo.setFilePath(product.getFilePath());
           productVo.setIsHot(product.getIsHot());
           productVo.setIsup(product.getIsup());
           if(product.getTypeName1()!=null){
               productVo.setTypeName(product.getTypeName1());
           }
           if(product.getTypeName1()!=null&&product.getTypeName2()!=null){
               productVo.setTypeName(product.getTypeName1()+"==>"+product.getTypeName2());
           }
           if(product.getTypeName1()!=null&&product.getTypeName2()!=null&&product.getTypeName3()!=null){
               productVo.setTypeName(product.getTypeName1()+"==>"+product.getTypeName2()+"==>"+product.getTypeName3());
           }

           productVoList.add(productVo);
       }
        DataTableResult dataTableResult =   new DataTableResult(productWhere.getDraw(),totalCount,totalCount,productVoList);
        return dataTableResult;
    }

    @Override
    public void deleteProduct(Long id) {
        productMapper.deleteProduct(id);
    }

    @Override
    public ServerResponse findById(Long id) {
        Product product = productMapper.findById(id);
        ProductVo productVo  = new ProductVo();
        productVo.setId(product.getId());
        productVo.setName(product.getName());
        productVo.setPrice(product.getPrice().toString());
        productVo.setBrandId(product.getBrandId());
        productVo.setCreateDate(DateUtil.se2te(product.getCreateDate(),DateUtil.Y_M_D));
        productVo.setShowTime(DateUtil.se2te(product.getShowTime(),DateUtil.FULL_TIME));
        productVo.setUpdateTime(DateUtil.se2te(product.getUpdateTime(),DateUtil.FULL_TIME));
        productVo.setFilePath(product.getFilePath());
        productVo.setType1(product.getType1());
        productVo.setType2(product.getType2());
        productVo.setType3(product.getType3());
        if(product.getTypeName1()!=null){
            productVo.setTypeName(product.getTypeName1());
        }
        if(product.getTypeName1()!=null&&product.getTypeName2()!=null){
            productVo.setTypeName(product.getTypeName1()+"==>"+product.getTypeName2());
        }
        if(product.getTypeName1()!=null&&product.getTypeName2()!=null&&product.getTypeName3()!=null){
            productVo.setTypeName(product.getTypeName1()+"==>"+product.getTypeName2()+"==>"+product.getTypeName3());
        }
        return ServerResponse.success(productVo);
    }

    @Override
    public void updateProduct(Product product) {
        product.setUpdateTime(new Date());
        productMapper.updateProduct(product);
    }

    @Override
    public void batchDelete(List<Long> idList) {
        productMapper.batchDelete(idList);
    }

    @Override
    public void updateIsHotStatus(Long id, int status) {
        productMapper.updateIsHotStatus(id,status);
        RedisUitl.del("hotProductList");
    }

    @Override
    public void updateIsupStatus(Long id, int status) {
        productMapper.updateIsupStatus(id,status);
        RedisUitl.del("hotProductList");
    }

    @Override
    public void batchAdd(List<Product> list) {
        productMapper.batchAdd(list);
    }


    @Override
    public Product findBProductyId(Long id) {
        return productMapper.findById(id);
    }


}
