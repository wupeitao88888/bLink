package com.shiliuke.BabyLink;

import android.content.Intent;
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
import com.shiliuke.utils.DialogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改昵称
 * Created by lvfl on 15/11/10.
 */
public class UpdateNameActivity extends ActivitySupport implements VolleyListerner{
    private EditText old_name;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_update_name);
        initView();
    }

    private void initView() {
        old_name = (EditText) findViewById(R.id.old_name);
        submit = (Button) findViewById(R.id.submit);
        setCtenterTitle("修改昵称");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(old_name.getText().toString()) ){
                    Toast.makeText(UpdateNameActivity.this, "请输入昵称", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> update_params = new HashMap<String,String>();
                update_params.put("member_id", sharedPreferencesHelper.getValue("member_id"));
                update_params.put("member_name", old_name.getText().toString());
                BasicRequest.getInstance().requestPost(UpdateNameActivity.this,
                        TaskID.ACTION_UPDATE_NAME, update_params, AppConfig.CHANGE_AEAR);
                DialogUtil.startDialogLoading(UpdateNameActivity.this);
            }
        });
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        DialogUtil.stopDialogLoading(UpdateNameActivity.this);
        if(taskid == TaskID.ACTION_UPDATE_NAME){
            Toast.makeText(UpdateNameActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("name_text", old_name.getText().toString());
            UpdateNameActivity.this.setResult(RESULT_OK, intent);
            sharedPreferencesHelper.putValue("member_name", old_name.getText().toString());
            AppConfig.loginInfo.setMember_name(old_name.getText().toString());
            finish();
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        DialogUtil.stopDialogLoading(UpdateNameActivity.this);
        Toast.makeText(UpdateNameActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
    }
}
