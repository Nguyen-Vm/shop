package com.nguyen.shop.controller.backend;

import com.google.common.collect.Maps;
import com.nguyen.shop.common.ServerResponse;
import com.nguyen.shop.pojo.Product;
import com.nguyen.shop.service.IFileService;
import com.nguyen.shop.service.IProductService;
import com.nguyen.shop.service.IUserService;
import com.nguyen.shop.utils.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author RWM
 * @date 2018/2/2
 */
@Controller
@RequestMapping("/manage/product/")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService ossFileService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse saveProduct(Product product) {
        //全部通过拦截器验证是否登录以及权限
        return iProductService.saveOrUpdateProduct(product);
    }

    @RequestMapping("set_product_status.do")
    @ResponseBody
    public ServerResponse setProductStatus(Integer productId, Integer status) {
        //全部通过拦截器验证是否登录以及权限
        return iProductService.setProductStatus(productId, status);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getProductDetail(Integer productId) {
        //全部通过拦截器验证是否登录以及权限
        return iProductService.getManageProductDetail(productId);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getProductList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSIze", defaultValue = "10") int pageSize) {
        //全部通过拦截器验证是否登录以及权限
        return iProductService.getProductList(pageNum, pageSize);
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse productSearch(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                        Integer productId, String productName) {
        //全部通过拦截器验证是否登录以及权限
        return iProductService.searchProduct(productName, productId, pageNum, pageSize);
    }

    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(@RequestParam(value = "upload_file", required = false) MultipartFile file,
                                 HttpServletRequest httpServletRequest) {
        //全部通过拦截器验证是否登录以及权限
        String path = httpServletRequest.getSession().getServletContext().getRealPath("upload");
        String targetFileName = ossFileService.upload(file, path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);
        return ServerResponse.createBySuccess(fileMap);
    }

    @RequestMapping("upload_oss.do")
    @ResponseBody
    public ServerResponse uploadOss(@RequestParam(value = "upload_file", required = false) MultipartFile file,
                                    HttpServletRequest httpServletRequest) {
        //全部通过拦截器验证是否登录以及权限
        String path = httpServletRequest.getSession().getServletContext().getRealPath("upload");
        String targetFileName = ossFileService.upload(file, path);
        String url = PropertiesUtil.getProperty("oss.server.http.prefix") + targetFileName;
        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);
        return ServerResponse.createBySuccess(fileMap);
    }

    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(@RequestParam(value = "upload_file", required = false) MultipartFile file,
                                 HttpServletRequest httpServletRequest, HttpServletResponse response) {
        Map resultMap = Maps.newHashMap();
        //全部通过拦截器验证是否登录以及权限
        String path = httpServletRequest.getSession().getServletContext().getRealPath("upload");
        String targetFileName = ossFileService.upload(file, path);
        if (StringUtils.isBlank(targetFileName)) {
            resultMap.put("success", false);
            resultMap.put("msg", "上传失败");
            return resultMap;
        }
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
        resultMap.put("success", true);
        resultMap.put("msg", "上传成功");
        resultMap.put("file_path", url);
        response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
        return resultMap;
    }


}
