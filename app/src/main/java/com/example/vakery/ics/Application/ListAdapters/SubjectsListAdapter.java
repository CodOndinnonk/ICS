package com.example.vakery.ics.Application.ListAdapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vakery.ics.R;

import java.util.ArrayList;

import com.example.vakery.ics.Domain.Entities.SubjectForSubjectsList;

public class SubjectsListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater layoutInflater;
    ArrayList<SubjectForSubjectsList> objects;
    final String myLog = "myLog";


    public SubjectsListAdapter(Context contextGet, ArrayList<SubjectForSubjectsList> subjects) {
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
    SubjectForSubjectsList getSubject(int position) {
        return ((SubjectForSubjectsList) getItem(position));
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
            view = layoutInflater.inflate(R.layout.subject_item, parent, false);
        }

        SubjectForSubjectsList subjectByPosition = getSubject(position);

        // заполняем View в пункте списка данными из будильника
        ((TextView) view.findViewById(R.id.ICSSubjectName)).setText(subjectByPosition.getmTitle());
        String lecturer = subjectByPosition.getmSurname() + " " + subjectByPosition.getmName() + " " +
                subjectByPosition.getmPatronymic();
        ((TextView) view.findViewById(R.id.ICSLecturer)).setText(lecturer);
        ((TextView) view.findViewById(R.id.ICSSubjectType)).setText(subjectByPosition.getmType());

        return view;
    }


}

