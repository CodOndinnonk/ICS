package com.example.vakery.ics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
    final String myLog = "myLog";
    Context mContext;


    private static final int DATABASE_VERSION = 1;//текущая версия БД

    public static final String DATABASE_NAME = "ICS.db";//название файла с БД
    public static final String KEY_ID = "id";//название поля id
    public static final String KEY_SUBJECT = "Subject";//название поля date

    public static final String TABLE_LECTURERS = "Lecturers";//название таблицы
    public static final String KEY_PHOTO = "Photo";//название поля date
    public static final String KEY_SURNAME = "Surname";//название поля date
    public static final String KEY_NAME = "Name";//название поля date
    public static final String KEY_PATRONYMIC = "Patronymic";//название поля date
    public static final String KEY_CONTACTS = "Contacts";//название поля date

    public static final String TABLE_SUBJECTS = "Subjects";//название таблицы
    public static final String KEY_SHORT_TITLE = "Short_title";//название поля date
    public static final String KEY_FULL_TITLE = "Full_title";//название поля date
    public static final String KEY_LECTURER = "Lecturer";//название поля date

    public static final String TABLE_WEEK = "Week";//НЕЧЕТНАЯ
    public static final String KEY_KIND_OF_WEEK = "Kind_of_week";//1- нечетная, 2- четная, 3- все недели
    public static final String KEY_DAY_OF_WEEK = "Day_of_week";//название поля date
    public static final String KEY_NUMBER_OF_SUBJECT = "Number_of_subject";//название поля date
    public static final String KEY_TYPE_OF_SUBJECT = "Type_of_subject";//название поля date
    public static final String KEY_ROOM_NUMBER = "Room_number";//название поля date

    public static final String TABLE_MARKS = "Marks";//название таблицы
    public static final String KEY_1_CHAPTER = "Chapter1";//название поля date
    public static final String KEY_2_CHAPTER = "Chapter2";//название поля date

    public static final String TABLE_VISITING = "Visiting";//название таблицы
    public static final String KEY_NUMBER_OF_DAYS = "Number_of_days";//название поля date

    public static final String TABLE_NOTIFICATIONS = "Notification";//название таблицы
    public static final String KEY_SENDER = "Sender";//название поля date
    public static final String KEY_CONTENT = "Content";//название поля date
    public static final String KEY_IS_READ = "Is_read";//название поля date


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //создание строки, содержащей команда для создания БД
        String CREATE_LECTURERS_TABLE = "CREATE TABLE " + TABLE_LECTURERS +
                "("
                + KEY_ID + " INTEGER NOT NULL PRIMARY KEY,"
                + KEY_PHOTO + " TEXT,"
                + KEY_SURNAME + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_PATRONYMIC + " TEXT,"
                + KEY_CONTACTS + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_LECTURERS_TABLE);

        String CREATE_SUBJECTS_TABLE = "CREATE TABLE " + TABLE_SUBJECTS +
                "("
                + KEY_ID + " INTEGER NOT NULL PRIMARY KEY,"
                + KEY_SHORT_TITLE + " TEXT,"
                + KEY_FULL_TITLE + " TEXT,"
                + KEY_LECTURER + " INTEGER"
                + ")";
        sqLiteDatabase.execSQL(CREATE_SUBJECTS_TABLE);

        String CREATE_WEEK_TABLE = "CREATE TABLE " + TABLE_WEEK +
                "("
                + KEY_ID + " INTEGER NOT NULL PRIMARY KEY autoincrement,"
                + KEY_KIND_OF_WEEK + " INTEGER,"
                + KEY_DAY_OF_WEEK + " INTEGER,"
                + KEY_NUMBER_OF_SUBJECT + " INTEGER,"
                + KEY_SUBJECT + " INTEGER,"
                + KEY_TYPE_OF_SUBJECT + " TEXT,"
                + KEY_ROOM_NUMBER + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_WEEK_TABLE);

        String CREATE_MARKS_TABLE = "CREATE TABLE " + TABLE_MARKS +
                "("
                + KEY_ID + " INTEGER NOT NULL PRIMARY KEY,"
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
                + KEY_ID + " INTEGER NOT NULL PRIMARY KEY,"
                + KEY_SENDER + " INTEGER,"
                + KEY_CONTENT + " TEXT,"
                + KEY_IS_READ + " INTEGER"
                + ")";
        sqLiteDatabase.execSQL(CREATE_NOTIFICATIONS_TABLE);

        ////////////////////////////////////////////////////////////////////////////////
        //тестовая загрузка бд
       FillDB fillDB = new FillDB(sqLiteDatabase);
        fillDB.startFillDB();

    }




    //при обновлении таблицы
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LECTURERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISITING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MARKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEEK);
        onCreate(db);
    }


    public Cursor getSchedule(int kindOfWeek, int day){
        SQLiteDatabase db = this.getReadableDatabase();//формат работы с БД

        Cursor cursor ;

        Log.d(myLog, "Расписание на "+kindOfWeek+" неделю "+day+" день");
        String sqlQuery = "SELECT w.Number_of_subject, w.Type_of_subject, w.Room_number, s.Short_title, s.Full_title \n" +
                "FROM Week w \n" +
                "\tINNER JOIN Subjects s ON ( w.Subject = s.id  )  \n" +
                "WHERE w.Day_of_week = "+String.valueOf(day)+" AND\n" +
                "\t(w.Kind_of_week = "+String.valueOf(kindOfWeek)+" OR\n" +
                "\tw.Kind_of_week = 3)" ;
        cursor = db.rawQuery(sqlQuery, null);
        logCursor(cursor);
        db.close();
        Log.d(myLog, "--- END---");
        return cursor;
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
