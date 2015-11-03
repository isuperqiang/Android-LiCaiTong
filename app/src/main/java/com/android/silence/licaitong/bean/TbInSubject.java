package com.android.silence.licaitong.bean;

/**
 * Created by Silence on 2015/10/26 0026.
 */
public class TbInSubject {
    private int id;              //主键
    private String subject;      //科目

    public TbInSubject() {
    }

    public TbInSubject(int id, String subject) {
        this.id = id;
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "TbInSubject{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                '}';
    }
}
