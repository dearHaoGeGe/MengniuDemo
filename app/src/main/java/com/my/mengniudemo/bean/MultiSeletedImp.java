package com.my.mengniudemo.bean;

/**
 * 这个接口是为了统一包装和业态实体类的adapter
 *
 * Created by YJH on 2016/12/28 13:49.
 */

public interface MultiSeletedImp {
    String getName();
    void setName(String name);

    boolean isSelected();

    void setSelected(boolean selected);
}
