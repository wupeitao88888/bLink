package com.shiliuke.BabyLink;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.adapter.TopicCommentListAdapter;
import com.shiliuke.adapter.TopicPicGridAdapter;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.Comment;
import com.shiliuke.bean.MeStarChange;
import com.shiliuke.bean.Topic;
import com.shiliuke.bean.TopicInfo;
import com.shiliuke.bean.UserImgs;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.model.OnSendMsg;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.utils.L;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.utils.ViewUtil;
import com.shiliuke.view.FaceRelativeLayout;
import com.shiliuke.view.NoScrollGridView;
import com.shiliuke.view.NoScrollListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的话题详情
 * Created by wupeitao on 15/11/5.
 */
public class MeTopicInfoActivity extends ActivitySupport implements View.OnClickListener, VolleyListerner {

    private ImageView hand_pic;//用户头像

    private TextView author,//用户姓名
            content,//话题内容
            time,//发表时间
            tv_share_names//点赞者
                    ;//编辑
    private NoScrollListView noscrolllistview;
    private NoScrollGridView gridView;

    private ImageView comment_image;//评论
    private ImageView price_image;//点赞
    private TopicCommentListAdapter topicCommentListAdapter;

    private PopupWindow popWindow;
    private TextView delect;
    private TopicInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_me_topic_info);
        initView();

    }


    private void initView() {
        setCtenterTitle("我的话题");
        hand_pic = (ImageView) findViewById(R.id.hand_pic);
        author = (TextView) findViewById(R.id.author);
        content = (TextView) findViewById(R.id.content);
        time = (TextView) findViewById(R.id.time);
        tv_share_names = (TextView) findViewById(R.id.tv_share_names);
        delect = (TextView) findViewById(R.id.delect);
        noscrolllistview = (NoScrollListView) findViewById(R.id.noscrolllistview);
        gridView = (NoScrollGridView) findViewById(R.id.gridView);
        comment_image = (ImageView) findViewById(R.id.comment_image);
        price_image = (ImageView) findViewById(R.id.comment_image);


        Intent intent = getIntent();
        mUserInfo = (TopicInfo) intent.getSerializableExtra("Topic");


        ViewUtil.setText(context, time, mUserInfo.getAdd_time());
        ViewUtil.setText(context, author, mUserInfo.getMember_name());
        ViewUtil.setText(context, content, mUserInfo.getInfo());


        setImage(hand_pic, mUserInfo.getMember_avar(), context);
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
        if (mUserInfo.getImages_url() != null && mUserInfo.getImages_url().size() > 0) {
            gridView.setVisibility(View.VISIBLE);
            gridView.setAdapter(new TopicPicGridAdapter(mUserInfo.getImages_url(),
                    context));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent,
                                        View view, int position, long id) {
                }
            });
        } else {
            gridView.setVisibility(View.GONE);
        }
        if (mUserInfo.getCommend_list() != null) {
            if (mUserInfo.getCommend_list().size() > 0) {
                noscrolllistview.setVisibility(View.VISIBLE);
                topicCommentListAdapter = new TopicCommentListAdapter(mUserInfo.getCommend_list(),
                        context, mUserInfo.getMember_id());
                noscrolllistview.setAdapter(topicCommentListAdapter);
//                final String commentName = mUserInfo.getCommend_list().get(position).getMember_name();
                noscrolllistview.setDivider(null);
            }
        }
        noscrolllistview
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        showPopup(view, mUserInfo, mUserInfo.getCommend_list().get(position).getFrom_id(), mUserInfo.getCommend_list().get(position).getFrom_name(), noscrolllistview);
                    }
                });
        comment_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("不能给自己评论");
//                showPopup(view, mUserInfo, mUserInfo.getMember_id(), mUserInfo.getMember_name(), noscrolllistview);
            }
        });
        price_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("不能给自己点赞");
//                initDate(mUserInfo.getTalk_id(), mUserInfo);
            }
        });


        delect.setOnClickListener(this);
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

    private void showPopup(View parent, final TopicInfo topicInfo, final String to_id, final String to_name, final NoScrollListView noscrolllistview) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.comment_popu, null);
        popWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        final FaceRelativeLayout faceRelativeLayout = (FaceRelativeLayout) view.findViewById(R.id.FaceRelativeLayout);
        faceRelativeLayout.setTextHinit("回复：" + to_name);

        faceRelativeLayout.setOnSendMsgListener(new OnSendMsg() {
            @Override
            public void OnSccessCallBack(final String info) {
                if (popWindow.isShowing())
                    popWindow.dismiss();
                if (AppConfig.loginInfo.getMember_id().equals(to_id)) {
                    ToastUtil.showShort(context, "不能给自己评论！");
                    return;
                }
                DialogUtil.startDialogLoading(context);
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
                                            context, topicInfo.getMember_id());
                                    noscrolllistview.setAdapter(topicCommentListAdapter);
                                }
                                topicCommentListAdapter.notifyDataSetChanged();
                                DialogUtil.stopDialogLoading(context);
                                break;
                        }
                    }

                    @Override
                    public void onResponseError(String error, int taskid) {
                        switch (taskid) {
                            case TaskID.ACTION_TALK_COMMENT:
                                ToastUtil.showShort(context, error);
                                DialogUtil.stopDialogLoading(context);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delect:
                setDelect();
                break;
        }
    }

    public void setDelect() {
        DialogUtil.startDialogLoading(context);
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        params.put("talk_id", mUserInfo.getTalk_id());
        BasicRequest.getInstance().requestPost(MeTopicInfoActivity.this, TaskID.DELECT_TOPIC, params, AppConfig.DELECT_TOPIC);
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        switch (taskid) {
            case TaskID.DELECT_TOPIC:
//                showToast(str);
                finish();
                break;
        }

    }

    @Override
    public void onResponseError(String error, int taskid) {
        switch (taskid) {
            case TaskID.DELECT_TOPIC:
                ToastUtil.showShort(context,error);
                break;
        }
    }
}
