package com.my.mengniudemo.bean;


/**
 * 产品业态
 * <p>
 * Created by YJH on 2016/12/28 11:35.
 */
public class Classify implements MultiSeletedImp {
    private String name;
    private boolean isSelected;     //是否选中

    public Classify(String name, boolean isSelected) {
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
        this.isSelected = selected;
    }
}
