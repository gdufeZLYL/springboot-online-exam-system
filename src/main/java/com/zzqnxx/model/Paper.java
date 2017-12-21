package com.zzqnxx.model;

import java.util.Date;

public class Paper {
    private int id;
    private String paperName;
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Paper{" +
                "id=" + id +
                ", paperName='" + paperName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
