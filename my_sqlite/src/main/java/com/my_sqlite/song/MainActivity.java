package com.my_sqlite.song;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private Button AddStudent;
    private TextView showinfo;

    private String tempinfo = "";
    private String tempclassid = "";
    private String tempstudentid = "";
    private String tempstudentname = "";
    private String tempscore = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddStudent = (Button) findViewById(R.id.AddStudent);
        AddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AddStudent.class);
                startActivity(intent);
            }
        });

        //获取添加界面的值
        Intent intent = getIntent();
        tempinfo = intent.getStringExtra("tempinfo");
        tempclassid = intent.getStringExtra("tempclassid");
        tempstudentid = intent.getStringExtra("tempstudentid");
        tempstudentname = intent.getStringExtra("tempstudentname");
        tempscore = intent.getStringExtra("tempscore");

        showinfo = (TextView) findViewById(R.id.showinfo);
        showinfo.setText("信息：" + tempinfo);
        showinfo.setText("班级ID：" + tempclassid + "\n" + "学生ID：" + tempstudentid + "\n"
                + "学生名称：" + tempstudentname + "\n" + "成绩：" + tempscore + "\n");
    }

}
