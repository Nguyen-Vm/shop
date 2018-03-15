package com.nguyen.shop.common;

/**
 * @author RWM
 * @date 2018/3/15
 */
public enum PayPlatformEnum {
    ALIPAY(1,"支付宝");

    PayPlatformEnum(int code,String value){
        this.code = code;
        this.value = value;
    }
    private int code;
    private String value;

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static PayPlatformEnum codeOf(int code){
        for(PayPlatformEnum payPlatformEnum : values()){
            if(payPlatformEnum.getCode() == code){
                return payPlatformEnum;
            }
        }
        throw new RuntimeException("没有找到对应的枚举");
    }
}
