package com.shiliuke.BabyLink;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsMessage;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DialogUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lc-php1 on 2015/11/22.
 */
public class OtherCompleteActivity extends ActivitySupport implements OnClickListener,VolleyListerner {

    private Button get_code ;
    private Button register_Btn;
    private int time;
    private Timer timer;
    public static final int LOGIN_RESEND = 1;
    private SmsReceiver receiver;
    private IntentFilter filter;

    private Handler hand = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case LOGIN_RESEND:
                    if (msg.arg1 < 0) {
                        if (timer != null) {
                            timer.cancel();
                        }
                        setGetcode(getResources().getString(R.string.aquire_auth_code), true);
                    } else if (msg.arg1 < 10) {
                        setGetcode(getResources().getString(R.string.reacquire)+"(0" + msg.arg1 + ")", false);
                    } else {
                        setGetcode(getResources().getString(R.string.reacquire)+"(" + msg.arg1 + ")", false);
                    }
                    break;
            }
        }
    };
    private EditText cade_password;
    private EditText user_name;
    private String member_name;
    private String member_avar;
    private String openid;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_complete_layout);
        member_name = sharedPreferencesHelper.getValue("member_name");
        member_avar = sharedPreferencesHelper.getValue("member_avar");
        type = sharedPreferencesHelper.getValue("type");
        openid = sharedPreferencesHelper.getValue("openid");
        initView();
    }

    private void initView() {
        user_name = (EditText) findViewById(R.id.user_name);
        get_code = (Button) findViewById(R.id.get_code);
        register_Btn = (Button) findViewById(R.id.register_Btn);
        cade_password = (EditText) findViewById(R.id.cade_password);
        register_Btn.setText("下一步");
        setCtenterTitle("手机验证");
        get_code.setOnClickListener(this);
        register_Btn.setOnClickListener(this);
    }

    public void setGetcode(String text, boolean isEnable) {
        get_code.setEnabled(isEnable);
        if (text != null) {
            get_code.setText(text);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_Btn:

                // 添加手机号码正确验证
                String code = cade_password.getText().toString();

                if( TextUtils.isEmpty(code) ){
                    Toast.makeText(this,"请输入验证码",Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> reparams = new HashMap<String,String>();
                Log.e("=======", user_name.getText().toString());
                reparams.put("mobile", user_name.getText().toString());
                reparams.put("code", code);
                reparams.put("password", "");
                reparams.put("member_name", member_name);
                reparams.put("member_avar", member_avar);
                reparams.put("openid", openid);
                reparams.put("type", type);
                Log.e("", member_name + ":" + member_avar + ":" + openid + ":" + type);
                BasicRequest.getInstance().sendRegisterPost(OtherCompleteActivity.this,
                        TaskID.ACTION_REGISTER, reparams);
                DialogUtil.startDialogLoading(this);
                break;
            case R.id.get_code:
                if(TextUtils.isEmpty(user_name.getText().toString()) ){
                    Toast.makeText(this,"请输入手机号",Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> params = new HashMap<String,String>();
                params.put("mobile", user_name.getText().toString());
                params.put("type", "2");
                repeatSend();
                BasicRequest.getInstance().getCodePost(OtherCompleteActivity.this,
                        TaskID.ACTION_GET_CODE, params);
                regSmsReceiver();
                break;
        }
    }

    public void repeatSend() {
        time = 59;
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Message message = hand.obtainMessage(LOGIN_RESEND);
                message.arg1 = time--;
                // message.sendToTarget();
                hand.sendMessage(message);
            }
        };
        timer.schedule(task, 0, 1000);
    }

    @Override
    public void onResponse(String str, int taskid,Object obj) {
        try {
            if(taskid == TaskID.ACTION_GET_CODE){
                getCodeOver(str);
            }else if(taskid == TaskID.ACTION_REGISTER){
                getRegisterOver(str);
                DialogUtil.stopDialogLoading(this);
            }else if( taskid == TaskID.ACTION_GET_INFORMATION_DATA){
                getInformationOver(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(taskid == TaskID.ACTION_GET_CODE){
                Toast.makeText(context,"获取验证码失败",Toast.LENGTH_SHORT).show();
            }else if(taskid == TaskID.ACTION_REGISTER){
                Toast.makeText(context,"注册失败",Toast.LENGTH_SHORT).show();
                DialogUtil.stopDialogLoading(this);
            }
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        Log.e("------失败--------", error);
        time = -1;
        if(taskid == TaskID.ACTION_GET_CODE){
            Toast.makeText(context,error.toString(),Toast.LENGTH_SHORT).show();
        }else if(taskid == TaskID.ACTION_REGISTER){
            DialogUtil.stopDialogLoading(this);
            Toast.makeText(context,error.toString(),Toast.LENGTH_SHORT).show();
        }else if(taskid == TaskID.ACTION_GET_INFORMATION_DATA){
            Toast.makeText(context,error.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取个人信息
     * @param object
     */
    private void getInformationOver(Object object) {
        JSONObject jsonObject = JSON.parseObject(object.toString());
        JSONObject adtasObject = jsonObject.getJSONObject("datas");
        Information information = JSON.parseObject(adtasObject.toString(), Information.class);
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
        mIntent(OtherCompleteActivity.this, MainTab.class);
        finish();
    }

    /**
     * 获得验证码解析
     * @param object
     */
    public void getCodeOver(Object object) {
        JSONObject jsonObject = JSON.parseObject(object.toString());
        String bodyObject = jsonObject.getString("code");
        String adtasObject = jsonObject.getString("datas");
        Toast.makeText(context,adtasObject,Toast.LENGTH_SHORT).show();
    }

    /**
     * 注册
     * @param object
     */
    public void getRegisterOver(Object object) {
        JSONObject jsonObject = JSON.parseObject(object.toString());
        String code = jsonObject.getString("code");
        String adtasObject = jsonObject.getString("datas");
        Log.e("code=",code);
        Log.e("object=",object.toString());
        if(code != null && "0".equals(code)){
            // 去完善资料没填过密码
            Intent intent = new Intent(OtherCompleteActivity.this, CompleteActivity.class);
            intent.putExtra("member_id",adtasObject);
            intent.putExtra("isVisible",true);
            startActivity(intent);
            finish();
            // 注册成功
        }else if(code != null && "1".equals(code)){
            // 失败
            Toast.makeText(context,adtasObject.toString(),Toast.LENGTH_SHORT).show();
        }else if(code != null && "2".equals(code)){
            // 完善过资料了
            getInformationOver(object);
//            Map<String, String> params = new HashMap<String,String>();
//            params.put("member_id", adtasObject);
//            BasicRequest.getInstance().getInformationPost(OtherCompleteActivity.this, TaskID.ACTION_GET_INFORMATION_DATA,
//                    params);
        }else if(code != null && "3".equals(code)){
            // 填过密码，没完善资料
            Intent intent = new Intent(OtherCompleteActivity.this, CompleteActivity.class);
            intent.putExtra("member_id",adtasObject);
            startActivity(intent);
            finish();
        }
    }

    private class SmsReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            Object[] objects = (Object[]) bundle.get("pdus");
            for (Object obj : objects) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
                String body = smsMessage.getDisplayMessageBody();
                receiveSms(body);
            }
        }
    }

    public void receiveSms(String body) {
//        time = -1;
        if(body.contains("【babyLink】验证码为：")){
            body  = body.replaceAll( ".*?(\\d{6}).*" , "$1");
            cade_password.setText("");
            cade_password.append(body);
            Log.e("================", body + "");
//            if (timer != null) {
//                timer.cancel();
//            }
        }
    }

    /**
     * 注册短信SmsReceivier监听器
     */
    public void regSmsReceiver() {
        if (receiver == null || filter == null) {
            receiver = new SmsReceiver();
            filter = new IntentFilter();
            filter.setPriority(1000);
            filter.addAction("android.provider.Telephony.SMS_RECEIVED");
            context.registerReceiver(receiver, filter);
        }
    }
}
