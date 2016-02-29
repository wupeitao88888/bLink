package com.shiliuke.adapter;

import android.content.Context;
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
import com.shiliuke.bean.EnableChange;
import com.shiliuke.bean.PayEnd;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;

import java.util.List;

/*****
 * 已付定金
 */
public class SpinnerAdapter extends BaseAdapter {

    private List<EnableChange.Datas> mList;
    private Context mContext;


    public SpinnerAdapter(final Context _context, List<EnableChange.Datas> mList) {

        this.mContext = _context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public EnableChange.Datas getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.layout_text_item, parent, false);
        }

        EnableChange.Datas payEnd = mList.get(position);
        TextView pay_url = ViewHolder.get(convertView, R.id.item);
        ViewUtil.setText(mContext, pay_url, payEnd.getFrom_gname());
        return convertView;
    }


}
