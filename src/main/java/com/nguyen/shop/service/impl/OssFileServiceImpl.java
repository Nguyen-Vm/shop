package com.nguyen.shop.service.impl;

import com.nguyen.shop.service.IFileService;
import com.nguyen.shop.utils.OSSUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @author RWM
 * @date 2018/11/23
 */
@Service("ossFileService")
@Slf4j
public class OssFileServiceImpl implements IFileService {
    public String upload(MultipartFile file, String path){
        String fileName = file.getOriginalFilename();
        //扩展名
        //abc.jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf("."));
        String uploadFileName = UUID.randomUUID().toString() + fileExtensionName;
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

            OSSUtil.upload(uploadFileName, targetFile);
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
