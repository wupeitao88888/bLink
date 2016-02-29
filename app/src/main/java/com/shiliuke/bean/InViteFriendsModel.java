package com.shiliuke.bean;

import java.util.ArrayList;

/**
 * Created by wangzhi on 15/11/25.
 */
public class InViteFriendsModel extends BaseModel {
    private ArrayList<Result> datas;

    public ArrayList<Result> getDatas() {
        return datas;
    }

    public class Result {
        private String friend_id;
        private String status;//2=邀请过了；1=没被邀请
        private String text;//来源
        private String friend_avar;
        private String friend_name;

        public String getText() {
            return text;
        }

        public String getFriend_id() {
            return friend_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getFriend_avar() {
            return friend_avar;
        }

        public String getFriend_name() {
            return friend_name;
        }
    }
}
