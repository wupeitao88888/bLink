package com.shiliuke.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 活动详情 ————————————今天双十一妈的！马云你还我钱
 * Created by wupeitao on 15/11/11.
 */
public class ActivityInfo implements Serializable {
    private String code;
    private Dates datas;

    public ActivityInfo() {
    }

    public Dates getDates() {
        return datas;
    }

    public void setDates(Dates datas) {
        this.datas = datas;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public class Dates implements Serializable {
        private String activity_id;
        private String member_id;
        private String member_name;
        private String member_avar;
        private String title;
        private String activity_address;
        private String begin_time;
        private String end_time;
        private String link_name;
        private String link_mobile;
        private String jihe_address;
        private String jihe_time;
        private String utils;
        private String max_man;
        private String count;
        private String price;
        private String pay_way;
        private String home;
        private String info;
        private String is_help;
        private String help;
        private String fenxiang_url;
        private String isOut;//是否过期
        private boolean is_end;//报名是否截止
        private boolean isSign;//是否已经报名
        private List<String> images;
        private List<UserInfo> log_list;
        private List<ActivityComment> commend_list;

        public String getFenxiang_url() {
            return fenxiang_url;
        }

        public boolean isSign() {
            return isSign;
        }

        public void setIsSign(boolean isSign) {
            this.isSign = isSign;
        }

        public void setIs_end(boolean is_end) {
            this.is_end = is_end;
        }

        public boolean is_end() {
            return is_end;
        }

        public String getIsOut() {
            return isOut;
        }

        public void setIsOut(String isOut) {
            this.isOut = isOut;
        }

        public String getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getActivity_address() {
            return activity_address;
        }

        public void setActivity_address(String activity_address) {
            this.activity_address = activity_address;
        }

        public String getBegin_time() {
            return begin_time;
        }

        public void setBegin_time(String begin_time) {
            this.begin_time = begin_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getLink_name() {
            return link_name;
        }

        public void setLink_name(String link_name) {
            this.link_name = link_name;
        }

        public String getLink_mobile() {
            return link_mobile;
        }

        public void setLink_mobile(String link_mobile) {
            this.link_mobile = link_mobile;
        }

        public String getJihe_address() {
            return jihe_address;
        }

        public void setJihe_address(String jihe_address) {
            this.jihe_address = jihe_address;
        }

        public String getJihe_time() {
            return jihe_time;
        }

        public void setJihe_time(String jihe_time) {
            this.jihe_time = jihe_time;
        }

        public String getUtils() {
            return utils;
        }

        public void setUtils(String utils) {
            this.utils = utils;
        }

        public String getMax_man() {
            return max_man;
        }

        public void setMax_man(String max_man) {
            this.max_man = max_man;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPay_way() {
            return pay_way;
        }

        public void setPay_way(String pay_way) {
            this.pay_way = pay_way;
        }

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getIs_help() {
            return is_help;
        }

        public void setIs_help(String is_help) {
            this.is_help = is_help;
        }

        public String getHelp() {
            return help;
        }

        public void setHelp(String help) {
            this.help = help;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public List<UserInfo> getLog_list() {
            return log_list;
        }

        public void setLog_list(List<UserInfo> log_list) {
            this.log_list = log_list;
        }

        public List<ActivityComment> getCommend_list() {
            return commend_list;
        }

        public void setCommend_list(List<ActivityComment> commend_list) {
            this.commend_list = commend_list;
        }
    }
}
