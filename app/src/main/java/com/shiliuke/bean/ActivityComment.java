package com.shiliuke.bean;

import java.io.Serializable;

/**
 * *
 * Created by wpt on 2015/10/28.
 */
public class ActivityComment implements Serializable {
   private String to_userId;
    private String from_userAvar;
    private String commend_id;
    private String to_userName;
    private String activity_id;
    private String from_userName;
    private String from_userId;
    private String add_time;
    private String info;

    public String getTo_userId() {
        return to_userId;
    }

    public void setTo_userId(String to_userId) {
        this.to_userId = to_userId;
    }

    public String getFrom_userAvar() {
        return from_userAvar;
    }

    public void setFrom_userAvar(String from_userAvar) {
        this.from_userAvar = from_userAvar;
    }

    public String getCommend_id() {
        return commend_id;
    }

    public void setCommend_id(String commend_id) {
        this.commend_id = commend_id;
    }

    public String getTo_userName() {
        return to_userName;
    }

    public void setTo_userName(String to_userName) {
        this.to_userName = to_userName;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public String getFrom_userName() {
        return from_userName;
    }

    public void setFrom_userName(String from_userName) {
        this.from_userName = from_userName;
    }

    public String getFrom_userId() {
        return from_userId;
    }

    public void setFrom_userId(String from_userId) {
        this.from_userId = from_userId;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
