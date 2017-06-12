package com.chair.manager.enums;


public enum PayStatus {
	//1未支付 2已支付
    PAY_FAIL(1,"付款失败"),
    PAY_SUCCESS(2,"付款成功");

    private int value;
    private String desc;

    PayStatus(int value, String desc) {

        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String getDesc(int value) {
        for (PayStatus paymentStatus : PayStatus.values()) {
            if (paymentStatus.getValue() == value) {
                return paymentStatus.getDesc();
            }
        }
        return null;
    }

}

