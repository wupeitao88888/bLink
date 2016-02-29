package com.shiliuke.bean;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 支付尾款
 * Created by wupeitao on 15/11/7.
 */
public class PayEnd {
    private ArrayList<PayEndResult> datas;

    public ArrayList<PayEndResult> getDatas() {
        return datas;
    }

    public class PayEndResult implements Serializable {
        private String order_no;//订单号
        private String order_id;//支付尾款id
        private String order_status;//订单状态
        private String add_time;//下单时间
        private String refund_status;//退款类型_0:定金——1：尾款
        private String order_code;//消费码
        private FindInfoModel.FindInfoModelResult goods;//商品信息

        public String getOrder_code() {
            return order_code;
        }

        public boolean isRefundDingjin() {
            if (TextUtils.isEmpty(refund_status)) {
                return true;
            }
            return "0".equalsIgnoreCase(refund_status);
        }

        public String getOrder_id() {
            return order_id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public String getOrder_status() {
            return order_status;
        }

        public String getAdd_time() {
            return add_time;
        }

        public FindInfoModel.FindInfoModelResult getGoods() {
            return goods;
        }
    }
}
