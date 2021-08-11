package com.sumjin.peppymvvm.common.model.domain;

import java.util.List;

public class DynamicInfo {

    @Override
    public String toString() {
        return "DynamicInfo{" +
                "msg='" + msg + '\'' +
                ", state=" + state +
                ", page=" + page +
                ", size=" + size +
                ", count=" + count +
                ", datas=" + datas +
                '}';
    }

    /**
     * msg : 获取数据成功
     * state : 10000
     * datas : [{"id":15,"nickname":"22333","avatar":"222","username":"test2","content":"发顺丰散发刷卡缴费卢卡斯","createtime":"2020-08-05 14:39:31.0","likes":0,"comments":0,"shares":0,"dynamic_type":"text","status":1,"download_url":"","state":0},{"id":14,"nickname":"222","avatar":"222","username":"test1","content":"发顺丰散发刷卡缴费卢卡斯","createtime":"2020-08-05 14:39:30.0","likes":0,"comments":7,"shares":0,"dynamic_type":"text","status":1,"download_url":"","state":0},{"id":13,"nickname":"222","avatar":"222","username":"test1","content":"发顺丰散发刷卡缴费卢卡斯","createtime":"2020-08-05 14:39:29.0","likes":0,"comments":0,"shares":0,"dynamic_type":"text","status":1,"download_url":"","state":0},{"id":12,"nickname":"222","avatar":"222","username":"test1","content":"发顺丰散发刷卡缴费卢卡斯","createtime":"2020-08-05 14:39:28.0","likes":0,"comments":0,"shares":0,"dynamic_type":"text","status":1,"download_url":"","state":0},{"id":11,"nickname":"222","avatar":"222","username":"test1","content":"发顺丰散发刷卡缴费卢卡斯","createtime":"2020-08-05 14:39:27.0","likes":0,"comments":0,"shares":0,"dynamic_type":"text","status":1,"download_url":"","state":0},{"id":10,"nickname":"222","avatar":"222","username":"test1","content":"发顺丰散发刷卡缴费卢卡斯","createtime":"2020-08-05 14:39:26.0","likes":0,"comments":0,"shares":0,"dynamic_type":"text","status":1,"download_url":"","state":0},{"id":9,"nickname":"222","avatar":"222","username":"test1","content":"发顺丰散发刷卡缴费卢卡斯","createtime":"2020-08-05 14:39:25.0","likes":0,"comments":0,"shares":0,"dynamic_type":"text","status":1,"download_url":"","state":0},{"id":8,"nickname":"222","avatar":"222","username":"test1","content":"发顺丰散发刷卡缴费卢卡斯","createtime":"2020-08-05 14:39:24.0","likes":0,"comments":0,"shares":0,"dynamic_type":"text","status":1,"download_url":"","state":0},{"id":7,"nickname":"222","avatar":"222","username":"test1","content":"发顺丰散发刷卡缴费卢卡斯","createtime":"2020-08-05 14:39:23.0","likes":0,"comments":0,"shares":0,"dynamic_type":"text","status":1,"download_url":"","state":0},{"id":6,"nickname":"222","avatar":"222","username":"test1","content":"发顺丰散发刷卡缴费卢卡斯","createtime":"2020-08-05 10:28:17.0","likes":0,"comments":0,"shares":0,"dynamic_type":"text","status":1,"download_url":"","state":0}]
     * page : 1
     * size : 10
     * count : 10
     */

    private String msg;
    private int state;
    private int page;
    private int size;
    private int count;
    private List<DatasBean> datas;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * id : 15
         * nickname : 22333
         * avatar : 222
         * username : test2
         * content : 发顺丰散发刷卡缴费卢卡斯
         * createtime : 2020-08-05 14:39:31.0
         * likes : 0
         * comments : 0
         * shares : 0
         * dynamic_type : text
         * status : 1
         * download_url :
         * state : 0
         */

        private int id;
        private String nickname;
        private String avatar;
        private String username;
        private String content;
        private String createtime;
        private int likes;
        private int comments;
        private int shares;
        private String dynamic_type;
        private int status;
        private String download_url;
        private int state;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public int getShares() {
            return shares;
        }

        public void setShares(int shares) {
            this.shares = shares;
        }

        public String getDynamic_type() {
            return dynamic_type;
        }

        public void setDynamic_type(String dynamic_type) {
            this.dynamic_type = dynamic_type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "id=" + id +
                    ", nickname='" + nickname + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", username='" + username + '\'' +
                    ", content='" + content + '\'' +
                    ", createtime='" + createtime + '\'' +
                    ", likes=" + likes +
                    ", comments=" + comments +
                    ", shares=" + shares +
                    ", dynamic_type='" + dynamic_type + '\'' +
                    ", status=" + status +
                    ", download_url='" + download_url + '\'' +
                    ", state=" + state +
                    '}';
        }
    }
}
