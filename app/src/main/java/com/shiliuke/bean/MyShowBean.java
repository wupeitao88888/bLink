package com.shiliuke.bean;

/**
 *我的秀逗
 * Created by wupeitao on 15/11/5.
 */
public class MyShowBean {
    private String sbean_pic;//秀逗图片
    private String sbean_name;//秀逗title
    private String sbean_status;//秀逗状态


    public MyShowBean(String sbean_pic, String sbean_name, String sbean_status) {
        this.sbean_pic = sbean_pic;
        this.sbean_name = sbean_name;
        this.sbean_status = sbean_status;

    }

    public MyShowBean() {
    }

    public String getSbean_pic() {
        return sbean_pic;
    }

    public void setSbean_pic(String sbean_pic) {
        this.sbean_pic = sbean_pic;
    }

    public String getSbean_name() {
        return sbean_name;
    }

    public void setSbean_name(String sbean_name) {
        this.sbean_name = sbean_name;
    }



    public String getSbean_status() {
        return sbean_status;
    }

    public void setSbean_status(String sbean_status) {
        this.sbean_status = sbean_status;
    }
}
