package com.shiliuke.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.shiliuke.BabyLink.BaseSendActivity;
import com.shiliuke.BabyLink.R;
import com.shiliuke.BabyLink.chooseimage.ImageUtil;
import com.shiliuke.utils.L;

import java.util.ArrayList;

/**
 * Created by wangzhi on 15/11/22.
 */
public class ChooseImageAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private int selectedPosition = -1;
    private boolean shape;
    private ArrayList<String> mImagePathList;

    public boolean isShape() {
        return shape;
    }

    public void setShape(boolean shape) {
        this.shape = shape;
    }

    public ChooseImageAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public ChooseImageAdapter(Context context, ArrayList<String> mImagePathList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mImagePathList = mImagePathList;
    }


    public int getCount() {
        return (mImagePathList.size() + 1);
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_published_grida,
                    parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView
                    .findViewById(R.id.item_grida_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == mImagePathList.size()) {
            Glide.with(context).load(R.drawable.icon_addpic_unfocused).into(holder.image);
            if (position == BaseSendActivity.imageNum) {
                holder.image.setVisibility(View.GONE);
            }else{
                holder.image.setVisibility(View.VISIBLE);
            }
        } else {
            holder.image.setVisibility(View.VISIBLE);
            Glide.with(context).load(ImageUtil.getPathForUpload(mImagePathList.get(position))).into(holder.image);
        }
        return convertView;
    }

    public class ViewHolder {
        public ImageView image;
    }
}