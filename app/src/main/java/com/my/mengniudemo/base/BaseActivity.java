package com.my.mengniudemo.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by YJH on 2016/12/21 21:29.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        bindEvent();
        bindValues();
    }

    /**
     * 初始化控件（必须）
     */
    protected abstract void initView();

    /**
     * 绑定事件（可选）
     */
    protected void bindEvent() {
    }

    /**
     * 绑定值（可选）
     */
    protected void bindValues() {
    }
}
