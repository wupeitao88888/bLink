package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.CityChoice;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DialogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lvfl on 2015/12/9.
 */
public class CityChoiceActivity extends ActivitySupport implements VolleyListerner{

    private ListView listView;
    private List<CityChoice.Datas> list = new ArrayList<>();
    private CityAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_choice_layout);
        initView();
    }

    private void initView() {
        setCtenterTitle("选择城市");
        listView = (ListView) findViewById(R.id.listView);
        Map<String, String> params = new HashMap<String,String>();
        BasicRequest.getInstance().requestPost(this, TaskID.ACTION_GET_CITY, params, AppConfig.CHOICE_GET_CITY, CityChoice.class);
        DialogUtil.startDialogLoading(this);
        cityAdapter = new CityAdapter();
        listView.setAdapter(cityAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("city_text", list.get(position).getValue());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        if(TaskID.ACTION_GET_CITY == taskid){
            CityChoice cityChoice = (CityChoice) obj;
            list.addAll(cityChoice.getDatas());
            cityAdapter.notifyDataSetChanged();
            DialogUtil.stopDialogLoading(this);
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        showToast("网络连接错误");

    }

    class CityAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CityChoice.Datas getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CityChoice.Datas city = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(CityChoiceActivity.this).inflate(
                        R.layout.city_choice_layout_item, parent, false);
            }
            TextView city_text = (TextView) convertView.findViewById(R.id.city_text);
            city_text.setText(city.getValue());
            return convertView;
        }
    }
}
