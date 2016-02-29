package com.shiliuke.bean;

/**
 * Created by wangzhi on 15/11/12.
 */
public class PayItem {
    private ZfbPayItem zfb_pay;
    private WxPayItem weixin_pay;

    public ZfbPayItem getZfb_pay() {
        return zfb_pay;
    }

    public WxPayItem getWeixin_pay() {
        weixin_pay.packageValue = "Sign=WXPay";
        return weixin_pay;
    }

    public class WxPayItem {
        public String sign;
        public String appid;
        public long timestamp;
        public String noncestr;
        public String partnerid;
        public String prepayid;
        public String packageValue;
    }

    public class ZfbPayItem {
        public String body;
        public String out_trade_no;
        public String subject;
        public String total_fee;
        public boolean isDingjin;
    }
}
