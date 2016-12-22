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
        RelativeLayout layout = null;
        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (RelativeLayout) inflater.inflate(R.layout.right_list_item, parent, false);
        } else {
            layout = (RelativeLayout) convertView;
        }
        TextView tv = (TextView) layout.findViewById(R.id.tv_right_item);
        final String name = cateBeanList.get(section).getList().get(position).getProductName();
        tv.setText(name);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "" + name, Toast.LENGTH_SHORT).show();
            }
        });
        return layout;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflater.inflate(R.layout.header_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        layout.setClickable(false);
        ((TextView) layout.findViewById(R.id.tv_right_header_item)).setText(cateBeanList.get(section).getTypeName()+"("+cateBeanList.get(section).getList().size()+")");
        return layout;
    }
}
