package com.example.vakery.ics;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import Entitys.SubjectForList;

public class ScheduleListFragment extends Fragment {
    final String myLog = "myLog";
    ListView lvSchedule;
    ArrayList<SubjectForList> listOfSubjects = new ArrayList<SubjectForList>();
    ScheduleListAdapter listAdapter;
    ProgressBar progressBar;
    int pageNumber;//ноиер текущей стро пейджера


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

        pageNumber = getArguments().getInt("N");
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

        //слешатель нажатия на пункт списка расписания
        lvSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(myLog, " Нажали на пункт списка = " + position);
            }
        });

        fillList();//////////////////////////////////////для тестов

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

        return view;
    }


    //для тестов
    public void fillList(){
        listOfSubjects.add(new SubjectForList("Информ технологии", "Информационные технологии", "Бабилунга", "Лекция", "408 ф"));
        listOfSubjects.add(new SubjectForList("Защита информации", "Защитае информационных технологий", "Болтенков", "Практика", "606 ф"));
    }


}
