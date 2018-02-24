package com.nguyen.shop.dto.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author RWM
 * @date 2018/2/24
 */
public class OrderProductResponse {
    public List<OrderItemResponse> orderItemVoList;
    public BigDecimal productTotalPrice;
    public String imageHost;
}
