package com.mmall.service.impl;

import com.mmall.service.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author:Chengkangxin
 * @Description:
 * @Date:Create in 15:36 2018/10/24
 */

@Service("iFileService")
public class FileServiceImpl implements IFileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file,String path){
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+fileExtensionName;

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.mkdirs();
            fileDir.setWritable(true);
        }

        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            logger.error("上传文件异常",e);
            return null;
        }

        return targetFile.getName();
    }


}
