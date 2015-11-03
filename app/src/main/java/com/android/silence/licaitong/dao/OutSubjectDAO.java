package com.android.silence.licaitong.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.silence.licaitong.bean.TbOutSubject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silence on 2015/10/26 0026.
 */
public class OutSubjectDAO {
    private SQLiteDatabase db;
    private DatabaseHelper helper;

    public OutSubjectDAO(Context content) {
        helper = DatabaseHelper.getInstance(content);
    }

    /**
     * 添加支出科目
     *
     * @param TbOutSubject 支出科目信息
     */
    public void add(TbOutSubject TbOutSubject) {
        db = helper.getWritableDatabase();
        String sql = "insert into out_sub (subject) values(?)";
        db.execSQL(sql, new Object[]{TbOutSubject.getSubject()});
    }

    /**
     * 获取所有的科目
     *
     * @return 科目列表
     */
    public List<String> getAllSubject() {
        db = helper.getReadableDatabase();
        String sql = "select subject from out_sub";
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
     * 删除指定支出科目
     *
     * @param subject 科目内容
     */
    public void remove(String subject) {
        db = helper.getWritableDatabase();
        String sql = "delete from out_sub where subject=?";
        db.execSQL(sql, new Object[]{subject});
    }

    /**
     * 删除所有的支出科目信息
     */
    public void removeAll() {
        db = helper.getWritableDatabase();
        String sql = "delete from out_sub";
        db.execSQL(sql);
    }

    /**
     * 重置支出科目表
     */
    public void reset() {
        removeAll();
        db = helper.getWritableDatabase();
        String insert1 = "insert into out_sub (subject) values ('用餐')";
        db.execSQL(insert1);
        String insert2 = "insert into out_sub (subject) values ('应酬')";
        db.execSQL(insert2);
        String insert3 = "insert into out_sub (subject) values ('打的')";
        db.execSQL(insert3);
    }

    /**
     * 获取最大的支出科目编号
     *
     * @return
     */
    public int getMaxId() {
        db = helper.getReadableDatabase();
        String sql = "select max(id) from out_sub";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            int maxId = cursor.getInt(0);
            cursor.close();
            return maxId;
        }
        return 0;
    }

}
