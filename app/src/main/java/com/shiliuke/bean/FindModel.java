package com.shiliuke.bean;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wangzhi on 15/11/8.
 */
public class FindModel extends BaseModel implements Serializable {
    private Datas datas;

    public Datas getDatas() {
        return datas;
    }

    public class Datas {
        private ArrayList<FindModelResult> list;
        private String yaoqing_num;//邀请数量

        public String getYaoqing_num() {
            if (TextUtils.isEmpty(yaoqing_num)) {
                return "";
            }
            try {
                if (Integer.parseInt(yaoqing_num) == 0) {
                    return "";
                }
            } catch (Exception e) {
                return "";
            }
            return yaoqing_num;
        }

        public ArrayList<FindModelResult> getList() {
            return list;
        }
    }


    public class FindModelResult implements Serializable {
        private String goods_dingjin;//定价
        private String image_url;//背景图
        private String store_name;//团购名称
        private String address;//团购地址
        private String meters;//距离
        private String goods_price;//最高价
        private String goods_baseprice;//最低价
        private String goods_id;//商品id
        private String member_avar;//被邀请——邀请人头像
        private String member_name;//被邀请——邀请人姓名

        public String getMember_avar() {
            return member_avar;
        }

        public String getMember_name() {
            return member_name;
        }

        public String getGoods_dingjin() {
            return goods_dingjin;
        }

        public String getImage_url() {
            return image_url;
        }

        public String getStore_name() {
            return store_name;
        }

        public String getAddress() {
            return address;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public String getGoods_baseprice() {
            return goods_baseprice;
        }

        public String getMeters() {
            return meters;
        }

        public String getGoods_id() {
            return goods_id;
        }
    }
}
