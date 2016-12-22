package com.my.mengniudemo.bean;

/**
 * 购物车实体类
 *
 * Created by YJH on 2016/12/21 22:18.
 */

public class ShopCartBean {
    private String name;
    private float price;
    private int buyNum;   //订购数量

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }
}
