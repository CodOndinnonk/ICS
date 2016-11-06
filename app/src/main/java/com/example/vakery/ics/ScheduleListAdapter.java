package com.example.vakery.ics;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import Entitys.SubjectForList;

public class ScheduleListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater layoutInflater;
    ArrayList<SubjectForList> objects;
    final String myLog = "myLog";


    ScheduleListAdapter(Context contextGet, ArrayList<SubjectForList> subjects) {
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

        SubjectForList subjectByPosition = getSubject(position);

        // заполняем View в пункте списка данными из будильника
        ((TextView) view.findViewById(R.id.numberOfSubject)).setText(String.valueOf(subjectByPosition.getmNumberOfSubject()));
        ((TextView) view.findViewById(R.id.subjectName)).setText(subjectByPosition.getmShort_title());
        ((TextView) view.findViewById(R.id.roomNumber)).setText(subjectByPosition.getmRoom());
        ((TextView) view.findViewById(R.id.typeOfSubject)).setText(subjectByPosition.getmType());
        ((TextView) view.findViewById(R.id.subjestLecturer)).setText(subjectByPosition.getmLecturer());

        return view;
    }


    // предмет по позиции
    SubjectForList getSubject(int position) {
        return ((SubjectForList) getItem(position));
    }


}

