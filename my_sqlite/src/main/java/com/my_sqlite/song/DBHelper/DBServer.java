package com.my_sqlite.song.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 宋福祯 on 15/8/11.
 */
public class DBServer {
    private DBHelper dbHelper;

    public DBServer(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    //添加班级
    public void addClass(Classes classes) {
        String insertCla = "insert into Classes(class_id,class_name)" +
                "values(?,?)";
        SQLiteDatabase sqLiteDatabase = this.dbHelper.getWritableDatabase();
        Object[] objects = new Object[2];
        objects[0] = classes.getClass_id();
        objects[1] = classes.getClass_name();
        sqLiteDatabase.execSQL(insertCla, objects);
        sqLiteDatabase.close();
    }

    //添加学生
    public void AddStudents(Students students) {
        //插入语句
        String insertStu = "insert into Students(student_id,student_name,score,class_id) " +
                "values(?,?,?,?)";
        //使之可写
        SQLiteDatabase sqLiteDatabase = this.dbHelper.getWritableDatabase();
        //定义数组，使之存储数据
        Object[] object = new Object[4];
        object[0] = students.getStudent_id();
        object[1] = students.getStudent_name();
        object[2] = students.getScore();
        object[3] = students.getClass_id();
        //数据库执行语句
        sqLiteDatabase.execSQL(insertStu, object);
        //关闭数据库
        sqLiteDatabase.close();
    }

}
