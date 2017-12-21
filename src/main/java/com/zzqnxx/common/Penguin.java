package com.zzqnxx.common;

public class Penguin {

    //table
    public final static String TABLE_EXAM_GRADE = "t_exam_grade";
    public final static String TABLE_EXAM_PAPER = "t_exam_paper";
    public final static String TABLE_EXAM_POST = "t_exam_post";
    public final static String TABLE_EXAM_STUDENT = "t_exam_student";
    public final static String TABLE_EXAM_SUBJECT = "t_exam_subject";
    public final static String TABLE_EXAM_TEACHER = "t_exam_teacher";

    // Session
    //当前登录信息
    public final static String CURRENT_ACCOUNT = "current_account";
    //当前登录身份
    public final static String CURRENT_IDENTITY = "current_identity";
    //考生
    public final static String IDENTITY_STUDENT = "student";
    //教师
    public final static String IDENTITY_TEACHER = "teacher";

    //单选题分数
    public final static int SINGLE_SCORE = 2;
    //不定项选择题
    public final static int MUL_SCORE = 4;
}
