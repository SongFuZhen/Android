package com.my_sqlite.song.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 宋福祯 on 15/8/11.
 */
public class DBHelper extends SQLiteOpenHelper {

    String CreateClasses = "create table Classes (class_id varchar(20) primary key," +
            "class_name varchar(20))";
    String CreateStudents = "create table Students (student_id varchar(20) primary key," +
            "student_name varchar(20),score varchar(4),class_id varchar(20)," +
            "foreign key(class_id) references Classes(class_id)" +
            "on delete cascade on update cascade)";

    public DBHelper(Context context) {
        //context，数据库名，null，版本号
        super(context, "My_SQL.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateClasses);
        Log.d("Song", "Create table Classes" + CreateClasses);
        db.execSQL(CreateStudents);
        Log.d("Song", "Create table Students" + CreateStudents);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
