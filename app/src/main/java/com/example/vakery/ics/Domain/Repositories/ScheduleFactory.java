package com.example.vakery.ics.Domain.Repositories;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vakery.ics.Application.Functional.Vars;
import com.example.vakery.ics.Domain.DB.DatabaseHandler;
import com.example.vakery.ics.Domain.Entities.Schedule;
import com.example.vakery.ics.Domain.Entities.Subject;

public class ScheduleFactory {


    /***
     * Добавления расписания на день
     * @param schedule
     */
    public static void addSchedule(Schedule schedule){//дописать все поля
        SQLiteDatabase db = new DatabaseHandler().getWritableDatabase();//формат работы с БД
        ContentValues values = new ContentValues();//создание переменной, позволяющей создать шаблот "записи" и заполниять его для добавления в БД
        values.put(DatabaseHandler.KEY_ID, schedule.getmId());
        values.put(DatabaseHandler.KEY_KIND_OF_WEEK, schedule.getmKindOfWeek());
        values.put(DatabaseHandler.KEY_DAY_OF_WEEK, schedule.getmDayOfWeek());
        values.put(DatabaseHandler.KEY_NUMBER_OF_SUBJECT_WEEK, schedule.getmNumberOfSubject());
        values.put(DatabaseHandler.KEY_SUBJECT, schedule.getmSubjectId());
        values.put(DatabaseHandler.KEY_TYPE_OF_SUBJECT, schedule.getmTypeOfSubject());
        values.put(DatabaseHandler.KEY_ROOM_NUMBER, schedule.getmRoomNumber());

        db.insert(DatabaseHandler.TABLE_WEEK, null, values);//добавление в таблицу шаблона, заполненного ранее
        db.close();
    }


    /***
     * берем расписание на заданный день заанной недели
     * @param kindOfWeek тип недели (1- нечетная, 2- четная, 3- все типы недель)
     * @param day день недели
     * @return
     */
    public static Cursor getSchedule(int kindOfWeek, int day){
        SQLiteDatabase db = new DatabaseHandler().getReadableDatabase();//формат работы с БД

        Cursor cursor ;

        Log.d(Vars.myLog, "Расписание на "+kindOfWeek+" неделю "+day+" день");

        String sqlQuery = "SELECT w.Number_of_subject_week, w.Type_of_subject, w.Room_number, s.Subject_id, s.Short_title," +
                " l.Lecturer_id, l.Surname, l.Name, l.Patronymic\n" +
                "FROM Week w \n" +
                "\tINNER JOIN Subjects s ON ( w.Subject = s.Subject_id  )  \n" +
                "\t\tINNER JOIN Lecturers l ON ( s.Lecturer_personal = l.Lecturer_id  )  \n" +
                "WHERE w.Day_of_week = "+String.valueOf(day)+"\n" +
                " AND\n" +
                "\t (w.Kind_of_week = "+String.valueOf(kindOfWeek)+" OR\n" +
                "\tw.Kind_of_week = 3)\n" ;

        cursor = db.rawQuery(sqlQuery, null);
        //db.close();
        return cursor;
    }


    /***
     * берем предмет из таблицы расписания
     * @param subjectId уникальный код предмета
     * @return
     */
    public static Cursor getPersonalSubject(int subjectId){
        SQLiteDatabase db = new DatabaseHandler().getReadableDatabase();//формат работы с БД

        Cursor cursor ;

        String sqlQuery = "SELECT s.Short_title, s.Full_title, l.Lecturer_id, l.Surname, l.Name, l.Patronymic, w.Type_of_subject, w.Room_number\n" +
                "FROM Subjects s \n" +
                "\tINNER JOIN Lecturers l ON ( s.Lecturer_personal = l.Lecturer_id  )  \n" +
                "\tINNER JOIN Week w ON ( s.Subject_id = w.Subject  )  \n" +
                "WHERE s.Subject_id =  " + String.valueOf(subjectId) ;

        cursor = db.rawQuery(sqlQuery, null);
      //  db.close();
        return cursor;
    }


    /***
     * Добавление предмета в бд (обычные прелметы)
     * @param subject
     */
    public static void addPersonalSubject(Subject subject){//дописать все поля
        SQLiteDatabase db = new DatabaseHandler().getWritableDatabase();//формат работы с БД
        ContentValues values = new ContentValues();//создание переменной, позволяющей создать шаблот "записи" и заполниять его для добавления в БД
        values.put(DatabaseHandler.KEY_SUBJECT_ID, subject.getmId());
        values.put(DatabaseHandler.KEY_SHORT_TITLE, subject.getmShortTitle());
        values.put(DatabaseHandler.KEY_FULL_TITLE, subject.getmFullTitle());
        values.put(DatabaseHandler.KEY_PERSONAL_LECTURER, subject.getmLecturerId());

        db.insert(DatabaseHandler.TABLE_PERSONAL_SUBJECTS, null, values);//добавление в таблицу шаблона, заполненного ранее
        db.close();
    }
}
