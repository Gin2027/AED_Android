package com.ljx.aed.db;

public class Advice {
    private int aid;
    private int sid;
    private int tid;
    private String message;
    private String reply;
    private String gtime;
    private String rtime;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getGtime() {
        return gtime;
    }

    public void setGtime(String gtime) {
        this.gtime = gtime;
    }

    public String getRtime() {
        return rtime;
    }

    public void setRtime(String rtime) {
        this.rtime = rtime;
    }

    @Override
    public String toString() {
        return "Advice{" +
                "aid=" + aid +
                ", sid=" + sid +
                ", tid=" + tid +
                ", message='" + message + '\'' +
                ", reply='" + reply + '\'' +
                ", gtime='" + gtime + '\'' +
                ", rtime='" + rtime + '\'' +
                '}';
    }
}
