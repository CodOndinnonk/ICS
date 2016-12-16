package com.example.vakery.ics;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import Entities.SubjectForScheduleList;
import Entities.SubjectForSubjectsList;

public class ScheduleListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater layoutInflater;
    ArrayList<SubjectForScheduleList> objects;
    final String myLog = "myLog";


    ScheduleListAdapter(Context contextGet, ArrayList<SubjectForScheduleList> subjects) {
        mContext = contextGet;
        objects = subjects;

        layoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }


    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // предмет по позиции
    SubjectForScheduleList getSubject(int position) {
        return ((SubjectForScheduleList) getItem(position));
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }


    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.schedule_item, parent, false);
        }

        SubjectForScheduleList subjectByPosition = getSubject(position);

        // заполняем View в пункте списка данными из будильника
        ((TextView) view.findViewById(R.id.numberOfSubject)).setText(String.valueOf(subjectByPosition.getmNumberOfSubject()));
        ((TextView) view.findViewById(R.id.ICSSubjectName)).setText(subjectByPosition.getmShortTitle());
        ((TextView) view.findViewById(R.id.roomNumber)).setText(subjectByPosition.getmRoom());
        ((TextView) view.findViewById(R.id.ICSSubjectKind)).setText(subjectByPosition.getmType());

        if(subjectByPosition.getmSurname() != null) {
            String lecturerInfo = subjectByPosition.getmSurname() + " " + String.valueOf(subjectByPosition.getmName().charAt(0)) +
                    ". " + String.valueOf(subjectByPosition.getmPatronymic().charAt(0)) + ".";
            ((TextView) view.findViewById(R.id.ICSSubjectLecturer)).setText(lecturerInfo);
        }else {
            ((TextView) view.findViewById(R.id.ICSSubjectLecturer)).setText("");

        }

        ((TextView) view.findViewById(R.id.subjectTime)).setText(Vars.getTimeInfo(subjectByPosition.getmNumberOfSubject()));

        return view;
    }






}

