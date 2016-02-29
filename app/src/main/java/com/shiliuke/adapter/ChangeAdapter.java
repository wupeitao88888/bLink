package com.shiliuke.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.ChangeInfoActivity;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.Change;
import com.shiliuke.utils.GlideCircleTransform;
import com.shiliuke.utils.L;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;

import java.util.List;

/**
 * 活动
 */
public class ChangeAdapter extends BaseAdapter {
    private Context context;
    private List<Change.Data> list;

    public ChangeAdapter(Context context, List<Change.Data> list) {
        super();
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Change.Data model = list.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_change_item, null);
        }

        ImageView changepic = ViewHolder.get(convertView, R.id.changepic);
        TextView changeName = ViewHolder.get(convertView, R.id.changeName);
        TextView changAddress = ViewHolder.get(convertView, R.id.changAddress);
        ImageView changeUrl = ViewHolder.get(convertView, R.id.changeUrl);
        TextView mineGoods = ViewHolder.get(convertView, R.id.mineGoods);
        TextView exchangeGoods = ViewHolder.get(convertView, R.id.exchangeGoods);
        Button look_info = ViewHolder.get(convertView, R.id.look_info);

        setImage(changeUrl, model.getImage_url(), context);
        setImageUserPic(changepic, model.getMember_avar(), context);
        ViewUtil.setText(context, changeName, model.getMember_name());
        ViewUtil.setText(context, changAddress, model.getHome());
        ViewUtil.setText(context, mineGoods, model.getFrom_gname());
        ViewUtil.setText(context, exchangeGoods, model.getTo_gname());
        look_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChangeInfoActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("exchange_id",model.getExchange_id());
                bundle.putString("changepic",model.getMember_avar());
                bundle.putString("changeName",model.getMember_name());
                bundle.putString("changAddress", model.getHome());
                intent.putExtra("model",bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }


    private void setImage(ImageView iview, String url, Context context) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.gray)
                .error(R.drawable.gray)
                .crossFade()
                .into(iview);

    }

    private void setImageUserPic(ImageView iview, String url, Context context) {
        Glide.with(context)
                .load(url)
                .override(35, 35)
                .placeholder(R.mipmap.morentoux)
                .error(R.mipmap.morentoux)
                .crossFade()
                .into(iview);
    }


}
