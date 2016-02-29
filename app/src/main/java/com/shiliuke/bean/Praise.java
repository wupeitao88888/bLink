package com.shiliuke.bean;

import java.io.Serializable;

/**
 * Created by wupeitao on 15/11/18.
 */
public class Praise implements Serializable {
    private String zan_id;
    private String talk_id;
    private String member_id;
    private String member_name;
    private String add_time;

    public Praise() {
    }

    public Praise(String zan_id, String talk_id, String member_id, String member_name, String add_time) {
        this.zan_id = zan_id;
        this.talk_id = talk_id;
        this.member_id = member_id;
        this.member_name = member_name;
        this.add_time = add_time;
    }

    public String getZan_id() {
        return zan_id;
    }

    public void setZan_id(String zan_id) {
        this.zan_id = zan_id;
    }

    public String getTalk_id() {
        return talk_id;
    }

    public void setTalk_id(String talk_id) {
        this.talk_id = talk_id;
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

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
