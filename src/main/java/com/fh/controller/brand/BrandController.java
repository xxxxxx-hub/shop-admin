package com.fh.controller.brand;


import com.fh.biz.brand.IBrandService;
import com.fh.common.ReposeEnum;
import com.fh.common.ServerResponse;
import com.fh.po.brand.Brand;
import com.fh.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("brand")
public class BrandController {

    @Resource(name="brandService")
    private IBrandService brandService;

    @RequestMapping("toIndex")
    public String toIndex(){
        return "brand/index";
    }

    @RequestMapping("findBrand")
    @ResponseBody
    public ServerResponse findBrand(){
        List <Brand> list = brandService.findBrand();
        return ServerResponse.success(list);
    }

    @RequestMapping("addBrand")
    @ResponseBody
    public ServerResponse addBrand(Brand brand){
        brandService.addBrand(brand);
        return ServerResponse.success();
    }
    @RequestMapping("deleteBrand")
    @ResponseBody
    public ServerResponse deleteBrand(Long id, HttpServletRequest request){
        Brand byId = brandService.getById(id);
        String logo = byId.getLogo();
        if(StringUtils.isNotBlank(logo)){
            String realPath = request.getServletContext().getRealPath(logo);
            File file  = new File(realPath);
            if(file.exists()){
                file.delete();
            }
        }
        brandService.deleteBrand(id);
        return ServerResponse.success();
    }

    @RequestMapping("getById")
    @ResponseBody
    public ServerResponse getById(Long id){
        Brand brand = brandService.getById(id);
        return ServerResponse.success(brand);
    }
    @RequestMapping("updateBrand")
    @ResponseBody
    public ServerResponse updateBrand(Brand brand, HttpServletRequest request){
        if(StringUtils.isNotBlank(brand.getLogo())){
            String oldLogo = brand.getOldLogo();
            if(StringUtils.isNotBlank(oldLogo)){
                String realPath = request.getServletContext().getRealPath(oldLogo);
                File file = new File(realPath);
                if(file.exists()){
                    file.delete();
                }
            }
        }
         brandService.updateBrand(brand);
        return ServerResponse.success();
    }
    @RequestMapping("uploadFile")
    @ResponseBody
    public ServerResponse uploadFile(@RequestParam MultipartFile cover , HttpServletRequest request) {

        InputStream is = null;
        try {
            is = cover.getInputStream();
            String filename = cover.getOriginalFilename();
            String realPath = request.getServletContext().getRealPath("/img");
            String s = FileUtil.copyFile(is, filename, realPath);
            return ServerResponse.success("/img/"+s);

        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.error(ReposeEnum.USERNAME_IS_NOT_EXISTS);
        }

    }
}
