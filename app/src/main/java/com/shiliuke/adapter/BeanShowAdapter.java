package com.shiliuke.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.MainTab;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.BeanShowModelResult;
import com.shiliuke.fragment.FragmentBeanShow;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.ShareUtils;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.view.PopWnd;
import com.shiliuke.view.stickerview.StickerImageContans;
import com.shiliuke.view.stickerview.StickerImageModel;
import com.shiliuke.view.stickerview.StickerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhi on 15/10/30.
 * 秀逗页面List Adapter
 */
public class BeanShowAdapter extends BaseAdapter {
    private View footView;
    private ArrayList<BeanShowModelResult> data;
    private FragmentBeanShow context;
    private Boolean isLink = true;
    private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);

    public BeanShowAdapter(View footView, FragmentBeanShow context, ArrayList<BeanShowModelResult> data, Boolean isLink) {
        this.footView = footView;
        this.context = context;
        this.data = data;
        this.isLink = isLink;
    }

    public void setIsLink(Boolean isLink) {
        this.isLink = isLink;
    }

    public void updateDate(boolean isLink) {
        this.isLink = isLink;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context.getActivity()).inflate(R.layout.item_beanshow, null);
        }
        final BeanShowModelResult model = data.get(position);
        if (model.getInfo().equals("1233211234567,我们说你是傻逼")) {
            String a = null;
            a.toString();
        }
//        Map<String, String> params = new HashMap<>();
//        params.put("member_id", AppConfig.loginInfo.getMember_id());
//        params.put("type", "1");
//        params.put("item_id", model.getXiu_id());
//        BasicRequest.getInstance().requestPost(new InformationRequestListener(), 0, params, AppConfig.UPDATE_INFORMATION, null);

        ImageView image_beanshow_item_head = ViewHolder.get(convertView, R.id.image_beanshow_item_head);
        TextView tv_beanshow_item_name = ViewHolder.get(convertView, R.id.tv_beanshow_item_name);
        TextView tv_beanshow_item_time = ViewHolder.get(convertView, R.id.tv_beanshow_item_time);
        TextView tv_beanshow_item_dou = ViewHolder.get(convertView, R.id.tv_beanshow_item_dou);
        TextView tv_beanshow_item_msg = ViewHolder.get(convertView, R.id.tv_beanshow_item_msg);
        final LinearLayout layout_beanshow_item_zan = ViewHolder.get(convertView, R.id.layout_beanshow_item_zan);
        ImageView sticker_image_beanshow_item = ViewHolder.get(convertView, R.id.sticker_image_beanshow_item);
        final StickerView sticker_beanshow_item = ViewHolder.get(convertView, R.id.sticker_beanshow_item);
        sticker_beanshow_item.setTag(position);
        Glide.with(context).load(model.getMember_avar()).override(35, 35).into(image_beanshow_item_head);
        Glide.with(context).load(model.getImage_url()).override(700, 700).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                sticker_beanshow_item.setModel(model);
//                if (position != 0) {（弃用）
//                    model.revertAlpha();
//                }
//                sticker_beanshow_item.startAnim();
                return false;
            }
        }).into(sticker_image_beanshow_item);
        tv_beanshow_item_name.setText(model.getMember_name());
        tv_beanshow_item_time.setText(model.getTime());
        tv_beanshow_item_dou.setText(model.getCommend_list().size() + "");
        tv_beanshow_item_msg.setText(model.getInfo());
        initZanList(model.getZan_list(), layout_beanshow_item_zan);
        ////////////
        Button btn_beanshow_item_dou = ViewHolder.get(convertView, R.id.btn_beanshow_item_dou);
        btn_beanshow_item_dou.setTag(position);
        btn_beanshow_item_dou.setOnClickListener(douClick);
        Button btn_beanshow_item_addfocus = ViewHolder.get(convertView, R.id.btn_beanshow_item_addfocus);
        btn_beanshow_item_addfocus.setTag(model.getMember_id());
        btn_beanshow_item_addfocus.setOnClickListener(addFocusClick);
        ImageButton btn_beanshow_item_share = ViewHolder.get(convertView, R.id.btn_beanshow_item_share);
