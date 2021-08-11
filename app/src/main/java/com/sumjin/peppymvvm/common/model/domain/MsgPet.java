package com.sumjin.peppymvvm.common.model.domain;

import java.util.List;

public class MsgPet {


    /**
     * msg : 获取数据成功
     * state : 10000
     * datas : [{"id":2,"pet_id":"111","username":"test1","nickname":"jacket","gender":"男","avatar":"pet_avatar/2020812/111pet_avator.png","species":"狗","breed":"贵宾犬","primary_color":"白色","heavy":"15","birthday":"1.1","doctor":"Doc.Lee","doctor_phone":"12345678","more_info":"可爱，温柔"}]
     * page : 0
     * size : 1
     * count : 1
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
         * id : 2
         * pet_id : 111
         * username : test1
         * nickname : jacket
         * gender : 男
         * avatar : pet_avatar/2020812/111pet_avator.png
         * species : 狗
         * breed : 贵宾犬
         * primary_color : 白色
         * heavy : 15
         * birthday : 1.1
         * doctor : Doc.Lee
         * doctor_phone : 12345678
         * more_info : 可爱，温柔
         */

        private int id;
        private String pet_id;
        private String username;
        private String nickname;
        private String gender;
        private String avatar;
        private String species;
        private String breed;
        private String primary_color;
        private String heavy;
        private String birthday;
        private String doctor;
        private String doctor_phone;
        private String more_info;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPet_id() {
            return pet_id;
        }

        public void setPet_id(String pet_id) {
            this.pet_id = pet_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSpecies() {
            return species;
        }

        public void setSpecies(String species) {
            this.species = species;
        }

        public String getBreed() {
            return breed;
        }

        public void setBreed(String breed) {
            this.breed = breed;
        }

        public String getPrimary_color() {
            return primary_color;
        }

        public void setPrimary_color(String primary_color) {
            this.primary_color = primary_color;
        }

        public String getHeavy() {
            return heavy;
        }

        public void setHeavy(String heavy) {
            this.heavy = heavy;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getDoctor() {
            return doctor;
        }

        public void setDoctor(String doctor) {
            this.doctor = doctor;
        }

        public String getDoctor_phone() {
            return doctor_phone;
        }

        public void setDoctor_phone(String doctor_phone) {
            this.doctor_phone = doctor_phone;
        }

        public String getMore_info() {
            return more_info;
        }

        public void setMore_info(String more_info) {
            this.more_info = more_info;
        }
    }
}
