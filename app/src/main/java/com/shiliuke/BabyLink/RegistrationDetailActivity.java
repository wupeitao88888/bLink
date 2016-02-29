package com.shiliuke.BabyLink;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.ActivityInfo;
import com.shiliuke.bean.UserInfo;
import com.shiliuke.utils.ViewHolder;

public class RegistrationDetailActivity extends ActivitySupport {

    private ActivityInfo.Dates datesActi;
    private ListView listview_registration_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_detail);
        setCtenterTitle("报名详情");
        try {
            datesActi = (ActivityInfo.Dates) getIntent().getSerializableExtra("model");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (datesActi == null) {
            finish();
            return;
        }
        TextView tv_registration_detail_num = (TextView) findViewById(R.id.tv_registration_detail_num);
        tv_registration_detail_num.setText("共"+datesActi.getCount()+"人报名");
        listview_registration_detail = (ListView) findViewById(R.id.listview_registration_detail);
        listview_registration_detail.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return datesActi.getLog_list().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(RegistrationDetailActivity.this).inflate(R.layout.layout_registration_detail_item,null);
            }
            UserInfo info = datesActi.getLog_list().get(position);
            ImageView image_registration_detail = ViewHolder.get(convertView, R.id.image_registration_detail);
            TextView tv_registration_detail_name = ViewHolder.get(convertView, R.id.tv_registration_detail_name);
            TextView tv_registration_detail_phone = ViewHolder.get(convertView, R.id.tv_registration_detail_phone);
            TextView tv_registration_detail_count = ViewHolder.get(convertView, R.id.tv_registration_detail_count);

            Glide.with(RegistrationDetailActivity.this).load(info.getMember_avar()).into(image_registration_detail);
            tv_registration_detail_name.setText("姓名：" + info.getMember_name());
            tv_registration_detail_phone.setText("联系方式："+info.getMember_mobile());
            tv_registration_detail_count.setText("参与人数："+info.getNum());
            return convertView;
        }
    }
}
