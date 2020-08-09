package com.fh.controller.product;
import com.fh.biz.brand.IBrandService;
import com.fh.biz.product.IProductService;
import com.fh.common.DataTableResult;
import com.fh.common.ServerResponse;
import com.fh.common.SystemFinal;
import com.fh.param.ProductWhere;
import com.fh.po.product.Product;
import com.fh.util.FileUtil;
import com.fh.util.OSSUtil;
import com.fh.util.RedisUitl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("product")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Resource(name="productService")
    private IProductService productService;

    @Resource(name="brandService")
    private IBrandService brandService;


    @RequestMapping("toAdd")
    public String toAdd(){
        return "product/add";
    }
    @RequestMapping("toIndex")
    public String toIndex(){
        return "product/index";
    }

    @RequestMapping("addProduct")
    @ResponseBody
    public ServerResponse addProduct(Product product){
        productService.addProduct(product);

        return ServerResponse.success();
    }
    @RequestMapping("updateProduct")
    @ResponseBody
    public ServerResponse updateProduct(Product product, HttpServletRequest request){

        if(StringUtils.isNotBlank(product.getFilePath())){
            String oldProduct = product.getOldImg();
            if(StringUtils.isNotBlank(oldProduct)){
                OSSUtil.deleteFile(oldProduct);
            }
        }else{
            product.setFilePath(product.getOldImg());
        }

        productService.updateProduct(product);

        return ServerResponse.success();
    }

    @RequestMapping("findPageList")
    @ResponseBody
    public DataTableResult findPageList(ProductWhere productWhere){
        DataTableResult dataTableResult = productService.findPageList(productWhere);
        return dataTableResult;
    }

    @RequestMapping("deleteProduct")
    @ResponseBody
    public ServerResponse deleteProduct(Long id, HttpServletRequest request){
        Product byId = productService.findBProductyId(id);

        if(StringUtils.isNotBlank(byId.getFilePath())){
            String filePath = byId.getFilePath();
            OSSUtil.deleteFile(filePath);
        }
        productService.deleteProduct(id);

        return ServerResponse.success();
    }
    @RequestMapping("toUpdate")
    public String toUpdate(Long id){

        return "product/update";
    }
    @RequestMapping("findProductById")
    @ResponseBody
    public ServerResponse findProductById(Long id){
        return productService.findById(id);
    }
    //修改热销
    @RequestMapping("updateIsHotStatus")
    @ResponseBody
    public ServerResponse updateIsHotStatus(Long id,int status){
        productService.updateIsHotStatus(id,status);
        return ServerResponse.success();
    }
    //修改上下架
    @RequestMapping("updateIsupStatus")
    @ResponseBody
    public ServerResponse updateIsupStatus(Long id,int status){
        productService.updateIsupStatus(id,status);
        return ServerResponse.success();
    }
    @RequestMapping("uploadFile")
    @ResponseBody
    public ServerResponse uploadFile(@RequestParam MultipartFile cover , HttpServletRequest request) {

        InputStream is = null;
        try {
            is = cover.getInputStream();
            String filename = cover.getOriginalFilename();
            String realPath = request.getServletContext().getRealPath(SystemFinal.IPORT_IMG);
            String s = FileUtil.copyFile(is, filename, realPath);
            return ServerResponse.success(SystemFinal.IPORT_IMG+s);

        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("上传用户图片过程中发生异常，异常信息为{}",e);
            return ServerResponse.error();
        }

    }


    @RequestMapping("batchDelete")
    @ResponseBody
    public ServerResponse batchDelete(@RequestParam("ids[]")List<Long>idList){
        productService.batchDelete(idList);
        return ServerResponse.success();
    }

    @RequestMapping("clearCache")
    @ResponseBody
    public ServerResponse clearCache(){
        RedisUitl.del("hotProductList");
        return ServerResponse.success();
    }



}
