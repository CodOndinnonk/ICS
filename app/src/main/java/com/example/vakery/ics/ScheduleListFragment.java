package com.example.vakery.ics;



import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import Entitys.SubjectForList;

import static com.example.vakery.ics.R.id.pager;

public class ScheduleListFragment extends Fragment {
    final String myLog = "myLog";
    ListView lvSchedule;
    ArrayList<SubjectForList> listOfSubjects = new ArrayList<SubjectForList>();
    ScheduleListAdapter listAdapter;
    ProgressBar progressBar;
    int pageNumber;//номер текущей стр пейджера
    DatabaseHandler db;
    Button kindWeekButton;
    int dayOfWeek;


    public static ScheduleListFragment newInstance(int page){
        ScheduleListFragment pageFragment = new ScheduleListFragment();
        Bundle args = new Bundle();
        //записываем в переменную номер текущей стр
        args.putInt("N", page);
        pageFragment.setArguments(args);
        return pageFragment;
    }



    public ScheduleListFragment(){
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        pageNumber = getArguments() != null ? getArguments().getInt("N") : 1;
        pageNumber = getArguments().getInt("N");

        db = new DatabaseHandler(getContext());



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result;
            result = ScheduleOfDay(inflater, container);
        return result;
    }


    public View ScheduleOfDay(LayoutInflater inflater,ViewGroup container){
        View view=inflater.inflate(R.layout.schedule_list_fragment, container, false);
        TextView dayOfWeek = (TextView)view.findViewById(R.id.dayOfWeekText);
        lvSchedule = (ListView)view.findViewById(R.id.listViewOfSchedule);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBarOfWeek);

        //настройка прогрессбара
        progressBar.setMax(7);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(pageNumber + 1);

        kindWeekButton = (Button)view.findViewById(R.id.kindOfWeekButton);
        kindWeekButton.setText(R.string.week_odd);
        kindWeekButton.setOnClickListener(onButtonClickListener);




        //слешатель нажатия на пункт списка расписания
        lvSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(myLog, " Нажали на пункт списка = " + position);
            }
        });

        //назначаем адаптер для списка с расписанием
        listAdapter = new ScheduleListAdapter(this.getContext(), listOfSubjects);
        lvSchedule.setAdapter(listAdapter);

        //добавляем к номеру стр 2 потому, что +1(так как первый день вс) +1(тут массив начинается с 1 а не 0)
        int numberDayInWeek = pageNumber + 2;
        //если № дня больше 7, то это вс (вс = 6 + 2 = 8), а по календарю он 1
        if(numberDayInWeek > 7){numberDayInWeek = 1;}
        // берем название текующего дня недели
        String strDayOfWeek = new DateFormatSymbols().getWeekdays()[numberDayInWeek];
        dayOfWeek.setText(strDayOfWeek);

        fillList();

        return view;
    }


    //для тестов
    public void fillList(){
        listOfSubjects.clear();
        int kindOfWeek = 3;//предметы за все типы недель
        if(kindWeekButton.getText().equals(R.string.week_odd)){
            kindOfWeek = 1;
        }else {
            kindOfWeek = 2;
        }

        Log.d(myLog, " день = " + MainActivity.currentPageNumber);


//        Cursor cursor = db.getSchedule(kindOfWeek,pageNumber+1);
//
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//               SubjectForList subjectForList = new SubjectForList();
//                do {
//                    for (String cn : cursor.getColumnNames()) {
//
//                        String[] str = cursor.getColumnNames();
//                        for (int i=1;i == str.length;i++) {
//                            Log.d(myLog, " колонка"+i+" = " + str[i]);
//
//                        }
//
////                        subjectForList.setmShort_title(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SHORT_TITLE)));
////                        subjectForList.setmFull_title(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_FULL_TITLE)));
////                     //   subjectForList.setmLecturer(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_LECTURER)));
////                        subjectForList.setmType(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_TYPE_OF_SUBJECT)));
////                        subjectForList.setmRoom(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_ROOM_NUMBER)));
//
//                        listOfSubjects.add(subjectForList);
//                    }
//                } while (cursor.moveToNext());
//            }
//        } else {
//            Log.d(myLog, "Cursor is null");
//        }

//        listOfSubjects.add(new SubjectForList("Информ технологии", "Информационные технологии", "Бабилунга", "Лекция", "408 ф"));
//        listOfSubjects.add(new SubjectForList("Защита информации", "Защитае информационных технологий", "Болтенков", "Практика", "606 ф"));
    }




View.OnClickListener onButtonClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        fillList();
        listAdapter.notifyDataSetChanged();
    }
};







}
