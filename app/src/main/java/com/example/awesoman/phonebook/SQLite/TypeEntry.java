package com.example.awesoman.phonebook.SQLite;

import android.provider.BaseColumns;

/**
 * Created by Awesome on 2016/9/2.
 */
public class TypeEntry implements BaseColumns {
    public static final String DATABASE_PATH = "/data/data/com.example.awesoman.phonebook/databases";
    //表名
    public static final String TABLE_NAME = "PhoneType";
    //列名
    public static final String COLUMNS_NAME_TYPE = "type";
    public static final String COLUMNS_NAME_SUBTABLE = "subtable";
    public static final String COLUMNS_PHONETYPE_NAME="name";
    public static final String COLUMNS_PHONETYPE_NUM="phoneNum";    //创建表格的SQL语句
    public static final String SQL_CREATE_TABLE =
            "create table " + TABLE_NAME + " (" +
                    _ID + " integer primary key," +
                    COLUMNS_NAME_TYPE + " text," +
                    COLUMNS_NAME_SUBTABLE + " text" + ")";
    //删除表格的SQL语句
    public static final String SQL_DELETE_TABLE =
            "drop table if exists " + TABLE_NAME;


    public static final String SUB_CATERING="CantinaService";//订餐电话
    public static final String SUB_PUBLIC_SERVICE="PublicService";//公共服务
    public static final String SUB_EXPRESSAGE="Expressage";//快递服务

    public static String[] PhoneTypes = new String[]{SUB_CATERING,SUB_PUBLIC_SERVICE,SUB_EXPRESSAGE};

}

