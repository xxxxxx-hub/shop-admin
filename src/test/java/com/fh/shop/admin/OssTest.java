package com.fh.shop.admin;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class OssTest {
    @Test
    public  void  test1(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
                String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
                String accessKeyId = "LTAI4GAeP8eCDL765c3MupeL";
                String accessKeySecret = "6yAuC0fDvDJjbwNcOKkP1UiDS9BkKp";
                InputStream inputStream = null;
                OSS ossClient =null;
                try {
        // 创建OSSClient实例。
                ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传文件流。
                    inputStream = new FileInputStream("C:Users/xusen/Pictures/Saved Pictures/image/1.jpg");
                    ossClient.putObject("xs1907", "a/1.jpg", inputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }finally {
                    if (null!= ossClient){
                        // 关闭OSSClient。
                        ossClient.shutdown();
                    }
                }



    }
    @Test
    public void test2(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4GAeP8eCDL765c3MupeL";
        String accessKeySecret = "6yAuC0fDvDJjbwNcOKkP1UiDS9BkKp";
        String bucketName = "xs1907";
        String objectName = "a/1.jpg";
        OSS ossClient =null;
        // 创建OSSClient实例。
        try {
             ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
            ossClient.deleteObject(bucketName, objectName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }  finally {
            if (null!=ossClient){
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
    }
}
