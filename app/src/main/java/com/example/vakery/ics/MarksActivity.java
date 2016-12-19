package com.example.vakery.ics;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.vakery.ics.DB.DatabaseHandler;
import com.example.vakery.ics.Functional.MyToolbar;
import com.example.vakery.ics.Functional.Vars;
import com.example.vakery.ics.ListAdapters.MarksListAdapter;


import java.util.ArrayList;

import com.example.vakery.ics.Entities.Mark;

public class MarksActivity extends AppCompatActivity {
    final String myLog = "myLog";
    DatabaseHandler db;
    ListView lvMarks;
    ArrayList<Mark> listOfMarks = new ArrayList<Mark>();
    MarksListAdapter listAdapter;
    MyToolbar toolbar;
    //название активити отображаемое в Toolbar
    String activityTitle = Vars.getContext().getString(R.string.drawer_item_marks);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);

        //создание Toolbar
        toolbar = new MyToolbar(this,activityTitle);

        db = new DatabaseHandler(this);

        lvMarks = (ListView)findViewById(R.id.listViewOfMarks);

        fillList();

        //назначаем адаптер для списка с расписанием
        listAdapter = new MarksListAdapter(this.getApplicationContext(), listOfMarks);
        lvMarks.setAdapter(listAdapter);
    }


    //заполнение списка предметов
    public void fillList(){
        //очищаем от предыдущих данных, на случай обновления
        listOfMarks.clear();

        Cursor cursor = db.getMarks();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Mark markForMarksList = new Mark();

                    markForMarksList.setmSubjectName(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_FULL_TITLE)));
                    markForMarksList.setmChapter1(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_1_CHAPTER)));
                    markForMarksList.setmChapter2(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_2_CHAPTER)));

                    listOfMarks.add(markForMarksList);
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
