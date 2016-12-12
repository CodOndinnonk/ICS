package com.example.vakery.ics;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Entities.Lecturer;
import Entities.SubjectForList;

public class LecturersListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater layoutInflater;
    ArrayList<Lecturer> objects;
    final String myLog = "myLog";


    LecturersListAdapter(Context contextGet, ArrayList<Lecturer> lecturers) {
        mContext = contextGet;
        objects = lecturers;

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
    Lecturer getLecturer(int position) {
        return ((Lecturer) getItem(position));
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
            view = layoutInflater.inflate(R.layout.lecturer_item, parent, false);
        }

        Lecturer lecturerByPosition = getLecturer(position);

        // заполняем View в пункте списка данными из будильника
       ImageView imageView = ((ImageView)view.findViewById(R.id.limageView));
        ((TextView) view.findViewById(R.id.lSurname)).setText(lecturerByPosition.getmSurname());
        ((TextView) view.findViewById(R.id.lName)).setText(lecturerByPosition.getmName());
        ((TextView) view.findViewById(R.id.lPatronymic)).setText(lecturerByPosition.getmPatronymic());

        Picasso.with(this.mContext) //передаем контекст приложения
                .load( "https://avatanplus.com/files/resources/mid/569d18a44d31a15255a841dc.png") //адрес изображения
                .into(imageView); //ссылка на ImageView

        return view;
    }






}

