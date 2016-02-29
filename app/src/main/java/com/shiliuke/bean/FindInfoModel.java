package com.shiliuke.bean;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wangzhi on 15/11/24.
 */
public class FindInfoModel extends BaseModel {
    private FindInfoModelResult datas;

    public FindInfoModelResult getDatas() {
        return datas;
    }

    public class FindInfoModelResult implements Serializable{
        private String goods_id;//商品ID
        private String end_time;//截止时间
        private String begin_time;//服务器时间
        private String order_num;//预定人数
        private String info;//商品详情
        private String mark;//预定须知
        private String buy_way;//收货方式
        private String mobile;//联系电话
        private String goods_price;//发现里是现价，订单里是定价
        private String goods_oprice;//订单里的最高价
        private String goods_baseprice;//订单里的最低价
        private String goods_dingjin;//订金
        private String goods_weikuan;//订单里的尾款
        private String image_url;//背景图
        private String goods_name;//商品名称
        private String end_status;//0:未结束；1：已结束
        private String fenxiang_url;//分享url
        private ArrayList<String> images;//图片list
        private String store_name;//团购名称
        private String address;//团购地址
        private String meters;//距离

        public String getEnd_status() {
            return end_status;
        }

        public String getStore_name() {
            return store_name;
        }

        public String getAddress() {
            return address;
        }

        public String getMeters() {
            return meters;
        }

        public String getFenxiang_url() {
            return fenxiang_url;
        }

        public boolean isGoodEnd() {
            if (TextUtils.isEmpty(end_status)) {
                return true;
            }
            return "1".equalsIgnoreCase(end_status);
        }

        public String getGoods_baseprice() {
            return goods_baseprice;
        }

        public String getGoods_oprice() {
            return goods_oprice;
        }

        public String getGoods_weikuan() {
            return goods_weikuan;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public String getImage_url() {
            return image_url;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public ArrayList<String> getImages() {
            return images;
        }

        public String getEnd_time() {
            return end_time;
        }

        public String getOrder_num() {
            return order_num;
        }

        public String getInfo() {
            return info;
        }

        public String getMark() {
            return mark;
        }

        public String getBuy_way() {
            return buy_way;
        }

        public String getMobile() {
            return mobile;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public String getGoods_dingjin() {
            return goods_dingjin;
        }

        public String getBegin_time() {
            return begin_time;
        }
    }
}
