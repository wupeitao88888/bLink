package com.shiliuke.bean;

import java.util.List;

/**
 * Created by lvfl on 2015/12/9.
 */
public class CityChoice extends BaseModel{

    private List<Datas> datas;

    public List<Datas> getDatas() {
        return datas;
    }

    public void setDatas(List<Datas> datas) {
        this.datas = datas;
    }

    public class Datas{
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
