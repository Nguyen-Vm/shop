package com.nguyen.shop.service.impl;

import com.google.common.collect.Lists;
import com.nguyen.shop.service.IFileService;
import com.nguyen.shop.utils.FTPUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @author RWM
 * @date 2018/2/6
 */
@Service("iFileService")
@Slf4j
public class FileServiceImpl implements IFileService {

    public String upload(MultipartFile file, String path){
        String fileName = file.getOriginalFilename();
        //扩展名
        //abc.jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        log.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);

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
            log.error("上传文件异常", e);
            return StringUtils.EMPTY;
        }
        return targetFile.getName();
    }
}
