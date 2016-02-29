package com.shiliuke.bean;

/**
 * Created by wangzhi on 15/11/12.
 */
public class WXPayItem {
    public WXPayItem(String sign, String timestamp, String noncestr, String partnerid, String prepayid, String packageValue) {
        this.sign = sign;
        this.timestamp = timestamp;
        this.noncestr = noncestr;
        this.partnerid = partnerid;
        this.prepayid = prepayid;
        this.packageValue = packageValue;
    }

    public String sign;
    public String timestamp;
    public String noncestr;
    public String partnerid;
    public String prepayid;
    public String packageValue;
}
