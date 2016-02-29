package com.shiliuke.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wpt on 2015/10/27.
 */
public class Topic {
    private String code;
    private List<TopicInfo> datas;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<TopicInfo> getDates() {
        return datas;
    }

    public void setDates(List<TopicInfo> dates) {
        this.datas = dates;
    }

    public Topic(String code, List<TopicInfo> dates) {
        this.code = code;
        this.datas = dates;
    }

    public Topic() {
    }
}
