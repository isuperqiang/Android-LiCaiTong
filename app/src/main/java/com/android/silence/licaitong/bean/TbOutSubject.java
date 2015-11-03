package com.android.silence.licaitong.bean;

/**
 * Created by Silence on 2015/10/26 0026.
 */
public class TbOutSubject {
    private int id;            //主键
    private String subject;    //科目

    public TbOutSubject() {
    }

    public TbOutSubject(int id, String subject) {
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
        return "TbOutSubject{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                '}';
    }
}
