package com.example.vakery.ics;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;

import Entities.Lecturer;

public class LecturersListFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    final String myLog = "myLog";
    DatabaseHandler db;
    private int mPage;
    ListView lvLecturers;
    LecturersListAdapter listAdapter;
    ArrayList<Lecturer> listOfLecturers = new ArrayList<Lecturer>();


    public static LecturersListFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        LecturersListFragment fragment = new LecturersListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);

        db = new DatabaseHandler(getContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_for_lecturers, container, false);
        //находим список для вывода преподавателей
        lvLecturers = (ListView)view.findViewById(R.id.listViewOfLecturers);
       // слушатель нажатия на пункт списка преподавателей
        lvLecturers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent go = new Intent(Vars.getContext(), LecturerInfo.class);
                //передаем с интентом id выбранного преподавателя
                go.putExtra("id", listOfLecturers.get(position).getmId());
                startActivity(go);
            }
        });

        fillList();

        //назначаем адаптер для списка с преподавателями
        listAdapter = new LecturersListAdapter(Vars.getContext(), listOfLecturers);
        lvLecturers.setAdapter(listAdapter);

        return view;
    }


    //заполнение списка предметов
    public void fillList(){
        //очищаем от предыдущих данных, на случай обновления
        listOfLecturers.clear();
        Cursor cursor = null;

        //проверка на тип выбранной вкладки
        if(mPage == 1){
            //берем из бд преродавателей кафедры
            cursor = db.getICSLecturers();
        }else if (mPage == 2){
            //берем из бд преродавателей не от кафедры
            cursor = db.getNotICSLecturers();
        }else if (mPage == 3) {
            //берем из бд всех преродавателей
             cursor = db.getAllLecturers();
        }

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Lecturer lecturer = new Lecturer();

                    lecturer.setmId((cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_LECTURER_ID))));
                    lecturer.setmPhoto((cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PHOTO_URL))));
                    lecturer.setmSurname((cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SURNAME))));
                    lecturer.setmName((cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_NAME))));
                    lecturer.setmPatronymic((cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PATRONYMIC))));
                    lecturer.setmContacts((cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_CONTACTS))));

                    listOfLecturers.add(lecturer);
                } while (cursor.moveToNext());
            }
        } else {
            Log.d(myLog, "Cursor is null");
        }
    }


}
