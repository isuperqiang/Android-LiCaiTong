package com.android.silence.licaitong.bean;

/**
 * Created by Silence on 2015/10/26 0026.
 */
public class TbInAccount {
    private int id;              //主键
    private String date;         //日期
    private String place;        //地点
    private float money;         //金额
    private String subject;      //科目
    private String note;         //备注

    public TbInAccount() {
    }

    public TbInAccount(int id, String date, String place, float money, String subject, String note) {
        this.id = id;
        this.date = date;
        this.place = place;
        this.money = money;
        this.subject = subject;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public float getMoney() {
        return money;
    }

    public String getNote() {
        return note;
    }

    public String getSubject() {
        return subject;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "TbInAccount{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", place='" + place + '\'' +
                ", money=" + money +
                ", subject='" + subject + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
