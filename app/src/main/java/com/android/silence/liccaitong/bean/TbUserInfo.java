package com.android.silence.liccaitong.bean;

/**
 * Created by Silence on 2015/10/26 0026.
 */
public class TbUserInfo {
    private int id;
    private String name;
    private int age;
    private String area;
    private String mail;
    private String gender;

    public TbUserInfo() {
    }

    public TbUserInfo(int id, String name, int age, String area, String mail, String gender) {
        this.id = id;                    //主键
        this.name = name;                //用户名
        this.age = age;                  //年龄
        this.area = area;                //地区
        this.mail = mail;                //邮箱
        this.gender = gender;            //性别
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "TbUserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", area='" + area + '\'' +
                ", mail='" + mail + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
