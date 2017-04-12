package com.example.vakery.ics;



import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.vakery.ics.DB.DatabaseHandler;
import com.example.vakery.ics.Functional.Vars;
import com.example.vakery.ics.ListAdapters.ScheduleListAdapter;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import com.example.vakery.ics.Entities.SubjectForScheduleList;

public class ScheduleListFragment extends Fragment {
    final String myLog = "myLog";
    ListView lvSchedule;
    ArrayList<SubjectForScheduleList> listOfSubjects = new ArrayList<SubjectForScheduleList>();
    ScheduleListAdapter listAdapter;
    ProgressBar progressBar;
    int pageNumber;//номер текущей стр пейджера
    DatabaseHandler db;
    Button kindWeekButton;
    //переменная для ображения к методу для обновления адаптера пейджера
    private MainActivity mActivity;
    Intent dialogIntent;//интент для перехода


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

        db = new DatabaseHandler();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result;
            result = ScheduleOfDay(inflater, container);
        return result;
    }

    /***
     * Заполнение листа пейджера контентом
     * @param inflater
     * @param container
     * @return
     */
    public View ScheduleOfDay(LayoutInflater inflater,ViewGroup container){
        //проверка на воскресенье
        if(pageNumber == 6){
            View view = inflater.inflate(R.layout.schedule_list_fragment_sunday, container, false);

            TextView dayOfWeek = (TextView) view.findViewById(R.id.dayOfWeekText);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBarOfWeek);

            //настройка прогрессбара
            progressBar.setMax(7);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(pageNumber + 1);

            kindWeekButton = (Button) view.findViewById(R.id.kindOfWeekButton);
            kindWeekButton.setOnClickListener(onButtonClickListener);


/////////////////////////////////////////////////////////////////////////////////////////////////
            //определение текущей недели
            if (Vars.getCurrentKindOfWeek() == 0) {
                Vars.setCurrentKindOfWeek(Vars.WEEK_ODD);//заглушка
            }

            fillList();//в воскресенье используется для корректной работы кнопки смены типа недели

            //добавляем к номеру стр 2 потому, что +1(так как первый день вс) +1(тут массив начинается с 0 а не 1)
            int numberDayInWeek = pageNumber + 2;
            //если № дня больше 7, то это вс (вс = 6 + 2 = 8), а по календарю он 1
            if (numberDayInWeek > 7) {
                numberDayInWeek = 1;
            }
            // берем название текующего дня недели
            String strDayOfWeek = new DateFormatSymbols().getWeekdays()[numberDayInWeek];
            dayOfWeek.setText(strDayOfWeek);

            return view;
        }else {
            View view = inflater.inflate(R.layout.schedule_list_fragment, container, false);
            TextView dayOfWeek = (TextView) view.findViewById(R.id.dayOfWeekText);
            lvSchedule = (ListView) view.findViewById(R.id.listViewOfSchedule);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBarOfWeek);

            //настройка прогрессбара
            progressBar.setMax(7);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(pageNumber + 1);

            kindWeekButton = (Button) view.findViewById(R.id.kindOfWeekButton);
            kindWeekButton.setOnClickListener(onButtonClickListener);

