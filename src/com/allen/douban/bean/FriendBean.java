package com.allen.douban.bean;

public class FriendBean {
    private Integer userId;
    private String nickname;

    public FriendBean(){
    }

    public FriendBean(Integer userId, String nickname){
        this.userId = userId;
        this.nickname = nickname;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
