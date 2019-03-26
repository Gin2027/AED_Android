package com.ljx.aed.gson;

import com.ljx.aed.db.Student;

public class LoginJsonBean {
    public String course;
    public Student student;
    public boolean status;

    @Override
    public String toString() {
        return "LoginJsonBean{" +
                "course='" + course + '\'' +
                ", student=" + student +
                ", status=" + status +
                '}';
    }
}
