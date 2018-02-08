package com.nguyen.shop.dto.response;

import java.math.BigDecimal;

/**
 * @author RWM
 * @date 2018/2/2
 */
public class ProductResponse {
    public Integer id;
    public Integer categotyId;
    public String name;
    public String subtitle;
    public String mainImages;
    public String subImages;
    public String detail;
    public BigDecimal price;
    public Integer stock;
    public Integer status;
    public String createTime;
    public String updateTime;
    public String imageHost;
}
