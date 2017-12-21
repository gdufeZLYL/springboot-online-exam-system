package com.zzqnxx.model;

import java.util.Date;

public class Grade {
    private int id;
    private int sId;
    private int paperId;
    private Date createTime;
    private int score;
    private int singleScore;
    private int mulScore;
    private int otherScore;
    private String answerJson;

    private Student student;
    private Paper paper;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSingleScore() {
        return singleScore;
    }

    public void setSingleScore(int singleScore) {
        this.singleScore = singleScore;
    }

    public int getMulScore() {
        return mulScore;
    }

    public void setMulScore(int mulScore) {
        this.mulScore = mulScore;
    }

    public int getOtherScore() {
        return otherScore;
    }

    public void setOtherScore(int otherScore) {
        this.otherScore = otherScore;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public String getAnswerJson() {
        return answerJson;
    }

    public void setAnswerJson(String answerJson) {
        this.answerJson = answerJson;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", sId=" + sId +
                ", paperId=" + paperId +
                ", createTime=" + createTime +
                ", score=" + score +
                ", singleScore=" + singleScore +
                ", mulScore=" + mulScore +
                ", otherScore=" + otherScore +
                ", student=" + student +
                ", paper=" + paper +
                '}';
    }
}
