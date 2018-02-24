package com.nguyen.shop.service;

import com.nguyen.shop.common.ServerResponse;

/**
 * @author RWM
 * @date 2018/2/23
 */
public interface IOrderService {

    ServerResponse pay(Long orderNo, Integer userId, String path);

}
