package com.example.bi4sqlite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.widget.Toast;

import com.example.bi4sqlite.model.Student;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.jar.Attributes;

public class DBManager extends SQLiteOpenHelper {

    private static final  String DATABASE_NAME = "students_manager";
    private static final  String TABLE_NAME    = "students";
    private static final  String ID            = "id";
    private static final  String NAME          = "name";
    private static final  String PHONE_NUMBER  = "phone";
    private static final  String ADDRESS       = "address";
    private static final  String EMAIL         = "email";
    private static final  int VERSION          = 1;
    private Context context;
    private  String SQLQuery = "CREATE TABLE " +TABLE_NAME+" ("+
            ID    +"  integer primary key, " +
            NAME  +  " TEXT, "+
            EMAIL +  " TEXT, "+
            PHONE_NUMBER + " TEXT, "+
            ADDRESS+ " TEXT)";

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addStudent(Student student){
        SQLiteDatabase  db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME,student.getmName());
        values.put(ADDRESS,student.getmAddress());
        values.put(EMAIL,student.getmEmail());
        values.put(PHONE_NUMBER,student.getmPhoneNumber());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public int upDataStudent(Student student) {

       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues contentValues = new  ContentValues();
       contentValues.put(NAME,student.getmName());
       contentValues.put(EMAIL,student.getmEmail());
       contentValues.put(PHONE_NUMBER,student.getmPhoneNumber());
       contentValues.put(ADDRESS,student.getmAddress());
       int KQ =  db.update(TABLE_NAME,contentValues,ID+"=?",new String[]{String.valueOf(student.getmID())});
       // cách thứ 2
       // String SQLQueryUpdata = "UPDATA ......";
        //db.execSQL(SQLQueryUpdata);
       db.close();
       return KQ;
    }
    public int deleteStudent(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        int kQ = db.delete(TABLE_NAME,ID+ "=?",new String[]{String.valueOf(id)});
        db.close();
        return kQ;

    }

    public List<Student> getAllStudent(){

        List<Student> listStudent = new ArrayList<>();
        String selectQuery ="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do{
                Student student = new Student();
                student.setmID(cursor.getInt(0));
                student.setmName(cursor.getString(1));
                student.setmEmail(cursor.getString(2));
                student.setmPhoneNumber(cursor.getString(3));
                student.setmAddress(cursor.getString(4));
                listStudent.add(student);
            }while (cursor.moveToNext());

        }
        db.close();
        return listStudent;
    }

}
