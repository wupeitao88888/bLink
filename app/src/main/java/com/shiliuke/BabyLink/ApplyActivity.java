package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.BaseModel;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.utils.L;
import com.shiliuke.utils.StrUtil;
import com.shiliuke.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 我要报名
 * 报名吧
 * Created by wpt on 2015/10/29.
 */
public class ApplyActivity extends ActivitySupport implements View.OnClickListener, VolleyListerner {

    private Button submit;
    private EditText exercise_name;
    private EditText exercise_phone;
    private EditText exercise_peoplecount;
    private String activity_ids;
    private int surplus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_apply);

        submit = (Button) findViewById(R.id.submit);
        exercise_name = (EditText) findViewById(R.id.exercise_name);
        exercise_phone = (EditText) findViewById(R.id.exercise_phone);
        exercise_peoplecount = (EditText) findViewById(R.id.exercise_peoplecount);
        submit.setOnClickListener(this);
        Intent intent = getIntent();
        activity_ids = intent.getStringExtra("activity_id");
        surplus = intent.getIntExtra("surplus", 0);
        setCtenterTitle("剩余报名人数" + surplus);
        try {
            exercise_name.setText(AppConfig.loginInfo.getMember_name());
            exercise_phone.setText(AppConfig.loginInfo.getMobile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        DialogUtil.startDialogLoading(context);

        if (TextUtils.isEmpty(exercise_name.getText().toString())) {
            showToast("联系人不能为空");
        }
        if (TextUtils.isEmpty(exercise_phone.getText().toString())) {
            showToast("联系方式不能为空");
        }
        if (TextUtils.isEmpty(exercise_peoplecount.getText().toString())) {
            showToast("人数不能为空");
        }
        if (!StrUtil.isMobileNO(exercise_phone.getText().toString())) {
            showToast("联系方式格式不正确！");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        params.put("activity_id", activity_ids);
        params.put("member_name", exercise_name.getText().toString());
        params.put("member_mobile", exercise_phone.getText().toString());
        params.put("num", exercise_peoplecount.getText().toString());
        params.put("activity_id", activity_ids);
        BasicRequest.getInstance().requestPost(ApplyActivity.this, TaskID.ACTION_ACTIVITY_SIGN_UP, params, AppConfig.ACTIVITY_SIGN_UP, BaseModel.class);
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        ToastUtil.showShort(context, "报名成功");
        DialogUtil.stopDialogLoading(context);
//        EventBus.getDefault().post(AppConfig.ACTION_REFRESH);
        Intent intent = new Intent();
        intent.putExtra("status", "1");
        setResult(1000, intent);
        if(ApplyActivity.applyCallBack!=null){
            ApplyActivity.applyCallBack.success(Integer.parseInt(exercise_peoplecount.getText().toString()));
        }
        finish();
    }

    @Override
    public void onResponseError(String error, int taskid) {
        ToastUtil.showShort(context, error);
        DialogUtil.stopDialogLoading(context);
    }

    public static ApplyCallBack applyCallBack;

    public interface ApplyCallBack {
        void success(int count);
        void del();
    }

    public static void setApplayCallBack(ApplyCallBack applyCallBack) {
        ApplyActivity.applyCallBack = applyCallBack;
    }

}
