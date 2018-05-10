package com.example.vakery.ics.Domain.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.vakery.ics.Domain.Entities.ICSSubject;
import com.example.vakery.ics.Domain.Entities.Lecturer;
import com.example.vakery.ics.Domain.Entities.Mark;
import com.example.vakery.ics.Domain.Entities.Notification;
import com.example.vakery.ics.Domain.Entities.Schedule;
import com.example.vakery.ics.Domain.Entities.Subject;
import com.example.vakery.ics.Domain.Entities.Time;
import com.example.vakery.ics.Application.Functional.Vars;

import java.util.GregorianCalendar;


public class DatabaseHandler extends SQLiteOpenHelper  {
    final String myLog = "myLog";
    final String logQuery = "logQuery";
    Context mContext = Vars.getContext();


    private static final int DATABASE_VERSION = 1;//текущая версия БД

    public static final String DATABASE_NAME = "ICS.db";//название файла с БД
    public static final String KEY_ID = "id";//название поля id
    public static final String KEY_SUBJECT = "Subject";//название поля date

    public static final String TABLE_LECTURERS = "Lecturers";//название таблицы
    public static final String KEY_LECTURER_ID = "Lecturer_id";//название поля id
    public static final String KEY_PHOTO_URL = "Photo_url";//название поля date
    public static final String KEY_SURNAME = "Surname";//название поля date
    public static final String KEY_NAME = "Name";//название поля date
    public static final String KEY_PATRONYMIC = "Patronymic";//название поля date
    public static final String KEY_CONTACTS = "Contacts";//название поля date
    public static final String KEY_ICS = "ICS";//название поля date

    public static final String TABLE_PERSONAL_SUBJECTS = "Subjects";//название таблицы
    public static final String KEY_SUBJECT_ID = "Subject_id";//название поля id
    public static final String KEY_SHORT_TITLE = "Short_title";//название поля date
    public static final String KEY_FULL_TITLE = "Full_title";//название поля date
    public static final String KEY_PERSONAL_LECTURER = "Lecturer_personal";//название поля date

    public static final String TABLE_WEEK = "Week";
    public static final String KEY_KIND_OF_WEEK = "Kind_of_week";//1- нечетная, 2- четная, 3- все недели
    public static final String KEY_DAY_OF_WEEK = "Day_of_week";//название поля date
    public static final String KEY_NUMBER_OF_SUBJECT_WEEK = "Number_of_subject_week";//название поля date
    public static final String KEY_TYPE_OF_SUBJECT = "Type_of_subject";//название поля date
    public static final String KEY_ROOM_NUMBER = "Room_number";//название поля date

    public static final String TABLE_MARKS = "Marks";//название таблицы
    public static final String KEY_1_CHAPTER = "Chapter1";//название поля date
    public static final String KEY_2_CHAPTER = "Chapter2";//название поля date

    public static final String TABLE_VISITING = "Visiting";//название таблицы
    public static final String KEY_NUMBER_OF_DAYS = "Number_of_days";//название поля date

    public static final String TABLE_NOTIFICATIONS = "Notification";//название таблицы
    public static final String KEY_NOTIFICATION_ID = "Notification_id";//название поля id
    public static final String KEY_SENDER = "Sender";//название поля date
    public static final String KEY_CONTENT = "Content";//название поля date
    public static final String KEY_IS_READ = "Is_read";//название поля date

    public static final String TABLE_TIME = "Time";//название таблицы
    public static final String KEY_NUMBER_OF_SUBJECT_TIME = "Number_of_subject_time";//название поля id
    public static final String KEY_TIME_START = "Time_start";//название поля id
    public static final String KEY_TIME_FINISH = "Time_finish";//название поля id

    public static final String TABLE_ICS_SUBJECTS = "ICS_subjects";//название таблицы
    public static final String KEY_ICS_SUBJECT_ID = "ICS_subject_id";//название поля id
    public static final String KEY_TITLE = "Title";//название поля id
    public static final String KEY_ICS_LECTURER = "Lecturer_ICS";//название поля id
    public static final String KEY_SEMESTERS = "Semesters";//название поля id
    public static final String KEY_KIND = "Kind";//название поля id
    public static final String KEY_SUBJECT_INFO = "Extra_info";//название поля id

    public static final String TABLE_GLOBAL = "Global";//название таблицы
    public static final String KEY_START_DATE = "Start_date";//название поля id
    public static final String KEY_NUMBER_OF_WEEKS = "Number_of_weeks";//название поля id

