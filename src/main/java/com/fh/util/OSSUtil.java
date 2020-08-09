package com.fh.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;

import java.util.Date;
import java.util.UUID;

public class OSSUtil {

    private static final String ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";

    private static final String ACCESSKEYID = "LTAI4GAeP8eCDL765c3MupeL";

    private static final String ACCESSKEYSECRET = "6yAuC0fDvDJjbwNcOKkP1UiDS9BkKp";

    private static final String BUCKET_NAME = "xs1907";

    private static final String DOMAIN = "http://xs1907.oss-cn-beijing.aliyuncs.com/";

    public static String uploadFile(InputStream is, String fileNameInfo) {
        OSS ossClient = null;
        String path = "";
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
            String dateStr = DateUtil.date2str(new Date(), DateUtil.Y_M_D);
            String fileName = UUID.randomUUID().toString()+getSuffix(fileNameInfo);
            // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
            path = dateStr + "/" + fileName;
            ossClient.putObject(BUCKET_NAME, path, is);
        } catch (OSSException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ossClient != null) {
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
        return DOMAIN+path;
    }

    public static void deleteFile(String path) {
        OSS ossClient = null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
            // 删除文件。
            String objName = path.replace(DOMAIN, "");
            ossClient.deleteObject(BUCKET_NAME, objName);
        } catch (OSSException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (null != ossClient) {
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }

    }

    private static String getSuffix(String fileName) {
        if (StringUtils.isNotEmpty(fileName)) {
            int i = fileName.lastIndexOf(".");
            return fileName.substring(i);
        }
        return "";
    }
}
