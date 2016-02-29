package com.shiliuke.adapter;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.FindModel;
import com.shiliuke.utils.ViewHolder;

import java.util.ArrayList;

/**
 * Created by wangzhi on 15/11/8.
 * 发现页面List Adapter
 */
public class FindAdapter extends BaseAdapter {
    private View footView;
    private ArrayList<FindModel.FindModelResult> data;
    private Fragment context;

    public FindAdapter(View footView, Fragment context, ArrayList<FindModel.FindModelResult> data) {
        this.footView = footView;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context.getActivity()).inflate(R.layout.item_find, null);
        }
        FindModel.FindModelResult model = data.get(position);
        ImageView image_item_find_topbg = ViewHolder.get(convertView, R.id.image_item_find_topbg);
        TextView tv_item_find_top = ViewHolder.get(convertView, R.id.tv_item_find_top);
        TextView tv_item_find_title = ViewHolder.get(convertView, R.id.tv_item_find_title);
        TextView tv_item_find_price = ViewHolder.get(convertView, R.id.tv_item_find_price);
        TextView tv_item_find_address = ViewHolder.get(convertView, R.id.tv_item_find_address);
        TextView tv_item_find_distance = ViewHolder.get(convertView, R.id.tv_item_find_distance);
        ///////
        Glide.with(context).load(model.getImage_url()).into(image_item_find_topbg);
        tv_item_find_title.setText(model.getStore_name());
        tv_item_find_address.setText(model.getAddress());
        tv_item_find_distance.setText(model.getMeters());
        if (TextUtils.isEmpty(model.getGoods_dingjin()) || Double.parseDouble(model.getGoods_dingjin()) == 0) {//免费
            tv_item_find_price.setText("¥0.00");
            tv_item_find_top.setText("免费");
            tv_item_find_top.setPadding(0, 20, 0, 0);
        } else {
            tv_item_find_top.setText("定金\n¥" + model.getGoods_dingjin());
            tv_item_find_top.setPadding(0, 10, 0, 0);
            tv_item_find_price.setText("¥" + model.getGoods_price() + " - ¥" + model.getGoods_baseprice());
        }
        return convertView;
    }
}
