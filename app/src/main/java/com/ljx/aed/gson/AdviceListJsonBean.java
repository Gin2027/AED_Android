package com.ljx.aed.gson;

import com.ljx.aed.db.Advice;

import java.util.List;

public class AdviceListJsonBean {
    private int count;
    private List<Advice> adviceList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Advice> getAdviceList() {
        return adviceList;
    }

    public void setAdviceList(List<Advice> adviceList) {
        this.adviceList = adviceList;
    }

    @Override
    public String toString() {
        return "AdviceListJsonBean{" +
                "count=" + count +
                ", adviceList=" + adviceList +
                '}';
    }
}
