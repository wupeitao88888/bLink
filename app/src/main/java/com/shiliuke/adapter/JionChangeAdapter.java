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
import com.shiliuke.BabyLink.information.InformationUtils;
import com.shiliuke.bean.MeStarChange;
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
 * 我参与的置换与我发起的置换的共用一个adapter
 */
public class JionChangeAdapter extends BaseAdapter {

    private List<MeStarChange.Datas> mList;
    private Context mContext;


    public JionChangeAdapter(final Context _context, List<MeStarChange.Datas> mList) {
        this.mContext = _context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public MeStarChange.Datas getItem(int position) {
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
                    R.layout.layout_jionchange_item, parent, false);
        }
        final MeStarChange.Datas meInitateActivity = mList.get(position);
        ImageView initate_pic = ViewHolder.get(convertView, R.id.initate_pic);
        TextView initate_name = ViewHolder.get(convertView, R.id.initate_name);
        TextView initate_time = ViewHolder.get(convertView, R.id.initate_time);
        Button initate_status = ViewHolder.get(convertView, R.id.initate_status);
        RelativeLayout my_change_re = ViewHolder.get(convertView, R.id.my_change_re);
        final Button initate_unchange = ViewHolder.get(convertView, R.id.initate_unchange);
        TextView tv_information_exchange = ViewHolder.get(convertView, R.id.tv_information_exchange);
        InformationUtils.setInformationText(meInitateActivity.getNum(), tv_information_exchange);
        setImage(initate_pic, meInitateActivity.getImage_url(), mContext);
        ViewUtil.setText(mContext, initate_name, meInitateActivity.getFrom_gname());
        ViewUtil.setText(mContext, initate_time, meInitateActivity.getAdd_time());
        if ("1".equals(meInitateActivity.getStatus())) {
            initate_status.setSelected(false);//true是灰色
            initate_status.setText("确认置换");
            initate_status.setEnabled(true);
            initate_unchange.setVisibility(View.VISIBLE);
        } else {
            initate_status.setSelected(true);//true是灰色
            initate_status.setText("置换完成");
            initate_status.setEnabled(false);
            initate_unchange.setVisibility(View.GONE);
        }
        //确认置换
        initate_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(meInitateActivity.getStatus())) {
                    confirmChange(meInitateActivity);
                }
            }
        });
        //取消置换
        initate_unchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(initate_unchange.getVisibility()==View.VISIBLE){
                    cancelChange(meInitateActivity, position);
                }
            }
        });
        my_change_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meInitateActivity.setNum("0");
                notifyDataSetChanged();
                Intent intent = new Intent(mContext, ChangeInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("exchange_id", meInitateActivity.getExchange_id());
                bundle.putString("changepic", meInitateActivity.getMember_avar());
                bundle.putString("changeName", meInitateActivity.getMember_name());
                bundle.putString("changAddress", meInitateActivity.getHome());
                intent.putExtra("model", bundle);
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

    //确认置换
    private void confirmChange(final MeStarChange.Datas meInitateActivity) {
        DialogUtil.startDialogLoading(mContext);
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        params.put("to_exchange_id", meInitateActivity.getLink_exchange_id());
        params.put("me_exchange_id", meInitateActivity.getExchange_id());
        BasicRequest.getInstance().requestPost(new VolleyListerner() {
                                                   @Override
                                                   public void onResponse(String str, int taskid, Object obj) {
                                                       DialogUtil.stopDialogLoading(mContext);
                                                       switch (taskid) {
                                                           case TaskID.FINISH_EXCHANGE:
                                                               meInitateActivity.setStatus("2");
                                                               notifyDataSetChanged();
                                                               break;
                                                       }
                                                   }

                                                   @Override
                                                   public void onResponseError(String error, int taskid) {
                                                       DialogUtil.stopDialogLoading(mContext);
                                                       switch (taskid) {
                                                           case TaskID.FINISH_EXCHANGE:
                                                               ToastUtil.showShort(mContext, "确认失败");
                                                               break;
                                                       }
                                                   }
                                               },
                TaskID.FINISH_EXCHANGE, params, AppConfig.FINISH_EXCHANGE, Result.class);
    }

    //取消置换
    private void cancelChange(final MeStarChange.Datas meInitateActivity, final int postion) {
        DialogUtil.startDialogLoading(mContext);
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        params.put("to_exchange_id", meInitateActivity.getLink_exchange_id());
        params.put("me_exchange_id", meInitateActivity.getExchange_id());
        BasicRequest.getInstance().requestPost(new VolleyListerner() {
                                                   @Override
                                                   public void onResponse(String str, int taskid, Object obj) {
                                                       DialogUtil.stopDialogLoading(mContext);
                                                       switch (taskid) {
                                                           case TaskID.CANCEL_EXCHANGE:
                                                               mList.remove(postion);
                                                               notifyDataSetChanged();
                                                               break;
                                                       }
                                                   }

                                                   @Override
                                                   public void onResponseError(String error, int taskid) {
                                                       DialogUtil.stopDialogLoading(mContext);
                                                       switch (taskid) {
                                                           case TaskID.CANCEL_EXCHANGE:
                                                               ToastUtil.showShort(mContext, "取消置换失败");
                                                               break;
                                                       }
                                                   }
                                               },
                TaskID.CANCEL_EXCHANGE, params, AppConfig.CANCEL_EXCHANGE, Result.class);
    }
}
