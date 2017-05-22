package com.example.vakery.ics.Domain.Repositories;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vakery.ics.Domain.DB.DatabaseHandler;
import com.example.vakery.ics.Domain.Entities.Time;

public class TimeFactory {

    /***
     * Добавление времени начала и окончания пары
     * @param time
     */
    public static void addTime(Time time){//дописать все поля
        SQLiteDatabase db = new DatabaseHandler().getWritableDatabase();//формат работы с БД
        ContentValues values = new ContentValues();//создание переменной, позволяющей создать шаблот "записи" и заполниять его для добавления в БД
        values.put(DatabaseHandler.KEY_ID, time.getmId());
        values.put(DatabaseHandler.KEY_NUMBER_OF_SUBJECT_TIME, time.getmNumberOfSubject());
        values.put(DatabaseHandler.KEY_TIME_START, time.getmTimeStart());
        values.put(DatabaseHandler.KEY_TIME_FINISH, time.getmTimeEnd());

        db.insert(DatabaseHandler.TABLE_TIME, null, values);//добавление в таблицу шаблона, заполненного ранее
        db.close();
    }


    /***
     * берем время начала и конца пар
     * @return
     */
    public static Cursor getTime(){
        SQLiteDatabase db = new DatabaseHandler().getReadableDatabase();//формат работы с БД

        Cursor cursor ;

        String sqlQuery = "SELECT * \n" +
                "FROM Time";

        cursor = db.rawQuery(sqlQuery, null);
        return cursor;
    }

}
