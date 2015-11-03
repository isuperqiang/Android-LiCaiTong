package com.android.silence.licaitong.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.silence.licaitong.bean.TbInAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silence on 2015/10/26 0026.
 */
public class InAccountDAO {
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public InAccountDAO(Context context) {
        helper = DatabaseHelper.getInstance(context);
    }

    /**
     * 添加收入信息
     *
     * @param tbInAccount 收入信息
     */
    public void add(TbInAccount tbInAccount) {
        db = helper.getWritableDatabase();
        String sql = "insert into in_account (date,place,money,subject,note) values (?,?,?,?,?)";
        db.execSQL(sql, new Object[]{tbInAccount.getDate(), tbInAccount.getPlace()
                , tbInAccount.getMoney(), tbInAccount.getSubject(), tbInAccount.getNote()});
    }

    /**
     * 修改收入信息
     *
     * @param tbInAccount 收入信息
     */
    public void update(TbInAccount tbInAccount) {
        db = helper.getWritableDatabase();
        String sql = "update in_account set date=?,place=?,money=?,subject=?,note=? where id=?";
        db.execSQL(sql, new Object[]{tbInAccount.getDate(), tbInAccount.getPlace(), tbInAccount.getMoney(),
                tbInAccount.getSubject(), tbInAccount.getNote(), tbInAccount.getId()});
    }

    /**
     * 删除指定收入信息
     *
     * @param id 主键
     */
    public void remove(int id) {
        db = helper.getWritableDatabase();
        String sql = "delete from in_account where id=?";
        db.execSQL(sql, new Object[]{id});
    }

    /**
     * 删除全部收入信息
     */
    public void removeAll() {
        db = helper.getWritableDatabase();
        String sql = "delete from in_account";
        db.execSQL(sql);
    }

    /**
     * 查找指定收入信息
     *
     * @param index 索引
     * @return 收入信息
     */
    public TbInAccount find(int index) {
        db = helper.getReadableDatabase();
        String sql = "select * from in_account where id=?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(index)});
        if (cursor.moveToFirst()) {
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String place = cursor.getString(cursor.getColumnIndex("place"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            String subject = cursor.getString(cursor.getColumnIndex("subject"));
            String note = cursor.getString(cursor.getColumnIndex("note"));
            return new TbInAccount(index, date, place, money, subject, note);
        }
        cursor.close();
        return null;
    }

    /**
     * 通过subject获取该科目下的所有金额
     *
     * @param subject 科目
     * @return 总金额
     */
    public float getMoneyOfEachSubject(String subject) {
        db = helper.getReadableDatabase();
        String sql = "select sum(money) from in_account where subject=?";
        Cursor cursor = db.rawQuery(sql, new String[]{subject});
        if (cursor.moveToFirst()) {
            float sum = cursor.getFloat(0);
            cursor.close();
            return sum;
        }
        return 0;
    }

    /**
     * 查询所有的收入信息
     *
     * @return 收入信息列表
     */
    public List<TbInAccount> findAll() {
        db = helper.getReadableDatabase();
        String sql = "select * from in_account";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            List<TbInAccount> list = new ArrayList<>();
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String place = cursor.getString(cursor.getColumnIndex("place"));
                float money = cursor.getFloat(cursor.getColumnIndex("money"));
                String subject = cursor.getString(cursor.getColumnIndex("subject"));
                String note = cursor.getString(cursor.getColumnIndex("note"));
                TbInAccount tbInAccount = new TbInAccount(id, date, place, money, subject, note);
                list.add(tbInAccount);
            } while (cursor.moveToNext());
            cursor.close();
            return list;
        }
        return null;
    }

    /**
     * 获取所有保存的收入科目
     *
     * @return 收入科目列表
     */
    public List<String> getAllSubjects() {
        db = helper.getReadableDatabase();
        String sql = "select subject from in_account";
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
     * 获取收入信息记录的数量
     *
     * @return 记录数
     */
    public int getCount() {
        db = helper.getReadableDatabase();
        String sql = "select count(id) from in_account";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        }
        return 0;
    }

    /**
     * 获取总收入金额
     *
     * @return 总收入金额
     */
    public float getAllMoney() {
        List<TbInAccount> list = findAll();
        if (list != null) {
            float sum = 0;
            for (TbInAccount tbInAccount : list) {
                sum += tbInAccount.getMoney();
            }
            return sum;
        }
        return 0;
    }

    /**
     * 获取收入表的最大编号
     *
     * @return 最大编号
     */
    public int getMaxId() {
        db = helper.getReadableDatabase();
        String sql = "select max(id) from in_account";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            int maxId = cursor.getInt(0);
            cursor.close();
            return maxId;
        }
        return 0;
    }
}
