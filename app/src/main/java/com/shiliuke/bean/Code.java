package com.shiliuke.bean;

import java.util.List;

/**
 * Created by wupeitao on 15/11/11.
 */
public class Code {
    private String code;
    private List<AdvertisementList> advertisementList;
    private List<Exercise> exercise;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public List<AdvertisementList> getAdvertisementList() {
        return advertisementList;
    }

    public void setAdvertisementList(List<AdvertisementList> advertisementList) {
        this.advertisementList = advertisementList;
    }

    public List<Exercise> getExercise() {
        return exercise;
    }

    public void setExercise(List<Exercise> exercise) {
        this.exercise = exercise;
    }
}
