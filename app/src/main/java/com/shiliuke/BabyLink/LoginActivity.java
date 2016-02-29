package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.utils.L;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler
        ;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by lc-php1 on 2015/10/26.
 */
public class LoginActivity extends ActivitySupport implements OnClickListener, VolleyListerner {

    private EditText user_name;
    private EditText password;
    private Button login_Btn;
    private TextView forget_password;
    private TextView qq_login;
    private TextView wx_login;
    private TextView sina_login;
    UMSocialService mController;
    private String mobile;
    private String openid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);
        mController = UMServiceFactory.getUMSocialService("com.umeng.login");

        mobile = getIntent().getStringExtra("mobile");
        initView();

    }

    private void initView() {
        user_name = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);
        login_Btn = (Button) findViewById(R.id.login_Btn);
        forget_password = (TextView) findViewById(R.id.forget_password);
        qq_login = (TextView) findViewById(R.id.qq_login);
        wx_login = (TextView) findViewById(R.id.wx_login);
        sina_login = (TextView) findViewById(R.id.sina_login);
        isLeftVisibility(false);
        setCtenterTitle(getResources().getString(R.string.login));
        setRightTitle(getResources().getString(R.string.registe));
        login_Btn.setOnClickListener(this);
        forget_password.setOnClickListener(this);
        qq_login.setOnClickListener(this);
        wx_login.setOnClickListener(this);
        sina_login.setOnClickListener(this);
        setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent(LoginActivity.this, RegisterActivity.class);
            }
        });
        if(TextUtils.isEmpty(mobile)){
            user_name.setText(mobile);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_Btn:
                if (user_name.getText().toString() == null) {
                    Toast.makeText(this, "请输入验账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString() == null) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", user_name.getText().toString());
                params.put("password", password.getText().toString());
                BasicRequest.getInstance().Login(this,
                        TaskID.ACTION_LOGIN, params);
                DialogUtil.startDialogLoading(this);
                break;
            case R.id.forget_password:
                mIntent(LoginActivity.this, ForgetPassActivity.class);
                break;
            case R.id.qq_login:
                QQlogin();
                DialogUtil.startDialogLoading(this);
                sharedPreferencesHelper.putValue("type", "1");
                break;
            case R.id.wx_login:
                WxLogin();
                DialogUtil.startDialogLoading(this);
                sharedPreferencesHelper.putValue("type", "2");
                break;
            case R.id.sina_login:
                Sinalogin();
                DialogUtil.startDialogLoading(this);
                sharedPreferencesHelper.putValue("type", "3");
                break;
        }
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        try {
            if (taskid == TaskID.ACTION_LOGIN) {
                getLoginOver(str);
            } else if(taskid == TaskID.ACTION_GET_QQ_LOGIN){
                getOtherLoginOver(str);
            }
            DialogUtil.stopDialogLoading(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
        DialogUtil.stopDialogLoading(this);
    }

    private void Sinalogin(){
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.doOauthVerify(this, SHARE_MEDIA.SINA,
                new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA platform) {

                    }

                    @Override
                    public void onError(SocializeException e,
                                        SHARE_MEDIA platform) {
                        Toast.makeText(LoginActivity.this, "授权错误",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete(Bundle value,
                                           SHARE_MEDIA platform) {

                        try {
                            openid = value.getString("access_token");
                            sharedPreferencesHelper.putValue("openid", openid);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // 获取相关授权信息
                        mController.getPlatformInfo(LoginActivity.this,
                                SHARE_MEDIA.SINA, new UMDataListener() {
                                    @Override
                                    public void onStart() {
//                                        Toast.makeText(LoginActivity.this,
//                                                "获取平台数据开始...",
//                                                Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onComplete(int status,
                                                           Map<String, Object> info) {
                                        // TODO Auto-generated method stub
                                        if (status == 200 && info != null) {
                                            sharedPreferencesHelper.putValue("member_name", info.get("screen_name").toString());
                                            sharedPreferencesHelper.putValue("member_avar", info.get("profile_image_url").toString());
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("openid", openid);
                                            params.put("type", "3");
                                            BasicRequest.getInstance().getOtherLoginPost(LoginActivity.this,
                                                    TaskID.ACTION_GET_QQ_LOGIN, params);
                                        } else {
                                            Toast.makeText(LoginActivity.this,
                                                    "发生错误："
                                                            + status,
                                                    Toast.LENGTH_SHORT).show();
                                            Log.d("TestData", "发生错误："
                                                    + status);
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(LoginActivity.this, "授权取消",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void QQlogin(){
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1104869195",
                "ZKscrjTqoGkHSEOE");
        qqSsoHandler.addToSocialSDK();
        mController.doOauthVerify(this, SHARE_MEDIA.QQ,
                new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA platform) {
//                        Toast.makeText(LoginActivity.this, "授权开始",
//                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(SocializeException e,
                                        SHARE_MEDIA platform) {
                        Toast.makeText(LoginActivity.this, "授权错误",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete(Bundle value,
                                           SHARE_MEDIA platform) {
//                        Toast.makeText(LoginActivity.this, "授权完成",
//                                Toast.LENGTH_SHORT).show();
                        try {
                            openid = value.getString("openid");
                            sharedPreferencesHelper.putValue("openid", openid);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // 获取相关授权信息
                        mController.getPlatformInfo(LoginActivity.this,
                                SHARE_MEDIA.QQ, new UMDataListener() {
                                    @Override
                                    public void onStart() {
//                                        Toast.makeText(LoginActivity.this,
//                                                "获取平台数据开始...",
//                                                Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onComplete(int status,
                                                           Map<String, Object> info) {
                                        // TODO Auto-generated method stub
                                        if (status == 200 && info != null) {
                                            sharedPreferencesHelper.putValue("member_name", info.get("screen_name").toString());
                                            sharedPreferencesHelper.putValue("member_avar", info.get("profile_image_url").toString());
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("openid", openid);
                                            params.put("type", "1");
                                            BasicRequest.getInstance().getOtherLoginPost(LoginActivity.this,
                                                    TaskID.ACTION_GET_QQ_LOGIN, params);
                                        } else {
                                            Toast.makeText(LoginActivity.this,
                                                    "发生错误："
                                                            + status,
                                                    Toast.LENGTH_SHORT).show();
                                            Log.d("TestData", "发生错误："
                                                    + status);
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(LoginActivity.this, "授权取消",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void WxLogin(){
        UMWXHandler wxHandler = new UMWXHandler(this, AppConfig.WX_APP_ID,
                AppConfig.WX_APP_SECRET);
        wxHandler.addToSocialSDK();
        mController.doOauthVerify(LoginActivity.this, SHARE_MEDIA.WEIXIN,
                new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA platform) {
//                        Toast.makeText(LoginActivity.this, "授权开始",
//                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(SocializeException e,
                                        SHARE_MEDIA platform) {
//                        Toast.makeText(LoginActivity.this, "授权错误",
//                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete(Bundle value,
                                           SHARE_MEDIA platform) {
                        Toast.makeText(LoginActivity.this, "授权完成",
                                Toast.LENGTH_SHORT).show();
                        try {
                            openid = value.getString("openid");
                            sharedPreferencesHelper.putValue("openid", openid);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // 获取相关授权信息
                        mController.getPlatformInfo(LoginActivity.this,
                                SHARE_MEDIA.WEIXIN, new UMDataListener() {
                                    @Override
                                    public void onStart() {
                                        Toast.makeText(LoginActivity.this,
                                                "获取平台数据开始...",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onComplete(int status,
                                                           Map<String, Object> info) {
                                        if (status == 200 && info != null) {
                                            sharedPreferencesHelper.putValue("member_name", info.get("nickname").toString());
                                            sharedPreferencesHelper.putValue("member_avar", info.get("headimgurl").toString());
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("openid", openid);
                                            params.put("type", "2");
                                            BasicRequest.getInstance().getOtherLoginPost(LoginActivity.this,
                                                    TaskID.ACTION_GET_QQ_LOGIN, params);
                                        } else {
                                            Log.d("TestData", "发生错误："
                                                    + status);
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(LoginActivity.this, "授权取消",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 登录解析
     *
     * @param object
     */
    private void getLoginOver(Object object) {
        JSONObject jsonObject = JSON.parseObject(object.toString());
        String code = jsonObject.getString("code");
        if (code != null && "0".equals(code)) {
            JSONObject adtasObject = jsonObject.getJSONObject("datas");
            Information information = JSON.parseObject(adtasObject.toString(), Information.class);
            saveInformation(information);
            sharedPreferencesHelper.putBoolean("islogin",true);
            if (TextUtils.isEmpty(information.getHome())) {
                Intent intent = new Intent(LoginActivity.this, CompleteActivity.class);
                intent.putExtra("member_id", information.getMember_id());
                startActivity(intent);
                finish();
            } else {
                mIntent(LoginActivity.this, MainTab.class);
                finish();
            }
            // 注册成功
        } else if (code != null && "1".equals(code)) {
            String adtasObject = jsonObject.getString("datas");
            Toast.makeText(context, adtasObject.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 登录解析
     *
     * @param object
     */
    private void getOtherLoginOver(Object object) {
        JSONObject jsonObject = JSON.parseObject(object.toString());
        String code = jsonObject.getString("code");
        if (code != null && "0".equals(code)) {
            JSONObject adtasObject = jsonObject.getJSONObject("datas");
            Information information = JSON.parseObject(adtasObject.toString(), Information.class);
            saveInformation(information);
            sharedPreferencesHelper.putBoolean("islogin",true);
            if(TextUtils.isEmpty(information.getHome())){
                Intent intent = new Intent(LoginActivity.this, CompleteActivity.class);
                intent.putExtra("member_id",information.getMember_id());
                startActivity(intent);
                finish();
            } else {
                mIntent(LoginActivity.this, MainTab.class);
                finish();
            }
        } else if (code != null && "1".equals(code)) {
            String adtasObject = jsonObject.getString("datas");
            Toast.makeText(context, adtasObject.toString(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this,OtherCompleteActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** 使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
                requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    private void saveInformation(Information information){
        AppConfig.loginInfo = information;
        sharedPreferencesHelper.putValue("member_id",information.getMember_id());
        sharedPreferencesHelper.putValue("member_name",information.getMember_name());
        sharedPreferencesHelper.putValue("password",information.getPassword());
        sharedPreferencesHelper.putValue("mobile",information.getMobile());
        sharedPreferencesHelper.putValue("openid",information.getOpenid());
        sharedPreferencesHelper.putValue("member_avar",information.getMember_avar());
        sharedPreferencesHelper.putValue("city",information.getCity());
        sharedPreferencesHelper.putValue("home",information.getHome());
        sharedPreferencesHelper.putValue("home2",information.getHome2());
        sharedPreferencesHelper.putValue("position",information.getPosition());
        sharedPreferencesHelper.putValue("baby_nam",information.getBaby_nam());
        sharedPreferencesHelper.putValue("baby_sex",information.getBaby_sex());
        sharedPreferencesHelper.putValue("baby_date",information.getBaby_date());
        sharedPreferencesHelper.putValue("baby_link",information.getBaby_link());
        sharedPreferencesHelper.putValue("add_time",information.getAdd_time());
        sharedPreferencesHelper.putValue("login_time",information.getLogin_time());
    }

}
