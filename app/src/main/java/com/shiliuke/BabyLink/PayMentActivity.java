package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import com.google.gson.Gson;
import com.shiliuke.BabyLink.alipay.AliPayUtil;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.PayItem;
import com.shiliuke.global.AppConfig;
import com.shiliuke.global.MApplication;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.utils.L;
import com.squareup.otto.Subscribe;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付页
 * Created by wupeitao on 15/11/8.
 */
public class PayMentActivity extends ActivitySupport implements VolleyListerner {
    private IWXAPI mWXApi;
    public static final String PARAMS_TITLE = "params_title";//必传
    public static final String PARAMS_PRICE = "params_price";//必传
    public static final String PARAMS_GOODS_ID = "goods_id";//必传
    public static final String PARAMS_PAYTYPE = "paytype";//必传
    public static final String PARAMS_ORDER_ID = "order_id";//可不传
    private TextView tv_payment_paytitle;
    private TextView tv_payment_price;
    private RadioButton radio_payment_weixin;//微信支付

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mWXApi = WXAPIFactory.createWXAPI(this, AppConfig.WX_APP_ID);
        mWXApi.registerApp(AppConfig.WX_APP_ID);
        MApplication.getInstance().getBus().register(this);
        setCtenterTitle("支付");
        initView();
        initData();
    }

    private void initView() {
        tv_payment_paytitle = (TextView) findViewById(R.id.tv_payment_paytitle);
        tv_payment_price = (TextView) findViewById(R.id.tv_payment_price);
        radio_payment_weixin = (RadioButton) findViewById(R.id.radio_payment_weixin);
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        tv_payment_paytitle.setText(intent.getStringExtra(PARAMS_TITLE));
        tv_payment_price.setText(intent.getStringExtra(PARAMS_PRICE));
    }


    /**
     * 微信支付
     *
     * @param item
     */
    protected void wxPayment(PayItem.WxPayItem item) {
        if (item == null) {
//            showToast("参数为空");
            return;
        }
        PayReq req = new PayReq();
        req.appId = AppConfig.WX_APP_ID;
        req.packageValue = item.packageValue;
        req.partnerId = item.partnerid;
        req.nonceStr = item.noncestr;
        req.prepayId = item.prepayid;
        req.timeStamp = item.timestamp+"";
        req.sign = item.sign;
        mWXApi.sendReq(req);
    }


    public void okClick(View v) {
        DialogUtil.startDialogLoading(this);
        Map<String, String> params = new HashMap<>();
        params.put("pay_way", radio_payment_weixin.isChecked() ? "2" : "1");
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        params.put("pay_type", getIntent().getStringExtra(PARAMS_PAYTYPE));
        params.put("goods_id", getIntent().getStringExtra(PARAMS_GOODS_ID));
        String order_id = getIntent().getStringExtra(PARAMS_ORDER_ID);
        if (!TextUtils.isEmpty(order_id)) {
            params.put("order_id", order_id);
        }
        BasicRequest.getInstance().requestPost(this, 0, params, AppConfig.PAY);
    }


    @Override
    public void onResponse(String str, int taskid, Object obj) {
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(str.trim(), Map.class);
        if (!radio_payment_weixin.isChecked()) {//支付宝
            PayItem.ZfbPayItem zfb = gson.fromJson(map.get("datas").toString(), PayItem.ZfbPayItem.class);
            zfb.isDingjin = "1".equalsIgnoreCase(getIntent().getStringExtra(PARAMS_PAYTYPE));
            AliPayUtil.getInstance(this).aliPay(zfb);
        } else {//微信
            PayItem.WxPayItem wx = gson.fromJson(map.get("datas").toString(), PayItem.WxPayItem.class);
            wx.packageValue = "Sign=WXPay";
            wxPayment(wx);
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        DialogUtil.stopDialogLoading(this);
        showToast(error);
    }

    @Subscribe
    public void handleMessage(Intent intent) {
        DialogUtil.stopDialogLoading(this);
        L.d(intent.getAction());
        switch (intent.getAction()) {
            case AppConfig.ACTION_ZHIFUBAO_SUCCESS:
            case AppConfig.ACTION_WXPAY_SUCCESS:
                showToast("订单支付成功，可在我的订单中查看");
                setResult(RESULT_OK);
                finish();
                break;
            case AppConfig.ACTION_ZHIFUBAO_FAILD:
                showToast("支付宝支付失败");
                break;
            case AppConfig.ACTION_ZHIFUBAO_CHECKING:
                showToast("支付结果确认中");
                break;
            case AppConfig.ACTION_WXPAY_FAILD:
                showToast("微信支付失败");
                break;
            case AppConfig.ACTION_WXPAY_UNSUPPORT:
                showToast("您的微信不支持支付");
                break;
            case AppConfig.ACTION_WXPAY_CANCLE:
                showToast("微信支付取消");
                break;

        }
//        if (AppConfig.ACTION_WXSHARE_SUCCESS.equalsIgnoreCase(intent.getAction())) {
//            showToast("分享成功");
//        } else if (AppConfig.ACTION_WXSHARE_FAILD.equalsIgnoreCase(intent.getAction())) {
//            showToast("分享失败");
//        }
    }

    @Override
    protected void onDestroy() {
        MApplication.getInstance().getBus().unregister(this);
        mWXApi.unregisterApp();
        super.onDestroy();
    }
}
