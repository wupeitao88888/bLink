package com.shiliuke.BabyLink;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.LevelListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.*;
import android.widget.*;

import com.shiliuke.BabyLink.information.InformationUtils;
import com.shiliuke.adapter.CommentAdapter;
import com.shiliuke.adapter.JionActivityAdapter;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.ActivityComment;
import com.shiliuke.bean.ActivityInfo;
import com.shiliuke.bean.Result;
import com.shiliuke.bean.UserInfo;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.model.OnSendMsg;
import com.shiliuke.utils.*;
import com.shiliuke.view.FaceRelativeLayout;
import com.shiliuke.view.NoScrollGridView;
import com.shiliuke.view.NoScrollListView;

import de.greenrobot.event.EventBus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动详情
 * Created by wpt on 2015/10/28.
 */
public class ExerciseActivity extends ActivitySupport implements View.OnClickListener, VolleyListerner {
    private Button exercise_apply_bt,//报名
            comment_on;//评论

    private ImageView exercise_pic,//活动图片
            exercise_authorPic;//发起人头像


    private TextView exercise_title,//活动标题
            activity_address,//活动地址
            begin_time,//begin_time
            max_man,//上限人数
            count,//报名人数
            price,//报名费用
            pay_way,//支付方式
            jihe_address,//集合地点
            utils,//交通工具
            link_name,//联系人
            link_mobile,//联系电话
            exercise_authorName,//发起人
            exercise_content,//活动介绍
            exercise_offer_content,//BabyLink以提供支持
            exercise_comment,//评价个数
            exercise_offer;//人数报名  报名人数（10人）
    //share
    private RelativeLayout weChat_friend;
    private RelativeLayout weChat;
    private RelativeLayout sina;
    private RelativeLayout qq;

    private NoScrollGridView exercise_gridView;
    private NoScrollListView exercise_noscrolllistview;
    private PopupWindow popWindow;
    private JionActivityAdapter jionActivityAdapter;
    private CommentAdapter commentAdapter;
    private List<ActivityComment> commend_list;
    public String infoContent;
    private String activity_id;
    private ScrollView sView;
    private ActivityInfo.Dates datesActi;
    //    private boolean isSign;
    private ActivityInfo.Dates activityInfo;
    private TextView comment_more;/*加载更多*/
    private ActivityComment activityComment;
    private TextView layout_exercise_offer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_exercise);

        initView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        setCtenterTitle("活动详情");
        Intent intent = getIntent();
        activity_id = intent.getStringExtra("activity_id");