/////////////////////////////////////////////////////////////////////////////////////////////////
            //определение текущей недели
            if (Vars.getCurrentKindOfWeek() == 0) {
                Vars.setCurrentKindOfWeek(Vars.WEEK_ODD);//заглушка
            }

            //слушатель нажатия на пункт списка расписания
            lvSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if ((listOfSubjects.get(position).getmShortTitle().length() < 0)) {
                        //если нажали на "нет пары"
                        Toast.makeText(getContext(), getString(R.string.no_lesson), Toast.LENGTH_SHORT).show();
                    } else {
                        //создание интента для перехода из не активити
                        dialogIntent = new Intent(getContext(), SubjectInfoActivity.class);
                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //передаем с интентом id выбранного предмета
                        dialogIntent.putExtra("id", listOfSubjects.get(position).getmSubjectId());
                        dialogIntent.putExtra("subjectKind", "personal");
                        startActivity(dialogIntent);
                    }
                }
            });

            fillList();

            //назначаем адаптер для списка с расписанием
            listAdapter = new ScheduleListAdapter(this.getContext(), listOfSubjects);
            lvSchedule.setAdapter(listAdapter);

            //добавляем к номеру стр 2 потому, что +1(так как первый день вс) +1(тут массив начинается с 0 а не 1)
            int numberDayInWeek = pageNumber + 2;
            //если № дня больше 7, то это вс (вс = 6 + 2 = 8), а по календарю он 1
            if (numberDayInWeek > 7) {
                numberDayInWeek = 1;
            }
            // берем название текующего дня недели
            String strDayOfWeek = new DateFormatSymbols().getWeekdays()[numberDayInWeek];
            dayOfWeek.setText(strDayOfWeek);

            return view;
        }
    }


    /***
     * Заполнение списка предметов
     */
    public void fillList(){
        //очищаем от предыдущих данных, на случай обновления
        listOfSubjects.clear();

        //устанавливаем нужную запись на кнопке
        if(Vars.getCurrentKindOfWeek() == Vars.WEEK_ODD) {
            kindWeekButton.setText(R.string.week_odd);
        } else {
            kindWeekButton.setText(R.string.week_even);
        }

        //если выбрали не воскресенье, то получаем расписание на день
        if(pageNumber != 6) {
            Cursor cursor = db.getSchedule(Vars.getCurrentKindOfWeek(), pageNumber + 1);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        SubjectForScheduleList subjectForScheduleList = new SubjectForScheduleList();

                        subjectForScheduleList.setmNumberOfSubject(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_NUMBER_OF_SUBJECT_WEEK)));
                        subjectForScheduleList.setmType(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_TYPE_OF_SUBJECT)));
                        subjectForScheduleList.setmRoom(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_ROOM_NUMBER)));
                        subjectForScheduleList.setmSubjectId(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_SUBJECT_ID)));
                        subjectForScheduleList.setmShortTitle(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SHORT_TITLE)));
                        subjectForScheduleList.setmLecturerId(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_LECTURER_ID)));
                        subjectForScheduleList.setmSurname(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SURNAME)));
                        subjectForScheduleList.setmName(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_NAME)));
                        subjectForScheduleList.setmPatronymic(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PATRONYMIC)));

                        listOfSubjects.add(subjectForScheduleList);
                    } while (cursor.moveToNext());
                }
            } else {
                Log.d(myLog, "Cursor is null");
            }
            //сортровка списка предметов по номеру пары
            Collections.sort(listOfSubjects, new MyComparator());

            int flag = 0;//переменная-счетчик для определения колва пройденных предметов
            //создаем временный список для модификаций, и в него клонируем основной список (клонируем, чтоб небыло привязки)
            ArrayList<SubjectForScheduleList> forPrepare = (ArrayList<SubjectForScheduleList>) listOfSubjects.clone();

            //try потому, что не во все дни есть расписание и чтоб не выпадала ошибка
            try {
                listOfSubjects.clear();//чистим основной лис, чтоб заполнить его с наличием пустых пар
                for (int i = 1; i < 8; i++) {//вместо 8 может быть любое число
                    if (i == forPrepare.get(flag).getmNumberOfSubject()) {//если текущая пара по счету такая же как и в списе, то записываем ее в список
                        listOfSubjects.add(forPrepare.get(flag));
                        flag++;//для перехода к следующему предмету
                    } else {//если номер пар не совал (к примеру у нас i=1 пара, а в списе первый элемент = 3 паре) то добавляем заглушку
                        listOfSubjects.add(new SubjectForScheduleList(i, ""));//заглушка с номером пары
                    }
                    //если мы просмотрели все предметы на день. считанные из бд, то выходим из цикла
                    if (flag == forPrepare.size()) {
                        break;
                    }
                }
            } catch (Exception e) {
                Log.d(myLog, "Выходной день в котором нет расписания");
            }
        }

    }


    /***
     * Класс реализующий сортировку обьектов по заданному полю
     */
    class MyComparator implements Comparator<SubjectForScheduleList> {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public int compare(SubjectForScheduleList o1, SubjectForScheduleList o2) {
            return Integer.compare(o1.getmNumberOfSubject(), o2.getmNumberOfSubject());
        }
    }


    /***
     * Обработчик нажатия на кнопку смены типа недели
     */
    View.OnClickListener onButtonClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        //изменяем значение выбранного типа недели
        switch (Vars.getCurrentKindOfWeek()) {
            case Vars.WEEK_ODD:
                Vars.setCurrentKindOfWeek(Vars.WEEK_EVEN);
                break;
            case Vars.WEEK_EVEN:
                Vars.setCurrentKindOfWeek(Vars.WEEK_ODD);
                break;
        }

        //проверка на воскресенье. Если воскресенье, то не надо получать расписание
        if(pageNumber != 6) {
            fillList();//вызываем метод для обновления данных о предметах
            listAdapter.notifyDataSetChanged();//сообщаем адаптеру, что внесены изменения, чтоб он обновил список
        }
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
