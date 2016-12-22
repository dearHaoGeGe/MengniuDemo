package com.my.mengniudemo.bean;

/**
 * 购物车实体类
 *
 * Created by YJH on 2016/12/21 22:18.
 */

public class ShoppingCartBean {
    private String name;
    private float price;
    private int OrderNum;   //订购数量

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

    public int getOrderNum() {
        return OrderNum;
    }

    public void setOrderNum(int orderNum) {
        OrderNum = orderNum;
    }
}