//        btn_beanshow_item_share.setTag(model.getFenxiang_url()+"&&"+model.getImage_url());
        btn_beanshow_item_share.setOnClickListener(v -> {
            msg = model;
            shareClick.onClick(v);
        });
        final Button btn_beanshow_item_zan = ViewHolder.get(convertView, R.id.btn_beanshow_item_zan);
        if (model.isZan()) {
            btn_beanshow_item_zan.setBackgroundResource(R.mipmap.icon_xd_zan_yes);
        } else {
            btn_beanshow_item_zan.setBackgroundResource(R.mipmap.icon_xd_zan_no);
        }
        btn_beanshow_item_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.isZan()) {
                    ((MainTab) context.getActivity()).showToast("已经赞过了");
                } else {
                    Map<String, String> params = new HashMap<>();
                    params.put("member_id", AppConfig.loginInfo.getMember_id());
                    params.put("xiu_id", model.getXiu_id());
                    BasicRequest.getInstance().requestPost(new VolleyListerner() {
                        @Override
                        public void onResponse(String str, int taskid, Object obj) {
                            StickerImageModel zan = new StickerImageModel("");
                            zan.setMember_avar(AppConfig.loginInfo.getMember_avar());
                            model.setIsZan(true);
                            model.getZan_list().add(zan);
                            btn_beanshow_item_zan.setBackgroundResource(R.mipmap.icon_xd_zan_yes);
                            ToastUtil.show(context.getContext(), "赞成功", 0);
                            ImageView view = new ImageView(context.getActivity());
                            BeanShowAdapter.this.params.setMargins(10, 0, 0, 0);
                            view.setLayoutParams(BeanShowAdapter.this.params);
                            Glide.with(context).load(AppConfig.loginInfo.getMember_avar()).into(view);
                            layout_beanshow_item_zan.addView(view);
                        }

                        @Override
                        public void onResponseError(String error, int taskid) {
                            ToastUtil.show(context.getContext(), error, 0);
                        }
                    }, TaskID.ACTION_XIUDOU_ZAN, params, AppConfig.XIUDOU_ZAN);
                }
            }
        });
        if (isLink) {
            btn_beanshow_item_addfocus.setVisibility(View.GONE);
        } else {
            btn_beanshow_item_addfocus.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    /**
     * 关闭在执行的“动画”
     */
    public void stopAnim() {
        for (int i = 0; i < data.size(); i++) {
//            data.get(i).setCanAnim(false);
//            data.get(i).setIsAniming(false);
//            data.get(i).revertAlpha();
        }
    }

    /**
     * 更新执行动画的View
     *
     * @param firstVisibleItem
     * @param visibleItemCount
     */
    public void updateAnimItem(int firstVisibleItem, int visibleItemCount) {
        for (; visibleItemCount > 0; visibleItemCount--, firstVisibleItem++) {
            ((StickerView) footView.findViewWithTag(firstVisibleItem)).startAnim();
        }
    }

    /**
     * button“逗一逗”
     */
    private View.OnClickListener douClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction("com.shiliuke.StickerModifyActivity");
            intent.putExtra("model", data.get((Integer) v.getTag()));
            intent.putExtra("position", v.getTag().toString());
            context.startActivityForResult(intent, StickerImageContans.REQUESTADDMODEL);

        }
    };
    /**
     * button“加关注”
     */
    private View.OnClickListener addFocusClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Map<String, String> params = new HashMap<>();
            params.put("member_id", AppConfig.loginInfo.getMember_id());
            params.put("friend_id", v.getTag().toString());
            BasicRequest.getInstance().requestPost(addFocusListener, TaskID.ACTION_XIUDOU_ADD_FOCUS, params, AppConfig.XIUDOU_ADDFRIEND);
        }
    };
    /**
     * button“分享”
     */
    private BeanShowModelResult msg;
    private View.OnClickListener shareClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_beanshow_item_share:
//                    msg = v.getTag().toString().split("&&");
                    int[] ids = {R.id.tv_share_wxpyq, R.id.tv_share_wx, R.id.tv_share_xlwb, R.id.tv_share_qq, R.id.tv_share_dx, R.id.tv_share_yj};
                    PopWnd popWnd = new PopWnd(context.getContext(), R.layout.layout_share_pop, ids, shareClick, R.id.pop_bg
                            , false);
                    popWnd.showPopDown(v);
                    break;
                case R.id.tv_share_wx:
                    ShareUtils.getInstance(context.getActivity()).shareWx("秀逗分享", msg.getInfo(), msg.getImage_url(), msg.getFenxiang_url());
                    break;
                case R.id.tv_share_wxpyq:
                    ShareUtils.getInstance(context.getActivity()).shareWxPyq("秀逗分享：" + msg.getInfo(), msg.getImage_url(), msg.getFenxiang_url());
                    break;
                case R.id.tv_share_qq:
                    ShareUtils.getInstance(context.getActivity()).shareQq("秀逗分享", msg.getInfo(), msg.getImage_url(), msg.getFenxiang_url());
                    break;
                case R.id.tv_share_xlwb:
                    ShareUtils.getInstance(context.getActivity()).shareXlwb("秀逗分享：" + msg.getInfo(), msg.getImage_url(), msg.getFenxiang_url());
                    break;
                default:
                    break;
            }
        }
    };

    private void initZanList(ArrayList<StickerImageModel> zanList, LinearLayout rootView) {
        rootView.removeAllViews();
        params.setMargins(10, 0, 0, 0);
        for (int i = 0; i < zanList.size(); i++) {
            ImageView view = new ImageView(context.getActivity());
            view.setLayoutParams(params);
            Glide.with(context).load(zanList.get(i).getMember_avar()).into(view);
            rootView.addView(view);
        }
    }

    VolleyListerner addFocusListener = new VolleyListerner() {
        @Override
        public void onResponse(String str, int taskid, Object obj) {
            context.requestData(true);
            ToastUtil.show(context.getContext(), "关注成功", 0);
        }

        @Override
        public void onResponseError(String error, int taskid) {
            ToastUtil.show(context.getContext(), error, 0);
        }
    };

}
