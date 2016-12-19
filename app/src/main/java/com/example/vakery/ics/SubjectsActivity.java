package com.example.vakery.ics;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.vakery.ics.DB.DatabaseHandler;
import com.example.vakery.ics.Functional.MyToolbar;
import com.example.vakery.ics.Functional.Vars;
import com.example.vakery.ics.ListAdapters.SubjectsListAdapter;


import java.util.ArrayList;

import com.example.vakery.ics.Entities.SubjectForSubjectsList;

public class SubjectsActivity extends AppCompatActivity {
    final String myLog = "myLog";
    DatabaseHandler db;
    ListView lvSubjects;
    ArrayList<SubjectForSubjectsList> listOfSubjects = new ArrayList<SubjectForSubjectsList>();
    SubjectsListAdapter listAdapter;
    MyToolbar toolbar;
    //название активити отображаемое в Toolbar
    String activityTitle = Vars.getContext().getString(R.string.drawer_item_subjects);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        //создание Toolbar
        toolbar = new MyToolbar(this,activityTitle);

        db = new DatabaseHandler(this);

        lvSubjects = (ListView)findViewById(R.id.listViewOfSubjects);

        //слушатель нажатия на пункт списка расписания
        lvSubjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //создание интента для перехода из не активити
                Intent intent = new Intent(Vars.getContext(), SubjectInfoActivity.class);
                intent.putExtra("id", listOfSubjects.get(position).getmId());
                intent.putExtra("subjectKind", "ICS");
                startActivity(intent);
                }
            }
        );

        fillList();

        //назначаем адаптер для списка с расписанием
        listAdapter = new SubjectsListAdapter(this.getApplicationContext(), listOfSubjects);
        lvSubjects.setAdapter(listAdapter);

    }


    //заполнение списка предметов
    public void fillList(){
        //очищаем от предыдущих данных, на случай обновления
        listOfSubjects.clear();

        Cursor cursor = db.getICSSubjects();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    SubjectForSubjectsList subjectForSubjectsList = new SubjectForSubjectsList();

                    subjectForSubjectsList.setmId(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_ICS_SUBJECT_ID)));
                    subjectForSubjectsList.setmTitle(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_TITLE)));
                    subjectForSubjectsList.setmLecturerId(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_LECTURER_ID)));
                    subjectForSubjectsList.setmSemesters(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SEMESTERS)));
                    subjectForSubjectsList.setmType(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_KIND)));
                    subjectForSubjectsList.setmInfo(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SUBJECT_INFO)));
                    subjectForSubjectsList.setmName(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_NAME)));
                    subjectForSubjectsList.setmSurname(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SURNAME)));
                    subjectForSubjectsList.setmPatronymic(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PATRONYMIC)));

                    listOfSubjects.add(subjectForSubjectsList);
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
