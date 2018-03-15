package com.nguyen.shop.service;

import com.github.pagehelper.PageInfo;
import com.nguyen.shop.common.ServerResponse;
import com.nguyen.shop.dto.response.OrderResponse;

import java.util.Map;

/**
 * @author RWM
 * @date 2018/2/23
 */
public interface IOrderService {

    ServerResponse pay(Long orderNo, Integer userId, String path);
    ServerResponse aliCallback(Map<String, String> params);
    ServerResponse queryOrderPayStatus(Integer id, Long orderNo);
    ServerResponse createOrder(Integer userId, Integer shippingId);
    ServerResponse<String> cancel(Integer userId,Long orderNo);
    ServerResponse getOrderCartProduct(Integer userId);
    ServerResponse<OrderResponse> getOrderDetail(Integer userId, Long orderNo);
    ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize);

    ServerResponse<PageInfo> manageList(int pageNum,int pageSize);
    ServerResponse<OrderResponse> manageDetail(Long orderNo);
    ServerResponse<PageInfo> manageSearch(Long orderNo,int pageNum,int pageSize);
    ServerResponse<String> manageSendGoods(Long orderNo);

    void closeOrder(int hour);
}
