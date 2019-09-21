package com.allen.douban.bean;

public class ChatLock {
    private Integer ownerId;
    private Integer callerId;
    private String callerName;

    public ChatLock(Integer ownerId) {
        this.ownerId = ownerId;
        this.callerId = 0;
    }

    public void reset(){
        setCallerName("");
        setCallerId(0);
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getCallerId() {
        return callerId;
    }

    public void setCallerId(Integer callerId) {
        this.callerId = callerId;
    }

    public String getCallerName() {
        return callerName;
    }

    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }
}
