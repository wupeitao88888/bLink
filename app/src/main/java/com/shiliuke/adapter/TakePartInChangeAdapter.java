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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.ChangeInfoActivity;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.ChangeInfo;
import com.shiliuke.bean.MeStarChange;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;

import java.util.List;

/*****
 * 参与置换的详情
 */
public class TakePartInChangeAdapter extends BaseAdapter {

    private List<ChangeInfo.Datas.ToExchange> mList;
    private Context mContext;


    public TakePartInChangeAdapter(final Context _context, List<ChangeInfo.Datas.ToExchange> mList) {
        this.mContext = _context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public ChangeInfo.Datas.ToExchange getItem(int position) {
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
                    R.layout.layout_takepartinchange_item, parent, false);
        }
        final ChangeInfo.Datas.ToExchange meInitateActivity = mList.get(position);
        ImageView initate_pic = ViewHolder.get(convertView, R.id.initate_pic);
        TextView initate_name = ViewHolder.get(convertView, R.id.initate_name);
        TextView initate_time = ViewHolder.get(convertView, R.id.initate_time);
        RelativeLayout my_change_re= ViewHolder.get(convertView, R.id.my_change_re);

        setImage(initate_pic, meInitateActivity.getImage_url(), mContext);
        ViewUtil.setText(mContext, initate_name, meInitateActivity.getFrom_gname());
        ViewUtil.setText(mContext, initate_time, meInitateActivity.getAdd_time());

//        my_change_re.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, ChangeInfoActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("exchange_id", meInitateActivity.getExchange_id());
//                bundle.putString("changepic", meInitateActivity.getMember_avar());
//                bundle.putString("changeName", meInitateActivity.getMember_name());
//                bundle.putString("changAddress", meInitateActivity.getHome());
//                intent.putExtra("model", bundle);
//                mContext.startActivity(intent);
//            }
//        });



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
