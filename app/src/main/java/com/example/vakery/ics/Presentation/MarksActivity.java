package com.example.vakery.ics.Presentation;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import com.example.vakery.ics.Domain.DB.DatabaseHandler;
import com.example.vakery.ics.Application.Functional.MyToolbar;
import com.example.vakery.ics.Application.Functional.Vars;
import com.example.vakery.ics.Application.ListAdapters.MarksListAdapter;

import java.util.ArrayList;

import com.example.vakery.ics.Domain.Entities.Mark;
import com.example.vakery.ics.Domain.Repositories.MarkFactory;
import com.example.vakery.ics.R;

public class MarksActivity extends MyToolbar {
    final String myLog = "myLog";
    DatabaseHandler db;
    ListView lvMarks;
    ArrayList<Mark> listOfMarks = new ArrayList<Mark>();
    MarksListAdapter listAdapter;
    //название активити отображаемое в Toolbar
    String activityTitle = Vars.getContext().getString(R.string.drawer_item_marks);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);

        //создание Toolbar
        this.createToolbar(activityTitle);

        db = new DatabaseHandler();

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

        Cursor cursor = MarkFactory.getMarks();

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


}
