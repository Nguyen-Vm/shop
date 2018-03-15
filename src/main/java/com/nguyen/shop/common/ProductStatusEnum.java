package com.nguyen.shop.common;

/**
 * @author RWM
 * @date 2018/3/15
 */
public enum ProductStatusEnum {

    ON_SALE(1, "在线");

    private int code;
    private String value;

    ProductStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
