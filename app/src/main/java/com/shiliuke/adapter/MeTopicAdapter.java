package com.shiliuke.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.MeTopicInfoActivity;
import com.shiliuke.BabyLink.R;
import com.shiliuke.BabyLink.information.InformationUtils;
import com.shiliuke.bean.MeInitateActivity;
import com.shiliuke.bean.TopicInfo;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;

import java.util.List;

/*****
 *
 * 我参与的与我发起的共用一个adapter
 *
 *
 */
public class MeTopicAdapter extends BaseAdapter {

    private List<TopicInfo> mList;
    private Context mContext;


    public MeTopicAdapter(final Context _context, List<TopicInfo> mList) {

        this.mContext = _context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public TopicInfo getItem(int position) {
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
                    R.layout.layout_metopic_item, parent, false);
        }
        TopicInfo meInitateActivity = mList.get(position);
        ImageView initate_pic = ViewHolder.get(convertView, R.id.initate_pic);
        TextView initate_name = ViewHolder.get(convertView, R.id.initate_name);
        TextView initate_time = ViewHolder.get(convertView, R.id.initate_time);
        TextView initate_info = ViewHolder.get(convertView, R.id.initate_info);
        TextView initate_count = ViewHolder.get(convertView, R.id.initate_count);
        TextView tv_information_person = ViewHolder.get(convertView, R.id.tv_information_person);

        InformationUtils.setInformationText(meInitateActivity.getTalk_num(),tv_information_person);

        setImage(initate_pic, meInitateActivity.getMember_avar(), mContext);
        ViewUtil.setText(mContext, initate_name, meInitateActivity.getMember_name());
        ViewUtil.setText(mContext, initate_time, meInitateActivity.getAdd_time());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.RED_TEXT));
        String size="有"+meInitateActivity.getCommend_list().size()+"人评论";
        SpannableStringBuilder builder = new SpannableStringBuilder(size);
        builder.setSpan(redSpan, 1, size.length() - 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        initate_count.setText(builder);
        ViewUtil.setText(mContext, initate_info, meInitateActivity.getInfo());
        return convertView;
    }

    private void setImage(ImageView iview, String url, Context context) {
        Glide.with(context)
                .load(url)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .placeholder(R.mipmap.morentoux)
                .error(R.mipmap.morentoux)
                .crossFade()
                .into(iview);

    }

}
