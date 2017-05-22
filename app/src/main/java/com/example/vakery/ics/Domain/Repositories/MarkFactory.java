package com.example.vakery.ics.Domain.Repositories;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vakery.ics.Domain.DB.DatabaseHandler;
import com.example.vakery.ics.Domain.Entities.Mark;
import com.example.vakery.ics.Domain.Entities.Time;

public class MarkFactory {

    /***
     * Добавление оценки по предмету
     * @param mark
     */
    public static void addMark(Mark mark){//дописать все поля
        SQLiteDatabase db = new DatabaseHandler().getWritableDatabase();//формат работы с БД
        ContentValues values = new ContentValues();//создание переменной, позволяющей создать шаблот "записи" и заполниять его для добавления в БД
        values.put(DatabaseHandler.KEY_ID, mark.getmId());
        values.put(DatabaseHandler.KEY_SUBJECT, mark.getmSubjectId());
        values.put(DatabaseHandler.KEY_1_CHAPTER, mark.getmChapter1());
        values.put(DatabaseHandler.KEY_2_CHAPTER, mark.getmChapter2());

        db.insert(DatabaseHandler.TABLE_MARKS, null, values);//добавление в таблицу шаблона, заполненного ранее
        db.close();
    }


    /***
     * берем все оценки
     * @return
     */
    public static Cursor getMarks(){
        SQLiteDatabase db = new DatabaseHandler().getReadableDatabase();//формат работы с БД

        Cursor cursor ;

        String sqlQuery = "SELECT m.Chapter1, m.Chapter2, s.Full_title\n" +
                "FROM Marks m \n" +
                "\tINNER JOIN Subjects s ON ( m.Subject = s.Subject_id  )  ";

        cursor = db.rawQuery(sqlQuery, null);
        return cursor;
    }




}
