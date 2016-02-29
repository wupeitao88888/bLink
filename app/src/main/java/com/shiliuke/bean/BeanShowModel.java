package com.shiliuke.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 秀逗页面list实体
 * Created by wangzhi on 15/10/30.
 */
public class BeanShowModel implements Serializable {
    private String code;

    private ArrayList<BeanShowModelResult> datas;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<BeanShowModelResult> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<BeanShowModelResult> datas) {
        this.datas = datas;
    }

}
