package com.nguyen.service;

import com.nguyen.common.ServerResponse;
import com.nguyen.pojo.Product;

/**
 * @author RWM
 * @date 2018/2/2
 */
public interface IProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse setProductStatus(Integer productId, Integer status);

    ServerResponse getProductDetail(Integer productId);

    ServerResponse getProductList(int pageNum, int pageSize);

    ServerResponse searchProduct(String productName, Integer productId, int pageNum, int pageSize);
}