    public DatabaseHandler() {
        super(Vars.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //создание строки, содержащей команда для создания БД
        String CREATE_LECTURERS_TABLE = "CREATE TABLE " + TABLE_LECTURERS +
                "("
                + KEY_LECTURER_ID + " INTEGER NOT NULL PRIMARY KEY,"
                + KEY_PHOTO_URL + " TEXT,"
                + KEY_SURNAME + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_PATRONYMIC + " TEXT,"
                + KEY_CONTACTS + " TEXT,"
                + KEY_ICS + " INTEGER"
                + ")";
        sqLiteDatabase.execSQL(CREATE_LECTURERS_TABLE);

        String CREATE_PERSONAL_SUBJECTS_TABLE = "CREATE TABLE " + TABLE_PERSONAL_SUBJECTS +
                "("
                + KEY_SUBJECT_ID + " INTEGER NOT NULL PRIMARY KEY,"
                + KEY_SHORT_TITLE + " TEXT,"
                + KEY_FULL_TITLE + " TEXT,"
                + KEY_PERSONAL_LECTURER + " INTEGER"
                + ")";
        sqLiteDatabase.execSQL(CREATE_PERSONAL_SUBJECTS_TABLE);

        String CREATE_WEEK_TABLE = "CREATE TABLE " + TABLE_WEEK +
                "("
                + KEY_ID + " INTEGER NOT NULL PRIMARY KEY autoincrement,"
                + KEY_KIND_OF_WEEK + " INTEGER,"
                + KEY_DAY_OF_WEEK + " INTEGER,"
                + KEY_NUMBER_OF_SUBJECT_WEEK + " INTEGER,"
                + KEY_SUBJECT + " INTEGER,"
                + KEY_TYPE_OF_SUBJECT + " TEXT,"
                + KEY_ROOM_NUMBER + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_WEEK_TABLE);

        String CREATE_MARKS_TABLE = "CREATE TABLE " + TABLE_MARKS +
                "("
                + KEY_ID + " INTEGER NOT NULL PRIMARY KEY,"
                + KEY_SUBJECT + " INTEGER,"
                + KEY_1_CHAPTER + " INTEGER,"
                + KEY_2_CHAPTER + " INTEGER"
                + ")";
        sqLiteDatabase.execSQL(CREATE_MARKS_TABLE);

        String CREATE_VISITING_TABLE = "CREATE TABLE " + TABLE_VISITING +
                "("
                + KEY_ID + " INTEGER NOT NULL PRIMARY KEY,"
                + KEY_NUMBER_OF_DAYS + " INTEGER"
                + ")";
        sqLiteDatabase.execSQL(CREATE_VISITING_TABLE);

        String CREATE_NOTIFICATIONS_TABLE = "CREATE TABLE " + TABLE_NOTIFICATIONS +
                "("
                + KEY_NOTIFICATION_ID + " INTEGER NOT NULL PRIMARY KEY,"
                + KEY_SENDER + " INTEGER,"
                + KEY_CONTENT + " TEXT,"
                + KEY_IS_READ + " INTEGER"
                + ")";
        sqLiteDatabase.execSQL(CREATE_NOTIFICATIONS_TABLE);

        String CREATE_TABLE_TIME = "CREATE TABLE " + TABLE_TIME +
                "("
                + KEY_ID + " INTEGER NOT NULL PRIMARY KEY autoincrement,"
                + KEY_NUMBER_OF_SUBJECT_TIME + " INTEGER,"
                + KEY_TIME_START + " TEXT,"
                + KEY_TIME_FINISH + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE_TIME);

        String CREATE_ICS_SUBJECTS_TABLE = "CREATE TABLE " + TABLE_ICS_SUBJECTS +
                "("
                + KEY_ICS_SUBJECT_ID + " INTEGER NOT NULL PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_ICS_LECTURER + " INTEGER,"
                + KEY_SEMESTERS + " TEXT,"
                + KEY_KIND + " TEXT,"
                + KEY_SUBJECT_INFO + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_ICS_SUBJECTS_TABLE);

        String CREATE_GLOBAL_TABLE = "CREATE TABLE " + TABLE_GLOBAL +
                "("
                + KEY_ID + " INTEGER NOT NULL PRIMARY KEY,"
                + KEY_START_DATE + " DATE NOT NULL,"
                + KEY_NUMBER_OF_WEEKS + " INTEGER NOT NULL"
                + ")";
        sqLiteDatabase.execSQL(CREATE_GLOBAL_TABLE);
        ////////////////////////////////////////////////////////////////////////////////
        //тестовая загрузка бд
       FillDB fillDB = new FillDB(sqLiteDatabase);
       fillDB.startFillDB();

    }


    /***
     * Очистка таблицы
     * @param tableName название таблицы, которую надо очистить
     */
    public void clearTable(String tableName){
        SQLiteDatabase db = this.getReadableDatabase();//формат работы с БД
        db.execSQL("DELETE FROM " + tableName);
    }


    /***
     * Обновление бд
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DropDatabase();
    }


    /***
     * Удаляет таблицы и содает их заново
     */
    public void DropDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LECTURERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONAL_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISITING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MARKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEEK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ICS_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GLOBAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIME);
        onCreate(db);
    }


    /***
     * Проверка на наличие информации в базе данных
     * @return true -> есть данные, false -> нет данных
     */
    public static boolean checkForInfo(){
        SQLiteDatabase db = new DatabaseHandler().getReadableDatabase();//формат работы с БД
        Cursor cursor ;
        String sqlQuery = "SELECT *  FROM Week" ;
        cursor = db.rawQuery(sqlQuery, null);
        if(cursor.getCount() > 0){
            db.close();
            return true;
        }else {
            db.close();
            return false;
        }
    }

