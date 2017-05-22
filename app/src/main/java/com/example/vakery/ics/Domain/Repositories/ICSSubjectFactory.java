package com.example.vakery.ics.Domain.Repositories;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vakery.ics.Domain.DB.DatabaseHandler;
import com.example.vakery.ics.Domain.Entities.ICSSubject;

public class ICSSubjectFactory {

    /***
     * Добавление предмета в бд
     * @param icsSubject объект предмета
     */
    public static void addICSSubject(ICSSubject icsSubject){//дописать все поля
        SQLiteDatabase db = new DatabaseHandler().getWritableDatabase();//формат работы с БД
        ContentValues values = new ContentValues();//создание переменной, позволяющей создать шаблот "записи" и заполниять его для добавления в БД
        values.put(DatabaseHandler.KEY_ICS_SUBJECT_ID, icsSubject.getMid());
        values.put(DatabaseHandler.KEY_TITLE, icsSubject.getmTitle());
        values.put(DatabaseHandler.KEY_ICS_LECTURER, icsSubject.getmLecturerId());
        values.put(DatabaseHandler.KEY_SEMESTERS, icsSubject.getmSemesters());
        values.put(DatabaseHandler.KEY_KIND, icsSubject.getmKind());
        values.put(DatabaseHandler.KEY_SUBJECT_INFO, icsSubject.getmExtraInfo());

        db.insert(DatabaseHandler.TABLE_ICS_SUBJECTS, null, values);//добавление в таблицу шаблона, заполненного ранее
        db.close();
    }


    /***
     * берем предмет из таблицы с всеми предметами кафедры ИКС
     * @param subjectId уникальный код предмета
     * @return
     */
    public static Cursor getICSSubject(int subjectId){
        SQLiteDatabase db = new DatabaseHandler().getReadableDatabase();//формат работы с БД

        Cursor cursor ;

        String sqlQuery = "SELECT i.Title, i.Semesters, i.Kind, i.Extra_info, l.Lecturer_id, l.Surname, l.Name, l.Patronymic\n" +
                "FROM ICS_subjects i \n" +
                "\tINNER JOIN Lecturers l ON ( i.Lecturer_ICS = l.Lecturer_id  )  \n" +
                "WHERE i.ICS_subject_id =   " + String.valueOf(subjectId) ;;

        cursor = db.rawQuery(sqlQuery, null);
        return cursor;
    }


    /***
     * берем все предметы из таблицы с всеми предметами кафедры ИКС
     * @return
     */
    public static Cursor getICSSubjects(){
        SQLiteDatabase db = new DatabaseHandler().getReadableDatabase();//формат работы с БД

        Cursor cursor ;

        String sqlQuery = "SELECT i.ICS_subject_id, i.Title, i.Semesters, i.Kind, i.Extra_info, l.Lecturer_id, l.Surname, l.Name, l.Patronymic\n" +
                "FROM ICS_subjects i \n" +
                "\tINNER JOIN Lecturers l ON ( i.Lecturer_ICS = l.Lecturer_id  )";

        cursor = db.rawQuery(sqlQuery, null);
        return cursor;
    }
}
