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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lc-php1 on 2015/10/26.
 */
public class ForgetPassActivity extends ActivitySupport implements OnClickListener, VolleyListerner {

    private TextView user_service;
    private Button get_code;
    private Button register_Btn;

    private int time;
    private Timer timer;
    public static final int LOGIN_RESEND = 1;
    private SmsReceiver receiver;
    private IntentFilter filter;
    private EditText cade_password;
    private EditText user_name;
    private EditText password;
    private EditText commit_password;

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
                        setGetcode(getResources().getString(R.string.reacquire) + "(0" + msg.arg1 + ")", false);
                    } else {
                        setGetcode(getResources().getString(R.string.reacquire) + "(" + msg.arg1 + ")", false);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_layout);
        initView();
    }

    private void initView() {
        user_name = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);
        commit_password = (EditText) findViewById(R.id.commit_password);
        cade_password = (EditText) findViewById(R.id.cade_password);
        user_service = (TextView) findViewById(R.id.user_service);
        get_code = (Button) findViewById(R.id.get_code);
        register_Btn = (Button) findViewById(R.id.register_Btn);
        register_Btn.setText(getResources().getString(R.string.confirm));
        setCtenterTitle(getResources().getString(R.string.confirm));
        user_service.setVisibility(View.GONE);
        get_code.setOnClickListener(this);
        register_Btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_Btn:
                // 添加手机号码正确验证
                String code = cade_password.getText().toString();
                String psw = password.getText().toString();
                String commit_psw = commit_password.getText().toString();

                if (code == null) {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (psw == null) {
                    Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (commit_psw == null) {
                    Toast.makeText(this, "请输入确认密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!psw.equals(commit_psw)) {
                    Toast.makeText(this, "两次输入密码不相同", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> b_params = new HashMap<String, String>();
                b_params.put("mobile", user_name.getText().toString());
                b_params.put("password", password.getText().toString());
                b_params.put("code", cade_password.getText().toString());
                BasicRequest.getInstance().getPswPost(ForgetPassActivity.this,
                        TaskID.ACTION_FORGET_PASSWORD, b_params);
                break;
            case R.id.get_code:
                if (TextUtils.isEmpty(user_name.getText().toString())) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", user_name.getText().toString());
                params.put("type", "1");
                repeatSend();
                BasicRequest.getInstance().getCodePost(ForgetPassActivity.this,
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

    public void setGetcode(String text, boolean isEnable) {
        get_code.setEnabled(isEnable);
        if (text != null) {
            get_code.setText(text);
        }
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        if (taskid == TaskID.ACTION_GET_CODE) {
            getCodeOver(str);
        } else if (taskid == TaskID.ACTION_FORGET_PASSWORD) {
            getPasswordOver(str);
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        if (taskid == TaskID.ACTION_GET_CODE) {
            Toast.makeText(context, "获取验证码失败", Toast.LENGTH_SHORT).show();
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
        if (body.contains("【babyLink】验证码为：")) {
            body = body.replaceAll(".*?(\\d{6}).*", "$1");
            cade_password.setText("");
            cade_password.append(body);
            Log.e("================", body + "");
//            if (timer != null) {
//                timer.cancel();
//            }
        }
    }

    /**
     * 获得验证码解析
     *
     * @param object
     */
    public void getCodeOver(Object object) {
        JSONObject jsonObject = JSON.parseObject(object.toString());
        String bodyObject = jsonObject.getString("code");
        String adtasObject = jsonObject.getString("datas");
        Toast.makeText(context, adtasObject, Toast.LENGTH_SHORT).show();
    }

    /**
     * 修改密码
     *
     * @param object
     */
    private void getPasswordOver(Object object) {
        JSONObject jsonObject = JSON.parseObject(object.toString());
        String code = jsonObject.getString("code");
        if (code != null && "0".equals(code)) {
            Intent intent = new Intent(ForgetPassActivity.this, LoginActivity.class);
            Toast.makeText(context, "修改成功，请重新登录", Toast.LENGTH_SHORT).show();
            intent.putExtra("mobile", user_name.getText().toString());
            startActivity(intent);
            finish();
        } else if (code != null && "1".equals(code)) {
            String adtasObject = jsonObject.getString("datas");
            Toast.makeText(context, adtasObject.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}
