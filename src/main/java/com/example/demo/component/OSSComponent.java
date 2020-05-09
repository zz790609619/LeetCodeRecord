package com.example.demo.component;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class OSSComponent {
    /**
     * 访问域名地址
     */
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    /**
     * 访问秘钥
     */
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    /**
     * 访问秘钥
     */
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    /**
     * 用来管理所存储Object的存储空间
     */
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    /**
     * 访问url
     */
    @Value("${aliyun.oss.public.url}")
    private String publicUrl;

    /**
     * 储存路径
     */
    @Value("${aliyun.oss.path}")
    private String ossPath;

    /**
     * oss 单个图片文件上传
     * @param file
     * @return
     */
    public String uploadImage(File file, String fileName){
        try {
            //创建OSSClient实例
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);
            ossClient.putObject(bucketName,fileName,file);
            ossClient.shutdown();

            return String.format(publicUrl,bucketName) + fileName;
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 多个图片文件上传
     * @param files
     * @return
     */
    public List<String> uploadImages(File[] files){
        List<String> urls = new ArrayList<>();
        try {
            //创建OSSClient实例
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            for (File file : files) {
                String fileName = ossPath + UUID.randomUUID().toString().replace("-", "");
                ossClient.putObject(bucketName, fileName, file);
                urls.add(String.format(publicUrl,bucketName) + fileName);
            }
            ossClient.shutdown();
            return urls;
        }catch (Exception e){
            throw e;
        }
    }
}

