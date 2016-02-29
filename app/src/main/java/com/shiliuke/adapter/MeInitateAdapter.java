package com.shiliuke.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.ExerciseActivity;
import com.shiliuke.BabyLink.R;
import com.shiliuke.BabyLink.information.InformationUtils;
import com.shiliuke.bean.MeInitateActivity;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;

import java.util.List;

/*****
 * 我参与的与我发起的共用一个adapter
 */
public class MeInitateAdapter extends BaseAdapter {

    private List<MeInitateActivity.Data> mList;
    private Context mContext;
    private boolean isUserActive = true;


    public MeInitateAdapter(final Context _context, List<MeInitateActivity.Data> mList, boolean isuseractive) {
        this.isUserActive = isuseractive;
        this.mContext = _context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public MeInitateActivity.Data getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.layout_meinitate_item, parent, false);
        }
        final MeInitateActivity.Data meInitateActivity = mList.get(position);
        ImageView initate_pic = ViewHolder.get(convertView, R.id.initate_pic);
        ImageView image_meinitate_item_person = ViewHolder.get(convertView, R.id.image_meinitate_item_person);
        ImageView image_meinitate_item_edit = ViewHolder.get(convertView, R.id.image_meinitate_item_edit);
        TextView initate_name = ViewHolder.get(convertView, R.id.initate_name);
        TextView initate_time = ViewHolder.get(convertView, R.id.initate_time);
        TextView initate_count = ViewHolder.get(convertView, R.id.initate_count);
        TextView tv_information_person = ViewHolder.get(convertView, R.id.tv_information_person);
        TextView tv_information_edit = ViewHolder.get(convertView, R.id.tv_information_edit);
        Button initate_status = ViewHolder.get(convertView, R.id.initate_status);

        if (isUserActive) {
            image_meinitate_item_person.setVisibility(View.VISIBLE);
            image_meinitate_item_edit.setVisibility(View.VISIBLE);
            InformationUtils.setInformationText(meInitateActivity.getLog_num(), tv_information_person);
            InformationUtils.setInformationText(meInitateActivity.getCommend_num(), tv_information_edit);
        } else {
            image_meinitate_item_person.setVisibility(View.INVISIBLE);
            InformationUtils.setInformationText(meInitateActivity.getCommend_num(), tv_information_edit);
        }


        setImage(initate_pic, meInitateActivity.getImage_url(), mContext);
        if (meInitateActivity.getTitle().length() > 10) {
            ViewUtil.setText(mContext, initate_name, meInitateActivity.getTitle().substring(0, 9));
        } else {
            ViewUtil.setText(mContext, initate_name, meInitateActivity.getTitle());
        }

        ViewUtil.setText(mContext, initate_time, meInitateActivity.getJihe_time() + "");
        ViewUtil.setText(mContext, initate_count, meInitateActivity.getCount());

        if ("1".equals(meInitateActivity.getStatus())) {
            initate_status.setBackgroundResource(R.drawable.button_normal);
            initate_status.setText(meInitateActivity.getStatus_desc());
        } else {
            initate_status.setBackgroundResource(R.drawable.button_gray_normal);
            initate_status.setText(meInitateActivity.getStatus_desc());
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meInitateActivity.setLog_num("0");
                meInitateActivity.setCommend_num("0");
                notifyDataSetChanged();
                Intent intent = new Intent(mContext, ExerciseActivity.class);
                intent.putExtra("activity_id", meInitateActivity.getActivity_id());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    private void setImage(ImageView iview, String url, Context context) {
        Glide.with(context)
                .load(url)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .placeholder(R.drawable.gray)
                .error(R.drawable.gray)
                .crossFade()
                .into(iview);

    }

}
