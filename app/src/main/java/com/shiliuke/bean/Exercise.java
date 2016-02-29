package com.shiliuke.bean;

import java.util.List;

/**
 * Created by wpt on 2015/10/26.
 */
public class Exercise {
    private String activity_id;//活动id
    private String image_url;//活动图片
    private String title;//活动标题
    private String activity_address;//活动地点
    private String count;//活动人数
    private String jihe_time;//活动时间
    private String member_name;//发起人
    private String member_avar;//发起人头像
    private List<UserInfo> userInfos;
    private String isOut;//是否过期
    private boolean isSign;//是否报名
    private boolean isFull;//报名是否已满

    public boolean isSign() {
        return isSign;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setIsFull(boolean isFull) {
        this.isFull = isFull;
    }

    public Exercise(String activity_id, String image_url, String title, String activity_address, String count, String jihe_time, String member_name, String member_avar, List<UserInfo> userInfos) {
        this.activity_id = activity_id;
        this.image_url = image_url;
        this.title = title;
        this.activity_address = activity_address;
        this.count = count;
        this.jihe_time = jihe_time;
        this.member_name = member_name;
        this.member_avar = member_avar;
        this.userInfos = userInfos;
    }

    public String getIsOut() {
        return isOut;
    }

    public void setIsOut(String isOut) {
        this.isOut = isOut;
    }

    public boolean getIsSign() {
        return isSign;
    }

    public void setIsSign(boolean isSign) {
        this.isSign = isSign;
    }

    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getJihe_time() {
        return jihe_time;
    }

    public void setJihe_time(String jihe_time) {
        this.jihe_time = jihe_time;
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

    public Exercise() {
    }
}
