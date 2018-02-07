package com.nguyen.dto.response;

import java.math.BigDecimal;

/**
 * @author RWM
 * @date 2018/2/7
 */
public class CartProductInfo{
    //结合了产品和购物车的一个抽象对象
    public Integer id;
    public Integer userId;
    public Integer productId;
    public Integer quantity;//购物车中此商品的数量
    public String productName;
    public String productSubtitle;
    public String productMainImage;
    public BigDecimal productPrice;
    public Integer productStatus;
    public BigDecimal productTotalPrice;
    public Integer productStock;
    public Integer productChecked;//此商品是否勾选

    public String limitQuantity;//限制数量的一个返回结果
}
