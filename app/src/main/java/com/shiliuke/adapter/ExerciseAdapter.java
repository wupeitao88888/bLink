package com.shiliuke.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.ApplyActivity;
import com.shiliuke.BabyLink.ExerciseActivity;
import com.shiliuke.BabyLink.HtmlTextActivity;
import com.shiliuke.bean.Exercise;
import com.shiliuke.BabyLink.R;
import com.shiliuke.utils.GlideCircleTransform;
import com.shiliuke.utils.L;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 活动
 */
public class ExerciseAdapter extends BaseAdapter {
    private Context context;
    private List<Exercise> list;

    public ExerciseAdapter(Context context, List<Exercise> list) {
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Exercise classList = list.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_exercise_item, null);
        }

        ImageView exercise_pic = ViewHolder.get(convertView, R.id.exercise_pic);
        ImageView exercise_authorPic = ViewHolder.get(convertView, R.id.exercise_authorPic);
        ImageView layout_icon1 = ViewHolder.get(convertView, R.id.layout_icon1);
        ImageView layout_icon2 = ViewHolder.get(convertView, R.id.layout_icon2);
        ImageView layout_icon3 = ViewHolder.get(convertView, R.id.layout_icon3);
        ImageView layout_icon4 = ViewHolder.get(convertView, R.id.layout_icon4);
        ImageView layout_icon5 = ViewHolder.get(convertView, R.id.layout_icon5);

        TextView exercise_title = ViewHolder.get(convertView, R.id.exercise_title);
        TextView exercise_address = ViewHolder.get(convertView, R.id.exercise_address);
        TextView exercise_usercount = ViewHolder.get(convertView, R.id.exercise_usercount);
        TextView exercise_authorName = ViewHolder.get(convertView, R.id.exercise_authorName);
        TextView exercise_time = ViewHolder.get(convertView, R.id.exercise_time);

        Button signup = ViewHolder.get(convertView, R.id.signup);
        setImage(exercise_pic, classList.getImage_url(), context);
        setImageUserPic(exercise_authorPic, classList.getMember_avar(), context);
        ImageView[] image = {layout_icon1, layout_icon2, layout_icon3, layout_icon4, layout_icon5};
        for (int i = 0; i < 5; i++) {
            try {
                setImageUserPic(image[i], classList.getUserInfos().get(i).getMember_avar(), context);
            } catch (Exception e) {
                image[i].setVisibility(View.GONE);
            }
        }
        if (classList.isFull()) {
            signup.setClickable(false);
            signup.setBackgroundResource(R.drawable.button_gray_normal);
            signup.setText("报名已满");
        } else {
            if (!"0".equals(classList.getIsOut())) {
                signup.setClickable(false);
                signup.setBackgroundResource(R.drawable.button_gray_normal);
                signup.setText("活动已结束");
            } else {

                signup.setClickable(true);

                if(classList.getIsSign()){
                    signup.setText("已报名");
                    signup.setBackgroundResource(R.drawable.button_gray_normal);
                }else{
                    signup.setText("我要报名");
                    signup.setBackgroundResource(R.drawable.button_normal);
                }

            }
        }
        ViewUtil.setText(context, exercise_title, classList.getTitle());
        if( classList.getActivity_address().length()>8){
            ViewUtil.setText(context, exercise_address, classList.getActivity_address().substring(0,7)+"...");
        }else{
            ViewUtil.setText(context, exercise_address, classList.getActivity_address());
        }

        String ddd =" <font color=#C03971 >已有" + classList.getCount() + "人报名</font> ";
        exercise_usercount.setText(Html.fromHtml(ddd));

        if(classList.getMember_name().length()>5){
            ViewUtil.setText(context, exercise_authorName, classList.getMember_name().substring(0,5)+"...");
        }else{
            ViewUtil.setText(context, exercise_authorName, classList.getMember_name());
        }

        ViewUtil.setText(context, exercise_time, "活动时间：" + classList.getJihe_time());
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ExerciseActivity.class);
                intent.putExtra("activity_id", classList.getActivity_id());
                context.startActivity(intent);
                ApplyActivity.setApplayCallBack(new ApplyActivity.ApplyCallBack() {
                    @Override
                    public void success(int count) {
                        classList.setIsSign(true);
                        if(!TextUtils.isEmpty(classList.getCount())){
                            classList.setCount((Integer.parseInt(classList.getCount())+count)+"");
                        }else{
                            classList.setCount(count+"");
                        }

                        notifyDataSetChanged();
                    }

                    @Override
                    public void del() {
                        classList.setIsSign(false);
                        notifyDataSetChanged();
                    }

                });


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
                .placeholder(R.drawable.gray)
                .error(R.mipmap.morentoux)
                .crossFade()
                .into(iview);
    }


}