//  ГЛОБАЛЬНАЯ ИНФОРМАЦИЯ  /////////////////////////////////////////////////////////////////////////

    /***
     * Добавление глобальной информации, полученной от сервера
     * @param id уникальный идентификатор записи на сервере
     * @param startDate дата начала учебного процесса
     * @param numberOfWeeks кол-во учебных недель
     */
    public void addGlobalInfo(int id, String startDate, int numberOfWeeks){//дописать все поля
        SQLiteDatabase db = this.getWritableDatabase();//формат работы с БД
        ContentValues values = new ContentValues();//создание переменной, позволяющей создать шаблот "записи" и заполниять его для добавления в БД
        values.put(KEY_ID, id);
        values.put(KEY_START_DATE, startDate);
        values.put(KEY_NUMBER_OF_WEEKS, numberOfWeeks);

        db.insert(TABLE_GLOBAL, null, values);//добавление в таблицу шаблона, заполненного ранее
        db.close();

    }


    /***
     * Возвращает текущий номер учебной недели
     * @return номер недели
     */
    public int getCurrentWeek(){
       try {
           SQLiteDatabase db = this.getReadableDatabase();
           Cursor cursor;
           String sqlQuery = "SELECT * from " + TABLE_GLOBAL;
           cursor = db.rawQuery(sqlQuery, null);
           int weekCount;
           //передвигаем курсор в начало,ч тоб получить значение
           cursor.moveToFirst();

           //определяем год, месяц и день начала обучения(берется из бд)
           int startYear = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_START_DATE)).substring(0, 4));
           int startMonth = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_START_DATE)).substring(5, 7));
           int startDay = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_START_DATE)).substring(8));

           //определение текущего дня
           Calendar currentDateCalendar = new GregorianCalendar().getInstance();
           currentDateCalendar.setFirstDayOfWeek(Calendar.MONDAY);

           //определение дня начала обучения
           Calendar startDateCalendar = new GregorianCalendar(startYear, startMonth - 1, startDay);//месяц - 1, так как отсчет с 0
           startDateCalendar.setFirstDayOfWeek(Calendar.MONDAY);

           //опредение дат в миллисекндах
           long currentDateMilliseconds = currentDateCalendar.getTimeInMillis();
           long startDateTimeMilliseconds = startDateCalendar.getTimeInMillis();

           //проверка прошла ли дата начала (началось обучение или нет)
           if (currentDateCalendar.getTime().after(startDateCalendar.getTime()) ||
                   currentDateCalendar.getTime().equals(startDateCalendar.getTime())) {
               //вычисляем разницу между датами в миллисекундах
               long difference = currentDateMilliseconds - startDateTimeMilliseconds;

               //делим колво прошедших секунд с начала обучения на кол-во дней и +1, так как оно не учитывает первый день начала учебы
               long dayCount = difference / 86400000 + 1;

               //если полученно кол-во недели дробное, то округляем в большую сторону
               if (((float) dayCount / 7) > ((int) dayCount / 7)) {
                   weekCount = (int) dayCount / 7 + 1;
               } else {
                   weekCount = (int) dayCount / 7;
               }
           } else {
               //если обучение еще не началось
               weekCount = 0;
           }
           return weekCount;
       }catch (Exception e){Log.e(Vars.myLog,e.toString());
       return 0;}
    }








//  КАФЕДРА ИКС ////////////////////////////////////////////////////////////////////////////////////




//  ПРЕПОДАВАТЕЛИ  /////////////////////////////////////////////////////////////////////////////////




//  ОЦЕНКИ  ////////////////////////////////////////////////////////////////////////////////////////

    /***
     * вывод в лог данных из курсора
     * @param c курсор с данными
     */
    void logCursor(Cursor c) {
        if (c != null) {
            if (c.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(cn + " = " + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(logQuery, str);
                } while (c.moveToNext());
            }
        } else
            Log.d(logQuery, "Cursor is null");
    }


}
