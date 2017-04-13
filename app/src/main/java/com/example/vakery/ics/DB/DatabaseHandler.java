package com.example.vakery.ics.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.example.vakery.ics.Entities.ICSSubject;
import com.example.vakery.ics.Entities.Lecturer;
import com.example.vakery.ics.Entities.Mark;
import com.example.vakery.ics.Entities.Notification;
import com.example.vakery.ics.Entities.Schedule;
import com.example.vakery.ics.Entities.Subject;
import com.example.vakery.ics.Entities.Time;
import com.example.vakery.ics.Functional.Vars;

import org.joda.time.DateTime;
import org.joda.time.Weeks;

import java.text.DateFormat;
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
//       FillDB fillDB = new FillDB(sqLiteDatabase);
//        fillDB.startFillDB();

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
    public boolean checkForInfo(){
        SQLiteDatabase db = this.getReadableDatabase();//формат работы с БД
        Cursor cursor ;
        String sqlQuery = "SELECT *  FROM Week" ;
        cursor = db.rawQuery(sqlQuery, null);
        cursor.close();
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
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor ;
        String sqlQuery = "SELECT * from " + TABLE_GLOBAL ;
        cursor = db.rawQuery(sqlQuery, null);
        int weekCount;
        //передвигаем курсор в начало,ч тоб получить значение
        cursor.moveToFirst();

        //определяем год, месяц и день начала обучения(берется из бд)
        int startYear = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_START_DATE)).substring(0,4));
        int startMonth = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_START_DATE)).substring(5,7));
        int startDay = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_START_DATE)).substring(8));

        //определение текущего дня
        Calendar currentDateCalendar = new GregorianCalendar().getInstance();
        currentDateCalendar.setFirstDayOfWeek(Calendar.MONDAY);

        //определение дня начала обучения
        Calendar startDateCalendar = new GregorianCalendar(startYear, startMonth-1, startDay);//месяц - 1, так как отсчет с 0
        startDateCalendar.setFirstDayOfWeek(Calendar.MONDAY);

        //опредение дат в миллисекндах
        long currentDateMilliseconds = currentDateCalendar.getTimeInMillis();
        long startDateTimeMilliseconds = startDateCalendar.getTimeInMillis() ;

        //проверка прошла ли дата начала (началось обучение или нет)
        if(currentDateCalendar.getTime().after(startDateCalendar.getTime()) ||
                currentDateCalendar.getTime().equals(startDateCalendar.getTime())){
            //вычисляем разницу между датами в миллисекундах
            long difference = currentDateMilliseconds - startDateTimeMilliseconds;

            //делим колво прошедших секунд с начала обучения на кол-во дней и +1, так как оно не учитывает первый день начала учебы
            long dayCount =  difference / 86400000 + 1;

            //если полученно кол-во недели дробное, то округляем в большую сторону
            if(((float) dayCount / 7) > ((int) dayCount / 7)){
                weekCount = (int) dayCount / 7 + 1;
            }else {
                weekCount = (int) dayCount / 7;
            }
        }else {
            //если обучение еще не началось
            weekCount = 0;
        }
        return weekCount;
    }

//  УВЕДОМЛЕНИЯ  ///////////////////////////////////////////////////////////////////////////////////

    /***
     * Добавление уведомления в локальную бд
     * @param notification
     */
    public void addNotification(Notification notification){//дописать все поля
        SQLiteDatabase db = this.getWritableDatabase();//формат работы с БД
        ContentValues values = new ContentValues();//создание переменной, позволяющей создать шаблот "записи" и заполниять его для добавления в БД
        values.put(KEY_NOTIFICATION_ID, notification.getmId());
        values.put(KEY_SENDER, notification.getmSenderId());
        values.put(KEY_CONTENT, notification.getmContent());
        values.put(KEY_IS_READ, notification.getmISRead());

        db.insert(TABLE_NOTIFICATIONS, null, values);//добавление в таблицу шаблона, заполненного ранее
        db.close();
    }


//  РАСПИСАНИЕ  ////////////////////////////////////////////////////////////////////////////////////

    /***
     * Добавления расписания на день
     * @param schedule
     */
    public void addSchedule(Schedule schedule){//дописать все поля
        SQLiteDatabase db = this.getWritableDatabase();//формат работы с БД
        ContentValues values = new ContentValues();//создание переменной, позволяющей создать шаблот "записи" и заполниять его для добавления в БД
        values.put(KEY_ID, schedule.getmId());
        values.put(KEY_KIND_OF_WEEK, schedule.getmKindOfWeek());
        values.put(KEY_DAY_OF_WEEK, schedule.getmDayOfWeek());
        values.put(KEY_NUMBER_OF_SUBJECT_WEEK, schedule.getmNumberOfSubject());
        values.put(KEY_SUBJECT, schedule.getmSubjectId());
        values.put(KEY_TYPE_OF_SUBJECT, schedule.getmTypeOfSubject());
        values.put(KEY_ROOM_NUMBER, schedule.getmRoomNumber());

        db.insert(TABLE_WEEK, null, values);//добавление в таблицу шаблона, заполненного ранее
        db.close();
    }


    /***
     * берем расписание на заданный день заанной недели
     * @param kindOfWeek тип недели (1- нечетная, 2- четная, 3- все типы недель)
     * @param day день недели
     * @return
     */
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


    /***
     * берем предмет из таблицы расписания
     * @param subjectId уникальный код предмета
     * @return
     */
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


    /***
     * Добавление предмета в бд (обычные прелметы)
     * @param subject
     */
    public void addPersonalSubject(Subject subject){//дописать все поля
        SQLiteDatabase db = this.getWritableDatabase();//формат работы с БД
        ContentValues values = new ContentValues();//создание переменной, позволяющей создать шаблот "записи" и заполниять его для добавления в БД
        values.put(KEY_SUBJECT_ID, subject.getmId());
        values.put(KEY_SHORT_TITLE, subject.getmShortTitle());
        values.put(KEY_FULL_TITLE, subject.getmFullTitle());
        values.put(KEY_PERSONAL_LECTURER, subject.getmLecturerId());

        db.insert(TABLE_PERSONAL_SUBJECTS, null, values);//добавление в таблицу шаблона, заполненного ранее
        db.close();
    }