//        isSign = intent.getBooleanExtra("isSign", false);
        sView = (ScrollView) findViewById(R.id.sView);
        weChat_friend = (RelativeLayout) findViewById(R.id.weChat_friend);
        weChat = (RelativeLayout) findViewById(R.id.weChat);
        sina = (RelativeLayout) findViewById(R.id.sina);
        qq = (RelativeLayout) findViewById(R.id.qq);

        exercise_apply_bt = (Button) findViewById(R.id.exercise_apply_bt);
        comment_on = (Button) findViewById(R.id.comment_on);

        exercise_pic = (ImageView) findViewById(R.id.exercise_pic);
        exercise_authorPic = (ImageView) findViewById(R.id.exercise_authorPic);

        comment_more = (TextView) findViewById(R.id.comment_more);
        exercise_title = (TextView) findViewById(R.id.exercise_title);
        activity_address = (TextView) findViewById(R.id.activity_address);
        begin_time = (TextView) findViewById(R.id.begin_time);
        max_man = (TextView) findViewById(R.id.max_man);
        count = (TextView) findViewById(R.id.count);
        price = (TextView) findViewById(R.id.price);
        pay_way = (TextView) findViewById(R.id.pay_way);
        jihe_address = (TextView) findViewById(R.id.jihe_address);
        utils = (TextView) findViewById(R.id.utils);
        link_name = (TextView) findViewById(R.id.link_name);
        link_mobile = (TextView) findViewById(R.id.link_mobile);
        exercise_authorName = (TextView) findViewById(R.id.exercise_authorName);
        exercise_content = (TextView) findViewById(R.id.exercise_content);
        exercise_offer_content = (TextView) findViewById(R.id.exercise_offer_content);
        exercise_offer = (TextView) findViewById(R.id.exercise_offer);
        exercise_comment = (TextView) findViewById(R.id.exercise_comment);

        exercise_gridView = (NoScrollGridView) findViewById(R.id.exercise_gridView);
        exercise_noscrolllistview = (NoScrollListView) findViewById(R.id.exercise_noscrolllistview);

        layout_exercise_offer=(TextView)findViewById(R.id.info_apply);
        exercise_apply_bt.setOnClickListener(this);
        comment_on.setOnClickListener(this);
        weChat_friend.setOnClickListener(this);
        weChat.setOnClickListener(this);
        sina.setOnClickListener(this);
        qq.setOnClickListener(this);
        comment_more.setOnClickListener(this);
        initNet();
    }

    private void initNet() {
        DialogUtil.startDialogLoading(context);

        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        params.put("activity_id", activity_id);
        BasicRequest.getInstance().requestPOST(ExerciseActivity.this, TaskID.ACTION_ACTIVITY_INFO, params, AppConfig.ACTIVITY_INFO, ActivityInfo.class);
    }

    private void cancelIssue() {
        DialogUtil.startDialogLoading(context);

        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        params.put("activity_id", activity_id);
        BasicRequest.getInstance().requestPOST(ExerciseActivity.this, TaskID.CANCEL_ISSUE, params, AppConfig.CANCEL_ISSUE, Result.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exercise_apply_bt:
                if (activityInfo.getIsOut().equals("0")) {
                    if (activityInfo.isSign()) {
                        //取消报名
                        cancelIssue();
                    } else {

                        if (!activityInfo.getCount().equals(activityInfo.getMax_man())) {
                            Intent intent = new Intent(this, ApplyActivity.class);
                            intent.putExtra("activity_id", this.activity_id);
                            intent.putExtra("surplus", MInteger(activityInfo.getMax_man()) - MInteger(activityInfo.getCount()));
                            startActivityForResult(intent, 1000);
                        }
                    }
                }

                break;
            case R.id.comment_on:
                showPopup(view, "0", "请对活动输入评价！", activityInfo.getMember_name());
                break;
            case R.id.weChat_friend:
//                ToastUtil.showShort(context, "该功能暂缓开通！");
                if (activityInfo == null) {
                    ToastUtil.showShort(context, "数据未准备就绪，分享失败！");
                    return;
                }
                if (TextUtils.isEmpty(activityInfo.getInfo())) {
                    ToastUtil.showShort(context, "内容为空，分享失败！");
                    return;
                }
                ShareUtils.getInstance(context).shareWxPyq("活动分享：" + activityInfo.getTitle(), activityInfo.getImages().get(0), activityInfo.getFenxiang_url());
                break;
            case R.id.weChat:
//                ToastUtil.showShort(context, "该功能暂缓开通！");
                if (activityInfo == null) {
                    ToastUtil.showShort(context, "数据未准备就绪，分享失败！");
                    return;
                }
                if (TextUtils.isEmpty(activityInfo.getInfo())) {
                    ToastUtil.showShort(context, "内容为空，分享失败！");
                    return;
                }
                ShareUtils.getInstance(context).shareWx("活动分享", activityInfo.getTitle(), activityInfo.getImages().get(0), activityInfo.getFenxiang_url());
                break;
            case R.id.sina:
//                ToastUtil.showShort(context, "该功能暂缓开通！");
                if (activityInfo == null) {
                    ToastUtil.showShort(context, "数据未准备就绪，分享失败！");
                    return;
                }
                if (TextUtils.isEmpty(activityInfo.getInfo())) {
                    ToastUtil.showShort(context, "内容为空，分享失败！");
                    return;
                }
                ShareUtils.getInstance(context).shareXlwb("活动分享：" + activityInfo.getTitle(), activityInfo.getImages().get(0), activityInfo.getFenxiang_url());
                break;
            case R.id.qq:
//                ToastUtil.showShort(context, "该功能暂缓开通！");
                if (activityInfo == null) {
                    ToastUtil.showShort(context, "数据未准备就绪，分享失败！");
                    return;
                }
                if (TextUtils.isEmpty(activityInfo.getInfo())) {
                    ToastUtil.showShort(context, "内容为空，分享失败！");
                    return;
                }
                if (TextUtils.isEmpty(activityInfo.getTitle())) {
                    ToastUtil.showShort(context, "内容为空，分享失败！");
                    return;
                }
                ShareUtils.getInstance(context).shareQq("活动分享", activityInfo.getTitle(), activityInfo.getImages().get(0), activityInfo.getFenxiang_url());
                break;
            case R.id.comment_more:
                if (commend_list == null)
                    return;
                if (commend_list.size() <= 3)
                    return;
                commentAdapter = new CommentAdapter(context, commend_list, false);
                exercise_noscrolllistview.setAdapter(commentAdapter);
                exercise_noscrolllistview.setDivider(null);
                break;
        }
    }

    private int MInteger(String obj) {
        if (TextUtils.isEmpty(obj)) {
            return 0;
        }
        return Integer.parseInt(obj);
    }

    private void showPopup(View parent, final String to_userId, String hinit, String to_name) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.comment_popu, null);
        // 创建一个PopuWidow对象
        popWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        final FaceRelativeLayout faceRelativeLayout = (FaceRelativeLayout) view.findViewById(R.id.FaceRelativeLayout);
        faceRelativeLayout.setTextHinit(hinit);
        faceRelativeLayout.clear();
        faceRelativeLayout.setOnSendMsgListener(new OnSendMsg() {
            @Override
            public void OnSccessCallBack(String info) {
                faceRelativeLayout.closeInput();
                if (popWindow.isShowing()) {
                    popWindow.dismiss();
                }

                DialogUtil.startDialogLoading(context);
                infoContent = info;
                activityComment = new ActivityComment();
                activityComment.setActivity_id(activity_id);
                activityComment.setAdd_time(DateUtil.getDateToString(System.currentTimeMillis()));
                activityComment.setCommend_id("");
                activityComment.setFrom_userAvar(AppConfig.loginInfo.getMember_avar());
                activityComment.setFrom_userName(AppConfig.loginInfo.getMember_name());
                activityComment.setFrom_userId(AppConfig.loginInfo.getMember_id());
                activityComment.setInfo(infoContent);
                activityComment.setTo_userId(to_userId);
                activityComment.setTo_userName(to_name);

                Map<String, String> params = new HashMap<>();
                params.put("member_id", AppConfig.loginInfo.getMember_id());
                params.put("activity_id", activity_id);
                params.put("info", info);
                params.put("to_userId", to_userId);
                BasicRequest.getInstance().requestPost(ExerciseActivity.this, TaskID.ACTION_ACTIVITY_COMMENT, params, AppConfig.ACTIVITY_COMMEND);
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onResponse(String str, int taskid, Object obj) {
        switch (taskid) {
            case TaskID.ACTION_ACTIVITY_INFO:
                InformationUtils.requestAllNum();
                ActivityInfo activityInfoCode = (ActivityInfo) obj;
                if (!"0".equals(activityInfoCode.getCode())) {
                    ToastUtil.showShort(context, str);
                    return;
                }
                datesActi = activityInfoCode.getDates();
                List<String> images = activityInfoCode.getDates().getImages();
                activityInfo = activityInfoCode.getDates();
                List<UserInfo> log_list = activityInfo.getLog_list();
                commend_list = activityInfo.getCommend_list();
                exercise_comment.setText("");

                if (commend_list != null) {
                    String bCount = "评论：(" + commend_list.size() + ")";
                    SpannableStringBuilder builder = new SpannableStringBuilder(bCount);
                    ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.RED_TEXT));
                    builder.setSpan(redSpan, 4, bCount.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    exercise_comment.setText(builder);
                    commentAdapter = new CommentAdapter(context, commend_list, true);
                    exercise_noscrolllistview.setAdapter(commentAdapter);
                    exercise_noscrolllistview.setDivider(null);
                    exercise_noscrolllistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            showPopup(view, datesActi.getCommend_list().get(position).getFrom_userId(), "回复：" + datesActi.getCommend_list().get(position).getFrom_userName(), datesActi.getCommend_list().get(position).getFrom_userName());
                        }
                    });
                } else {
                    exercise_comment.setText("评论");
                }

                jionActivityAdapter = new JionActivityAdapter(context, log_list);
                exercise_gridView.setAdapter(jionActivityAdapter);
                if (images.size() > 0)
                    ViewUtil.setImage(exercise_pic, images.get(0), context);
                ViewUtil.setImage(exercise_authorPic, activityInfo.getMember_avar(), context);
                ViewUtil.setText(context, exercise_title, activityInfo.getTitle());
                if (!TextUtils.isEmpty(activityInfo.getActivity_address()))
                    ViewUtil.setText(context, activity_address, "活动地点：" + activityInfo.getActivity_address());
                else
                    activity_address.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(activityInfo.getBegin_time()))
                    try {
                        ViewUtil.setText(context, begin_time, "活动时间：" + activityInfo.getJihe_time());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        begin_time.setVisibility(View.GONE);
                    }
                else {
                    begin_time.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(activityInfo.getMax_man()))
                    ViewUtil.setText(context, max_man, "上限人数：" + activityInfo.getMax_man());
                else
                    max_man.setVisibility(View.GONE);

                if (!TextUtils.isEmpty(activityInfo.getCount())) {
                    String bRen = "报名人数：" + activityInfo.getCount();
                    SpannableStringBuilder builder = new SpannableStringBuilder(bRen);
                    ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.RED_TEXT));
                    builder.setSpan(redSpan, 4, bRen.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    count.setText(builder);
                }
                if (!TextUtils.isEmpty(activityInfo.getPrice())) {
                    String bPrice = "报名费用：￥" + activityInfo.getPrice();
                    SpannableStringBuilder builder = new SpannableStringBuilder(bPrice);
                    ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.RED_TEXT));
                    builder.setSpan(redSpan, 4, bPrice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    price.setText(builder);
                } else
                    price.setVisibility(View.GONE);

                if (!TextUtils.isEmpty(activityInfo.getPay_way()))
                    ViewUtil.setText(context, pay_way, "支付方式：" + activityInfo.getPay_way());
                else
                    pay_way.setVisibility(View.GONE);

                if (!TextUtils.isEmpty(activityInfo.getJihe_address()))
                    ViewUtil.setText(context, jihe_address, "集合地方：" + activityInfo.getJihe_address());
                else
                    jihe_address.setVisibility(View.GONE);

                if (!TextUtils.isEmpty(activityInfo.getUtils()))
                    ViewUtil.setText(context, utils, "交通工具：" + activityInfo.getUtils());
                else
                    utils.setVisibility(View.GONE);

                if (!TextUtils.isEmpty(activityInfo.getLink_name()))
                    ViewUtil.setText(context, link_name, "联\t\t系人：" + activityInfo.getLink_name());
                else
                    link_name.setVisibility(View.GONE);

                if (!TextUtils.isEmpty(activityInfo.getLink_mobile()))
                    ViewUtil.setText(context, link_mobile, "联系电话：" + activityInfo.getLink_mobile());
                else
                    link_mobile.setVisibility(View.GONE);
                ViewUtil.setText(context, exercise_authorName, activityInfo.getMember_name());
                ViewUtil.setText(context, exercise_content, activityInfo.getInfo());
                ViewUtil.setText(context, exercise_offer_content, activityInfo.getHelp());
                setCountP();
                buttonStatus();
                sView.smoothScrollTo(0, 20);
                DialogUtil.stopDialogLoading(context);
                break;

            case TaskID.ACTION_ACTIVITY_COMMENT:
                DialogUtil.stopDialogLoading(context);
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    int code = jsonObject.optInt("code", 1);
                    if (code == 0) {
//                        ActivityComment comment = new ActivityComment("", activity_id, AppConfig.loginInfo.getMember_id(), DateUtil.getDateToString(System.currentTimeMillis()), AppConfig.loginInfo.getMember_name(), AppConfig.loginInfo.getMember_avar(), infoContent);
                        commend_list.add(0, activityComment);
                        commentAdapter.notifyDataSetChanged();
                        ToastUtil.showShort(context, "提交成功");
                        String bCount = "评论：(" + commend_list.size() + ")";
                        SpannableStringBuilder builder = new SpannableStringBuilder(bCount);
                        ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.RED_TEXT));
                        builder.setSpan(redSpan, 4, bCount.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        exercise_comment.setText(builder);

                    } else {
                        JSONObject datas = jsonObject.getJSONObject("datas");
                        String error = datas.optString("error", "评论失败！");
                        ToastUtil.showShort(context, error);
                    }
                } catch (JSONException e) {
                    ToastUtil.showShort(context, "评论失败！");
                }

                break;
            case TaskID.CANCEL_ISSUE:
                DialogUtil.stopDialogLoading(context);
                Result result = (Result) obj;
                ToastUtil.showShort(context, "取消成功");
                if(ApplyActivity.applyCallBack!=null){
                    ApplyActivity.applyCallBack.del();
                }
                activityInfo.setIsSign(false);
                if (!TextUtils.isEmpty(activityInfo.getCount())) {
                    activityInfo.setCount((Integer.parseInt(activityInfo.getCount()) - 1) + "");
                } else {
                    activityInfo.setCount(0 + "");
                }
                buttonStatus();
                setCountP();
                break;
        }

    }


    public void setCountP() {
        if (!TextUtils.isEmpty(activityInfo.getCount())) {
            String bCount = "参与人数：(" + activityInfo.getCount() + "人)";
            SpannableStringBuilder builder = new SpannableStringBuilder(bCount);
            ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.RED_TEXT));
            builder.setSpan(redSpan, 6, bCount.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            exercise_offer.setText(builder);
        } else {
            exercise_offer.setText("参与人数");
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void buttonStatus() {
        if (datesActi.getMember_id().equals(AppConfig.loginInfo.getMember_id())) {
            exercise_apply_bt.setVisibility(View.GONE);
            layout_exercise_offer.setVisibility(View.VISIBLE);
        } else if (activityInfo.getIsOut().equals("1")) {
            exercise_apply_bt.setBackgroundResource(R.drawable.button_gray_normal);
            exercise_apply_bt.setText("活动已结束");
            exercise_apply_bt.setClickable(false);
        } else {
            if (activityInfo.isSign()) {
                exercise_apply_bt.setBackgroundResource(R.drawable.button_normal_long);
                exercise_apply_bt.setText("取消报名");
                exercise_apply_bt.setClickable(true);
            } else {
                //判断报名是否已满
                if (activityInfo.getCount().equals(activityInfo.getMax_man())) {
                    exercise_apply_bt.setBackgroundResource(R.drawable.button_gray_normal);
                    exercise_apply_bt.setText("报名已满");
                    exercise_apply_bt.setClickable(false);
                } else if (activityInfo.is_end()) {
                    exercise_apply_bt.setBackgroundResource(R.drawable.button_gray_normal);
                    exercise_apply_bt.setText("报名已截止");
                    exercise_apply_bt.setClickable(false);
                } else {
                    exercise_apply_bt.setBackgroundResource(R.drawable.button_normal_long);
                    exercise_apply_bt.setText("我要报名");
                    exercise_apply_bt.setClickable(true);
                }
            }

        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        switch (taskid) {
            case TaskID.ACTION_ACTIVITY_INFO:
                DialogUtil.stopDialogLoading(context);
                ToastUtil.showShort(context, "获取详情失败！");
                break;
            case TaskID.ACTION_ACTIVITY_COMMENT:
                DialogUtil.stopDialogLoading(context);
                ToastUtil.showShort(context, error);
                break;
            case TaskID.CANCEL_ISSUE:
                DialogUtil.stopDialogLoading(context);
                ToastUtil.showShort(context, error);
                break;
        }
    }

    public void registrationDetailClick(View v) {
        Intent intent = new Intent(this, RegistrationDetailActivity.class);
        intent.putExtra("model", datesActi);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        switch (arg0) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 1000:
                initNet();
                break;
            default:
                break;
        }
    }
}
