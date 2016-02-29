package com.shiliuke.bean;

import java.io.Serializable;

/**
 * Created by wangzhi on 15/11/19.
 */
public class BaseModel implements Serializable {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
