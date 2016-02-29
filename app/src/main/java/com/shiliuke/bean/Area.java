package com.shiliuke.bean;

import java.util.List;

/**
 * Created by lvfl on 2015/11/27.
 */
public class Area extends BaseModel {

    public List<Datas> getDatas() {
        return datas;
    }

    public void setDatas(List<Datas> datas) {
        this.datas = datas;
    }

    private List<Datas> datas;
    public class Datas{

        private String name;
        private String address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
