package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.Image;
import com.shiliuke.bean.Unconsume;

/**
 * 未消费与已消费公用一个兑换码页面
 * Created by wupeitao on 15/11/7.
 */
public class UnconsumeCodeActivity extends ActivitySupport {
    private TextView unconsume_title;
    private TextView unconsume_endtime;
    private TextView unsonsume_code;
    private TextView tv_find_des_goods;
    private ImageView code_image;
    private Unconsume.Data unconsume;
    private ImageView xiaofei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_unconsumecode);
        unconsume = (Unconsume.Data) getIntent().getSerializableExtra("unconsume");
        initView();
    }

    private void initView() {
        xiaofei = (ImageView) findViewById(R.id.xiaofei);
        tv_find_des_goods = (TextView) findViewById(R.id.tv_find_des_goods);
        if("7".equals(unconsume.getOrder_status()) || "8".equals(unconsume.getOrder_status())){
            setCtenterTitle("已消费");
            xiaofei.setVisibility(View.VISIBLE);
        }else{
            setCtenterTitle("未消费");
            xiaofei.setVisibility(View.GONE);
        }
        unconsume_title = (TextView) findViewById(R.id.unconsume_title);
        unconsume_endtime = (TextView) findViewById(R.id.unconsume_endtime);
        unsonsume_code = (TextView) findViewById(R.id.unsonsume_code);
        code_image = (ImageView) findViewById(R.id.code_image);
        unconsume_title.setText(unconsume.getGoods().getGoods_name());
        unconsume_endtime.setText("有效期至" + unconsume.getGoods().getUse_time());
        unsonsume_code.setText("密码"+unconsume.getOrder_code());
        Glide.with(this).load(unconsume.getQrcode()).into(code_image);
    }

    public void click(View v){
        Intent intent = new Intent(this,FindDesActivity.class);
        intent.putExtra("goods_id",unconsume.getGoods_id());
        startActivity(intent);
    }
}
