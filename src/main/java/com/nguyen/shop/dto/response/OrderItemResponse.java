package com.nguyen.shop.dto.response;

import java.math.BigDecimal;

/**
 * @author RWM
 * @date 2018/2/24
 */
public class OrderItemResponse {
    public Long orderNo;

    public Integer productId;

    public String productName;
    public String productImage;

    public BigDecimal currentUnitPrice;

    public Integer quantity;

    public BigDecimal totalPrice;

    public String createTime;
}
