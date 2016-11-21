package com.example.vakery.ics;


import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import Entities.TimeSchedule;

public class Vars {

    static final String myLog = "myLog";

    public static final int WEEK_ODD = 1;
    public static final int WEEK_EVEN = 2;

    public static int currentKindOfWeek = 0;//текущий тип недели, отображаемый в расписании

    public  static ArrayList<TimeSchedule> listOfTime = new ArrayList<TimeSchedule>();

    public static void setCurrentKindOfWeek(int setCurrentKindOfWeek) {
        Vars.currentKindOfWeek = setCurrentKindOfWeek;
        Log.d(myLog, "Vars.currentKindOfWeek = " + currentKindOfWeek);
    }

    public static void setListOfTime(ArrayList<TimeSchedule> listOfTime) {
        Vars.listOfTime = listOfTime;
    }

    public static ArrayList<TimeSchedule> getListOfTime() {
        return listOfTime;
    }

    public static void fillTimeList(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        Cursor cursor = db.getTime();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    TimeSchedule time = new TimeSchedule();

                    time.setmSubjectNumber(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_NUMBER_OF_SUBJECT_TIME)));
                    time.setmStart(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_TIME_START)));
                    time.setmFinish(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_TIME_FINISH)));

                    listOfTime.add(time);
                } while (cursor.moveToNext());
            }
        } else {
            Log.d(myLog, "Cursor is null fillTimeList");
        }
    }

    //возвращает строке с временем начала и конца пары
    public static String getTimeInfo(int numberOfSubject){
        String result = "-";
        //поиск нуной нам пары в списке
        for (int i = 0; i < listOfTime.size(); i++){
            Log.d(myLog, "listOfTime.get(i).getmSubjectNumber() = " + listOfTime.get(i).getmSubjectNumber());
            Log.d(myLog, "numberOfSubject = " + numberOfSubject);
            if(listOfTime.get(i).getmSubjectNumber() == numberOfSubject){
                result = listOfTime.get(i).getmStart() + " - " +
                        listOfTime.get(i).getmFinish();
            }
        }
        return result;
    }

    public static int getCurrentKindOfWeek() {
        return currentKindOfWeek;
    }
}
