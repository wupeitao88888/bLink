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
import com.shiliuke.bean.Area;
import com.shiliuke.bean.PayEnd;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;

import java.util.List;

/*****
 * 已付定金
 */
public class areaListAdapter extends BaseAdapter {

    private List<Area.Datas> mList;
    private Context mContext;


    public areaListAdapter(Context _context, List<Area.Datas> mList) {
        this.mContext = _context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Area.Datas getItem(int position) {
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
                    R.layout.area_item_layout, parent, false);
        }

        Area.Datas area = mList.get(position);
        TextView area_name = ViewHolder.get(convertView, R.id.area_name);
        TextView area_address = ViewHolder.get(convertView, R.id.area_address);
        area_name.setText(area.getName());
        area_address.setText(area.getAddress());

        return convertView;
    }

}
