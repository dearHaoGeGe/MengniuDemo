package com.my.mengniudemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        holder.tv_product_name.setText(cateBeanList.get(section)
                .getList().get(position).getProductName());     //设置产品名称

        OnClickListenerRightItem onClickListener = new OnClickListenerRightItem(section, position, holder);
        holder.ll_right_list.setOnClickListener(onClickListener);
        holder.iv_add_product.setOnClickListener(onClickListener);
        holder.iv_remove_product.setOnClickListener(onClickListener);
        return convertView;
    }

    /**
     * 点击事件实现类
     */
    private class OnClickListenerRightItem implements View.OnClickListener {

        private int section;
        private int position;
        private ViewHolder holder;

        private OnClickListenerRightItem(int section, int position, ViewHolder holder) {
            this.section = section;
            this.position = position;
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_right_list:
                    Toast.makeText(context, "" + cateBeanList.get(section).getList().get(position).getProductName(), Toast.LENGTH_SHORT).show();
                    break;

                case R.id.iv_add_product:
                    Toast.makeText(context, "添加商品", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.iv_remove_product:
                    Toast.makeText(context, "移除商品", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private class ViewHolder {
        TextView tv_product_name;
        LinearLayout ll_right_list;
        ImageView iv_remove_product;
        ImageView iv_add_product;
        TextView tv_product_num;

        private ViewHolder(View convertView) {
            tv_product_name = (TextView) convertView.findViewById(R.id.tv_right_item);
            ll_right_list = (LinearLayout) convertView.findViewById(R.id.ll_right_list);
            iv_remove_product = (ImageView) convertView.findViewById(R.id.iv_remove_product);
            iv_add_product = (ImageView) convertView.findViewById(R.id.iv_add_product);
            tv_product_num = (TextView) convertView.findViewById(R.id.tv_product_num);
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
