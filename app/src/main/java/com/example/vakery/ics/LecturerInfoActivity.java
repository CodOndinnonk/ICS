package com.example.vakery.ics;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.vakery.ics.DB.DatabaseHandler;
import com.example.vakery.ics.Functional.MyToolbar;

public class LecturerInfoActivity extends AppCompatActivity {
    final String myLog = "myLog";
    DatabaseHandler db;
    int lecturerId;
    TextView lecturerName, lecturerContacts;
    MyToolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_info);

        //создание Toolbar
        toolbar = new MyToolbar(this);

        //заполнение полей
        lecturerName = (TextView)findViewById(R.id.lecturerInfoName);
        lecturerContacts = (TextView)findViewById(R.id.lecturerInfoContacts);

        db = new DatabaseHandler(this);
        Intent intent = getIntent();
        //извлекаем переданное id для получения данных
        lecturerId = intent.getIntExtra("id",999);

        setInfo();
    }


    public void setInfo(){

        Cursor cursor = db.getPersonalSubject(lecturerId);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String lecturer = cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SURNAME)) + " " +
                            cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_NAME)) + " " +
                            cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PATRONYMIC));
                    lecturerName.setText(lecturer);
//                    lecturerContacts.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_CONTACTS)));
                } while (cursor.moveToNext());
            }
        } else {
            Log.d(myLog, "Cursor is null");
        }
    }


    @Override
    public void onBackPressed() {
        // сообщаем в MyToolbar что нажали НАЗАД, если меню открыто, то оно свернется и вернет false, если меню не открыто, то вернется true
        if(toolbar.onBackPressed()){
            //если менб небыло открыто(выполняем действие НАЗАД)
            super.onBackPressed();
        }
    }


}
