package com.shiliuke.adapter;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.shiliuke.BabyLink.InvitefriendsActivity;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.InViteFriendsModel;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.utils.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhi on 15/11/11.
 */
public class InvitefriendsAdapter extends BaseAdapter {
    private ArrayList<InViteFriendsModel.Result> infoList;
    private InvitefriendsActivity context;
    private String goods_id;

    public InvitefriendsAdapter(ArrayList<InViteFriendsModel.Result> infoList, InvitefriendsActivity context, String goods_id) {
        this.infoList = infoList;
        this.context = context;
        this.goods_id = goods_id;
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_inviterfriends, null);
        }
        final InViteFriendsModel.Result info = infoList.get(position);
        ImageView image_item_inviterfriends_head = ViewHolder.get(convertView, R.id.image_item_inviterfriends_head);
        TextView tv_item_inviterfriends_name = ViewHolder.get(convertView, R.id.tv_item_inviterfriends_name);
        TextView tv_item_inviterfriends_source = ViewHolder.get(convertView, R.id.tv_item_inviterfriends_source);
        final Button btn_item_inviterfiends_inviter = ViewHolder.get(convertView, R.id.btn_item_inviterfiends_inviter);
        ///////////////
        tv_item_inviterfriends_source.setText(info.getText());
        Glide.with(context).load(info.getFriend_avar()).into(image_item_inviterfriends_head);
        tv_item_inviterfriends_name.setText(info.getFriend_name());
        if ("2".equalsIgnoreCase(info.getStatus())) {
            btn_item_inviterfiends_inviter.setBackgroundResource(R.drawable.button_gray_normal);
            btn_item_inviterfiends_inviter.setText("已邀请");
        }else {
            btn_item_inviterfiends_inviter.setBackgroundResource(R.drawable.selector_btn_inviterfriends);
            btn_item_inviterfiends_inviter.setText("");
        }

        btn_item_inviterfiends_inviter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("2".equalsIgnoreCase(info.getStatus())) {
                    context.showToast("已经邀请过了");
                    return;
                }
                DialogUtil.startDialogLoading(context, false);
                Map<String, String> params = new HashMap<>();
                params.put("goods_id", goods_id);
                params.put("member_id", AppConfig.loginInfo.getMember_id());
                params.put("friend_id", info.getFriend_id());
                BasicRequest.getInstance().requestPost(new VolleyListerner() {
                    @Override
                    public void onResponse(String str, int taskid, Object obj) {
                        DialogUtil.stopDialogLoading(context);
                        context.showToast("邀请成功");
                        infoList.get(position).setStatus("2");
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onResponseError(String error, int taskid) {
                        DialogUtil.stopDialogLoading(context);
                        context.showToast(error);
                    }
                }, 0, params, AppConfig.FIND_INVITE_FRIENDS);
            }
        });
        return convertView;
    }

}
