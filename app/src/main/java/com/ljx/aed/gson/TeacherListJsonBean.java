package com.ljx.aed.gson;

import com.ljx.aed.db.Teacher;

import java.util.List;

public class TeacherListJsonBean {
    public int count;
    public List<Teacher> teacherList;

    @Override
    public String toString() {
        return "TeacherListJsonBean{" +
                "count=" + count +
                ", teacherList=" + teacherList +
                '}';
    }
}
