package com.nguyen.shop.dto.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author RWM
 * @date 2018/2/24
 */
public class OrderResponse {
    public Long orderNo;

    public BigDecimal payment;

    public Integer paymentType;

    public String paymentTypeDesc;
    public Integer postage;

    public Integer status;


    public String statusDesc;

    public String paymentTime;

    public String sendTime;

    public String endTime;

    public String closeTime;

    public String createTime;

    //订单的明细
    public List<OrderItemResponse> orderItemVoList;

    public String imageHost;
    public Integer shippingId;
    public String receiverName;

    public ShippingResponse shippingResponse;
}
