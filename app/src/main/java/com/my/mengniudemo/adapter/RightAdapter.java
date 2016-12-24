package com.my.mengniudemo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.my.mengniudemo.R;
import com.my.mengniudemo.bean.CategoryBean;
import com.my.mengniudemo.bean.ProductBean;

import java.util.List;

/**
 * 右侧ListView的Adapter
 * <p>
 * Created by YJH on 2016/12/22 9:57.
 */

public class RightAdapter extends SectionedBaseAdapter {

    private final String TAG = getClass().getSimpleName() + "--->";
    public static final int ADD = 2;
    public static final int REMOVE = 1;
    private Context context;
    private List<CategoryBean> cateBeanList;
    private LeftListAdapter leftAdapter;

    public RightAdapter(Context context, List<CategoryBean> cateBeanList, LeftListAdapter leftAdapter) {
        this.context = context;
        this.cateBeanList = cateBeanList;
        this.leftAdapter = leftAdapter;
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

        ProductBean bean = cateBeanList.get(section).getList().get(position);
        holder.tv_product_name.setText(bean.getProductName());     //设置产品名称

        if (bean.getBuyNum() == 0) {
            holder.setIsVisible(View.GONE);
        } else {
            holder.setIsVisible(View.VISIBLE);
            holder.tv_product_num.setText(String.valueOf(bean.getBuyNum()));
        }

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
                    addAndRemoveNum(section, position, ADD);
                    break;

                case R.id.iv_remove_product:
                    addAndRemoveNum(section, position, REMOVE);
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

        /**
         * 控制减少数量按钮和数量tv是否显示
         *
         * @param visible View.GONE     View.INVISIBLE    View.VISIBLE
         */
        private void setIsVisible(int visible) {
            iv_remove_product.setVisibility(visible);
            tv_product_num.setVisibility(visible);
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

    /**
     * 点击添加或者删除按钮，通知左边的ListView数据变化并且刷新
     *
     * @param section  section
     * @param position position
     * @param mode     1 = remove，2 = add
     */
    private void addAndRemoveNum(int section, int position, int mode) {
        ProductBean bean = cateBeanList.get(section).getList().get(position);
        int num = bean.getBuyNum();
        switch (mode) {
            case ADD:
                bean.setBuyNum(++num);
                leftAdapter.showBuyNum(section, 1);         //通知左边的ListView数据变化并且刷新
                break;

            case REMOVE:
                if (num > 0) {
                    bean.setBuyNum(--num);
                    leftAdapter.showBuyNum(section, -1);    //通知左边的ListView数据变化并且刷新
                }
                break;

            default:
                Log.e(TAG, "模式设置错误");
        }
        notifyDataSetChanged();
    }

}
