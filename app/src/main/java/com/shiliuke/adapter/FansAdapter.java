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
import com.shiliuke.bean.Fans;
import com.shiliuke.bean.Friends;
import com.shiliuke.bean.Result;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*****
 * 我的粉丝
 */
public class FansAdapter extends BaseAdapter {

    private List<Fans.Datas> mList;
    private Context mContext;


    public FansAdapter(final Context _context, List<Fans.Datas> mList) {
        this.mContext = _context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Fans.Datas getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.layout_fans_item, parent, false);
        }
        final Fans.Datas friends = mList.get(position);
        ImageView friends_pic = ViewHolder.get(convertView, R.id.friends_pic);
        TextView friends_name = ViewHolder.get(convertView, R.id.friends_name);
        Button friends_remove = ViewHolder.get(convertView, R.id.friends_remove);


        setImage(friends_pic, friends.getFans_avar(), mContext);
        ViewUtil.setText(mContext, friends_name, friends.getFans_name());
        friends_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.startDialogLoading(mContext);
                Map<String, String> params = new HashMap<>();
                params.put("friend_id", friends.getFans_id());
                params.put("member_id", AppConfig.loginInfo.getMember_id());
                BasicRequest.getInstance().requestPost(new VolleyListerner() {
                    @Override
                    public void onResponse(String str, int taskid, Object obj) {
                        if (taskid == TaskID.DEL_FRIEND) {
                            DialogUtil.stopDialogLoading(mContext);
                            mList.get(position).setFlag("1");
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onResponseError(String error, int taskid) {
                        if (taskid == TaskID.DEL_FRIEND) {
                            DialogUtil.stopDialogLoading(mContext);
                            ToastUtil.showShort(mContext, error);
                        }
                    }
                }, TaskID.DEL_FRIEND, params, AppConfig.XIUDOU_ADDFRIEND, Result.class);
            }
        });
        if (friends.isGuanZhu()) {
            friends_remove.setOnClickListener(null);
            friends_remove.setText("已关注");
        }else {
            friends_remove.setText("加关注");
        }
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
