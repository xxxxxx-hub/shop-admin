package com.fh.controller.product;

import com.fh.common.ServerResponse;
import com.fh.util.OSSUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/file")
public class FileController {
    //上传图片
    @RequestMapping("/uploadFile")
    @ResponseBody
    public ServerResponse uploadFile(@RequestParam MultipartFile imageInfo, HttpServletRequest request){
        try {
            //获取输入流
            InputStream is = imageInfo.getInputStream();
            //获取文件全名称
            String fileName = imageInfo.getOriginalFilename();
            //获取项目绝对路径
           /* String realPath = request.getServletContext().getRealPath("/");
            String file = FileUtil.copyFile(is, filename, realPath+ SystemConstant.FILE_IMAGE_PATH);*/
            String uploadFile = OSSUtil.uploadFile(is, fileName);
            return ServerResponse.success(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
    }


    //上传文件

}
