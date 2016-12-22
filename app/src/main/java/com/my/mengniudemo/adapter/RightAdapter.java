package com.my.mengniudemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.my.mengniudemo.R;
import com.my.mengniudemo.bean.CategoryBean;

import java.util.List;

/**
 * 右侧ListView的Adapter
 * <p>
 * Created by YJH on 2016/12/22 9:57.
 */

public class RightAdapter extends SectionedBaseAdapter {

    private Context context;
    private List<CategoryBean> cateBeanList;

    public RightAdapter(Context context, List<CategoryBean> cateBeanList) {
        this.context = context;
        this.cateBeanList = cateBeanList;
    }

    @Override
    public Object getItem(int section, int position) {
        return cateBeanList.get(section).getList().get(position);
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return cateBeanList.size();
    }

    @Override
    public int getCountForSection(int section) {
        return cateBeanList.get(section).getList().size();
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.right_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String name = cateBeanList.get(section).getList().get(position).getProductName();
        holder.tv_product_name.setText(name);
        holder.rl_right_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "" + name, Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView tv_product_name;
        RelativeLayout rl_right_list;

        private ViewHolder(View convertView) {
            tv_product_name = (TextView) convertView.findViewById(R.id.tv_right_item);
            rl_right_list = (RelativeLayout) convertView.findViewById(R.id.rl_right_list);
        }
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        HeaderViewHolder holder = null;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.header_item, parent, false);
            holder = new HeaderViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        holder.tv_right_header_item.setText(cateBeanList.get(section).getTypeName() + "(" + cateBeanList.get(section).getList().size() + ")");
        return convertView;
    }

    private class HeaderViewHolder {
        LinearLayout ll_right_header;
        TextView tv_right_header_item;

        private HeaderViewHolder(View convertView) {
            ll_right_header = (LinearLayout) convertView.findViewById(R.id.ll_right_header);
            tv_right_header_item = (TextView) convertView.findViewById(R.id.tv_right_header_item);
        }
    }
}
