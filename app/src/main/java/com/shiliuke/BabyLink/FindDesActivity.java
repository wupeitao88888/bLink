package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.FindInfoModel;
import com.shiliuke.global.AppConfig;
import com.shiliuke.global.MApplication;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DateUtil;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.utils.ShareUtils;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.sso.UMSsoHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wangzhi on 15/11/8.
 * 发现list详情页面
 */
public class FindDesActivity extends ActivitySupport implements VolleyListerner {

    private String goods_id;
    private TextView tv_find_des_deadline;//截止时间
    private TextView tv_find_des_yuding;//预定人数
    private TextView tv_find_des_day;//天
    private TextView tv_find_des_hour;//小时
    private TextView tv_find_des_minute;//分
    private TextView tv_find_des_second;//秒
    private View layout_find_des_yaoqing;//邀请布局
    private TextView tv_share_sina;//分享至新浪微博
    private TextView tv_share_qq;//分享到qq
    private TextView tv_share_wx;//分享到微信
    private TextView tv_share_wxpyq;//分享到微信朋友圈
    private View layout_find_des_daojishi;//倒计时布局
    private TextView tv_find_des_ydxz;//预定须知
    private TextView tv_find_des_xffs;//消费方式
    private TextView tv_find_des_lxfs;//联系方式
    private ImageView image_item_find_topbg;//商品图片

    private TextView tv_item_find_top;
    private TextView tv_item_find_title;
    private TextView tv_item_find_price;
    private TextView tv_item_find_address;
    private TextView tv_item_find_distance;

    private Button btn_find_des_xianj;//现价
    private Button btn_find_des_reserve;//我要预定

