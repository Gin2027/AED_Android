package com.ljx.aed.db;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Student implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("account")
    private String account;

    @SerializedName("password")
    private String password;

    @SerializedName("name")
    private String name;

    @SerializedName("age")
    private int age;

    @SerializedName("location")
    private String location;

    @SerializedName("school")
    private String school;

    @SerializedName("phone")
    private String phone;

    @SerializedName("qq")
    private String qq;

    @SerializedName("tid")
    private int tid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }
}
