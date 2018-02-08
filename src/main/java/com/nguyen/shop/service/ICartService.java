package com.nguyen.shop.service;

import com.nguyen.shop.common.ServerResponse;

/**
 * @author RWM
 * @date 2018/2/7
 */
public interface ICartService {

    ServerResponse add(Integer userId, Integer productId, Integer count);

    ServerResponse list(Integer userId);

    ServerResponse update(Integer userId, Integer productId, Integer count);

    ServerResponse deleteProduct(Integer userId, String productIds);

    ServerResponse selectOrUnSelect(Integer userId, Integer productId, Integer checked);

    ServerResponse<Integer> getCartProductCount(Integer userId);

}
