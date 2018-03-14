package com.nguyen.shop.controller.portal;

import com.nguyen.shop.common.ServerResponse;
import com.nguyen.shop.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author RWM
 * @date 2018/2/7
 */
@Controller
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse detail(Integer productId){
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse detailRESTful(@PathVariable Integer productId){
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "keyword", required = false)String keyword,
                               @RequestParam(value = "categoryId", required = false)Integer categoryId,
                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                               @RequestParam(value = "orderBy", defaultValue = "") String orderBy){
        return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }

    @RequestMapping(value = "/{keyword}/{categoryId}/{pageNum}/{pageSize}/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listRESTful(@PathVariable(value = "keyword")String keyword,
                               @PathVariable(value = "categoryId")Integer categoryId,
                               @PathVariable(value = "pageNum") Integer pageNum,
                               @PathVariable(value = "pageSize") Integer pageSize,
                               @PathVariable(value = "orderBy") String orderBy){
        if (pageNum == null){
            pageNum = 1;
        }
        if (pageSize == null){
            pageSize = 20;
        }
        if (StringUtils.isBlank(orderBy)){
            orderBy = "price_asc";
        }
        return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }

    @RequestMapping(value = "/keyword/{keyword}/{pageNum}/{pageSize}/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listRESTful(@PathVariable(value = "keyword")String keyword,
                                      @PathVariable(value = "pageNum") Integer pageNum,
                                      @PathVariable(value = "pageSize") Integer pageSize,
                                      @PathVariable(value = "orderBy") String orderBy){
        if (pageNum == null){
            pageNum = 1;
        }
        if (pageSize == null){
            pageSize = 20;
        }
        if (StringUtils.isBlank(orderBy)){
            orderBy = "price_asc";
        }
        return iProductService.getProductByKeywordCategory(keyword, null, pageNum, pageSize, orderBy);
    }

    @RequestMapping(value = "/categoryId/{categoryId}/{pageNum}/{pageSize}/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listRESTful(@PathVariable(value = "categoryId")Integer categoryId,
                                      @PathVariable(value = "pageNum") Integer pageNum,
                                      @PathVariable(value = "pageSize") Integer pageSize,
                                      @PathVariable(value = "orderBy") String orderBy){
        if (pageNum == null){
            pageNum = 1;
        }
        if (pageSize == null){
            pageSize = 20;
        }
        if (StringUtils.isBlank(orderBy)){
            orderBy = "price_asc";
        }
        return iProductService.getProductByKeywordCategory("", categoryId, pageNum, pageSize, orderBy);
    }
}
