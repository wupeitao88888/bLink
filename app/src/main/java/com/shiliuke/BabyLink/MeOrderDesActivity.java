package com.shiliuke.BabyLink;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.PayEnd;

public class MeOrderDesActivity extends ActivitySupport {
    public enum ORDERTYPE {
        DINGJIN,
        ALL,
        REFUND
    }

    private ImageView iamge_order_des_goodsbg;//团购图片
    private ImageView iamge_order_des_status;//订单状态
    private TextView tv_order_des_name;//团购名
    private TextView tv_order_des_dingjin;//定价（只有退款才显示，默认不显示）
    private TextView tv_order_des_refund_title;//支付情况
    private View layout_order_des_refund;//退款布局（只有退款才显示，默认不显示）
    private TextView tv_order_des_refund_num;//退款金额
    private View layout_order_des_use;//消费码布局（只有已付全款才显示，默认不显示）
    private TextView tv_order_des_usecode;//消费码
    private TextView tv_order_des_orderno;//订单编号
    private TextView tv_order_des_payno;//支付单号
    private TextView tv_order_des_paytime;//下单时间
    private PayEnd.PayEndResult modle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_order_des);
        initView();
        initData();
    }

    private void initData() {
        modle = (PayEnd.PayEndResult) getIntent().getSerializableExtra("model");
        if (modle == null) {
            finish();
            return;
        }
        switch ((ORDERTYPE) getIntent().getSerializableExtra("type")) {
            case DINGJIN:
                setCtenterTitle("已付定金");
                iamge_order_des_status.setImageResource(R.mipmap.icon_order_noweikuan);
                tv_order_des_refund_title.setText("¥" + modle.getGoods().getGoods_dingjin());
                break;
            case ALL:
                setCtenterTitle("已付全款");
                if ("7".equalsIgnoreCase(modle.getOrder_status())) {
                    iamge_order_des_status.setImageResource(R.mipmap.icon_order_user);
                } else {
                    iamge_order_des_status.setImageResource(R.mipmap.icon_order_nouse);
                }
                tv_order_des_refund_title.setText("¥" + modle.getGoods().getGoods_price());
                layout_order_des_use.setVisibility(View.VISIBLE);
                tv_order_des_usecode.setText(modle.getOrder_code());
                break;
            case REFUND:
                setCtenterTitle("已退款");
                if ("6".equalsIgnoreCase(modle.getOrder_status())) {
                    iamge_order_des_status.setImageResource(R.mipmap.icon_order_refunding);
                } else {
                    iamge_order_des_status.setImageResource(R.mipmap.icon_order_refunded);
                }
                layout_order_des_refund.setVisibility(View.VISIBLE);
                tv_order_des_dingjin.setVisibility(View.VISIBLE);
                tv_order_des_dingjin.setText("定价：" + modle.getGoods().getGoods_price());
                if (modle.isRefundDingjin()) {
                    tv_order_des_refund_title.setText("订金：¥" + modle.getGoods().getGoods_dingjin());
                    tv_order_des_refund_num.setText("¥" + modle.getGoods().getGoods_dingjin());
                } else {
                    tv_order_des_refund_title.setText("尾款：¥" + modle.getGoods().getGoods_weikuan());
                    tv_order_des_refund_num.setText("¥" + modle.getGoods().getGoods_weikuan());
                }
                break;
        }
        Glide.with(this).load(modle.getGoods().getImage_url()).into(iamge_order_des_goodsbg);
        tv_order_des_name.setText(modle.getGoods().getGoods_name());
        tv_order_des_orderno.setText("订单编号：" + modle.getOrder_no());
        tv_order_des_paytime.setText("下单时间：" + modle.getAdd_time());
    }

    private void initView() {
        iamge_order_des_goodsbg = (ImageView) findViewById(R.id.iamge_order_des_goodsbg);
        iamge_order_des_status = (ImageView) findViewById(R.id.iamge_order_des_status);
        tv_order_des_name = (TextView) findViewById(R.id.tv_order_des_name);
        tv_order_des_dingjin = (TextView) findViewById(R.id.tv_order_des_dingjin);
        tv_order_des_refund_title = (TextView) findViewById(R.id.tv_order_des_refund_title);

        layout_order_des_refund = findViewById(R.id.layout_order_des_refund);
        tv_order_des_refund_num = (TextView) findViewById(R.id.tv_order_des_refund_num);

        layout_order_des_use = findViewById(R.id.layout_order_des_use);
        tv_order_des_usecode = (TextView) findViewById(R.id.tv_order_des_usecode);

        tv_order_des_orderno = (TextView) findViewById(R.id.tv_order_des_orderno);
        tv_order_des_payno = (TextView) findViewById(R.id.tv_order_des_payno);
        tv_order_des_paytime = (TextView) findViewById(R.id.tv_order_des_paytime);

    }

}
