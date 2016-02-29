package com.shiliuke.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wupeitao on 15/11/18.
 */
public class TopicInfo implements Serializable {
    private String talk_id;
    private String member_id;
    private String member_name;
    private String member_avar;
    private String home;
    private String add_time;
    private String info;
    private String images;
    private String talk_num;//未读数量
    private List<String> images_url;
    private List<Praise> zan_list;
    private List<Comment> commend_list;

    public int getTalk_num() {
        int num;
        try{
            num = Integer.parseInt(talk_num);
        }catch (Exception e){
            num = 0;
        }

        return num;
    }

    public void setTalk_num(String talk_num) {
        this.talk_num = talk_num;
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public List<String> getImages_url() {
        return images_url;
    }

    public void setImages_url(List<String> images_url) {
        this.images_url = images_url;
    }

    public List<Praise> getZan_list() {
        return zan_list;
    }

    public void setZan_list(List<Praise> zan_list) {
        this.zan_list = zan_list;
    }

    public List<Comment> getCommend_list() {
        return commend_list;
    }

    public void setCommend_list(List<Comment> commend_list) {
        this.commend_list = commend_list;
    }

    public TopicInfo(String talk_id, String member_id, String member_name, String member_avar, String home, String add_time, String info, String images, List<String> images_url, List<Praise> zan_list, List<Comment> commend_list) {
        this.talk_id = talk_id;
        this.member_id = member_id;
        this.member_name = member_name;
        this.member_avar = member_avar;
        this.home = home;
        this.add_time = add_time;
        this.info = info;
        this.images = images;
        this.images_url = images_url;
        this.zan_list = zan_list;
        this.commend_list = commend_list;
    }

    public TopicInfo() {
    }
}
