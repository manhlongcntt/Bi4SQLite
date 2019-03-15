package com.example.bi4sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bi4sqlite.adapter.CustomAdapter;
import com.example.bi4sqlite.data.DBManager;
import com.example.bi4sqlite.model.Student;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText edtName, edtNumber, edtAddress,edtEmail,edtId;
    private Button   btnSave,btnUpdata;
    private ListView lvSV;
    private CustomAdapter customAdapter;
    private List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniWidget();
        final DBManager dbManager = new DBManager(this);
        studentList = dbManager.getAllStudent();
        setAdapter();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = createStudent();
                if (student != null) {
                    dbManager.addStudent(student);
                }
                studentList.clear();
                studentList.addAll(dbManager.getAllStudent());
                setAdapter();
            }
        });
        lvSV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student student = studentList.get(position);
                edtId.setText(String.valueOf(student.getmID()));
                edtName.setText(student.getmName());
                edtNumber.setText(student.getmPhoneNumber());
                edtAddress.setText(student.getmAddress());
                edtEmail.setText(student.getmEmail());
                btnSave.setEnabled(false);
                btnUpdata.setEnabled(true);
            }
        });
        btnUpdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student();
                student.setmID(Integer.parseInt(edtId.getText().toString()));
                student.setmName(edtName.getText().toString());
                student.setmPhoneNumber(edtNumber.getText().toString());
                student.setmEmail(edtEmail.getText().toString());
                student.setmAddress(edtAddress.getText().toString());
                int ketQua = dbManager.upDataStudent(student);
                if (ketQua > 0) {
                    studentList.clear();
                    studentList.addAll(dbManager.getAllStudent());
                    if (customAdapter != null){
                        customAdapter.notifyDataSetChanged();
                    }
                }
                btnSave.setEnabled(true);
                btnUpdata.setEnabled(false);
            }
        });
        lvSV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Student student = studentList.get(position);
                int KQ = dbManager.deleteStudent(student.getmID());
                if (KQ > 0){
                    Toast.makeText(MainActivity.this, "Dã xoá thành công", Toast.LENGTH_SHORT).show();
                    studentList.clear();
                    studentList.addAll(dbManager.getAllStudent());
                    if (customAdapter != null){
                        customAdapter.notifyDataSetChanged();
                    }
                }
                btnSave.setEnabled(true);
                btnUpdata.setEnabled(false);
                return false;
            }
        });
    }
    private void iniWidget(){
        edtName    = findViewById(R.id.edt_Name);
        edtId      = findViewById(R.id.edt_Id);
        edtNumber  = findViewById(R.id.edt_Number);
        edtAddress = findViewById(R.id.edt_Address);
        edtEmail   = findViewById(R.id.edt_Email);
        btnSave    = findViewById(R.id.btn_Save);
        btnUpdata  = findViewById(R.id.btn_Updata);
        lvSV       = findViewById(R.id.lv_SV);
    }
    private void clickEvent(){

    }
    private Student createStudent(){
        String name         = edtName.getText().toString();
        String address      = String.valueOf(edtAddress.getText());
        String phone        = edtNumber.getText()+"";
        String email        = edtEmail.getText().toString();
        Student student     = new Student(name,address,phone,email);
        return student;

    }

    private void setAdapter(){
        if (customAdapter == null){
            customAdapter = new CustomAdapter(this,R.layout.items_list_student,studentList);
            lvSV.setAdapter(customAdapter);
        }else{
            customAdapter.notifyDataSetChanged();
            lvSV.setSelection(customAdapter.getCount()-1);
        }
    }
    public void updataStudent(){

    }
}
