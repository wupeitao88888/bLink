package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import com.alibaba.fastjson.JSON;
import com.shiliuke.adapter.areaListAdapter;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.Area;
import com.shiliuke.global.AppConfig;
import com.shiliuke.global.MApplication;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DialogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lvfl on 2015/11/26.
 */
public class LocationAreaActivity extends ActivitySupport implements VolleyListerner{


    private ListView listView;
    private EditText autoCompleteTextView;
    private List<Area.Datas> list = new ArrayList<Area.Datas>();
    private areaListAdapter areaAdapter;
    private String tag_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_area_layout);
        tag_id = getIntent().getStringExtra("tag_id");
        initView();
    }

    public void initView(){
        listView = (ListView) findViewById(R.id.listView);
        autoCompleteTextView = (EditText) findViewById(R.id.autoCompleteTextView);
        setCtenterTitle("选择小区");
        setRightTitle("确定");
        setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAreaInfromation(autoCompleteTextView.getText().toString());
            }
        });
        areaAdapter = new areaListAdapter(this,list);
        listView.setAdapter(areaAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autoCompleteTextView.setText(list.get(position).getName());
                setAreaInfromation(list.get(position).getName());
            }
        });
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", s.toString());
                params.put("city", sharedPreferencesHelper.getValue("city"));
                BasicRequest.getInstance().requestPost(LocationAreaActivity.this,
                        TaskID.ACTION_GET_AREA_LIST_AUTO, params, AppConfig.GET_AREA_LIST_AUTO, Area.class);
            }
        });

        Map<String, String> params = new HashMap<String,String>();
        params.put("lo", MApplication.locationBD.getLongitude() + "");
        params.put("la", MApplication.locationBD.getLatitude() + "");
        BasicRequest.getInstance().requestPost(this, TaskID.ACTION_GET_AREA_LIST, params, AppConfig.GET_AREA_LIST, Area.class);
        DialogUtil.startDialogLoading(this);
    }

    private void setAreaInfromation(String area){
        Intent intent = new Intent();
        intent.putExtra("area_text", area);
        LocationAreaActivity.this.setResult(RESULT_OK, intent);
        if ("link".equals(tag_id)) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("member_id", sharedPreferencesHelper.getValue("member_id"));
            params.put("city", sharedPreferencesHelper.getValue("city"));
            params.put("home", "");
            params.put("home2", area);
            BasicRequest.getInstance().requestPost(LocationAreaActivity.this,
                    TaskID.ACTION_CHANGE_AEAR, params, AppConfig.CHANGE_AEAR);
            return;
        } else if ("update".equals(tag_id)) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("member_id", sharedPreferencesHelper.getValue("member_id"));
            params.put("city", sharedPreferencesHelper.getValue("city"));
            params.put("home", area);
            params.put("home2", "");
            BasicRequest.getInstance().requestPost(LocationAreaActivity.this,
                    TaskID.ACTION_CHANGE_AEAR, params, AppConfig.CHANGE_AEAR);
            return;

        }
        finish();
    }


    @Override
    public void onResponse(String str, int taskid, Object obj) {
        try {
            if(taskid == TaskID.ACTION_CHANGE_AEAR){
                finish();
            } else {
                DialogUtil.stopDialogLoading(this);
                Area area = JSON.parseObject(str,Area.class);
                list.clear();
                list.addAll(area.getDatas());
                areaAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        if(taskid == TaskID.ACTION_GET_AREA_LIST_AUTO){
            showToast(error.toString());
        }else if(taskid == TaskID.ACTION_GET_AREA_LIST){
            showToast(error.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
