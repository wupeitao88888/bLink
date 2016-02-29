package com.shiliuke.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shiliuke.BabyLink.ImagePagerActivity;
import com.shiliuke.BabyLink.R;
import com.shiliuke.BabyLink.information.InformationRequestListener;
import com.shiliuke.bean.Comment;
import com.shiliuke.bean.Praise;
import com.shiliuke.bean.Topic;
import com.shiliuke.bean.TopicInfo;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.model.OnSendMsg;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.utils.FaceConversionUtil;
import com.shiliuke.utils.L;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;
import com.shiliuke.view.FaceRelativeLayout;
import com.shiliuke.view.NoScrollGridView;
import com.shiliuke.view.NoScrollListView;

/****
 * 话题界面
 */
public class TopicAdapter extends BaseAdapter {

    private List<TopicInfo> mList;
    private Context mContext;
    private PopupWindow popWindow;
    private TopicCommentListAdapter topicCommentListAdapter;

    public TopicAdapter(final Context _context) {

        this.mContext = _context;

    }

    public void setData(List<TopicInfo> _list) {
        this.mList = _list;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final TopicInfo mUserInfo = getItem(position);
//        Map<String, String> params = new HashMap<>();
//        params.put("member_id", AppConfig.loginInfo.getMember_id());
//        params.put("type", "0");
//        params.put("item_id", mUserInfo.getTalk_id());
//        BasicRequest.getInstance().requestPost(new InformationRequestListener(), 0, params, AppConfig.UPDATE_INFORMATION, null);

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.friends_circle_item, parent, false);
        }


        ImageView hand_pic = com.shiliuke.utils.ViewHolder.get(convertView, R.id.hand_pic);//头像
        NoScrollGridView gridView = com.shiliuke.utils.ViewHolder.get(convertView, R.id.gridView);//图片
        final NoScrollListView noscrolllistview = com.shiliuke.utils.ViewHolder.get(convertView, R.id.noscrolllistview);//评论
        ImageView comment_image = com.shiliuke.utils.ViewHolder.get(convertView, R.id.comment_image);//评论
        ImageView price_image = com.shiliuke.utils.ViewHolder.get(convertView, R.id.price_image);//点赞
        TextView tv_share_names = com.shiliuke.utils.ViewHolder.get(convertView, R.id.tv_share_names);//点赞姓名
        TextView time = com.shiliuke.utils.ViewHolder.get(convertView, R.id.time);
        TextView author = com.shiliuke.utils.ViewHolder.get(convertView, R.id.author);
        TextView content = com.shiliuke.utils.ViewHolder.get(convertView, R.id.content);
        ViewUtil.setText(mContext, time, mUserInfo.getAdd_time());
        ViewUtil.setText(mContext, author, mUserInfo.getMember_name());
        ViewUtil.setText(mContext, content, mUserInfo.getInfo());


        setImage(hand_pic, mUserInfo.getMember_avar(), mContext);
        StringBuilder sb = new StringBuilder();
        if (mUserInfo.getZan_list() != null) {
            for (int i = 0; i < mUserInfo.getZan_list().size(); i++) {
                sb.append(mUserInfo.getZan_list().get(i).getMember_name() + ",");
            }
        }
        if (!TextUtils.isEmpty(sb)) {
            String substring = sb.substring(0, sb.length() - 1);
            tv_share_names.setText(substring);
        } else {
            tv_share_names.setText("");
        }
        if (mList != null && mList.size() > 0) {
            gridView.setVisibility(View.VISIBLE);
            gridView.setAdapter(new TopicPicGridAdapter(mUserInfo.getImages_url(),
                    mContext));

        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                Intent intent = new Intent(mContext, ImagePagerActivity.class);
                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
                ArrayList<String> news_img = (ArrayList<String>) mUserInfo.getImages_url();
                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, news_img);
                mContext.startActivity(intent);
            }
        });
        if (mUserInfo.getCommend_list() != null) {
            if (mUserInfo.getCommend_list().size() > 0) {
                noscrolllistview.setVisibility(View.VISIBLE);
                topicCommentListAdapter = new TopicCommentListAdapter(mUserInfo.getCommend_list(),
                        mContext, mUserInfo.getMember_id());
                noscrolllistview.setAdapter(topicCommentListAdapter);
//                final String commentName = mUserInfo.getCommend_list().get(position).getMember_name();
                noscrolllistview.setDivider(null);
                noscrolllistview
                        .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent,
                                                    View view, int position, long id) {

                                showPopup(view, mUserInfo, mUserInfo.getCommend_list().get(position).getFrom_id(), mUserInfo.getCommend_list().get(position).getFrom_name(), noscrolllistview, false);
                            }
                        });
            }
        }

        comment_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view, mUserInfo, "", mUserInfo.getMember_name(), noscrolllistview, true);
            }
        });
        price_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDate(mUserInfo.getTalk_id(), mUserInfo);
            }
        });

        return convertView;
    }

    //点赞
    private void initDate(final String talk_id, final TopicInfo topic) {
        DialogUtil.startDialogLoading(mContext);
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        params.put("talk_id", talk_id);
        BasicRequest.getInstance().requestPost(new VolleyListerner() {
            @Override
            public void onResponse(String str, int taskid, Object obj) {
                switch (taskid) {
                    case TaskID.ACTION_TALK_ZAN:
                        DialogUtil.stopDialogLoading(mContext);
                        Praise praise = new Praise(null, talk_id + "", AppConfig.loginInfo.getMember_id(), AppConfig.loginInfo.getMember_id(), "" + System.currentTimeMillis());
                        topic.getZan_list().add(praise);
                        notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onResponseError(String error, int taskid) {
                switch (taskid) {
                    case TaskID.ACTION_TALK_ZAN:
                        ToastUtil.showShort(mContext, error);
                        DialogUtil.stopDialogLoading(mContext);
                        break;
                }
            }
        }, TaskID.ACTION_TALK_ZAN, params, AppConfig.TALK_ZAN);
    }

    /*时间戳转换成字符窜*/
    public String getDateToString(long mill) {
        Date date = new Date(mill);
        String strs = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            strs = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    private void showPopup(View parent, final TopicInfo topicInfo, final String to_id, final String to_name, final NoScrollListView noscrolllistview, boolean istrue) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.comment_popu, null);
        popWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        final FaceRelativeLayout faceRelativeLayout = (FaceRelativeLayout) view.findViewById(R.id.FaceRelativeLayout);
        if (!istrue)
            faceRelativeLayout.setTextHinit("回复：" + to_name);
        else
            faceRelativeLayout.setTextHinit("请输入评价！");
        faceRelativeLayout.setOnSendMsgListener(new OnSendMsg() {
            @Override
            public void OnSccessCallBack(final String info) {
//                if (AppConfig.loginInfo.getMember_id().equals(to_id)) {
//                    ToastUtil.showShort(mContext, "不能给自己评论！");
//                    return;
//                }
                if (popWindow.isShowing()) {
                    popWindow.dismiss();
                }
                DialogUtil.startDialogLoading(mContext);
                Map<String, String> params = new HashMap<>();
                params.put("member_id", AppConfig.loginInfo.getMember_id());
                params.put("talk_id", topicInfo.getTalk_id());
                params.put("info", info);
                params.put("from_id", AppConfig.loginInfo.getMember_id());
                params.put("from_name", AppConfig.loginInfo.getMember_name());
                params.put("to_id", to_id);
                params.put("to_name", to_name);
                faceRelativeLayout.clear();
                BasicRequest.getInstance().requestPost(new VolleyListerner() {
                    @Override
                    public void onResponse(String str, int taskid, Object obj) {
                        switch (taskid) {
                            case TaskID.ACTION_TALK_COMMENT:
                                Comment comment = new Comment(null, null, AppConfig.loginInfo.getMember_id(), System.currentTimeMillis() + "", null, null, info, topicInfo.getTalk_id(), AppConfig.loginInfo.getMember_id(), AppConfig.loginInfo.getMember_name(), to_id, to_name);
                                topicInfo.getCommend_list().add(comment);
                                if (topicCommentListAdapter == null) {
                                    noscrolllistview.setVisibility(View.VISIBLE);
                                    topicCommentListAdapter = new TopicCommentListAdapter(topicInfo.getCommend_list(),
                                            mContext, topicInfo.getMember_id());
                                    noscrolllistview.setAdapter(topicCommentListAdapter);
                                }
                                notifyDataSetChanged();
                                DialogUtil.stopDialogLoading(mContext);
                                break;
                        }
                    }

                    @Override
                    public void onResponseError(String error, int taskid) {
                        switch (taskid) {
                            case TaskID.ACTION_TALK_COMMENT:
                                ToastUtil.showShort(mContext, error);
                                DialogUtil.stopDialogLoading(mContext);

                                break;
                        }
                    }
                }, TaskID.ACTION_TALK_COMMENT, params, AppConfig.TALK_COMMEND);
            }
        });
        //popupwindow弹出时的动画		popWindow.setAnimationStyle(R.style.popupWindowAnimation);
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        popWindow.setFocusable(true);
        // 设置允许在外点击消失
        popWindow.setOutsideTouchable(false);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popWindow.setBackgroundDrawable(new LevelListDrawable());

        //软键盘不会挡着popupwindow
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置菜单显示的位置
        popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);

        //监听菜单的关闭事件
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        //监听触屏事件
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {

                faceRelativeLayout.closeInput();
                return false;
            }
        });


    }


    private void setImage(ImageView iview, String url, Context context) {
        Glide.with(context)
                .load(url)
                .override(35, 35)
                .placeholder(R.drawable.gray)
                .error(R.mipmap.morentoux)
                .crossFade()
                .into(iview);

    }


}
