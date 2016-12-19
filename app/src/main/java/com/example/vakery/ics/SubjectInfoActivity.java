package com.example.vakery.ics;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.vakery.ics.DB.DatabaseHandler;
import com.example.vakery.ics.Functional.MyToolbar;
import com.example.vakery.ics.Functional.Vars;


public class SubjectInfoActivity extends AppCompatActivity {
    final String myLog = "myLog";
    DatabaseHandler db;
    int subjectId;
    int kindOfSubject;//0-персональный предмет(из расписания), 1-предмет кафедры, взятый из списка всех предметов
    int lecturerId;
    TextView subjectName, lecturerInfo, type, roomOrSemesterText,roomOrSemester , extraInfoText, extraInfo ;
    MyToolbar toolbar;
    //название активити отображаемое в Toolbar
    String activityTitle = Vars.getContext().getString(R.string.detail_information_text);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_info);

        //создание Toolbar
        toolbar = new MyToolbar(this,activityTitle);

        //заполнение полей
        subjectName = (TextView)findViewById(R.id.subjectInfoSubjectName);
        lecturerInfo = (TextView)findViewById(R.id.subjectInfoLecturer);
        type = (TextView)findViewById(R.id.subjectInfoType);
        roomOrSemesterText = (TextView)findViewById(R.id.subjectInfoRoomOrSemesterInfoText);
        roomOrSemester = (TextView)findViewById(R.id.subjectInfoRoomOrSemester);
        extraInfoText = (TextView)findViewById(R.id.subjectInfoExtraInfoText);
        extraInfo = (TextView)findViewById(R.id.subjectInfoExtraInfo);

        lecturerInfo.setOnClickListener(onLecturerClickListener);

        db = new DatabaseHandler(this);
        Intent intent = getIntent();
        //извлекаем переданное id для получения данных
        Cursor cursor = null;
        if(intent.getStringExtra("subjectKind").equals("personal")) {
            kindOfSubject = 0;//0-персональный предмет(из расписания)
            roomOrSemesterText.setText(R.string.room_of_subject);
            extraInfoText.setEnabled(false);
            extraInfoText.setVisibility(View.INVISIBLE);
            extraInfo.setEnabled(false);
            extraInfo.setVisibility(View.INVISIBLE);
            subjectId = intent.getIntExtra("id", 999);
            Log.d(myLog,"SubjectInfoActivity предмет = personal  id =" + subjectId);
            cursor = db.getPersonalSubject(subjectId);
        }else if(intent.getStringExtra("subjectKind").equals("ICS")){
            kindOfSubject = 1;//1-предмет кафедры, взятый из списка всех предметов
            roomOrSemesterText.setText(R.string.semesters);
            extraInfoText.setEnabled(true);
            extraInfoText.setVisibility(View.VISIBLE);
            extraInfoText.setText(R.string.extra_info);
            extraInfo.setEnabled(true);
            extraInfo.setVisibility(View.VISIBLE);
            subjectId = intent.getIntExtra("id", 999);
            Log.d(myLog,"SubjectInfoActivity предмет = ICS  id =" + subjectId);
            cursor = db.getICSSubject(subjectId);
        }
        if(cursor != null)
        setInfo(cursor);
    }


    public void setInfo(Cursor cursor){
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    if(kindOfSubject == 0) {//из расписания
                        lecturerId = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_LECTURER_ID));
                        subjectName.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_FULL_TITLE)));
                        String lecturer = cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SURNAME)) + " " +
                                cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_NAME)) + " " +
                                cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PATRONYMIC));
                        lecturerInfo.setText(lecturer);
                        type.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_TYPE_OF_SUBJECT)));
                        roomOrSemester.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_ROOM_NUMBER)));
                    }else if (kindOfSubject == 1) {//из списка всех предметов кафедры
                        lecturerId = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_LECTURER_ID));
                        subjectName.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_TITLE)));
                        String lecturer = cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SURNAME)) + " " +
                                cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_NAME)) + " " +
                                cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PATRONYMIC));
                        lecturerInfo.setText(lecturer);
                        type.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_KIND)));
                        roomOrSemester.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SEMESTERS)));
                        extraInfo.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SUBJECT_INFO)));
                    }
                } while (cursor.moveToNext());
            }
        } else {
            Log.d(myLog, "Cursor is null");
        }
    }

    View.OnClickListener onLecturerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent go = new Intent(getApplicationContext(), LecturerInfoActivity.class);
            //передаем с интентом id выбранного преподавателя
            go.putExtra("id", lecturerId);
            startActivity(go);
        }
    };


    @Override
    public void onBackPressed() {
        // сообщаем в MyToolbar что нажали НАЗАД, если меню открыто, то оно свернется и вернет false, если меню не открыто, то вернется true
        if(toolbar.onBackPressed()){
            //если менб небыло открыто(выполняем действие НАЗАД)
            super.onBackPressed();
        }

    }


}
