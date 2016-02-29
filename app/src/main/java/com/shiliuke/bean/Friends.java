package com.shiliuke.bean;

import java.util.List;

/**
 * 我的好友
 * Created by wupeitao on 15/11/9.
 */
public class Friends extends BaseModel {
    private List<Data> datas;

    public List<Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }

    public class Data {
        private String member_friend_id;
        private String member_id;
        private String friend_id;
        private String add_time;
        private String friend_name;
        private String friend_avar;

        public String getMember_friend_id() {
            return member_friend_id;
        }

        public void setMember_friend_id(String member_friend_id) {
            this.member_friend_id = member_friend_id;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getFriend_id() {
            return friend_id;
        }

        public void setFriend_id(String friend_id) {
            this.friend_id = friend_id;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getFriend_name() {
            return friend_name;
        }

        public void setFriend_name(String friend_name) {
            this.friend_name = friend_name;
        }

        public String getFriend_avar() {
            return friend_avar;
        }

        public void setFriend_avar(String friend_avar) {
            this.friend_avar = friend_avar;
        }
    }
}
