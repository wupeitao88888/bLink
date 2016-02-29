package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.information.InformationUtils;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.global.AppConfig;
import com.shiliuke.utils.GlideCircleTransform;
import com.shiliuke.utils.ViewUtil;

/**
 * Created by wupeitao on 15/11/5.
 */
public class MeHomePage extends ActivitySupport implements View.OnClickListener {
    private RelativeLayout user_icon_rl,//用户信息
            user_activity_re,//我的活动
            user_topic_re,//我的话题
            user_change_re,//我的置换
            user_showbean_re,//我的秀逗
            user_friends_re,//我的朋友
            user_fans_re;
    ;
    private ImageView user_icon_image;//用户头像


    private TextView user_name;//用户姓名
    private TextView tv_active_information;//
    private TextView tv_talk_information;//
    private TextView tv_exchange_information;//
    private TextView tv_xiu_information;//
    private TextView tv_fensi_information;//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_homepage);
        initView();
        tv_active_information = (TextView) findViewById(R.id.tv_active_information);
        tv_talk_information = (TextView) findViewById(R.id.tv_talk_information);
        tv_exchange_information = (TextView) findViewById(R.id.tv_exchange_information);
        tv_xiu_information = (TextView) findViewById(R.id.tv_xiu_information);
        tv_fensi_information = (TextView) findViewById(R.id.tv_fensi_information);
        InformationUtils.setInformationView(InformationUtils.InformationKey.XIU,tv_xiu_information);
        InformationUtils.setInformationView(InformationUtils.InformationKey.ACTIVE,tv_active_information);
        InformationUtils.setInformationView(InformationUtils.InformationKey.TALK,tv_talk_information);
        InformationUtils.setInformationView(InformationUtils.InformationKey.EXCHANGE,tv_exchange_information);
        InformationUtils.setInformationView(InformationUtils.InformationKey.FENSI,tv_fensi_information);
        InformationUtils.updataAllView();
    }

    private void initView() {
        setCtenterTitle("我的主页");
        user_icon_rl = (RelativeLayout) findViewById(R.id.user_activity_re);
        user_friends_re = (RelativeLayout) findViewById(R.id.user_friends_re);
        user_activity_re = (RelativeLayout) findViewById(R.id.user_activity_re);
        user_topic_re = (RelativeLayout) findViewById(R.id.user_topic_re);
        user_change_re = (RelativeLayout) findViewById(R.id.user_change_re);
        user_showbean_re = (RelativeLayout) findViewById(R.id.user_showbean_re);
        user_fans_re = (RelativeLayout) findViewById(R.id.user_fans_re);
        user_icon_image = (ImageView) findViewById(R.id.user_icon_image);
        user_name = (TextView) findViewById(R.id.user_name);
        ViewUtil.setText(context,user_name, AppConfig.loginInfo.getMember_name());
        Glide.with(context)
                .load(AppConfig.loginInfo.getMember_avar())
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).transform(new GlideCircleTransform(context))
                .placeholder(R.mipmap.morentoux)
                .error(R.mipmap.morentoux)
                .crossFade()
                .into(user_icon_image);
        user_icon_rl.setOnClickListener(this);
        user_friends_re.setOnClickListener(this);
        user_activity_re.setOnClickListener(this);
        user_topic_re.setOnClickListener(this);
        user_change_re.setOnClickListener(this);
        user_showbean_re.setOnClickListener(this);
        user_fans_re.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_icon_rl:
                // 我的信息
                break;
            case R.id.user_activity_re:
                // 我的活动
                Intent intent = new Intent(context, MeActivity.class);
                startActivity(intent);
                break;
            case R.id.user_topic_re:
                // 我的话题
                startActivity(new Intent(context, MeTopicActivity.class));
                break;
            case R.id.user_change_re:
                mIntent(context, MeChangeActivity.class);
                break;
            case R.id.user_showbean_re:
                // 我的秀逗
                mIntent(context, MeShowBeanActivity.class);
                break;
            case R.id.user_friends_re:
                // 我的朋友
                mIntent(context, MeFriendsActivity.class);
                break;
            case R.id.user_fans_re:
                // 我的粉丝
                mIntent(context, MeFansActivity.class);
                break;

        }
    }
}
