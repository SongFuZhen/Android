package com.sort;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class SortCheckActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText StorageLocation, StorageValue;
    private Button Btn_Check;
    private TextView txt_sequenceID;
    private GridView gridView;

    private Button btn_scanlocation, btn_scanlocationvalue, btn_scanlocationwrong, btn_scanlocationvaluewrong;
    int[] StorageLocationTOGV = {1, 2, 3, 4, 5, 6};
    int[] StorageLocationValueTOGV = {45162, 45183, 45184, 45185, 45173, 45176};
    int[] ImageTOGV = {R.drawable.right, R.drawable.error};
    ArrayList<HashMap<String, Integer>> arrayList = new ArrayList<HashMap<String, Integer>>();

    //如果计数到达0，则表示所有的库位都检查完成
    int count = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_check);

        String GetSortNr = getIntent().getStringExtra("Edit_SortNr").trim();
        txt_sequenceID = (TextView) findViewById(R.id.txt_sequenceID);
        txt_sequenceID.setText(GetSortNr);

        gridView = (GridView) findViewById(R.id.GV_Show);
        //写数据到arraylist。
        PutValueForArr();
        //绘制图形
        DrawGrid();

        StorageLocation = (EditText) findViewById(R.id.StorageLocation);
        StorageValue = (EditText) findViewById(R.id.StorageValue);
        StorageLocation.requestFocus();

        Btn_Check = (Button) findViewById(R.id.btn_check);
        Btn_Check.setOnClickListener(this);
        btn_scanlocation = (Button) findViewById(R.id.btn_scanlocation);
        btn_scanlocation.setOnClickListener(this);
        btn_scanlocationvalue = (Button) findViewById(R.id.btn_scanlocationvalue);
        btn_scanlocationvalue.setOnClickListener(this);
        btn_scanlocationvaluewrong = (Button) findViewById(R.id.btn_scanlocationvaluewrong);
        btn_scanlocationvaluewrong.setOnClickListener(this);
        btn_scanlocationwrong = (Button) findViewById(R.id.btn_scanlocationwrong);
        btn_scanlocationwrong.setOnClickListener(this);
        //此处为输入Enter键的时候执行代码
        StorageValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                final String GetStorageLocation = StorageLocation.getText().toString().trim();
                final String GetStorageValue = StorageValue.getText().toString();
                //判断是否为0
                JudgeLogic(GetStorageLocation, GetStorageValue);
                return false;
            }
        });

    }

    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 点击按钮，进行判断。
             * 1、输入框是否为空
             * 2、输入框不为空，判断库位号是否存在。
             * 3、库位号存在，库位值是否正确
             */
            case R.id.btn_check:
                final String GetStorageLocation = StorageLocation.getText().toString().trim();
                final String GetStorageValue = StorageValue.getText().toString();
                //判断是否为0
                JudgeLogic(GetStorageLocation, GetStorageValue);
                break;
            case R.id.btn_scanlocation:
                int Location = StorageLocationTOGV[(int) (Math.random() * 6)];
                StorageLocation.setText(Location + "\n");
                String GetRadomStorageLocation = StorageLocation.getText().toString();
                if (!GetRadomStorageLocation.isEmpty() && GetRadomStorageLocation.substring(GetRadomStorageLocation.length() - 1).equals("\n")) {
                    StorageValue.requestFocus();
                }
                break;
            case R.id.btn_scanlocationwrong:
                int WrongLocation = (int) (Math.random() * 100);
                StorageLocation.setText(WrongLocation + "\n");
                String GetRadomStorageLocationWrong = StorageLocation.getText().toString();
                if (!GetRadomStorageLocationWrong.isEmpty() && GetRadomStorageLocationWrong.substring(GetRadomStorageLocationWrong.length() - 1).equals("\n")) {
                    StorageValue.requestFocus();
                }
                break;
            case R.id.btn_scanlocationvalue:
                String GetScanLocation = StorageLocation.getText().toString().trim();

                if (!GetScanLocation.isEmpty()) {
                    final int GetScanStorageLocation = Integer.parseInt(GetScanLocation);

                    for (int i = 0; i < arrayList.size(); i++) {
                        if (GetScanStorageLocation == (arrayList.get(i).get("StorageLocationGV"))) {
                            int Value = arrayList.get(i).get("StorageLocationValueGV");
                            StorageValue.setText(Value + "\n");
                            break;
                        } else {
                            if (i == arrayList.size() - 1) {
                                Toast.makeText(SortCheckActivity.this, "没找到库位号", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("用1s的时间来显示库位值");
                            String GetScanStorageValue = StorageValue.getText().toString();
                            System.out.println("---->" + GetScanStorageValue.length());
                            if (!GetScanStorageValue.isEmpty() && GetScanStorageValue.substring(GetScanStorageValue.length() - 1).equals("\n")) {
                                JudgeLogic(GetScanStorageLocation + "", GetScanStorageValue);
                            }
                        }
                    }, 1000);
                } else {
                    StorageLocation.requestFocus();
                    Toast.makeText(SortCheckActivity.this, "库位号不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_scanlocationvaluewrong:
                final String GetScanStorageLocationWrong = StorageLocation.getText().toString().trim();
                if (!GetScanStorageLocationWrong.isEmpty()) {
                    String RadomSLVWrong = (int) (Math.random() * 80000) + "\n";
                    StorageValue.setText(RadomSLVWrong);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("用1s来显示随机的值");
                            String GetScanStorageLocationValueWrong = StorageValue.getText().toString();
                            if (!GetScanStorageLocationValueWrong.isEmpty() &&
                                    GetScanStorageLocationValueWrong.substring(GetScanStorageLocationValueWrong.length() - 1).equals("\n")) {
                                JudgeLogic(GetScanStorageLocationWrong, GetScanStorageLocationValueWrong);
                            }
                        }
                    }, 1000);
                } else {
                    StorageLocation.requestFocus();
                    Toast.makeText(SortCheckActivity.this, "库位号不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    //绘制Grid
    private void DrawGrid() {
        SimpleAdapter adapter = new SimpleAdapter(this, arrayList, R.layout.gridview,
                new String[]{"StorageLocationGV", "Image", "StorageLocationValueGV"}, new int[]{
                R.id.GV_Location, R.id.GV_icon, R.id.GV_LocationValue});
        gridView.setAdapter(adapter);
    }

    //为ArrayList 添加数据
    private void PutValueForArr() {
        for (int i = 0; i < 6; i++) {
            HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
            hashMap.put("StorageLocationGV", StorageLocationTOGV[i]);
            hashMap.put("Image", ImageTOGV[1]);
            hashMap.put("StorageLocationValueGV", StorageLocationValueTOGV[i]);

            arrayList.add(hashMap);
        }
    }

    //清空输入框的值，并且设置焦点
    private void ClearInput(EditText editText1, EditText editText2) {
        editText1.setText("");
        editText2.setText("");
        editText1.requestFocus();
    }

    //展示对话框
    private void ShowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SortCheckActivity.this);
        builder.setTitle(getText(R.string.CheckOver))
                .setMessage(getText(R.string.CheckMsg))
                .setNegativeButton("NO", null)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SortCheckActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }

    //逻辑判断
    private void JudgeLogic(String Location, String value) {
        if (!Location.isEmpty()) {
            if (!value.isEmpty()) {
                final int GetLocation = Integer.parseInt(Location);
                final int GetValue = Integer.parseInt(value.trim());

                for (int i = 0; i < arrayList.size(); i++) {
                    if (GetLocation == arrayList.get(i).get("StorageLocationGV")) {
                        if (arrayList.get(i).get("StorageLocationValueGV") == GetValue) {
                            /**
                             * 判断是否检查
                             * 通过图片进行判断，如果是未检查的，则count是否减一。
                             * 如果是检查过的，count不变，进行提醒。输入框清空，设置输入框焦点,并且退出循环
                             */
                            if (!(arrayList.get(i).get("Image").toString().equals("2130837568"))) {
                                count--;
                            } else {
                                ClearInput(StorageLocation, StorageValue);
                                Toast.makeText(SortCheckActivity.this, "此库位已检查", Toast.LENGTH_SHORT).show();
                                break;
                            }

                            //改变图片样式
                            HashMap<String, Integer> changeimg = new HashMap<String, Integer>();
                            changeimg.put("StorageLocationGV", GetLocation);
                            changeimg.put("Image", ImageTOGV[0]);
                            changeimg.put("StorageLocationValueGV", GetValue);
                            arrayList.set(i, changeimg);
                            //重新绘制Grid
                            DrawGrid();

                            //清空输入框，设置焦点
                            ClearInput(StorageLocation, StorageValue);

                            //count变为0之后显示对话框
                            if (count == 0) {
                                ShowDialog();
                            }

                            break;
                        } else {
                            Toast.makeText(SortCheckActivity.this, "有库位，但是值不对", Toast.LENGTH_SHORT).show();
                            StorageValue.setText("");
                            break;
                        }
                    } else {
                        if (i == (arrayList.size() - 1)) {
                            Toast.makeText(SortCheckActivity.this, "没找到库位", Toast.LENGTH_SHORT).show();
                            ClearInput(StorageLocation, StorageValue);
                        }
                    }
                }

            } else {
                Toast.makeText(SortCheckActivity.this, "库位值不能为空", Toast.LENGTH_SHORT).show();
                StorageValue.requestFocus();
            }
        } else {
            Toast.makeText(SortCheckActivity.this, "库位号不能为空", Toast.LENGTH_SHORT).show();
            StorageLocation.requestFocus();
        }
    }


}
