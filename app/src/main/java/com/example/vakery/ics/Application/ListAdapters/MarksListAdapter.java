package com.example.vakery.ics.Application.ListAdapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vakery.ics.R;

import java.util.ArrayList;

import com.example.vakery.ics.Domain.Entities.Mark;

public class MarksListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater layoutInflater;
    ArrayList<Mark> objects;
    final String myLog = "myLog";


    public MarksListAdapter(Context contextGet, ArrayList<Mark> marks) {
        mContext = contextGet;
        objects = marks;

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
    Mark getMark(int position) {
        return ((Mark) getItem(position));
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
            view = layoutInflater.inflate(R.layout.mark_item, parent, false);
        }

        Mark markByPosition = getMark(position);

        ((TextView) view.findViewById(R.id.markSubjectName)).setText(markByPosition.getmSubjectName());
        ((TextView) view.findViewById(R.id.mark1Semester)).setText(String.valueOf(markByPosition.getmChapter1()));
        ((TextView) view.findViewById(R.id.mark2Semester)).setText(String.valueOf(markByPosition.getmChapter2()));
        ((TextView) view.findViewById(R.id.markSum)).setText(String.valueOf(markByPosition.getmChapter1() +
                markByPosition.getmChapter2()));

        return view;
    }


}

