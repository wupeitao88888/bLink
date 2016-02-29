package com.shiliuke.BabyLink.information;

import java.io.Serializable;

/**
 * Created by wangzhi on 15/12/21.
 */
public class InformationModel implements Serializable{
    private int allNum = 100;

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }
}
