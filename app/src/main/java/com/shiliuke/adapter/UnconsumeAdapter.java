package com.shiliuke.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.MeInitateActivity;
import com.shiliuke.bean.Unconsume;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;

import java.util.List;

/*****
 *
 * 未消费与已消费共用一个adapter
 *
 *
 */
public class UnconsumeAdapter extends BaseAdapter {

    private List<Unconsume.Data> mList;
    private Context mContext;


    public UnconsumeAdapter(final Context _context, List<Unconsume.Data> mList) {

        this.mContext = _context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Unconsume.Data getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.layout_unconsume_item, parent, false);
        }
        Unconsume.Data unconsume = mList.get(position);

        TextView unconsume_title = ViewHolder.get(convertView, R.id.unconsume_title);
        TextView unconsume_endtime = ViewHolder.get(convertView, R.id.unconsume_endtime);
        TextView unconsume_count = ViewHolder.get(convertView, R.id.unconsume_count);
        ViewUtil.setText(mContext, unconsume_title, unconsume.getGoods().getGoods_name());
        ViewUtil.setText(mContext, unconsume_endtime,"有效期至"+unconsume.getGoods().getUse_time());
        ViewUtil.setText(mContext, unconsume_count, "1");

        return convertView;
    }




}
