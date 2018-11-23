package com.nguyen.shop.utils;

import com.aliyun.oss.OSSClient;

import java.io.File;

/**
 * @author RWM
 * @date 2018/11/23
 */
public class OSSUtil {

    private static String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
    private static String accessKeyId = "LTAI1Vm7JTL8CSPf";
    private static String accessKeySecret = "jMpy3B5MjhYYP0M5Upg16CyvGRBGgt";
    private static String bucketName = "alws";
    private static OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

    public static void upload(String key, File file) {
        ossClient.putObject(bucketName, key, file);
    }
}
