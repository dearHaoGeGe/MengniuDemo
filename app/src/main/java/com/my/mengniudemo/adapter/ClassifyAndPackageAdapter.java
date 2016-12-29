package com.my.mengniudemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.my.mengniudemo.R;
import com.my.mengniudemo.bean.MultiSeletedImp;

import java.util.List;

/**
 * 显示业态和包装的adapter
 * <p>
 * Created by YJH on 2016/12/28 13:54.
 */
public class ClassifyAndPackageAdapter extends BaseAdapter {

    private Context mContext;
    private List<? extends MultiSeletedImp> list;

    public ClassifyAndPackageAdapter(Context mContext, List<? extends MultiSeletedImp> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_constellation_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        fillValue(position, holder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.mText.setText(list.get(position).getName());
        if (list.get(position).isSelected()) {
            viewHolder.mText.setTextColor(mContext.getResources().getColor(R.color.drop_down_selected));
            viewHolder.mText.setBackgroundResource(R.drawable.check_bg);
        } else {
            viewHolder.mText.setTextColor(mContext.getResources().getColor(R.color.drop_down_unselected));
            viewHolder.mText.setBackgroundResource(R.drawable.uncheck_bg);
        }
    }

    private class ViewHolder {
        TextView mText;

        ViewHolder(View view) {
            mText = (TextView) view.findViewById(R.id.text);
        }
    }

    public void setIsSelected(int position) {
        if (list.get(position).isSelected()) {
            list.get(position).setSelected(false);
        } else {
            list.get(position).setSelected(true);
        }
        notifyDataSetChanged();
    }
}
