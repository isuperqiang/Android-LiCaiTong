package com.android.silence.licaitong.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.silence.licaitong.bean.TbInSubject;
import com.android.silence.licaitong.utils.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silence on 2015/10/26 0026.
 */
public class InSubjectDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;

    public InSubjectDAO(Context context) {
        helper = DBOpenHelper.getInstance(context);
    }

    /**
     * 添加收入科目
     *
     * @param tbInSubject 收入科目信息
     */
    public void add(TbInSubject tbInSubject) {
        db = helper.getWritableDatabase();
        String sql = "insert into in_sub (subject) values(?)";
        db.execSQL(sql, new Object[]{tbInSubject.getSubject()});
    }

    /**
     * 删除指定收入科目信息
     *
     * @param subject 科目内容
     */
    public void remove(String subject) {
        db = helper.getWritableDatabase();
        String sql = "delete from in_sub where subject=?";
        db.execSQL(sql, new Object[]{subject});
    }

    /**
     * 删除所有的收入科目信息
     */
    public void removeAll() {
        db = helper.getWritableDatabase();
        String sql = "delete from in_sub";
        db.execSQL(sql);
    }

    /**
     * 重置收入科目表
     */
    public void reset() {
        removeAll();
        db = helper.getWritableDatabase();
        String insert1 = "insert into in_sub (subject) values ('工资')";
        db.execSQL(insert1);
        String insert2 = "insert into in_sub (subject) values ('兼职')";
        db.execSQL(insert2);
        String insert3 = "insert into in_sub (subject) values ('奖金')";
        db.execSQL(insert3);
    }

    /**
     * 获取所有的科目
     *
     * @return 科目列表
     */
    public List<String> getAllSubject() {
        db = helper.getReadableDatabase();
        String sql = "select subject from in_sub";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            List<String> list = new ArrayList<>();
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
            cursor.close();
            return list;
        }
        return null;
    }

    /**
     * 获取最大的收入科目编号
     *
     * @return
     */
    public int getMaxId() {
        db = helper.getReadableDatabase();
        String sql = "select max(id) from in_sub";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            int maxId = cursor.getInt(0);
            cursor.close();
            return maxId;
        }
        return 0;
    }
}
