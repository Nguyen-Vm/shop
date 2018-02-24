package com.nguyen.shop.service;

import com.nguyen.shop.common.ServerResponse;

import java.util.Map;

/**
 * @author RWM
 * @date 2018/2/23
 */
public interface IOrderService {

    ServerResponse pay(Long orderNo, Integer userId, String path);

    ServerResponse aliCallback(Map<String, String> params);

    ServerResponse queryOrderPayStatus(Integer id, Long orderNo);
}
