package com.my.mengniudemo.bean;


/**
 * 产品包装
 * <p>
 * Created by YJH on 2016/12/28 11:35.
 */
public class Packaging implements MultiSelectedImp {
    private String name;
    private boolean isSelected;

    public Packaging(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
