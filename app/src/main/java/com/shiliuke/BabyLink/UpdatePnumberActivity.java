package com.shiliuke.BabyLink;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.BaseModel;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 修改电话
 * Created by wupeitao on 15/11/10.
 */
public class UpdatePnumberActivity extends ActivitySupport implements View.OnClickListener,VolleyListerner {
    private EditText newPhoneNumber,//新号码
            identifying_code;//请输入验证码
    private Button send_identifying_code,//发送验证码
            submit;//提交
    private int time;
    private Timer timer;
    public static final int LOGIN_RESEND = 1;
    private SmsReceiver receiver;
    private IntentFilter filter;

    private Handler hand = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_update_pnumber);
        initView();
    }

    private void initView() {
        newPhoneNumber = (EditText) findViewById(R.id.newPhoneNumber);
        identifying_code = (EditText) findViewById(R.id.identifying_code);
        send_identifying_code = (Button) findViewById(R.id.send_identifying_code);
        submit = (Button) findViewById(R.id.submit);
        send_identifying_code.setOnClickListener(this);
        submit.setOnClickListener(this);
        setCtenterTitle("修改手机号");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_identifying_code:
                if(TextUtils.isEmpty(newPhoneNumber.getText().toString()) ){
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> params = new HashMap<String,String>();
                params.put("mobile", newPhoneNumber.getText().toString());
                repeatSend();
                BasicRequest.getInstance().getCodePost(UpdatePnumberActivity.this,
                        TaskID.ACTION_GET_CODE, params);
                regSmsReceiver();
                break;
            case R.id.submit:
                if(TextUtils.isEmpty(newPhoneNumber.getText().toString()) ){
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(identifying_code.getText().toString()) ){
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> update_params = new HashMap<String,String>();
                update_params.put("member_id", sharedPreferencesHelper.getValue("member_id"));
                update_params.put("mobile", newPhoneNumber.getText().toString());
                update_params.put("code", identifying_code.getText().toString());
                BasicRequest.getInstance().requestPost(UpdatePnumberActivity.this,
                        TaskID.ACTION_UPADTE_MOBILE, update_params, AppConfig.UPDATE_MOBILE);
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

    public void setGetcode(String text, boolean isEnable) {
        send_identifying_code.setEnabled(isEnable);
        if (text != null) {
            send_identifying_code.setText(text);
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
            identifying_code.setText("");
            identifying_code.append(body);
            Log.e("================", body + "");
//            if (timer != null) {
//                timer.cancel();
//            }
        }
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        if(taskid == TaskID.ACTION_GET_CODE){
            getCodeOver(str);
        }else if(taskid == TaskID.ACTION_UPADTE_MOBILE){
            sharedPreferencesHelper.putValue("mobile",newPhoneNumber.getText().toString());
            Toast.makeText(context,"修改成功",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        time = -1;
        Toast.makeText(context,error.toString(),Toast.LENGTH_SHORT).show();
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
}
