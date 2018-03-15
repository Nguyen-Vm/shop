package com.nguyen.shop.common;

/**
 * @author RWM
 * @date 2018/3/15
 */
public enum PaymentTypeEnum {
    ONLINE_PAY(1,"在线支付");

    PaymentTypeEnum(int code,String value){
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

    public static PaymentTypeEnum codeOf(int code){
        for(PaymentTypeEnum paymentTypeEnum : values()){
            if(paymentTypeEnum.getCode() == code){
                return paymentTypeEnum;
            }
        }
        throw new RuntimeException("没有找到对应的枚举");
    }
}
