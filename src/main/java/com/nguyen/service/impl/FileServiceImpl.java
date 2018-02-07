package com.nguyen.service.impl;

import com.google.common.collect.Lists;
import com.nguyen.service.IFileService;
import com.nguyen.utils.FTPUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @author RWM
 * @date 2018/2/6
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file, String path){
        String fileName = file.getOriginalFilename();
        //扩展名
        //abc.jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);

        File fileDir = new File(path);
        if (!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
            //文件已经上传成功

            FTPUtils.uploadFile(Lists.newArrayList(targetFile));
            //已经上传到FTP服务器上

            //上传完之后，删除upload下面的文件
            targetFile.delete();
        }catch (Exception e){
            logger.error("上传文件异常", e);
            return StringUtils.EMPTY;
        }
        return targetFile.getName();
    }
}
