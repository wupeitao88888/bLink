package com.shiliuke.bean;

import java.io.Serializable;

/**
 * Created by wpt on 2015/9/25.
 */
public class AdvertisementList implements Serializable {
    private  String id;
    private String  url;
    private String title;
    private String cover;
    private String home;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AdvertisementList() {

    }

    public AdvertisementList(String id, String url, String title, String cover, String home) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.cover = cover;
        this.home = home;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }
}
