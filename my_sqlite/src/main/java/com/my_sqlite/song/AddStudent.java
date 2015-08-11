package com.my_sqlite.song;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.my_sqlite.song.DBHelper.Classes;
import com.my_sqlite.song.DBHelper.DBServer;
import com.my_sqlite.song.DBHelper.Students;

import java.util.Random;


public class AddStudent extends ActionBarActivity implements View.OnClickListener {

    private Button reset, confirm;
    private EditText classid, studentid, studentname, score;
    private DBServer dbServer;
    private static final String ClassName = "A/B/C/D";
    private static final String StudentName = "张阿三/李王思/吴八丹/证六/田七/孔氏以";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        initView();
        sharedPreferences = getSharedPreferences("DataBaseDemo", 0);
        editor = sharedPreferences.edit();
        dbServer = new DBServer(this);
        if (sharedPreferences.getInt("times", 0) == 0) {
            initDatabase();
            editor.putInt("times", 1);
            editor.commit();
        }
    }

    private void initDatabase() {
        String[] classtemp = ClassName.split("/");
        Classes classes;
        for (int i = 0; i < classtemp.length; i++) {
            classes = new Classes();
            classes.setClass_id("00" + i);
            classes.setClass_name(classtemp[i]);
            dbServer.addClass(classes);
            Log.i("Song", "Add to Classes" + classes.toString());
        }

        String[] studenttemp = StudentName.split("/");
        Students students;
        for (int j = 0; j < studenttemp.length; j++) {
            students = new Students();
            students.setStudent_id("20150811" + j);
            students.setStudent_name(studenttemp[j]);
            students.setScore(String.valueOf(new Random().nextInt(100) + 1));
            dbServer.AddStudents(students);
            Log.i("Song", "Add to Students" + students.toString());
        }
    }

    private void initView() {

        classid = (EditText) findViewById(R.id.edtclassid);
        studentid = (EditText) findViewById(R.id.edtstudentid);
        studentname = (EditText) findViewById(R.id.edtstudentname);
        score = (EditText) findViewById(R.id.edtscore);

        reset = (Button) findViewById(R.id.reset);
        confirm = (Button) findViewById(R.id.confirm);

        reset.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.reset:
                classid.setText("");
                studentid.setText("");
                studentname.setText("");
                score.setText("");
                break;
            case R.id.confirm:
                AddStudent();
                break;
            default:
                break;
        }
    }

    //添加学生
    public void AddStudent() {
        String tempclassid = classid.getText().toString().trim();
        String tempstudentid = studentid.getText().toString().trim();
        String tempstudentname = studentname.getText().toString().trim();
        String tempscore = score.getText().toString().trim();

        if (CheckInfo(tempclassid) && CheckInfo(tempstudentid)
                && CheckInfo(tempstudentname) && CheckInfo(tempscore)) {
            //调用Students实体
            Students temp = new Students();
            temp.setClass_id(tempclassid);
            temp.setStudent_id(tempstudentid);
            temp.setStudent_name(tempstudentname);
            temp.setScore(tempscore);
            dbServer.AddStudents(temp);

            Intent intent = new Intent();
            intent.putExtra("tempinfo", temp.toString());
            intent.putExtra("tempclassid", tempclassid.toString());
            intent.putExtra("tempstudentid", tempstudentid.toString());
            intent.putExtra("tempstudentname", tempstudentname.toString());
            intent.putExtra("tempscore", tempscore.toString());
            intent.setClass(AddStudent.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(AddStudent.this, "添加成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddStudent.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    //判断是否为空
    public boolean CheckInfo(String s) {
        if (s.equals("") || s == null)
            return false;
        else
            return true;
    }


}
