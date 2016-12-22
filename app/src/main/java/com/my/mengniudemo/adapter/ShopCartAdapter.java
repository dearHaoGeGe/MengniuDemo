package com.my.mengniudemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.mengniudemo.R;
import com.my.mengniudemo.bean.ShopCartBean;

import java.util.List;

/**
 * 底部购物车
 * <p>
 * Created by YJH on 2016/12/22 23:22.
 */

public class ShopCartAdapter extends BaseAdapter {

    private Context mContext;
    private List<ShopCartBean> scBeanList;

    public ShopCartAdapter(Context mContext, List<ShopCartBean> scBeanList) {
        this.mContext = mContext;
        this.scBeanList = scBeanList;
    }

    @Override
    public int getCount() {
        return scBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return scBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.shop_cart_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ShopCartBean bean = scBeanList.get(position);
        holder.tv_name.setText(bean.getName());
        holder.tv_count.setText(String.valueOf(bean.getBuyNum()));
        holder.tv_price.setText(String.valueOf(bean.getPrice()));

        return convertView;
    }

    private class ViewHolder {
        TextView tv_name;
        TextView tv_price;
        TextView tv_count;
        ImageView iv_add;
        ImageView iv_remove;

        private ViewHolder(View convertView) {
            tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            iv_add = (ImageView) convertView.findViewById(R.id.iv_add);
            iv_remove = (ImageView) convertView.findViewById(R.id.iv_remove);
        }
    }
}
