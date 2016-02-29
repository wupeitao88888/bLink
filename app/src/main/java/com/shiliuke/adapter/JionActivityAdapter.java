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
import com.shiliuke.bean.PayEnd;
import com.shiliuke.bean.UserInfo;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;

import java.util.List;

/*****
 * 评论人
 */
public class JionActivityAdapter extends BaseAdapter {

    private List<UserInfo> mList;
    private Context mContext;


    public JionActivityAdapter(final Context _context, List<UserInfo> mList) {

        this.mContext = _context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public UserInfo getItem(int position) {
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
                    R.layout.layout_jionactivity_item, parent, false);
        }
        UserInfo payEnd = mList.get(position);
        ImageView member_avar = ViewHolder.get(convertView, R.id.member_avar);
        TextView member_name = ViewHolder.get(convertView, R.id.member_name);
        setImage(member_avar, payEnd.getMember_avar(), mContext);
        ViewUtil.setText(mContext, member_name, payEnd.getMember_name());
        return convertView;
    }

    private void setImage(ImageView iview, String url, Context context) {
        Glide.with(context)
                .load(url)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .placeholder(R.drawable.gray)
                .error(R.mipmap.morentoux)
                .crossFade()
                .into(iview);

    }

}
