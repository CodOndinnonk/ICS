package com.example.vakery.ics;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.vakery.ics.DB.DatabaseHandler;
import com.example.vakery.ics.Functional.MyToolbar;
import com.example.vakery.ics.Functional.Vars;

public class LecturerInfoActivity extends MyToolbar {
    final String myLog = "myLog";
    DatabaseHandler db;
    int lecturerId;
    TextView lecturerName, lecturerContacts;
    //название активити отображаемое в Toolbar
    String activityTitle = Vars.getContext().getString(R.string.detail_information_text);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_info);

//        //создание Toolbar
        this.createToolbar(activityTitle);

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


}
