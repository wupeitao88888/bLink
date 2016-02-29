package com.shiliuke.adapter;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.UserImgs;

public class TopicPicGridAdapter extends BaseAdapter {
    private List<String> mUI;
    private Context context;
    private LayoutInflater mLayoutInflater;

    public TopicPicGridAdapter(List<String> ui, Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mUI = ui;
        this.context=context;
    }

    @Override
    public int getCount() {
        return mUI == null ? 0 : mUI.size();
    }

    @Override
    public String getItem(int position) {
        return mUI.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyGridViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MyGridViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.user_img_item,
                    parent, false);
            viewHolder.imageView = (ImageView) convertView
                    .findViewById(R.id.iv_user_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyGridViewHolder) convertView.getTag();
        }
        String url = getItem(position);
        setImage(viewHolder.imageView, url, context);
        return convertView;
    }

    private void setImage(ImageView iview, String url, Context context) {
        Glide.with(context)
                .load(url)
                .override(90, 90)
                .placeholder(R.drawable.gray)
                .error(R.drawable.gray)
                .crossFade()
                .into(iview);

    }

    private static class MyGridViewHolder {
        ImageView imageView;
    }
}
