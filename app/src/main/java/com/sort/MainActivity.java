package com.sort;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    private EditText edit_sortnr;
    private Button btn_scannr;
    private Button btn_enter;
    private long exitTime = 0;
    String Edit_sortnr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_sortnr = (EditText) findViewById(R.id.edit_sortnr);
        Edit_sortnr = edit_sortnr.getText().toString().trim();

        btn_scannr = (Button) findViewById(R.id.btn_scannr);
        btn_scannr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String RandomNr = GetRadomNr(5);
                edit_sortnr.setText(RandomNr + "\n");
                Edit_sortnr = RandomNr + "\n";
                if (!Edit_sortnr.isEmpty() && Edit_sortnr.substring(Edit_sortnr.length() - 1).equals("\n")) {
                    IntentToSortCheckActivity(Edit_sortnr);
                }
            }
        });

        btn_enter = (Button) findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_sortnr = edit_sortnr.getText().toString().trim();
                if (Edit_sortnr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "请输入排序单号", Toast.LENGTH_SHORT).show();
                } else {
                    IntentToSortCheckActivity(Edit_sortnr);
                }
            }
        });

        //软键盘的enter键
        edit_sortnr.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Edit_sortnr = edit_sortnr.getText().toString().trim();
                IntentToSortCheckActivity(Edit_sortnr);
                return false;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
                return false;
            } else {
                finish();
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void IntentToSortCheckActivity(String value) {
        Intent intent = new Intent(MainActivity.this, SortCheckActivity.class);
        intent.putExtra("Edit_SortNr", value);
        startActivity(intent);
        finish();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static String GetRadomNr(int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append((char) (ThreadLocalRandom.current().nextInt(48, 128)));
        }
        return builder.toString();
    }
}
