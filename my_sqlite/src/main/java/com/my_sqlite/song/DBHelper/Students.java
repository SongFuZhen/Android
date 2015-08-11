package com.my_sqlite.song.DBHelper;

/**
 * Created by 宋福祯 on 15/8/11.
 */
public class Students {
    private String student_id;
    private String student_name;
    private String score;
    private String class_id;

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    @Override
    public String toString() {
        return "Students{" +
                "student_id='" + student_id + '\'' +
                ", student_name='" + student_name + '\'' +
                ", score='" + score + '\'' +
                ", class_id='" + class_id + '\'' +
                '}';
    }
}
