package com.example.demo.util;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;

public class TransferFile extends FileUtils {
    /**
     * MultipartFile转换File
     *
     * @param file       文件
     * @param fileName   文件名
     * @param fileSuffix 文件后缀
     * @param path       文件存储路径
     * @return File
     * @throws IOException
     */
    public static File transMultipartFile(@NotNull MultipartFile file, String fileName, String fileSuffix, String path) throws IOException {
        //创建保存文件的路径
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //创造一个设定好 fileName(文件名称)、fileSuffix(文件类型)、dir(路径)的文件
        File tempFile = File.createTempFile(fileName, fileSuffix, dir);
        //将前端传来的文件转换成我们创造的文件
        file.transferTo(tempFile);
        return tempFile;
    }

}
