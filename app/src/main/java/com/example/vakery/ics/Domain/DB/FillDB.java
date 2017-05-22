package com.example.vakery.ics.Domain.DB;


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
    String [] lectPhoto = {"http://murkote.com/wp-content/uploads/2016/04/snoopy-cat-10.jpg.pagespeed.ce.skntr4ZWir.jpg",
            "http://murkote.com/wp-content/uploads/2016/04/snoopy-cat-10.jpg.pagespeed.ce.skntr4ZWir.jpg",
            "http://murkote.com/wp-content/uploads/2016/04/snoopy-cat-10.jpg.pagespeed.ce.skntr4ZWir.jpg",
            "http://murkote.com/wp-content/uploads/2016/04/snoopy-cat-10.jpg.pagespeed.ce.skntr4ZWir.jpg",
            "http://murkote.com/wp-content/uploads/2016/04/snoopy-cat-10.jpg.pagespeed.ce.skntr4ZWir.jpg",
            "http://murkote.com/wp-content/uploads/2016/04/snoopy-cat-10.jpg.pagespeed.ce.skntr4ZWir.jpg",
            "http://murkote.com/wp-content/uploads/2016/04/snoopy-cat-10.jpg.pagespeed.ce.skntr4ZWir.jpg",
            "http://murkote.com/wp-content/uploads/2016/04/snoopy-cat-10.jpg.pagespeed.ce.skntr4ZWir.jpg"
    };
    String [] lectSurname = {"Кондратев", "Тесленко", "Неврев", "Бабилунга", "Сытник", "Козерацкий", "Болтенков", "Шановская"};
    String [] lectName = {"Сергей", "Павел", "О", "О", "В", "Г", "В", "О"};
    String [] lectPatronymic = {"Борисович", "Александрович", "И", "Ю", "А", "В", "О", "А"};
    String [] lectContacts = {"000", "000", "000", "000", "000", "000", "000", "000"};
    int [] lectICS = {1, 1, 1, 1, 1, 0, 1, 0};

    int[] NLectId = {1, 2, 3, 4};
    String[] timeStart = {"8:00", "9:50", "11:40", "13:30"};
    String[] timeFinish = {"9:35", "11:25", "13:15", "15:05"};

    int[] ICSSubjId = {1,2};
    String [] ICSTitle = {"Микропроцессорное программирование","Системный анализ"};
    int[] ICSLect = {1,5};
    String[] ICSSemesters = {"5,6","4"};
    String[] ICSKind = {"экзамен", "зачет"};
    String[] ICSInfo = {"123456789","http://studentsthebest.blogspot.com/p/blog-page_88.html"};

    int[] marksSubject = {1, 6};
    int[] marks1 = {30, 40};
    int[] marks2 = {40,40};


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
           cv.put(dh.KEY_PERSONAL_LECTURER, teacherId[i]);
           mdb.insert(dh.TABLE_PERSONAL_SUBJECTS, null, cv);
       }

       // данные для таблицы расписание
       for (int i = 0; i < kindOfWeek.length; i++) {
           cv.clear();
           cv.put(dh.KEY_KIND_OF_WEEK, kindOfWeek[i]);
           cv.put(dh.KEY_DAY_OF_WEEK, dayOfWeek[i]);
           cv.put(dh.KEY_NUMBER_OF_SUBJECT_WEEK, NOfSubj[i]);
           cv.put(dh.KEY_SUBJECT, keySubj[i]);
           cv.put(dh.KEY_TYPE_OF_SUBJECT, typeSubj[i]);
           cv.put(dh.KEY_ROOM_NUMBER, room[i]);
           mdb.insert(dh.TABLE_WEEK, null, cv);
       }

       // данные для таблицы преподавателей
       for (int i = 0; i < lectId.length; i++) {
           cv.clear();
           cv.put(dh.KEY_LECTURER_ID, lectId[i]);
           cv.put(dh.KEY_PHOTO_URL, lectPhoto[i]);
           cv.put(dh.KEY_SURNAME, lectSurname[i]);
           cv.put(dh.KEY_NAME, lectName[i]);
           cv.put(dh.KEY_PATRONYMIC, lectPatronymic[i]);
           cv.put(dh.KEY_CONTACTS, lectContacts[i]);
           cv.put(dh.KEY_ICS, lectICS[i]);
           mdb.insert(dh.TABLE_LECTURERS, null, cv);
       }

       // данные для таблицы времени
       for (int i = 0; i < NLectId.length; i++) {
           cv.clear();
           cv.put(dh.KEY_NUMBER_OF_SUBJECT_TIME, NLectId[i]);
           cv.put(dh.KEY_TIME_START, timeStart[i]);
           cv.put(dh.KEY_TIME_FINISH, timeFinish[i]);
           mdb.insert(dh.TABLE_TIME, null, cv);
       }

       // данные для таблицы предметов ИКС
       for (int i = 0; i < ICSSubjId.length; i++) {
           cv.clear();
           cv.put(dh.KEY_ICS_SUBJECT_ID, ICSSubjId[i]);
           cv.put(dh.KEY_TITLE, ICSTitle[i]);
           cv.put(dh.KEY_ICS_LECTURER, ICSLect[i]);
           cv.put(dh.KEY_SEMESTERS, ICSSemesters[i]);
           cv.put(dh.KEY_KIND, ICSKind[i]);
           cv.put(dh.KEY_SUBJECT_INFO, ICSInfo[i]);
           mdb.insert(dh.TABLE_ICS_SUBJECTS, null, cv);
       }

       // данные для таблицы оценок
       for (int i = 0; i < marksSubject.length; i++) {
           cv.clear();
           cv.put(dh.KEY_SUBJECT, marksSubject[i]);
           cv.put(dh.KEY_1_CHAPTER, marks1[i]);
           cv.put(dh.KEY_2_CHAPTER, marks2[i]);
           mdb.insert(dh.TABLE_MARKS, null, cv);
       }

//проверка введенных данных
       Cursor c;
//       Log.d(myLog, "--- Table TABLE_WEEK ---");
//       c = mdb.query(dh.TABLE_WEEK, null, null, null, null, null, null);
//       logCursor(c);
//       c.close();
//       Log.d(myLog, "--- ---");
//       Log.d(myLog, "--- Table TABLE_SUBJECTS ---");
//       c = mdb.query(dh.TABLE_PERSONAL_SUBJECTS, null, null, null, null, null, null);
//       logCursor(c);
//       c.close();
//       Log.d(myLog, "--- ---");
       Log.d(myLog, "--- Table TABLE_ICS_SUBJECTS ---");
       c = mdb.query(dh.TABLE_MARKS, null, null, null, null, null, null);
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
