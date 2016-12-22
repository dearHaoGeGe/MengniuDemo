package com.my.mengniudemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.my.mengniudemo.R;
import com.my.mengniudemo.bean.CategoryBean;

import java.util.List;

/**
 * 左面ListView的Adapter（只是一个普通的ListView）
 * <p>
 * Created by YJH on 2016/12/21 23:13.
 */

public class LeftListAdapter extends BaseAdapter {

    private Context context;
    private List<CategoryBean> data;

    public LeftListAdapter(Context context, List<CategoryBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.left_list_item, parent, false);
            holder = new ViewHolder();
            holder.left_list_item = (TextView) convertView.findViewById(R.id.tv_left_list_item);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.updateBackgroundColor(position);

        return convertView;
    }

    private class ViewHolder {
        private TextView left_list_item;

        private void updateBackgroundColor(int position) {
            CategoryBean bean = data.get(position);
            left_list_item.setText(bean.getTypeName());
            if (bean.isFlag()) {
                left_list_item.setBackgroundColor(Color.rgb(255, 255, 255));  //白色
            } else {
                left_list_item.setBackgroundColor(Color.TRANSPARENT);   //透明色
            }
        }
    }

    public void setData(List<CategoryBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
