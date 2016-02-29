package com.shiliuke.bean;

/**
 * Created by lvfl on 2015/12/19.
 */
public class OrderNum extends BaseModel {

    private Date datas;

    public Date getDatas() {
        return datas;
    }

    public void setDatas(Date datas) {
        this.datas = datas;
    }

    public class Date {
        private int wei_num;
        private int code_num;
        private int sum_num;
        private int xiu_num;
        private int activity_num;
        private int exchange_num;
        private int talk_num;
        private int fans_num;

        public int getFans_num() {
            return fans_num;
        }

        public void setFans_num(int fans_num) {
            this.fans_num = fans_num;
        }

        public int getSum_num() {
            return sum_num;
        }

        public int getXiu_num() {
            return xiu_num;
        }

        public int getActivity_num() {
            return activity_num;
        }

        public int getExchange_num() {
            return exchange_num;
        }

        public int getTalk_num() {
            return talk_num;
        }

        public int getWei_num() {
            return wei_num;
        }


        public int getCode_num() {
            return code_num;
        }

    }
}
