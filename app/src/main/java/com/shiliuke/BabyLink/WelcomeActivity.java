package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import com.shiliuke.base.ActivitySupport;
import com.shiliuke.global.AppConfig;

public class WelcomeActivity extends ActivitySupport {

    private RelativeLayout splash_rl;
    private static final int sleepTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_welcome);

        setRemoveTitle();
        splash_rl = (RelativeLayout) findViewById(R.id.splash_rl);
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1500);
        splash_rl.startAnimation(animation);
    }


    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                }
                if( null !=sharedPreferencesHelper && !TextUtils.isEmpty(sharedPreferencesHelper.getValue("member_id")) ) {
                    if(!sharedPreferencesHelper.getBoolean("islogin")){
                        mIntent(WelcomeActivity.this, LoginActivity.class);
                    } else if( TextUtils.isEmpty(sharedPreferencesHelper.getValue("home")) ){
                        Intent intent = new Intent(WelcomeActivity.this, CompleteActivity.class);
                        intent.putExtra("member_id",sharedPreferencesHelper.getValue("member_id"));
                        startActivity(intent);
                    } else {
                        Information information = new Information();
                        information.setMember_id(sharedPreferencesHelper.getValue("member_id"));
                        information.setMember_name(sharedPreferencesHelper.getValue("member_name"));
                        information.setPassword(sharedPreferencesHelper.getValue("password"));
                        information.setMobile(sharedPreferencesHelper.getValue("mobile"));
                        information.setOpenid(sharedPreferencesHelper.getValue("openid"));
                        information.setMember_avar(sharedPreferencesHelper.getValue("member_avar"));
                        information.setCity(sharedPreferencesHelper.getValue("city"));
                        information.setHome(sharedPreferencesHelper.getValue("home"));
                        information.setHome(sharedPreferencesHelper.getValue("home2"));
                        information.setPosition(sharedPreferencesHelper.getValue("position"));
                        information.setBaby_nam(sharedPreferencesHelper.getValue("baby_nam"));
                        information.setBaby_sex(sharedPreferencesHelper.getValue("baby_sex"));
                        information.setBaby_date(sharedPreferencesHelper.getValue("baby_date"));
                        information.setBaby_link(sharedPreferencesHelper.getValue("baby_link"));
                        information.setAdd_time(sharedPreferencesHelper.getValue("add_time"));
                        information.setLogin_time(sharedPreferencesHelper.getValue("login_time"));
                        AppConfig.loginInfo = information;
                        mIntent(WelcomeActivity.this,MainTab.class);
                    }
                } else {
                    mIntent(WelcomeActivity.this, LoginActivity.class);
                }
                finish();
            }
        }).start();

    }

}
