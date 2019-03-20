package com.example.bi4sqlite;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.Nullable;
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

    private Button   btnSave;
    private ListView lvSV;
    private CustomAdapter customAdapter;
    private List<Student> studentList;
    public static final  String ID ="ID";
    public static final  String NAME ="NAME";
    public static final  String NUMBER ="NUMBER";
    public static final  String ADDRESS  ="ADDRESS";
    public static final  String EMAIL ="EMAIL";
    public static final int REQUEST_CODE  = 120;
    public static final int REQUEST_CODE2 = 100;

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
                Intent intent = new Intent(MainActivity.this,addActivity.class);
                intent.putExtra(ID,"");
                startActivityForResult(intent,REQUEST_CODE);

               // Student student = createStudent();
              //  if (!student.getmName().isEmpty() && !student.getmEmail().isEmpty()) {
              //      dbManager.addStudent(student);
               //     studentList.clear();
               //     studentList.addAll(dbManager.getAllStudent());
               //     setAdapter();
               // }else{
               //     Toast.makeText(MainActivity.this, "Họ tên và email không được bỏ trống", Toast.LENGTH_SHORT).show();
               // }
            }
        });
        lvSV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student student = studentList.get(position);
                Intent intent = new Intent(MainActivity.this,addActivity.class);
                intent.putExtra(ID,String.valueOf(student.getmID()));
                intent.putExtra(NAME,String.valueOf(student.getmName()));
                intent.putExtra(ADDRESS,student.getmAddress().toString());
                intent.putExtra(NUMBER,student.getmPhoneNumber().toString());
                intent.putExtra(EMAIL,student.getmEmail().toString());
                startActivityForResult(intent,REQUEST_CODE);

            }
        });

        lvSV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setTitle("Thông báo");
                dialog.setContentView(R.layout.show_dialog);
                dialog.setCancelable(false);
                Button btn_dong_y = (Button) dialog.findViewById(R.id.btn_dong_y);
                Button btn_bo_qua = (Button) dialog.findViewById(R.id.btn_bo_qua);
                btn_dong_y.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Student student = studentList.get(position);
                        int KQ = dbManager.deleteStudent(student.getmID());
                        if (KQ > 0){
                            Toast.makeText(MainActivity.this, "Đã xoá thành công", Toast.LENGTH_SHORT).show();
                            studentList.clear();
                            studentList.addAll(dbManager.getAllStudent());
                            if (customAdapter != null){
                                customAdapter.notifyDataSetChanged();
                            }
                        }
                        dialog.dismiss();
                    }
                });
                btn_bo_qua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return false;
            }
        });
    }
    private void iniWidget(){
        btnSave    = findViewById(R.id.btn_Save);
        lvSV       = findViewById(R.id.lv_SV);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DBManager dbManager = new DBManager(this);
        //requestCode trả về bằng hàm khai báo REQUEST_CODE trường hợp có nhiều REQUEST_CODE trả về cần phải kiểm tra
        //resultCode giá trị từ bên ActivityOutput trả về với định nghĩa trước đó là RESULT_PIN = 20
        if(requestCode == REQUEST_CODE ){
            switch (resultCode){
                case addActivity.RESULT_PIN:
                    studentList.clear();
                    studentList.addAll(dbManager.getAllStudent());
                    setAdapter();
                    break;
                default:
                    break;
            }
        }
    }

}
