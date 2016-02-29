package com.shiliuke.bean;

import java.io.Serializable;

/**
 * Created by wupeitao on 15/10/26.
 */
public class UserInfo implements Serializable {
    private String member_avar;
    private String member_name;
    private String member_id;
    private String member_mobile;
    private String num;

    public String getNum() {
        return num;
    }

    public String getMember_mobile() {
        return member_mobile;
    }

    public String getMember_name() {
        if (null == member_name || "null".equalsIgnoreCase(member_name)) {
            return "";
        }
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_avar() {
        return member_avar;
    }

    public void setMember_avar(String member_avar) {
        this.member_avar = member_avar;
    }


    public UserInfo(String member_avar, String member_name, String member_id) {
        this.member_avar = member_avar;
        this.member_name = member_name;
        this.member_id = member_id;
    }

    public UserInfo(String member_avar) {
        this.member_avar = member_avar;
    }

    public UserInfo() {
    }


}
