package com.shiliuke.bean;

import java.util.List;

/**
 * Created by wupeitao on 15/11/21.
 */
public class ChangeInfo extends BaseModel {
    private Datas datas;

    public Datas getDatas() {
        return datas;
    }

    public void setDatas(Datas datas) {
        this.datas = datas;
    }

    public class Datas {
        private String exchange_id;
        private String member_id;
        private String home;
        private String from_gname;
        private String to_gname;
        private String info;
        private String add_time;
        private String ing_time;
        private String end_time;
        private String status;
        private String role;
        private String link_exchange_id;
        private List<String> images;
        private ToExchange to_exchange;
        private String link_mobile;

        public ToExchange getTo_exchange() {
            return to_exchange;
        }

        public void setTo_exchange(ToExchange to_exchange) {
            this.to_exchange = to_exchange;
        }

        public String getLink_mobile() {
            return link_mobile;
        }

        public void setLink_mobile(String link_mobile) {
            this.link_mobile = link_mobile;
        }

        public ToExchange getToExchange() {
            return to_exchange;
        }

        public void setToExchange(ToExchange toExchange) {
            this.to_exchange = toExchange;
        }

        public class ToExchange {
            private String exchange_id;
            private String member_id;
            private String home;
            private String images;
            private String from_gname;
            private String to_gname;
            private String info;
            private String add_time;
            private String ing_time;
            private String end_time;
            private String status;
            private String role;
            private String link_exchange_id;
            private String image_url;
            private String link_name;
            private String link_mobile;
            private String link_avar;

            public String getLink_avar() {
                return link_avar;
            }

            public void setLink_avar(String link_avar) {
                this.link_avar = link_avar;
            }

            public String getExchange_id() {
                return exchange_id;
            }

            public void setExchange_id(String exchange_id) {
                this.exchange_id = exchange_id;
            }

            public String getMember_id() {
                return member_id;
            }

            public void setMember_id(String member_id) {
                this.member_id = member_id;
            }

            public String getHome() {
                return home;
            }

            public void setHome(String home) {
                this.home = home;
            }

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public String getFrom_gname() {
                return from_gname;
            }

            public void setFrom_gname(String from_gname) {
                this.from_gname = from_gname;
            }

            public String getTo_gname() {
                return to_gname;
            }

            public void setTo_gname(String to_gname) {
                this.to_gname = to_gname;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getIng_time() {
                return ing_time;
            }

            public void setIng_time(String ing_time) {
                this.ing_time = ing_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getLink_exchange_id() {
                return link_exchange_id;
            }

            public void setLink_exchange_id(String link_exchange_id) {
                this.link_exchange_id = link_exchange_id;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }

            public String getLink_name() {
                return link_name;
            }

            public void setLink_name(String link_name) {
                this.link_name = link_name;
            }

            public String getLink_mobile() {
                return link_mobile;
            }

            public void setLink_mobile(String link_mobile) {
                this.link_mobile = link_mobile;
            }
        }


        public String getExchange_id() {
            return exchange_id;
        }

        public void setExchange_id(String exchange_id) {
            this.exchange_id = exchange_id;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getFrom_gname() {
            return from_gname;
        }

        public void setFrom_gname(String from_gname) {
            this.from_gname = from_gname;
        }

        public String getTo_gname() {
            return to_gname;
        }

        public void setTo_gname(String to_gname) {
            this.to_gname = to_gname;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getIng_time() {
            return ing_time;
        }

        public void setIng_time(String ing_time) {
            this.ing_time = ing_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getLink_exchange_id() {
            return link_exchange_id;
        }

        public void setLink_exchange_id(String link_exchange_id) {
            this.link_exchange_id = link_exchange_id;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
