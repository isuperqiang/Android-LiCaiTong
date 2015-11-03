package com.android.silence.licaitong.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.silence.licaitong.bean.TbUserInfo;

/**
 * Created by Silence on 2015/10/26 0026.
 */
public class UserInfoDAO {
    private SQLiteDatabase db;
    private DatabaseHelper helper;

    public UserInfoDAO(Context context) {
        helper = DatabaseHelper.getInstance(context);
    }

    /**
     * 修改用户信息
     *
     * @param tbUserInfo 用户信息
     */
    public void update(TbUserInfo tbUserInfo) {
        db = helper.getWritableDatabase();
        String sql = "update user set name=?,age=?,mail=?,gender=?,area=?";
        db.execSQL(sql, new Object[]{tbUserInfo.getName(), tbUserInfo.getAge(),
                tbUserInfo.getMail(), tbUserInfo.getGender(), tbUserInfo.getArea()});
    }

    /**
     * 添加用户信息
     *
     * @param tbUserInfo 用户信息
     */
    public void add(TbUserInfo tbUserInfo) {
        db = helper.getWritableDatabase();
        String sql = "insert into user (name,age,mail,gender,area) values (?,?,?,?,?)";
        db.execSQL(sql, new Object[]{tbUserInfo.getName(), tbUserInfo.getAge(),
                tbUserInfo.getMail(), tbUserInfo.getGender(), tbUserInfo.getArea()});
    }

    /**
     * 根据索引获取用户信息
     *
     * @param index 索引
     * @return 用户信息
     */
    public TbUserInfo find(int index) {
        db = helper.getReadableDatabase();
        String sql = "select name, age, mail, gender, area from user where id=?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(index)});
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            String mail = cursor.getString(cursor.getColumnIndex("mail"));
            String gender = cursor.getString(cursor.getColumnIndex("gender"));
            String area = cursor.getString(cursor.getColumnIndex("area"));
            cursor.close();
            return new TbUserInfo(index, name, age, area, mail, gender);
        }
        return null;
    }

    /**
     * 获取最大的索引
     *
     * @return MaxId
     */
    public int getMaxId() {
        db = helper.getReadableDatabase();
        String sql = "select max(id) from user";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            int maxId = cursor.getInt(0);
            cursor.close();
            return maxId;
        }
        return 0;
    }

}


