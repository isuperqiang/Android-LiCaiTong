package com.android.silence.licaitong.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Silence on 2015/10/25 0025.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "account.db";     //数据库名字
    private static final int DB_VERSION = 1;                //数据库版本号
    private static DBOpenHelper sDBOpenHelper;          //当前实例

    //收入表
    private static final String CREATE_IN_TABLE_SQL = "create table if not exists in_account" +
            "(" +
            "id integer primary key autoincrement," +
            "date varchar(10)," +
            "place varchar(10)," +
            "money decimal," +
            "subject varchar(10)," +
            "note varchar(50)" +
            ")";

    //收入科目表
    private static final String CREATE_IN_SUBJECT_TABLE_SQL = "create table if not exists in_sub" +
            "(" +
            "id integer primary key autoincrement," +
            "subject varchar(10)" +
            ")";

    //支出表
    private static final String CREATE_OUT_TABLE_SQL = "create table if not exists out_account" +
            "(" +
            "id integer primary key autoincrement," +
            "date varchar(10)," +
            "place varchar(10)," +
            "money decimal," +
            "subject varchar(10)," +
            "note varchar(50)" +
            ")";

    //支出科目表
    private static final String CREATE_OUT_SUBJECT_TABLE_SQL = "create table if not exists out_sub" +
            "(" +
            "id integer primary key autoincrement," +
            "subject varchar(10)" +
            ")";

    //账户信息表
    private static final String CREATE_USER_INFO_TABLE_SQL = "create table if not exists user" +
            "(" +
            "id integer primary key autoincrement," +
            "name varchar(10)," +
            "age integer," +
            "mail varchar(20)," +
            "gender varchar(4)," +
            "area varchar(10)" +
            ")";

    //初始化收入科目表
    private static final String CREATE_IN_SUBJECT_DATA_SQL1 = "insert into in_sub (subject) values ('工资')";
    private static final String CREATE_IN_SUBJECT_DATA_SQL2 = "insert into in_sub (subject) values ('兼职')";
    private static final String CREATE_IN_SUBJECT_DATA_SQL3 = "insert into in_sub (subject) values ('奖金')";

    //初始化支出科目表
    private static final String CREATE_OUT_SUBJECT_DATA_SQL1 = "insert into out_sub (subject) values ('用餐')";
    private static final String CREATE_OUT_SUBJECT_DATA_SQL2 = "insert into out_sub (subject) values ('应酬')";
    private static final String CREATE_OUT_SUBJECT_DATA_SQL3 = "insert into out_sub (subject) values ('打的')";

    /**
     * 构造方法
     *
     * @param context context
     */
    private DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DBOpenHelper getInstance(Context context) {
        if (sDBOpenHelper == null) {
            synchronized (DBOpenHelper.class) {
                if (sDBOpenHelper == null) {
                    sDBOpenHelper = new DBOpenHelper(context);
                }
            }
        }
        return sDBOpenHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_IN_TABLE_SQL);
        db.execSQL(CREATE_OUT_TABLE_SQL);
        db.execSQL(CREATE_IN_SUBJECT_TABLE_SQL);
        db.execSQL(CREATE_OUT_SUBJECT_TABLE_SQL);
        db.execSQL(CREATE_USER_INFO_TABLE_SQL);
        db.execSQL(CREATE_IN_SUBJECT_DATA_SQL1);
        db.execSQL(CREATE_IN_SUBJECT_DATA_SQL2);
        db.execSQL(CREATE_IN_SUBJECT_DATA_SQL3);
        db.execSQL(CREATE_OUT_SUBJECT_DATA_SQL1);
        db.execSQL(CREATE_OUT_SUBJECT_DATA_SQL2);
        db.execSQL(CREATE_OUT_SUBJECT_DATA_SQL3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}