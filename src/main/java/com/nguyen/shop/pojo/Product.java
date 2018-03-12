package com.nguyen.shop.pojo;

import com.nguyen.shop.dto.response.ProductResponse;
import com.nguyen.shop.utils.DateFormat;
import com.nguyen.shop.utils.DateUtil;
import com.nguyen.shop.utils.PropertiesUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private String subImages;
    private String detail;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private Date createTime;
    private Date updateTime;

    public ProductResponse response() {
        ProductResponse response = new ProductResponse();
        response.id = id;
        response.categotyId = categoryId;
        response.name = name;
        response.subtitle = subtitle;
        response.mainImages = mainImage;
        response.subImages = subImages;
        response.detail = detail;
        response.price = price;
        response.stock = stock;
        response.status = status;
        response.imageHost = PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/");
        response.createTime = DateUtil.format(createTime, DateFormat.StrikeDateTime);
        response.updateTime = DateUtil.format(updateTime, DateFormat.StrikeDateTime);
        return response;
    }
}