    private boolean isMianFei = false;//是否免费
    private String info;//商品详情
    private String goods_dingjin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_des);
        setCtenterTitle("发现详情");
        goods_id = getIntent().getStringExtra("goods_id");
        if (TextUtils.isEmpty(goods_id)) {
            finish();
            return;
        }
        requestData();
        initView();
    }

    private void initView() {
        image_item_find_topbg = (ImageView) findViewById(R.id.image_item_find_topbg);
        tv_item_find_top = (TextView) findViewById(R.id.tv_item_find_top);
        tv_item_find_title = (TextView) findViewById(R.id.tv_item_find_title);
        tv_item_find_price = (TextView) findViewById(R.id.tv_item_find_price);
        tv_item_find_address = (TextView) findViewById(R.id.tv_item_find_address);
        tv_item_find_distance = (TextView) findViewById(R.id.tv_item_find_distance);
        layout_find_des_daojishi = findViewById(R.id.layout_find_des_daojishi);
        btn_find_des_reserve = (Button) findViewById(R.id.btn_find_des_reserve);
        btn_find_des_xianj = (Button) findViewById(R.id.btn_find_des_xianj);
        layout_find_des_yaoqing = findViewById(R.id.layout_find_des_yaoqing);
        tv_find_des_deadline = (TextView) findViewById(R.id.tv_find_des_deadline);
        tv_find_des_yuding = (TextView) findViewById(R.id.tv_find_des_yuding);
        tv_find_des_day = (TextView) findViewById(R.id.tv_find_des_day);
        tv_find_des_hour = (TextView) findViewById(R.id.tv_find_des_hour);
        tv_find_des_minute = (TextView) findViewById(R.id.tv_find_des_minute);
        tv_find_des_second = (TextView) findViewById(R.id.tv_find_des_second);
        tv_find_des_ydxz = (TextView) findViewById(R.id.tv_find_des_ydxz);
        tv_find_des_xffs = (TextView) findViewById(R.id.tv_find_des_xffs);
        tv_find_des_lxfs = (TextView) findViewById(R.id.tv_find_des_lxfs);
        tv_share_sina = (TextView) findViewById(R.id.tv_share_sina);
        tv_share_qq = (TextView) findViewById(R.id.tv_share_qq);
        tv_share_wx = (TextView) findViewById(R.id.tv_share_wx);
        tv_share_wxpyq = (TextView) findViewById(R.id.tv_share_wxpyq);
    }

    public void click(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_find_des_goods://商品详情
                intent = new Intent(this, HtmlTextActivity.class);
                intent.putExtra(HtmlTextActivity.CONTENT, info);
                intent.putExtra(HtmlTextActivity.TITLE, "商品详情");
                intent.putExtra(HtmlTextActivity.TYPE, HtmlTextActivity.TEXT);
                startActivity(intent);
                break;
            case R.id.tv_find_des_village://小区好友
                intent = new Intent(this, InvitefriendsActivity.class);
                intent.putExtra("goods_id", goods_id);
                startActivity(intent);
                break;
            case R.id.tv_find_des_invite://邀请好友
                if (layout_find_des_yaoqing.getVisibility() == View.VISIBLE) {
                    layout_find_des_yaoqing.setVisibility(View.GONE);
                } else {
                    layout_find_des_yaoqing.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_find_des_reserve://我要预定
                if (isMianFei) {
                    Map<String, String> params = new HashMap<>();
                    params.put("pay_way", "1");
                    params.put("member_id", AppConfig.loginInfo.getMember_id());
                    params.put("pay_type", "0");
                    params.put("goods_id", goods_id);
                    DialogUtil.startDialogLoading(this, false);
                    BasicRequest.getInstance().requestPost(this, TaskID.PAY, params, AppConfig.PAY);
                    return;
                }
                intent = new Intent(this, PayMentActivity.class);
                intent.putExtra(PayMentActivity.PARAMS_TITLE, "支付定金");
                intent.putExtra(PayMentActivity.PARAMS_PRICE, goods_dingjin);
                intent.putExtra(PayMentActivity.PARAMS_GOODS_ID, goods_id);
                intent.putExtra(PayMentActivity.PARAMS_PAYTYPE, "1");
                startActivityForResult(intent, 0);
                break;
        }
    }

    private void requestData() {
        DialogUtil.startDialogLoading(this, false);
        Map<String, String> params = new HashMap<>();
        params.put("goods_id", goods_id);
        params.put("latitude", MApplication.locationBD.getLatitude() + "");
        params.put("longitude", MApplication.locationBD.getLongitude() + "");
        BasicRequest.getInstance().requestPost(this, 0, params, AppConfig.FINDINFO, FindInfoModel.class);
    }

    private long differTime;
    private Timer mDjsTimer;
    private Timer mTpTimer;

    private int imagePosition = 0;

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        DialogUtil.stopDialogLoading(this);
        if (taskid == TaskID.PAY) {
            showToast("预定成功");
            finish();
            return;
        }
        final FindInfoModel.FindInfoModelResult result = ((FindInfoModel) obj).getDatas();
        mTpTimer = new Timer();
        mTpTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (result.getImages().isEmpty()) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String url = "";
                        try {
                            url = result.getImages().get(imagePosition);
                        } catch (Exception e) {
                            imagePosition = 0;
                            url = result.getImages().get(imagePosition);
                        }
                        Glide.with(context).load(url).into(image_item_find_topbg);
                    }
                });
                imagePosition++;
                if (imagePosition == result.getImages().size()) {
                    imagePosition = 0;
                }
            }
        }, 0, 3000);
        long nowTime = Long.parseLong(result.getBegin_time());
        long endTime = Long.parseLong(result.getEnd_time());
        differTime = endTime - nowTime;
        info = result.getInfo();
        tv_find_des_deadline.setText(DateUtil.getStringByFormat(endTime * 1000, DateUtil.dateFormatYMDHM));
        tv_find_des_yuding.setText(result.getOrder_num() + "人");
        tv_find_des_ydxz.setText(result.getMark());
        tv_find_des_xffs.setText(result.getBuy_way());
        tv_find_des_lxfs.setText("联系方式：" + result.getMobile());
        tv_item_find_title.setText(result.getStore_name());
        tv_item_find_address.setText(result.getAddress());
        tv_item_find_distance.setText(result.getMeters());
        goods_dingjin = result.getGoods_dingjin();
        if (TextUtils.isEmpty(goods_dingjin) || Double.parseDouble(result.getGoods_dingjin()) == 0) {//免费
            isMianFei = true;
            layout_find_des_daojishi.setVisibility(View.GONE);
            tv_item_find_price.setText("¥0.00");
            tv_item_find_top.setVisibility(View.VISIBLE);
            tv_item_find_top.setText("免费");
            btn_find_des_xianj.setText("¥0.00");
            btn_find_des_reserve.setText("我要预订");
        } else {
            isMianFei = false;
            tv_item_find_price.setText("¥" + result.getGoods_price() + " - ¥" + result.getGoods_baseprice());
            btn_find_des_xianj.setText("现价\n¥" + result.getGoods_price());
            btn_find_des_reserve.setText("我要预订\n¥" + result.getGoods_dingjin());
        }
        String share = result.getFenxiang_url();
        tv_share_sina.setOnClickListener(v -> {
            ShareUtils.getInstance(FindDesActivity.this).shareXlwb("发现分享：" + result.getStore_name(), result.getImages().get(0), share);
        });
        tv_share_qq.setOnClickListener(v -> {
            ShareUtils.getInstance(FindDesActivity.this).shareQq("发现分享", result.getStore_name(), result.getImages().get(0), share);
        });
        tv_share_wx.setOnClickListener(v -> {
            ShareUtils.getInstance(FindDesActivity.this).shareWx("发现分享", result.getStore_name(), result.getImages().get(0), share);
        });
        tv_share_wxpyq.setOnClickListener(v -> {
            ShareUtils.getInstance(FindDesActivity.this).shareWxPyq("发现分享：" + result.getStore_name(), result.getImages().get(0), share);
        });
        startDaoJiShi();
    }

    @Override
    public void onResponseError(String error, int taskid) {
        DialogUtil.stopDialogLoading(this);
        showToast(error);
    }

    private void startDaoJiShi() {
        if (mDjsTimer != null) {
            mDjsTimer.cancel();
        }
        mDjsTimer = new Timer();
        mDjsTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (differTime < 1) {
                    mDjsTimer.cancel();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            layout_find_des_daojishi.setVisibility(View.GONE);
                        }
                    });
                    return;
                }
                differTime -= 1;
                final String[] formatTime = DateUtil.formatTimeToSfm(differTime * 1000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (formatTime == null) {
                            layout_find_des_daojishi.setVisibility(View.GONE);
                        } else {
                            tv_find_des_day.setText(formatTime[0]);
                            tv_find_des_hour.setText(formatTime[1]);
                            tv_find_des_minute.setText(formatTime[2]);
                            tv_find_des_second.setText(formatTime[3]);
                        }
                    }
                });
            }
        }, 1000, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDjsTimer != null) {
            mDjsTimer.cancel();
        }
        if (mTpTimer != null) {
            mTpTimer.cancel();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMSsoHandler ssoHandler = UMServiceFactory
                .getUMSocialService("com.umeng.share").getConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
        switch (resultCode) {
            case RESULT_OK:
                finish();
                break;
        }
    }
}
