package com.shiliuke.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wpt on 2015/10/28.
 */
public class Change extends BaseModel {
    private ArrayList<Data> datas;

    public ArrayList<Data> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<Data> datas) {
        this.datas = datas;
    }

    public class Data implements Serializable{
        private String exchange_id;//
        private String member_id;//置换人id
        private String member_name;//置换人name
        private String member_avar;//置换人头像
        private String home;//置换人小区
        private String image_url;//置换图片
        private String from_gname;//物品名称
        private String to_gname;//想要换得物品名称
        private String info;//商品描述
        private String add_time;//
        private String ing_time;//
        private String end_time;//
        private String role;//
        private String link_exchange_id;//
//    private String changeName;//交换人姓名
//    private String changAddress;//交换地址
//    private String changePic;//交换人头像
//    private String changeUrl;//交换图片
//    private String mineGoods;//我的物品
//    private String exchangeGoods;//换的物品


        public String getExchange_id() {
            return exchange_id;
        }

        public void setExchange_id(String exchange_id) {
            this.exchange_id = exchange_id;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getMember_avar() {
            return member_avar;
        }

        public void setMember_avar(String member_avar) {
            this.member_avar = member_avar;
        }

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getFrom_gname() {
            return from_gname;
        }

        public void setFrom_gname(String from_gname) {
            this.from_gname = from_gname;
        }

        public String getTo_gname() {
            return to_gname;
        }

        public void setTo_gname(String to_gname) {
            this.to_gname = to_gname;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getIng_time() {
            return ing_time;
        }

        public void setIng_time(String ing_time) {
            this.ing_time = ing_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getLink_exchange_id() {
            return link_exchange_id;
        }

        public void setLink_exchange_id(String link_exchange_id) {
            this.link_exchange_id = link_exchange_id;
        }
    }
}