//  КАФЕДРА ИКС ////////////////////////////////////////////////////////////////////////////////////

    /***
     * Добавление предмета в бд
     * @param icsSubject объект предмета
     */
    public void addICSSubject(ICSSubject icsSubject){//дописать все поля
        SQLiteDatabase db = this.getWritableDatabase();//формат работы с БД
        ContentValues values = new ContentValues();//создание переменной, позволяющей создать шаблот "записи" и заполниять его для добавления в БД
        values.put(KEY_ICS_SUBJECT_ID, icsSubject.getMid());
        values.put(KEY_TITLE, icsSubject.getmTitle());
        values.put(KEY_ICS_LECTURER, icsSubject.getmLecturerId());
        values.put(KEY_SEMESTERS, icsSubject.getmSemesters());
        values.put(KEY_KIND, icsSubject.getmKind());
        values.put(KEY_SUBJECT_INFO, icsSubject.getmExtraInfo());

        db.insert(TABLE_ICS_SUBJECTS, null, values);//добавление в таблицу шаблона, заполненного ранее
        db.close();
    }


    /***
     * берем предмет из таблицы с всеми предметами кафедры ИКС
     * @param subjectId уникальный код предмета
     * @return
     */
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


    /***
     * берем все предметы из таблицы с всеми предметами кафедры ИКС
     * @return
     */
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

    /***
     * Добавление препдавателя в бд
     * @param lecturer объект "преподаватель"
     */
    public void addLecturer(Lecturer lecturer){//дописать все поля
        SQLiteDatabase db = this.getWritableDatabase();//формат работы с БД
        ContentValues values = new ContentValues();//создание переменной, позволяющей создать шаблот "записи" и заполниять его для добавления в БД
        values.put(KEY_LECTURER_ID, lecturer.getmId());
        values.put(KEY_PHOTO_URL, lecturer.getmPhoto());
        values.put(KEY_SURNAME, lecturer.getmSurname());
        values.put(KEY_NAME, lecturer.getmName());
        values.put(KEY_PATRONYMIC, lecturer.getmPatronymic());
        values.put(KEY_CONTACTS, lecturer.getmContacts());
        values.put(KEY_ICS, lecturer.getmIsICS());
        db.insert(TABLE_LECTURERS, null, values);//добавление в таблицу шаблона, заполненного ранее
        db.close();
    }


    /***
     * берем всех преподавателей
     * @return
     */
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


    /***
     * берем преподавателей, работающих на кафедре ИКС
     * @return
     */
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


    /***
     * берем препоавателей со всех кафедр кроме ИКС
     * @return
     */
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


    /***
     * берем определенного преподавателя из всех
     * @param lecturerId уникальный код преподавателя
     * @return
     */
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
        cursor.close();
        db.close();
        return lecturer;
    }


    /***
     * берем спикой id всех преподавателей, у которых есть ссылка на фотограцию, используется при скачивании
     * @return
     */
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
        } else {
            Log.d(logQuery, "Cursor is null");
        }
        db.close();
        cursor.close();
        return lecturersId;
    }


//  ОЦЕНКИ  ////////////////////////////////////////////////////////////////////////////////////////

    /***
     * Добавление оценки по предмету
     * @param mark
     */
    public void addMark(Mark mark){//дописать все поля
        SQLiteDatabase db = this.getWritableDatabase();//формат работы с БД
        ContentValues values = new ContentValues();//создание переменной, позволяющей создать шаблот "записи" и заполниять его для добавления в БД
        values.put(KEY_ID, mark.getmId());
        values.put(KEY_SUBJECT, mark.getmSubjectId());
        values.put(KEY_1_CHAPTER, mark.getmChapter1());
        values.put(KEY_2_CHAPTER, mark.getmChapter2());

        db.insert(TABLE_MARKS, null, values);//добавление в таблицу шаблона, заполненного ранее
        db.close();
    }


    /***
     * берем все оценки
     * @return
     */
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

    /***
     * Добавление времени начала и окончания пары
     * @param time
     */
    public void addTime(Time time){//дописать все поля
        SQLiteDatabase db = this.getWritableDatabase();//формат работы с БД
        ContentValues values = new ContentValues();//создание переменной, позволяющей создать шаблот "записи" и заполниять его для добавления в БД
        values.put(KEY_ID, time.getmId());
        values.put(KEY_NUMBER_OF_SUBJECT_TIME, time.getmNumberOfSubject());
        values.put(KEY_TIME_START, time.getmTimeStart());
        values.put(KEY_TIME_FINISH, time.getmTimeEnd());

        db.insert(TABLE_TIME, null, values);//добавление в таблицу шаблона, заполненного ранее
        db.close();
    }


    /***
     * берем время начала и конца пар
     * @return
     */
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
