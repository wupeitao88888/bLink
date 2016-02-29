package com.shiliuke.bean;

import java.util.List;

/**
 * 我发起的
 * Created by wupeitao on 15/11/5.
 */
public class MeInitateActivity extends BaseModel {

    private List<Data> datas;

    public List<Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }

    public class Data {
        private String activity_id;
        private String images;
        private String title;
        private String jihe_time;
        private String activity_address;
        private String member_id;
        private String count;
        private String status;
        private String status_desc;
        private String image_url;
        private String log_num;//未读参与数
        private String commend_num;//未读评论数

        public int getLog_num() {
            int num;
            try{
                num = Integer.parseInt(log_num);
            }catch (Exception e){
                num = 0;
            }

            return num;
        }
        public int getCommend_num() {
            int num;
            try{
                num = Integer.parseInt(commend_num);
            }catch (Exception e){
                num = 0;
            }

            return num;
        }

        public void setCommend_num(String commend_num) {
            this.commend_num = commend_num;
        }

        public void setLog_num(String log_num) {
            this.log_num = log_num;
        }

        public String getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getJihe_time() {
            return jihe_time;
        }

        public void setJihe_time(String jihe_time) {
            this.jihe_time = jihe_time;
        }

        public String getActivity_address() {
            return activity_address;
        }

        public void setActivity_address(String activity_address) {
            this.activity_address = activity_address;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus_desc() {
            return status_desc;
        }

        public void setStatus_desc(String status_desc) {
            this.status_desc = status_desc;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }
}
