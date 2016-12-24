package com.my.mengniudemo.bean;

/**
 * ProductBean中的产品列表实体类(右边显示的item中的实体类)
 * 购物车中显示的item也是用这个实体类
 * <p>
 * Created by YJH on 2016/12/21 22:09.
 */
public class ProductBean {
    private String productName;
    private float price;
    private int buyNum;    //订购数量

    public ProductBean() {
    }

    public ProductBean(float price, int buyNum, String productName) {
        this.productName = productName;
        this.price = price;
        this.buyNum = buyNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
