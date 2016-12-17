package com.example.vakery.ics.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import com.example.vakery.ics.Entities.Lecturer;

public class DatabaseHandler extends SQLiteOpenHelper {
    final String myLog = "myLog";
    final String logQuery = "logQuery";
    Context mContext;


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

    public static final String TABLE_WEEK = "Week";//НЕЧЕТНАЯ
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




    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
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

        ////////////////////////////////////////////////////////////////////////////////
        //тестовая загрузка бд
       FillDB fillDB = new FillDB(sqLiteDatabase);
        fillDB.startFillDB();

    }


    //при обновлении таблицы
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LECTURERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONAL_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISITING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MARKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEEK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ICS_SUBJECTS);
        onCreate(db);
    }


//  РАСПИСАНИЕ  ////////////////////////////////////////////////////////////////////////////////////


    //берем расписание на заданный день заанной недели
    public Cursor getSchedule(int kindOfWeek, int day){
        SQLiteDatabase db = this.getReadableDatabase();//формат работы с БД

        Cursor cursor ;

        Log.d(myLog, "Расписание на "+kindOfWeek+" неделю "+day+" день");

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
        logCursor(cursor);
        db.close();
        Log.d(logQuery, "--- END---");
        return cursor;
    }


    // берем предмет из таблицы расписания
    public Cursor getPersonalSubject(int subjectId){
        SQLiteDatabase db = this.getReadableDatabase();//формат работы с БД

        Cursor cursor ;

        String sqlQuery = "SELECT s.Short_title, s.Full_title, l.Lecturer_id, l.Surname, l.Name, l.Patronymic, w.Type_of_subject, w.Room_number\n" +
                "FROM Subjects s \n" +
                "\tINNER JOIN Lecturers l ON ( s.Lecturer_personal = l.Lecturer_id  )  \n" +
                "\tINNER JOIN Week w ON ( s.Subject_id = w.Subject  )  \n" +
                "WHERE s.Subject_id =  " + String.valueOf(subjectId) ;

        cursor = db.rawQuery(sqlQuery, null);
        logCursor(cursor);
        db.close();
        Log.d(logQuery, "--- END---");
        return cursor;
    }


//  КАФЕДРА ИКС ////////////////////////////////////////////////////////////////////////////////////


    //берем предмет из таблицы с всеми предметами кафедры ИКС
    public Cursor getICSSubject(int subjectId){
        SQLiteDatabase db = this.getReadableDatabase();//формат работы с БД

        Cursor cursor ;

        String sqlQuery = "SELECT i.Title, i.Semesters, i.Kind, i.Extra_info, l.Lecturer_id, l.Surname, l.Name, l.Patronymic\n" +
                "FROM ICS_subjects i \n" +
                "\tINNER JOIN Lecturers l ON ( i.Lecturer_ICS = l.Lecturer_id  )  \n" +
                "WHERE i.ICS_subject_id =   " + String.valueOf(subjectId) ;;

        cursor = db.rawQuery(sqlQuery, null);
        logCursor(cursor);
        db.close();
        Log.d(logQuery, "--- END---");
        return cursor;
    }


    //берем все предметы из таблицы с всеми предметами кафедры ИКС
    public Cursor getICSSubjects(){
        SQLiteDatabase db = this.getReadableDatabase();//формат работы с БД

        Cursor cursor ;

        String sqlQuery = "SELECT i.ICS_subject_id, i.Title, i.Semesters, i.Kind, i.Extra_info, l.Lecturer_id, l.Surname, l.Name, l.Patronymic\n" +
                "FROM ICS_subjects i \n" +
                "\tINNER JOIN Lecturers l ON ( i.Lecturer_ICS = l.Lecturer_id  )";

        cursor = db.rawQuery(sqlQuery, null);
        logCursor(cursor);
        db.close();
        Log.d(logQuery, "--- END---");
        return cursor;
    }


//  ПРЕПОДАВАТЕЛИ  /////////////////////////////////////////////////////////////////////////////////


    //берем всех преподавателей
    public Cursor getAllLecturers(){
        SQLiteDatabase db = this.getReadableDatabase();//формат работы с БД

        Cursor cursor ;

        String sqlQuery = "SELECT * \n" +
                "FROM Lecturers l";

        cursor = db.rawQuery(sqlQuery, null);
        logCursor(cursor);
        db.close();
        Log.d(logQuery, "--- END---");
        return cursor;
    }


    //берем преподавателей, работающих на кафедре ИКС
    public Cursor getICSLecturers(){
        SQLiteDatabase db = this.getReadableDatabase();//формат работы с БД

        Cursor cursor ;

        String sqlQuery = "SELECT * \n" +
                "FROM Lecturers l \n" +
                "WHERE l.ICS = 1";

        cursor = db.rawQuery(sqlQuery, null);
        logCursor(cursor);
        db.close();
        Log.d(logQuery, "--- END---");
        return cursor;
    }


    //берем препоавателей со всех кафедр кроме ИКС
    public Cursor getNotICSLecturers(){
        SQLiteDatabase db = this.getReadableDatabase();//формат работы с БД

        Cursor cursor ;

        String sqlQuery = "SELECT * \n" +
                "FROM Lecturers l \n" +
                "WHERE l.ICS = 0";

        cursor = db.rawQuery(sqlQuery, null);
        logCursor(cursor);
        db.close();
        Log.d(logQuery, "--- END---");
        return cursor;
    }


    //берем определенного преподавателя из всех
    public Lecturer getLecturer(int lecturerId) {
        SQLiteDatabase db = this.getReadableDatabase();//формат работы с БД
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
            Log.d(myLog, "Cursor is null");
        }
        return lecturer;
    }


    //берем спикой id всех преподавателей, у которых есть ссылка на фотограцию, используется при скачивании
    public ArrayList<Integer> getLecturersId(){
        SQLiteDatabase db = this.getReadableDatabase();//формат работы с БД
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
        } else
            Log.d(logQuery, "Cursor is null");

        db.close();
        return lecturersId;
    }


//  ОЦЕНКИ  ////////////////////////////////////////////////////////////////////////////////////////


    //берем все оценки
    public Cursor getMarks(){
        SQLiteDatabase db = this.getReadableDatabase();//формат работы с БД

        Cursor cursor ;

        String sqlQuery = "SELECT m.Chapter1, m.Chapter2, s.Full_title\n" +
                "FROM Marks m \n" +
                "\tINNER JOIN Subjects s ON ( m.Subject = s.Subject_id  )  ";

        cursor = db.rawQuery(sqlQuery, null);
        logCursor(cursor);
        db.close();
        Log.d(logQuery, "--- END---");
        return cursor;
    }


//  ВРЕМЯ  /////////////////////////////////////////////////////////////////////////////////////////


    //берем время начала и конца пар
    public Cursor getTime(){
        SQLiteDatabase db = this.getReadableDatabase();//формат работы с БД

        Cursor cursor ;

        String sqlQuery = "SELECT * \n" +
                "FROM Time";

        cursor = db.rawQuery(sqlQuery, null);
        logCursor(cursor);
        db.close();
        Log.d(logQuery, "--- END---");
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
                    Log.d(logQuery, str);
                } while (c.moveToNext());
            }
        } else
            Log.d(logQuery, "Cursor is null");
    }


}
