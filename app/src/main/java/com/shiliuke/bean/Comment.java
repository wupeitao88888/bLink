package com.shiliuke.bean;

import java.io.Serializable;

/**
 * *
 * Created by wpt on 2015/10/28.
 */
public class Comment implements Serializable {
    private String commend_id;
    private String activity_id;
    private String member_id;
    private String add_time;
    private String member_name;
    private String member_avar;
    private String info;//与
    private String talk_id;
    private String from_id;
    private String from_name;//评论者
    private String to_id;
    private String to_name; //接收评论者"

    public Comment(String commend_id, String activity_id, String member_id, String add_time, String member_name, String member_avar, String info, String talk_id, String from_id, String from_name, String to_id, String to_name) {
        this.commend_id = commend_id;
        this.activity_id = activity_id;
        this.member_id = member_id;
        this.add_time = add_time;
        this.member_name = member_name;
        this.member_avar = member_avar;
        this.info = info;
        this.talk_id = talk_id;
        this.from_id = from_id;
        this.from_name = from_name;
        this.to_id = to_id;
        this.to_name = to_name;
    }

    public String getTalk_id() {
        return talk_id;
    }

    public void setTalk_id(String talk_id) {
        this.talk_id = talk_id;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getTo_id() {
        return to_id;
    }

    public void setTo_id(String to_id) {
        this.to_id = to_id;
    }

    public String getTo_name() {
        return to_name;
    }

    public void setTo_name(String to_name) {
        this.to_name = to_name;
    }

    public Comment(String commend_id, String activity_id, String member_id, String add_time, String member_name, String member_avar, String info) {
        this.commend_id = commend_id;
        this.activity_id = activity_id;
        this.member_id = member_id;
        this.add_time = add_time;
        this.member_name = member_name;
        this.member_avar = member_avar;
        this.info = info;
    }

    public Comment() {
    }

    public String getCommend_id() {
        return commend_id;
    }

    public void setCommend_id(String commend_id) {
        this.commend_id = commend_id;
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

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
