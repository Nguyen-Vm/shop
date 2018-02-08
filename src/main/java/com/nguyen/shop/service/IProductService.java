package com.nguyen.shop.service;

import com.nguyen.shop.common.ServerResponse;
import com.nguyen.shop.pojo.Product;

/**
 * @author RWM
 * @date 2018/2/2
 */
public interface IProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse setProductStatus(Integer productId, Integer status);

    ServerResponse getManageProductDetail(Integer productId);

    ServerResponse getProductList(int pageNum, int pageSize);

    ServerResponse searchProduct(String productName, Integer productId, int pageNum, int pageSize);

    ServerResponse getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy);

    ServerResponse getProductDetail(Integer productId);
}
