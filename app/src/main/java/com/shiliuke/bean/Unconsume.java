package com.shiliuke.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wupeitao on 15/11/5.
 */
public class Unconsume extends BaseModel{

    private List<Data> datas;

    public List<Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }

    public class Data implements Serializable{

        private String dingjin_time;
        private String qrcode;
        private String goods_id;
        private Goods goods;
        private String add_time;
        private String order_id;
        private String order_status;
        private String order_code;
        private String order_no;

        public String getDingjin_time() {
            return dingjin_time;
        }

        public void setDingjin_time(String dingjin_time) {
            this.dingjin_time = dingjin_time;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public Goods getGoods() {
            return goods;
        }

        public void setGoods(Goods goods) {
            this.goods = goods;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public class Goods implements Serializable{

            private String goods_dingjin;
            private String end_status_desc;
            private String goods_price;
            private String end_status;
            private String image_url;
            private String use_time;
            private String goods_id;
            private String goods_name;
            private String goods_oprice;
            private String goods_baseprice;
            private String goods_weikuan;

            public String getGoods_dingjin() {
                return goods_dingjin;
            }

            public void setGoods_dingjin(String goods_dingjin) {
                this.goods_dingjin = goods_dingjin;
            }

            public String getEnd_status_desc() {
                return end_status_desc;
            }

            public void setEnd_status_desc(String end_status_desc) {
                this.end_status_desc = end_status_desc;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public String getEnd_status() {
                return end_status;
            }

            public void setEnd_status(String end_status) {
                this.end_status = end_status;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }

            public String getUse_time() {
                return use_time;
            }

            public void setUse_time(String use_time) {
                this.use_time = use_time;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_oprice() {
                return goods_oprice;
            }

            public void setGoods_oprice(String goods_oprice) {
                this.goods_oprice = goods_oprice;
            }

            public String getGoods_baseprice() {
                return goods_baseprice;
            }

            public void setGoods_baseprice(String goods_baseprice) {
                this.goods_baseprice = goods_baseprice;
            }

            public String getGoods_weikuan() {
                return goods_weikuan;
            }

            public void setGoods_weikuan(String goods_weikuan) {
                this.goods_weikuan = goods_weikuan;
            }
        }
    }
}
