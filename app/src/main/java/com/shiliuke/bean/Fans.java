package com.shiliuke.bean;

import java.util.List;

/**
 * Created by wupeitao on 15/11/27.
 */
public class Fans extends BaseModel {
    private List<Datas> datas;

    public List<Datas> getDatas() {
        return datas;
    }

    public void setDatas(List<Datas> datas) {
        this.datas = datas;
    }

    public class Datas{
        private String fans_id;
        private String fans_name;
        private String fans_avar;
        private String flag;

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public boolean isGuanZhu(){
            return "1".equalsIgnoreCase(flag);
        }

        public String getFans_id() {
            return fans_id;
        }

        public void setFans_id(String fans_id) {
            this.fans_id = fans_id;
        }

        public String getFans_name() {
            return fans_name;
        }

        public void setFans_name(String fans_name) {
            this.fans_name = fans_name;
        }

        public String getFans_avar() {
            return fans_avar;
        }

        public void setFans_avar(String fans_avar) {
            this.fans_avar = fans_avar;
        }
    }
}
