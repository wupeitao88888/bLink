package com.shiliuke.adapter;

import android.content.Context;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
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
import com.shiliuke.bean.ActivityComment;
import com.shiliuke.bean.Comment;
import com.shiliuke.bean.PayEnd;
import com.shiliuke.global.AppConfig;
import com.shiliuke.utils.FaceConversionUtil;
import com.shiliuke.utils.L;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*****
 *
 *
 * 评论
 */
public class CommentAdapter extends BaseAdapter {

    private List<ActivityComment> mList;
    private Context mContext;
    private boolean blean;

    public CommentAdapter(final Context _context, List<ActivityComment> mList, boolean blean) {
        this.mContext = _context;
        this.mList = mList;
        this.blean = blean;
    }


    @Override
    public int getCount() {
        if (blean) {

            return mList == null ? 0 : (mList.size() >= 3 ? 3 : mList.size());
        } else {
            return mList == null ? 0 : mList.size();
        }
    }

    @Override
    public ActivityComment getItem(int position) {
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
                    R.layout.layout_commentactivity_item, parent, false);
        }

        ActivityComment payEnd = mList.get(position);
        ImageView member_avar = ViewHolder.get(convertView, R.id.member_avar);
        TextView member_name = ViewHolder.get(convertView, R.id.member_name);
        TextView info = ViewHolder.get(convertView, R.id.info);
        TextView add_time = ViewHolder.get(convertView, R.id.add_time);

        setImage(member_avar, payEnd.getFrom_userAvar(), mContext);

        if (!TextUtils.isEmpty(payEnd.getInfo())) {
            SpannableString expressionString = FaceConversionUtil.getInstace().getExpressionString(mContext, payEnd.getInfo());
            info.setText(expressionString);
        } else {
            info.setText("");
        }

        if (payEnd.getTo_userId().equalsIgnoreCase(payEnd.getFrom_userId()) || "0".equalsIgnoreCase(payEnd.getTo_userId()) || TextUtils.isEmpty(payEnd.getTo_userId())) {
            ViewUtil.setText(mContext, member_name, payEnd.getFrom_userName());
        } else {
            String name_ = payEnd.getFrom_userName() + "<font color=#c1c1c1>回复</font>" + payEnd.getTo_userName();
            member_name.setText(Html.fromHtml(name_));
        }
        ViewUtil.setText(mContext, add_time, payEnd.getAdd_time());


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
