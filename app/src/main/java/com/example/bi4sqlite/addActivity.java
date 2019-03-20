package com.example.bi4sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bi4sqlite.data.DBManager;
import com.example.bi4sqlite.model.Student;

public class addActivity extends AppCompatActivity {
    private EditText edtId, edtName,edtEmail,edtNumber,edtAddress;
    private Button btnAdd, btnClose;
    public static final int RESULT_PIN = 20;
    public static final int RESULT_PIN1 = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        iniWidget();
        getData();
        final DBManager dbManager = new DBManager(this);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = createStudent();
                if (TextUtils.isEmpty(edtId.getText().toString()) ){
                   // tạo mới
                    if (!student.getmName().isEmpty() && !student.getmEmail().isEmpty()) {
                        dbManager.addStudent(student);
                        Toast.makeText(addActivity.this, " Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                        //đóng form AddActivity trả về Main
                        setResult(RESULT_PIN);
                        finish();
                    }else{
                        Toast.makeText(addActivity.this, "Họ tên và email không được bỏ trống", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    //Updata
                    if (!student.getmName().isEmpty() && !student.getmEmail().isEmpty()) {
                        int ketQua = dbManager.upDataStudent(student);
                        if (ketQua > 0) {
                            Toast.makeText(addActivity.this, "updata thành công", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_PIN);
                            finish();
                        }
                    }else{
                        Toast.makeText(addActivity.this, "Họ tên và email không được bỏ trống", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_PIN1);
                finish();
            }
        });

    }
    private void iniWidget(){
        edtName    = findViewById(R.id.edt_Name);
        edtId      = findViewById(R.id.edt_Id);
        edtNumber  = findViewById(R.id.edt_Number);
        edtAddress = findViewById(R.id.edt_Address);
        edtEmail   = findViewById(R.id.edt_Email);
        btnAdd     = findViewById(R.id.btn_All);
        btnClose   = findViewById(R.id.btn_Close);

    }
    public void getData(){

        Intent intent = getIntent();
        edtId.setText(intent.getStringExtra(MainActivity.ID));
        edtName.setText(intent.getStringExtra(MainActivity.NAME));
        edtAddress.setText(intent.getStringExtra(MainActivity.ADDRESS));
        edtEmail.setText(intent.getStringExtra(MainActivity.EMAIL));
        edtNumber.setText(intent.getStringExtra(MainActivity.NUMBER));

    }
    private Student createStudent(){

        int id              = Integer.parseInt(edtId.getText().toString());
        String name         = edtName.getText().toString();
        String address      = String.valueOf(edtAddress.getText());
        String phone        = edtNumber.getText()+"";
        String email        = edtEmail.getText().toString();
        Student student     = new Student(id,name,address,phone,email);
        return student;
    }
}
