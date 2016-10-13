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
    public static final String KEY_KIND_OF_WEEK = "Kind_of_week";//1- нечетная, 2- четная
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
    public void onCreate(SQLiteDatabase db) {
        Log.d(myLog, "onCreate DB");
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
        db.execSQL(CREATE_LECTURERS_TABLE);

        String CREATE_SUBJECTS_TABLE = "CREATE TABLE " + TABLE_SUBJECTS +
                "("
                + KEY_ID + " INTEGER NOT NULL PRIMARY KEY,"
                + KEY_SHORT_TITLE + " TEXT,"
                + KEY_FULL_TITLE + " TEXT,"
                + KEY_LECTURER + " INTEGER"
                + ")";
        db.execSQL(CREATE_SUBJECTS_TABLE);

        String CREATE_WEEK_TABLE = "CREATE TABLE " + TABLE_WEEK +
                "("
                + KEY_ID + " INTEGER NOT NULL PRIMARY KEY,"
                + KEY_KIND_OF_WEEK + " INTEGER,"
                + KEY_DAY_OF_WEEK + " INTEGER,"
                + KEY_NUMBER_OF_SUBJECT + " INTEGER,"
                + KEY_SUBJECT + " INTEGER,"
                + KEY_TYPE_OF_SUBJECT + " TEXT,"
                + KEY_ROOM_NUMBER + " TEXT"
                + ")";
        db.execSQL(CREATE_WEEK_TABLE);

        String CREATE_MARKS_TABLE = "CREATE TABLE " + TABLE_MARKS +
                "("
                + KEY_ID + " INTEGER NOT NULL PRIMARY KEY,"
                + KEY_1_CHAPTER + " INTEGER,"
                + KEY_2_CHAPTER + " INTEGER"
                + ")";
        db.execSQL(CREATE_MARKS_TABLE);

        String CREATE_VISITING_TABLE = "CREATE TABLE " + TABLE_VISITING +
                "("
                + KEY_ID + " INTEGER NOT NULL PRIMARY KEY,"
                + KEY_NUMBER_OF_DAYS + " INTEGER"
                + ")";
        db.execSQL(CREATE_VISITING_TABLE);

        String CREATE_NOTIFICATIONS_TABLE = "CREATE TABLE " + TABLE_NOTIFICATIONS +
                "("
                + KEY_ID + " INTEGER NOT NULL PRIMARY KEY,"
                + KEY_SENDER + " TEXT,"
                + KEY_CONTENT + " TEXT,"
                + KEY_IS_READ + " INTEGER"
                + ")";
        db.execSQL(CREATE_NOTIFICATIONS_TABLE);
        DemoFill(db);
    }


    public void DemoFill(SQLiteDatabase db){
        ContentValues record = new ContentValues();//создание переменной, позволяющей создать шаблот "записи" и заполниять его для добавления в БД
        record.put(KEY_PHOTO, "no");
        record.put(KEY_SURNAME, "Бабилунга");
        record.put(KEY_NAME, "Оксана");
        record.put(KEY_PATRONYMIC, "Юрьевна");
        record.put(KEY_CONTACTS, "123-12-32");
        db.insert(TABLE_LECTURERS, null, record);//добавление в таблицу щаблона, заполненного ранее
        record.clear();
        record.put(KEY_PHOTO, "no");
        record.put(KEY_SURNAME, "Болтенков");
        record.put(KEY_NAME, "В");
        record.put(KEY_PATRONYMIC, "O");
        record.put(KEY_CONTACTS, "123@mail.ru");
        db.insert(TABLE_LECTURERS, null, record);//добавление в таблицу щаблона, заполненного ранее
        record.clear();

        record.put(KEY_SHORT_TITLE, "Инф Технологии");
        record.put(KEY_FULL_TITLE, "Информационные технологии");
        record.put(KEY_LECTURER, 1);
        db.insert(TABLE_SUBJECTS, null, record);//добавление в таблицу щаблона, заполненного ранее
        record.clear();
        record.put(KEY_SHORT_TITLE, "Защита информации");
        record.put(KEY_FULL_TITLE, "Защита информационных технологий");
        record.put(KEY_LECTURER, 2);
        db.insert(TABLE_SUBJECTS, null, record);//добавление в таблицу щаблона, заполненного ранее
        record.clear();
    }


    //при обновлении таблицы
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(myLog,"onUpgrade DB");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LECTURERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISITING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MARKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEEK);
        onCreate(db);
    }


    public void getAlarmById(int Id) {//берем запись по Id, необходимое нам Id передается с другой активности
        Log.d(myLog,"getAlarmById = ");
        SQLiteDatabase db = this.getWritableDatabase();
        //переменная, хранящая найденую запись
        Cursor cursor = db.query(TABLE_LECTURERS, new String[] { DatabaseHandler.KEY_ID, DatabaseHandler.KEY_PHOTO,
                        KEY_SURNAME, KEY_NAME, DatabaseHandler.KEY_PATRONYMIC, DatabaseHandler.KEY_CONTACTS },  KEY_ID + "=?",
                new String[] { String.valueOf(Id) }, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
//создание обьекта ЗАПИСЬ и заполняем его данными из найденной ранее записи
        Log.d(myLog, "cursor.getString(3) = " + cursor.getString(3));
    }
}
