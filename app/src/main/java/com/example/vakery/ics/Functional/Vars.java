package com.example.vakery.ics.Functional;


import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;

import com.example.vakery.ics.DB.DatabaseHandler;

import java.io.File;
import java.util.ArrayList;

import com.example.vakery.ics.Entities.TimeSchedule;

public  class Vars {
//класс основных глобальных переменных приложения
    public static final String myLog = "myLog";

    private static Context context = null ;

    public static final int WEEK_ODD = 1;
    public static final int WEEK_EVEN = 2;

    private static int currentKindOfWeek = 0;//текущий тип недели, отображаемый в расписании

    //список с временем начала и конца всех пар (находится здесь так, как этодействие происходит один раз за цикл жизни)
    private  static ArrayList<TimeSchedule> listOfTime = new ArrayList<TimeSchedule>();

    //путь к папке, в которой хранятся фотографии
    private static File imageFileDir;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //создание пути для хранения фото
    static {imageFileDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ICS");
          }


    public static Context getContext() {
        return context;
    }


    public static void setImageFileDir(File imageFileDir) {
        Vars.imageFileDir = imageFileDir;
    }


    public static void setContext(Context context) {
        Vars.context = context;
    }


    public static File getImageFileDir() {
        return imageFileDir;
    }

    /***
     * установка текущего типа недели
     * @param setCurrentKindOfWeek число 1,2,3
     */
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


    /***
     * Заполнение списка времени пар
     */
    public static void fillTimeList(){
        DatabaseHandler db = new DatabaseHandler();
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


    /***
     * возвращает строку с временем начала и конца пары
     * @param numberOfSubject номер интересующей пары
     * @return
     */
    public static String getTimeInfo(int numberOfSubject){
        String result = "-";
        //поиск нужной нам пары в списке
        for (int i = 0; i < listOfTime.size(); i++){
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
