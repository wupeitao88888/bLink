package com.shiliuke.BabyLink;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shiliuke.base.ActivitySupport;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改密码
 * Created by wupeitao on 15/11/10.
 */
public class UpdatePWActivity extends ActivitySupport implements VolleyListerner{
    private EditText old_pw,
            new_pw,
            re_new_pw;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_updatepw);
        initView();
    }

    private void initView() {
        old_pw = (EditText) findViewById(R.id.old_pw);
        new_pw = (EditText) findViewById(R.id.new_pw);
        re_new_pw = (EditText) findViewById(R.id.re_new_pw);
        submit = (Button) findViewById(R.id.submit);
        setCtenterTitle("修改密码");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(old_pw.getText().toString()) ){
                    Toast.makeText(UpdatePWActivity.this, "请输入旧密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(new_pw.getText().toString()) ){
                    Toast.makeText(UpdatePWActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if( !new_pw.getText().toString().equals(re_new_pw.getText().toString()) ){
                    Toast.makeText(UpdatePWActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> update_params = new HashMap<String,String>();
                update_params.put("member_id", sharedPreferencesHelper.getValue("member_id"));
                update_params.put("old_password", old_pw.getText().toString());
                update_params.put("new_password", new_pw.getText().toString());
                BasicRequest.getInstance().requestPost(UpdatePWActivity.this,
                        TaskID.ACTION_UPADTE_PASSWORK, update_params, AppConfig.UPDATE_PASSWORD);
            }
        });
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        if(taskid == TaskID.ACTION_UPADTE_PASSWORK){
            Toast.makeText(UpdatePWActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        Toast.makeText(UpdatePWActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
    }
}
