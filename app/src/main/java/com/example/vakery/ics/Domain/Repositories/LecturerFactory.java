package com.example.vakery.ics.Domain.Repositories;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vakery.ics.Application.Functional.Vars;
import com.example.vakery.ics.Domain.DB.DatabaseHandler;
import com.example.vakery.ics.Domain.Entities.Lecturer;

import java.util.ArrayList;

public class LecturerFactory {

    /***
     * Добавление препдавателя в бд
     * @param lecturer объект "преподаватель"
     */
    public static void addLecturer(Lecturer lecturer){//дописать все поля
        SQLiteDatabase db = new DatabaseHandler().getWritableDatabase();//формат работы с БД
        ContentValues values = new ContentValues();//создание переменной, позволяющей создать шаблот "записи" и заполниять его для добавления в БД
        values.put(DatabaseHandler.KEY_LECTURER_ID, lecturer.getmId());
        values.put(DatabaseHandler.KEY_PHOTO_URL, lecturer.getmPhoto());
        values.put(DatabaseHandler.KEY_SURNAME, lecturer.getmSurname());
        values.put(DatabaseHandler.KEY_NAME, lecturer.getmName());
        values.put(DatabaseHandler.KEY_PATRONYMIC, lecturer.getmPatronymic());
        values.put(DatabaseHandler.KEY_CONTACTS, lecturer.getmContacts());
        values.put(DatabaseHandler.KEY_ICS, lecturer.getmIsICS());
        db.insert(DatabaseHandler.TABLE_LECTURERS, null, values);//добавление в таблицу шаблона, заполненного ранее
        db.close();
    }


    /***
     * берем всех преподавателей
     * @return
     */
    public static Cursor getAllLecturers(){
        SQLiteDatabase db = new DatabaseHandler().getReadableDatabase();//формат работы с БД

        Cursor cursor ;

        String sqlQuery = "SELECT * \n" +
                "FROM Lecturers l";

        cursor = db.rawQuery(sqlQuery, null);
        return cursor;
    }


    /***
     * берем преподавателей, работающих на кафедре ИКС
     * @return
     */
    public static Cursor getICSLecturers(){
        SQLiteDatabase db = new DatabaseHandler().getReadableDatabase();//формат работы с БД

        Cursor cursor ;

        String sqlQuery = "SELECT * \n" +
                "FROM Lecturers l \n" +
                "WHERE l.ICS = 1";

        cursor = db.rawQuery(sqlQuery, null);
        return cursor;
    }


    /***
     * берем препоавателей со всех кафедр кроме ИКС
     * @return
     */
    public static Cursor getNotICSLecturers(){
        SQLiteDatabase db = new DatabaseHandler().getReadableDatabase();//формат работы с БД

        Cursor cursor ;

        String sqlQuery = "SELECT * \n" +
                "FROM Lecturers l \n" +
                "WHERE l.ICS = 0";

        cursor = db.rawQuery(sqlQuery, null);
        return cursor;
    }


    /***
     * берем определенного преподавателя из всех
     * @param lecturerId уникальный код преподавателя
     * @return
     */
    public static Lecturer getLecturer(int lecturerId) {
        SQLiteDatabase db = new DatabaseHandler().getReadableDatabase();//формат работы с БД
        Cursor cursor;
        String sqlQuery = "SELECT *\n" +
                "FROM Lecturers l \n" +
                "WHERE l.Lecturer_id = " + String.valueOf(lecturerId);

        cursor = db.rawQuery(sqlQuery, null);
        Lecturer lecturer = new Lecturer();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                lecturer.setmId((cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_LECTURER_ID))));
                lecturer.setmPhoto((cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PHOTO_URL))));
                lecturer.setmSurname((cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SURNAME))));
                lecturer.setmName((cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_NAME))));
                lecturer.setmPatronymic((cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PATRONYMIC))));
                lecturer.setmContacts((cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_CONTACTS))));
            }
        } else {
            Log.d(Vars.myLog, "Cursor is null");
        }
        cursor.close();
        db.close();
        return lecturer;
    }


    /***
     * берем спикой id всех преподавателей, у которых есть ссылка на фотограцию, используется при скачивании
     * @return
     */
    public static ArrayList<Integer> getLecturersId(){
        SQLiteDatabase db = new DatabaseHandler().getReadableDatabase();//формат работы с БД
        Cursor cursor ;
        String sqlQuery = "SELECT l.Lecturer_id\n" +
                "FROM Lecturers l " +
                "WHERE l.Photo_url IS NOT NULL "
                ;

        cursor = db.rawQuery(sqlQuery, null);
        ArrayList<Integer> lecturersId = new ArrayList<>(cursor.getCount());
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    lecturersId.add(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_LECTURER_ID)));
                } while (cursor.moveToNext());
            }
        } else {
            Log.d(Vars.myLog, "Cursor is null");
        }
        db.close();
        cursor.close();
        return lecturersId;
    }
}
