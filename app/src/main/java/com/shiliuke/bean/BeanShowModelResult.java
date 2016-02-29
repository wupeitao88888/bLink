package com.shiliuke.bean;

import com.shiliuke.view.stickerview.StickerImageModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wangzhi on 15/11/14.
 */
public class BeanShowModelResult implements Serializable {
    public BeanShowModelResult() {
    }

    private String xiu_id;//
    private String member_id;//发布人Id
    private String member_avar;//发布人头像地址
    private String member_name;//发布人姓名
    private String home;//发布人小区
    private String time;//发布时间
    private String info;//msg
    private String image_url;//秀逗图片地址
    private boolean isZan;//
    private String xiu_num;//未读评论数
    private String fenxiang_url;//分享url
    private ArrayList<StickerImageModel> commend_list;//评论list
    private ArrayList<StickerImageModel> zan_list;//赞list
    public int getXiu_num() {
        int num;
        try{
            num = Integer.parseInt(xiu_num);
        }catch (Exception e){
            num = 0;
        }

        return num;
    }

    public void setXiu_num(String xiu_num) {
        this.xiu_num = xiu_num;
    }
    //    private boolean canAnim = true;//
//    private boolean isAniming = false;//

//    public void revertAlpha() {（弃用）
//        if (commend_list == null || commend_list.isEmpty()) {
//            return;
//        }
//        for (int i = 0; i < commend_list.size(); i++) {
//            commend_list.get(i).setAlpha(StickerImageContans.MAXALPHA);
//        }
//    }

//    public void setCanAnim(boolean canAnim) {
//        this.canAnim = canAnim;
//    }
//
//    public boolean isCanAnim() {
//
//        return canAnim;
//    }
//
//    public boolean isAniming() {
//        return isAniming;
//    }
//
//    public void setIsAniming(boolean isAniming) {
//        this.isAniming = isAniming;
//    }


    public String getFenxiang_url() {
        return fenxiang_url;
    }

    public String getXiu_id() {
        return xiu_id;
    }

    public void setXiu_id(String xiu_id) {
        this.xiu_id = xiu_id;
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

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String add_time) {
        this.time = add_time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public ArrayList<StickerImageModel> getCommend_list() {
        return commend_list;
    }

    public boolean isZan() {
        return isZan;
    }

    public void setIsZan(boolean isZan) {
        this.isZan = isZan;
    }

    public ArrayList<StickerImageModel> getZan_list() {
        return zan_list;
    }

    public void setZan_list(ArrayList<StickerImageModel> zan_list) {
        this.zan_list = zan_list;
    }

    public void setCommend_list(ArrayList<StickerImageModel> commend_list) {
        this.commend_list = commend_list;
    }
}