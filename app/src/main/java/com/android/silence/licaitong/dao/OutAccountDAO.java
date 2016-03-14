package com.android.silence.licaitong.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.silence.licaitong.bean.TbOutAccount;
import com.android.silence.licaitong.utils.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silence on 2015/10/26 0026.
 */
public class OutAccountDAO {
    private DBOpenHelper helper;
    private SQLiteDatabase db;

    public OutAccountDAO(Context context) {
        helper = DBOpenHelper.getInstance(context);
    }

    /**
     * 添加支出信息
     *
     * @param TbOutAccount 支出信息
     */
    public void add(TbOutAccount TbOutAccount) {
        db = helper.getWritableDatabase();
        String sql = "insert into out_account (date,place,money,subject,note) values (?,?,?,?,?)";
        db.execSQL(sql, new Object[]{TbOutAccount.getDate(), TbOutAccount.getPlace()
                , TbOutAccount.getMoney(), TbOutAccount.getSubject(), TbOutAccount.getNote()});
    }

    /**
     * 修改支出信息
     *
     * @param TbOutAccount 支出信息
     */
    public void update(TbOutAccount TbOutAccount) {
        db = helper.getWritableDatabase();
        String sql = "update out_account set date=?,place=?,money=?,subject=?,note=? where id=?";
        db.execSQL(sql, new Object[]{TbOutAccount.getDate(), TbOutAccount.getPlace(), TbOutAccount.getMoney(),
                TbOutAccount.getSubject(), TbOutAccount.getNote(), TbOutAccount.getId()});
    }

    /**
     * 删除指定支出信息
     *
     * @param id 主键
     */
    public void remove(int id) {
        db = helper.getWritableDatabase();
        String sql = "delete from out_account where id=?";
        db.execSQL(sql, new Object[]{id});
    }

    /**
     * 删除全部支出信息
     */
    public void removeAll() {
        db = helper.getWritableDatabase();
        String sql = "delete from out_account";
        db.execSQL(sql);
    }

    /**
     * 获取所有保存的支出科目
     *
     * @return 收入科目列表
     */
    public List<String> getAllSubjects() {
        db = helper.getReadableDatabase();
        String sql = "select subject from out_account";
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
     * 查找指定支出信息
     *
     * @param index 索引
     * @return 支出信息
     */
    public TbOutAccount find(int index) {
        db = helper.getReadableDatabase();
        String sql = "select * from out_account where id=?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(index)});
        if (cursor.moveToFirst()) {
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String place = cursor.getString(cursor.getColumnIndex("place"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            String subject = cursor.getString(cursor.getColumnIndex("subject"));
            String note = cursor.getString(cursor.getColumnIndex("note"));
            return new TbOutAccount(index, date, place, money, subject, note);
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
        String sql = "select sum(money) from out_account where subject=?";
        Cursor cursor = db.rawQuery(sql, new String[]{subject});
        if (cursor.moveToFirst()) {
            float sum = cursor.getFloat(0);
            cursor.close();
            return sum;
        }
        return 0;
    }

    /**
     * 查询所有的支出信息
     *
     * @return 支出信息列表
     */
    public List<TbOutAccount> findAll() {
        db = helper.getReadableDatabase();
        String sql = "select * from out_account";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            List<TbOutAccount> list = new ArrayList<>();
			TbOutAccount TbOutAccount;
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String place = cursor.getString(cursor.getColumnIndex("place"));
                float money = cursor.getFloat(cursor.getColumnIndex("money"));
                String subject = cursor.getString(cursor.getColumnIndex("subject"));
                String note = cursor.getString(cursor.getColumnIndex("note"));
                TbOutAccount = new TbOutAccount(id, date, place, money, subject, note);
                list.add(TbOutAccount);
            } while (cursor.moveToNext());
            cursor.close();
            return list;
        }
        return null;
    }

    /**
     * 获取支出信息记录的数量
     *
     * @return 记录数
     */
    public int getCount() {
        db = helper.getReadableDatabase();
        String sql = "select count(id) from out_account";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        }
        return 0;
    }

    /**
     * 获取总支出金额
     *
     * @return 总支出金额
     */
    public float getAllMoney() {
        List<TbOutAccount> list = findAll();
        if (list != null) {
            float sum = 0;
            for (TbOutAccount TbOutAccount : list) {
                sum += TbOutAccount.getMoney();
            }
            return sum;
        }
        return 0;
    }

    /**
     * 获取支出表的最大编号
     *
     * @return 最大编号
     */
    public int getMaxId() {
        db = helper.getReadableDatabase();
        String sql = "select max(id) from out_account";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            int maxId = cursor.getInt(0);
            cursor.close();
            return maxId;
        }
        return 0;
    }
}
