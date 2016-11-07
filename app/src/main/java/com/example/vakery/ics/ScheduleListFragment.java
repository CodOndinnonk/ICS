package com.example.vakery.ics;



import android.annotation.TargetApi;
import android.app.Activity;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.util.Collections;
import java.util.Comparator;
import Entitys.SubjectForList;

public class ScheduleListFragment extends Fragment {
    final String myLog = "myLog";
    ListView lvSchedule;
    ArrayList<SubjectForList> listOfSubjects = new ArrayList<SubjectForList>();
    ScheduleListAdapter listAdapter;
    ProgressBar progressBar;
    int pageNumber;//номер текущей стр пейджера
    DatabaseHandler db;
    Button kindWeekButton;
    //переменная для ображения к методу для обновления адаптера пейджера
    private MainActivity mActivity;


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
        kindWeekButton.setOnClickListener(onButtonClickListener);

/////////////////////////////////////////////////////////////////////////////////////////////////
        //определение текущей недели
        if(Vars.getCurrentKindOfWeek() == 0) {
            Vars.setCurrentKindOfWeek(Vars.WEEK_ODD);//заглушка
        }

        //слушатель нажатия на пункт списка расписания
        lvSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(myLog, " Нажали на пункт списка = " + position);
            }
        });

        fillList();

        //назначаем адаптер для списка с расписанием
        listAdapter = new ScheduleListAdapter(this.getContext(), listOfSubjects);
        lvSchedule.setAdapter(listAdapter);

        //добавляем к номеру стр 2 потому, что +1(так как первый день вс) +1(тут массив начинается с 0 а не 1)
        int numberDayInWeek = pageNumber + 2;
        //если № дня больше 7, то это вс (вс = 6 + 2 = 8), а по календарю он 1
        if(numberDayInWeek > 7){numberDayInWeek = 1;}
        // берем название текующего дня недели
        String strDayOfWeek = new DateFormatSymbols().getWeekdays()[numberDayInWeek];
        dayOfWeek.setText(strDayOfWeek);

        return view;
    }


    //заполнение списка предметов
    public void fillList(){
        //очищаем от предыдущих данных, на случай обновления
        listOfSubjects.clear();

        //устанавливаем нужную запись на кнопке
        if(Vars.getCurrentKindOfWeek() == Vars.WEEK_ODD) {
            kindWeekButton.setText(R.string.week_odd);
        } else {
            kindWeekButton.setText(R.string.week_even);
        }

        Cursor cursor = db.getSchedule(Vars.getCurrentKindOfWeek(),pageNumber+1);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    SubjectForList subjectForList = new SubjectForList();

                        subjectForList.setmNumberOfSubject(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_NUMBER_OF_SUBJECT)));
                        subjectForList.setmShort_title(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SHORT_TITLE)));
                        subjectForList.setmFull_title(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_FULL_TITLE)));
//                     //   subjectForList.setmLecturer(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_LECTURER)));
                        subjectForList.setmType(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_TYPE_OF_SUBJECT)));
                        subjectForList.setmRoom(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_ROOM_NUMBER)));

                        listOfSubjects.add(subjectForList);
                } while (cursor.moveToNext());
            }
        } else {
            Log.d(myLog, "Cursor is null");
        }
        //сортровка списка предметов по номеру пары
        Collections.sort(listOfSubjects, new MyComparator ());

        int flag =0;//переменная-счетчик для определения колва пройденных предметов
        //создаем временный список для модификаций, и в него клонируем основной список (клонируем, чтоб небыло привязки)
        ArrayList<SubjectForList> forPrepare = (ArrayList<SubjectForList>) listOfSubjects.clone();

        //try потому, что не во все дни есть расписание и чтоб не выпадала ошибка
        try {
            listOfSubjects.clear();//чистим основной лис, чтоб заполнить его с наличием пустых пар
            for(int i = 1; i < 8; i++){//вместо 8 может быть любое число
                if(i == forPrepare.get(flag).getmNumberOfSubject()){//если текущая пара по счету такая же как и в списе, то записываем ее в список
                    listOfSubjects.add(forPrepare.get(flag));
                    flag++;//для перехода к следующему предмету
                }else {//если номер пар не совал (к примеру у нас i=1 пара, а в списе первый элемент = 3 паре) то добавляем заглушку
                    listOfSubjects.add(new SubjectForList(i,getString(R.string.no_lesson)));//заглушка с номером пары
                }
                //если мы просмотрели все предметы на день. считанные из бд, то выходим из цикла
                if(flag == forPrepare.size()){
                    break;
                }
            }
        } catch (Exception e) {
            Log.d(myLog, "Выходной день в котором нет расписания");
        }

    }



    // класс реализующий сортировку обьектов по заданному полю
    class MyComparator implements Comparator<SubjectForList> {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public int compare(SubjectForList o1, SubjectForList o2) {
            return Integer.compare(o1.getmNumberOfSubject(), o2.getmNumberOfSubject());
        }
    }


View.OnClickListener onButtonClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        //изменяем жначение выбранного типа недели
        switch (Vars.getCurrentKindOfWeek()) {
            case Vars.WEEK_ODD:
                Vars.setCurrentKindOfWeek(Vars.WEEK_EVEN);
                break;
            case Vars.WEEK_EVEN:
                Vars.setCurrentKindOfWeek(Vars.WEEK_ODD);
                break;
        }

        fillList();//вызываем метод для обновления данных о предметах
        listAdapter.notifyDataSetChanged();//сообщаем адаптеру, что внесены изменения, чтоб он обновил список

        if (mActivity != null) {
            //вызываем метод, осуществляющий обновление фрагментов в пейджере(используется для изменения типа недели дл всех страниц пейджера)
            mActivity.notifyAdapter(pageNumber);
        }
    }
};


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //так как данный фрагмент используется в пейджере, который находится в MainActivity, то при выове этого метода
        //мы получаем ссылку на активити, что позволяет потом вызывать нежные методы из MainActivity
        this.mActivity = (MainActivity) activity;
    }
}
