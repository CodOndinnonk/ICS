package com.example.vakery.ics;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FillDB {
    final String myLog = "myLog";
    SQLiteDatabase mdb;
    DatabaseHandler dh;

    // данные для таблицы предметы
    int[] subjId = { 1, 2, 3, 4, 5, 6, 7, 8 };
    String[] shortName = { "Микропроцессоры", "Управление проектами", "Телеком. сервисы", "Инф. технологии", "Сист. анализ", "БЖД",
            "Защита информации", "Политология" };

    String[] fullName = { "Микропроцессорное программирование", "Управление проектами", "Телекомуникацилнные сервисы",
            "Информационные технологии", "Системный анализ", "Безопасность жизнедеятельности", "Защита информационных  технологий",
            "Политология" };
    int[] teacherId = { 1, 2, 3, 4, 5, 6, 7, 8 };

    // данные для таблицы расписания
    int[] kindOfWeek = { 1, 3, 3, 2, 3, 3, 3, 2, 2, 3, 3, 2, 1, 2, 3, 1, 2, 3, 2};
    int[] dayOfWeek = { 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 5, 5, 5};
    int[] NOfSubj = { 1, 2, 3, 4, 2, 3, 4, 1, 2, 3, 4, 1, 2, 2, 1, 2, 2, 3, 4};
    int[] keySubj = { 1, 2, 3, 3, 4, 5, 4, 4, 6, 7, 7, 6, 5, 8, 8, 3, 2, 1, 1};
    String[] typeSubj = { "практика", "лекция", "лекция", "практика", "лекция", "лекция", "лабораторная", "лекция", "практика", "лекция",
            "лабораторная", "лекция", "лабораторная", "практика", "лекция", "практика", "лабораторная", "лекция", "лабораторная" };

    String[] room = { "606ф", "308ф", "308ф", "606ф", "408ф", "408ф", "608ф", "408ф", "413ф", "308ф", "606ф", "409ф", "608ф",
            "311ф", "203ф", "606ф", "609ф", "308ф", "608ф" };

    // данные для таблицы преподавателей
    int[] lectId = {1, 2, 3, 4, 5, 6, 7, 8};
    String [] lectPhoto = {"no", "no", "no", "no", "no", "no", "no", "no"};
    String [] lectSurname = {"Кондратев", "Тесленко", "Неврев", "Бабилунга", "Сытник", "Козерацкий", "Болтенков", "Шановская"};
    String [] lectName = {"Сергей", "Павел", "О", "О", "В", "Г", "В", "О"};
    String [] lectPatronymic = {"Борисович", "Александрович", "И", "Ю", "А", "В", "О", "А"};
    String [] lectContacts = {"000", "000", "000", "000", "000", "000", "000", "000"};



    public FillDB(SQLiteDatabase db){
        mdb = db;
    }


   public void startFillDB() {
       Log.d(myLog, "--- FillDB ---");


       ContentValues cv = new ContentValues();

       // данные для таблицы предметы
       for (int i = 0; i < subjId.length; i++) {
           cv.clear();
           cv.put(dh.KEY_SUBJECT_ID, subjId[i]);
           cv.put(dh.KEY_SHORT_TITLE, shortName[i]);
           cv.put(dh.KEY_FULL_TITLE, fullName[i]);
           cv.put(dh.KEY_LECTURER, teacherId[i]);
           mdb.insert(dh.TABLE_SUBJECTS, null, cv);
       }

       // данные для таблицы расписание
       for (int i = 0; i < kindOfWeek.length; i++) {
           cv.clear();
           cv.put(dh.KEY_KIND_OF_WEEK, kindOfWeek[i]);
           cv.put(dh.KEY_DAY_OF_WEEK, dayOfWeek[i]);
           cv.put(dh.KEY_NUMBER_OF_SUBJECT, NOfSubj[i]);
           cv.put(dh.KEY_SUBJECT, keySubj[i]);
           cv.put(dh.KEY_TYPE_OF_SUBJECT, typeSubj[i]);
           cv.put(dh.KEY_ROOM_NUMBER, room[i]);
           mdb.insert(dh.TABLE_WEEK, null, cv);
       }

       // данные для таблицы преподавателей
       for (int i = 0; i < lectId.length; i++) {
           cv.clear();
           cv.put(dh.KEY_LECTURER_ID, lectId[i]);
           cv.put(dh.KEY_PHOTO, lectPhoto[i]);
           cv.put(dh.KEY_SURNAME, lectSurname[i]);
           cv.put(dh.KEY_NAME, lectName[i]);
           cv.put(dh.KEY_PATRONYMIC, lectPatronymic[i]);
           cv.put(dh.KEY_CONTACTS, lectContacts[i]);
           mdb.insert(dh.TABLE_LECTURERS, null, cv);
       }


//проверка введенных данных
       Cursor c;
       Log.d(myLog, "--- Table TABLE_WEEK ---");
       c = mdb.query(dh.TABLE_WEEK, null, null, null, null, null, null);
       logCursor(c);
       c.close();
       Log.d(myLog, "--- ---");
       Log.d(myLog, "--- Table TABLE_SUBJECTS ---");
       c = mdb.query(dh.TABLE_SUBJECTS, null, null, null, null, null, null);
       logCursor(c);
       c.close();
       Log.d(myLog, "--- ---");
       Log.d(myLog, "--- Table TABLE_LECTURERS ---");
       c = mdb.query(dh.TABLE_LECTURERS, null, null, null, null, null, null);
       logCursor(c);
       c.close();
       Log.d(myLog, "--- ---");


   }



    // вывод в лог данных из курсора
    void logCursor(Cursor c) {
        if (c != null) {
            if (c.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(cn + " = " + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(myLog, str);
                } while (c.moveToNext());
            }
        } else
            Log.d(myLog, "Cursor is null");
    }






}
