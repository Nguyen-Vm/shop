package com.nguyen.shop.dto.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author RWM
 * @date 2018/2/7
 */
public class CartResponse {

    public List<CartProductInfo> cartProductVoList;
    public BigDecimal cartTotalPrice;
    public Boolean allChecked;//是否已经都勾选
    public String imageHost;
}
