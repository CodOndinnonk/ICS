package com.example.vakery.ics.Functional;


import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.vakery.ics.DB.DatabaseHandler;
import com.example.vakery.ics.Entities.ICSSubject;
import com.example.vakery.ics.Entities.Lecturer;
import com.example.vakery.ics.Entities.Mark;
import com.example.vakery.ics.Entities.Notification;
import com.example.vakery.ics.Entities.Schedule;
import com.example.vakery.ics.Entities.Subject;
import com.example.vakery.ics.Entities.Time;
import com.example.vakery.ics.R;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DownloadInfo {
    final String myLog = "myLog";
    DatabaseHandler db = new DatabaseHandler();


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //некорректная работа
    public DownloadInfo() {
        //prepareForLoadingImg();
    }


    /***
     * Подготовительные действия для загрущзки изображений с сервера
     */
    public void prepareForLoadingImg() {
        Executor downloadExecutor = Executors.newFixedThreadPool(5);
        ActivityManager am = (ActivityManager) Vars.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        int memClass = am.getMemoryClass();
        final int memoryCacheSize = 1024 * 1024 * memClass / 16;
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(android.R.color.transparent)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .cacheInMemory(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(Vars.getContext().getApplicationContext())
                .taskExecutor(downloadExecutor)
                .memoryCache(new UsingFreqLimitedMemoryCache(memoryCacheSize)) // 2 Mb
                .imageDownloader(new BaseImageDownloader(Vars.getContext(), 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);
    }


    /***
     * Проверка на наличие всех необходимых даный на локальном устройстве(фото, бд)
     */
    public void checkForInformation() {
        final ArrayList<Lecturer> listOfLecturers = new ArrayList<Lecturer>();
        ArrayList<Integer> lecturersId = db.getLecturersId();

        // загрузка изображений

        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(myLog, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }

        //проверяем существует ли папка для хранения фото, берем ссылку на папку (File) из Vars
        if (!Vars.getImageFileDir().exists()) {
            Log.d(myLog, "директории нет, создаем ее");
            // создаем каталог
            Vars.getImageFileDir().mkdirs();
        } else {
        }

        for (int i = 0; i < lecturersId.size(); i++) {
            //формируем имя файла для проверки его наличия
            String name = "lecturer_" + lecturersId.get(i).toString() + ".png";
            try {
                //проверяем наличие фотографии в папке
                if (!new File(Vars.getImageFileDir() + File.separator + name).exists()) {
                    //создаем экземпляр преподавателя с id и Photo_url
                    listOfLecturers.add(new Lecturer(db.getLecturer(lecturersId.get(i)).getmId(), db.getLecturer(lecturersId.get(i)).getmPhoto()));
                } else {
                }
            } catch (Exception e) {
                Log.d(myLog, "Ошибка проверки наличия фото");
            }
        }
        //если лист с преподавателями, которых надо скачать не пустой
        if (listOfLecturers.size() > 0) {
            //проверка на наличие интернет соединения
            if (checkInternetConnection(Vars.getContext())) {

                //создаем фоновый поток, чтоб основной не подвисал
                //Thread t = new Thread(new Runnable() {
                //    public void run() {
                this.loadImg(listOfLecturers);
                //     }
//            });
//            //запуск потока
//            t.start();
            } else {
                Log.d(myLog, "отсутствует интернет соединение");
                Toast.makeText(Vars.getContext(), R.string.no_internet_connection_info, Toast.LENGTH_LONG).show();
            }
        }
        // загрузка данных
        if(db.checkForInfo()){
            //если информация в бд есть, то ничего не делаем
        }else {
            //загружаем информацию с сервера
            updateInfo();
        }
    }


    /***
     * Загрузка фотографий преподавателей
     * @param lecturers объект "преподаватель" с id и photoUrl
     */
    public void loadImg(final ArrayList<Lecturer> lecturers) {
        for (int i = 0; i < lecturers.size(); i++) {
            //тоже самое, что i в цикле, но final, так как список тоже final
            final int finalI = i;
            //прописываем ссылку на изображене в инете

            ImageLoader.getInstance().loadImage(lecturers.get(i).getmPhoto(), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                    String name = "lecturer_" + String.valueOf(lecturers.get(finalI).getmId()) + ".png";
                    //финальный файл (путь расположения , File.separator -> разделитель для деррикторий, название, под которым сохраняем файл )
                    //используем передаваемый, а не из Vars потому, что в передаваемом есть имя файла в идиректории, в а Vars нет(только путь к папке)
                    File mediaFile = new File(Vars.getImageFileDir() + File.separator + name);

                    try {
                        FileOutputStream fos = new FileOutputStream(mediaFile);
                        loadedImage.compress(Bitmap.CompressFormat.PNG, 90, fos);
                        fos.close();
                        Log.d(myLog, "файл скачан = " + mediaFile.getAbsolutePath());

                    } catch (FileNotFoundException e) {
                        Toast.makeText(Vars.getContext(), R.string.loading_error, Toast.LENGTH_SHORT).show();
                        Log.d(myLog, "File not found: " + e.getMessage());
                    } catch (IOException e) {
                        Log.d(myLog, "Error accessing file: " + e.getMessage());
                    }
                }
            });
        }
    }


    /***
     * проверка наличия интернет соединения
     * @param context
     * @return true - есть соединение, false - нет соединения
     */
    public boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }


    /***
     * загрузка данных с сервера
     * @return покачто всегда возвращает true
     */
    public boolean updateInfo() {

    if(checkInternetConnection(Vars.getContext())) {
        //загрузка общей информации
        Thread commonInfo = new Thread(null, loadInfoWithoutLogin,
                "Background");
        commonInfo.start();

        //подгрузка личной информации
        if (LocalSettingsFile.isUserInfo()) { //если пользователь заполнил регистрационную форму
            // проверка на наличие указанного студента в базе сервера и сохранение его id
            Thread userIdInfo = new Thread(null, checkUser,
                    "Background");
            userIdInfo.start();

//НУЖНО СДЕЛАТЬ МЕХАНИЗМ, КОТОРЫЙ ПОСЛЕ ЗАВЕРШЕНИЯ ПРОВЕРКИ СТУДЕНТА БУДЕТ ПУСКАТЬ ДАЛЬШЕ, А ТО ПРОХОДИТ ДАЛЬШЕ НЕ ЗАКОНЧИВ ПРЕДЫДУЩЕЕ И ОШИБКА
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {

                    if(LocalSettingsFile.isSuchStudent()){ //если пользователь был найден и идентифицирован
                        Thread userInfo = new Thread(null, loadPersonalInfo,
                                "Background");
                        userInfo.start();
                    }else {
                        Toast.makeText(Vars.getContext(),R.string.incorrect_user_data,Toast.LENGTH_LONG).show();
                    }
                }
            }, 3000);
        } else {
            Toast.makeText(Vars.getContext(),R.string.complete_registration,Toast.LENGTH_LONG).show();
        }
    }else {
        Toast.makeText(Vars.getContext(),R.string.no_internet_connection_info,Toast.LENGTH_LONG).show();
    }
        return true;
    }


    /***
     * Проверка на наличие пользоватлея в базе сервера и сохранение его id и id его группы
     */
    private Runnable checkUser = new Runnable() {
        public void run() {
            String loadLink = "http://onpu-iks.pe.hu/php/api/checkForStudent.php?name="+ LocalSettingsFile.getUserName() +"&surname="+
                    LocalSettingsFile.getUserSurname() + "&group=" + LocalSettingsFile.getUserGroup();
            try {
                int studentId;
                int groupId;
                DefaultHttpClient httpClient = new DefaultHttpClient();
                ResponseHandler responseHandler = new BasicResponseHandler();
                HttpGet http = new HttpGet(loadLink);
                //получаем ответ от сервера
                String response = (httpClient.execute(http, responseHandler)).toString();

                //проверка на наличие необходимых данные в ответе сервера
                if (!new JSONObject(response).get("studentId").equals(null)) {
                    studentId = new JSONObject(response).getJSONArray("studentId").getJSONObject(0).getInt("Student_id");
                }else {
                    studentId = -1;
                }

                //проверка на наличие необходимых данные в ответе сервера
                if (!new JSONObject(response).get("groupId").equals(null)) {
                    groupId = new JSONObject(response).getJSONArray("groupId").getJSONObject(0).getInt("Group_id");
                }else {
                    groupId = -1;
                }
                LocalSettingsFile.setUserInfo(studentId,groupId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    /***
     * Тело потока для загрузки данный без данных регистрации
     */
    private Runnable loadInfoWithoutLogin = new Runnable() {
        public void run() {
            String loadLink = "http://onpu-iks.pe.hu/php/api/getDataWithoutLogin.php";

            try {
                DatabaseHandler db = new DatabaseHandler();
                JSONArray array;
                JSONObject object;
                DefaultHttpClient httpClient = new DefaultHttpClient();
                ResponseHandler responseHandler = new BasicResponseHandler();
                HttpGet http = new HttpGet(loadLink);
                //получаем ответ от сервера
                String response = (httpClient.execute(http, responseHandler)).toString();

                //сохранение предметов кафедры ИС

                //проверка на наличие необходимых данные в ответе сервера
                if (!new JSONObject(response).get("ics_subjects").equals(null)) {
                    array = new JSONObject(response).getJSONArray("ics_subjects");
                    //удаление имеющихся данных в таблице
                    db.clearTable(db.TABLE_ICS_SUBJECTS);

                    for (int i = 0; i < array.length(); i++) {
                        object = array.getJSONObject(i);

                        ICSSubject icsSubject = new ICSSubject();
                        icsSubject.setMid(object.getInt("ICS_subject_id"));
                        icsSubject.setmTitle(object.getString("Title"));
                        //проверка на значение в поле, если оно null, то ставим 0 (так как это int, при null вылетает ошибка)
                        if (!object.get("Lecturer_ICS").equals(null)) {
                            icsSubject.setmLecturerId(object.getInt("Lecturer_ICS"));
                        } else {
                            icsSubject.setmLecturerId(0);
                        }
                        icsSubject.setmSemesters(object.getString("Semesters"));
                        icsSubject.setmKind(object.getString("Kind"));
                        icsSubject.setmExtraInfo(object.getString("Extra_info"));

                        db.addICSSubject(icsSubject);
                    }
                } else {}

                //сохранение преподавателей

                //проверка на наличие необходимых данные в ответе сервера
                if (!new JSONObject(response).get("lecturers").equals(null)) {
                    array = new JSONObject(response).getJSONArray("lecturers");

                    //удаление имеющихся данных в таблице
                    db.clearTable(db.TABLE_LECTURERS);

                    for (int i = 0; i < array.length(); i++) {
                        object = array.getJSONObject(i);

                        Lecturer lecturer = new Lecturer();
                        lecturer.setmId(object.getInt("Lecturer_id"));
                        lecturer.setmPhoto(object.getString("Photo"));
                        lecturer.setmSurname(object.getString("Surname"));
                        lecturer.setmName(object.getString("Name"));
                        lecturer.setmPatronymic(object.getString("Patronymic"));
                        lecturer.setmContacts(object.getString("Contacts"));

                        //проверка на значение в поле, если оно null, то ставим 0 (так как это int, при null вылетает ошибка)
                        if (object.get("ICS").equals(null)) {
                            lecturer.setmIsICS(0);
                        } else {
                            lecturer.setmIsICS(1);
                        }
                        db.addLecturer(lecturer);
                    }
                } else {}

                //сохранение времени занятий

                //проверка на наличие необходимых данные в ответе сервера
                if (!new JSONObject(response).get("time").equals(null)) {
                    array = new JSONObject(response).getJSONArray("time");

                    //удаление имеющихся данных в таблице
                    db.clearTable(db.TABLE_TIME);

                    for (int i = 0; i < array.length(); i++) {
                        object = array.getJSONObject(i);

                        Time time = new Time();
                        time.setmId(object.getInt("id"));
                        time.setmNumberOfSubject(object.getInt("Number_of_subject_time"));
                        time.setmTimeStart(object.getString("Time_start"));
                        time.setmTimeEnd(object.getString("Time_finish"));

                        db.addTime(time);
                    }
                } else {}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    /***
     * Тело потока для загрузки личных данный пользователя, таких как расписание и тд
     */
    private Runnable loadPersonalInfo = new Runnable() {
        public void run() {
            DatabaseHandler db = new DatabaseHandler();

            db.getCurrentWeek();

            String loadLink = "http://onpu-iks.pe.hu/php/api/getDataWithLogin.php?studentId="+LocalSettingsFile.getUserId()+
                    "&groupId="+LocalSettingsFile.getGroupId()+"&course="+LocalSettingsFile.getUserCourse();
            try {
                JSONArray array;
                JSONObject object;
                DefaultHttpClient httpClient = new DefaultHttpClient();
                ResponseHandler responseHandler = new BasicResponseHandler();
                HttpGet http = new HttpGet(loadLink);
                //получаем ответ от сервера
                String response = (httpClient.execute(http, responseHandler)).toString();

                //сохранение оценок студента

                //проверка на наличие необходимых данные в ответе сервера
                if (!new JSONObject(response).get("marks").equals(null)) {
                    array = new JSONObject(response).getJSONArray("marks");
                    //удаление имеющихся данных в таблице
                    db.clearTable(db.TABLE_MARKS);

                    for (int i = 0; i < array.length(); i++) {
                        object = array.getJSONObject(i);

                        Mark mark = new Mark();
                        mark.setmId(object.getInt("id"));
                          //проверка на значение в поле, если оно null, то ставим -1 (так как это int, при null вылетает ошибка)
                        if (!object.get("Subject").equals(null)) {
                            mark.setmSubjectId(object.getInt("Subject"));
                        } else {
                            mark.setmSubjectId(0);
                        }

                        if (!object.get("Chapter1").equals(null)) {
                            mark.setmChapter1(object.getInt("Chapter1"));
                        } else {
                            mark.setmChapter1(0);
                        }

                        if (!object.get("Chapter2").equals(null)) {
                            mark.setmChapter2(object.getInt("Chapter2"));
                        } else {
                            mark.setmChapter2(0);
                        }

                        mark.setmSubjectName(object.getString("Short_title"));

                        db.addMark(mark);
                    }
                } else {}


                //сохранение увеломлений пользователя

                //проверка на наличие необходимых данные в ответе сервера
                if (!new JSONObject(response).get("notification").equals(null)) {
                    array = new JSONObject(response).getJSONArray("notification");

                    //удаление имеющихся данных в таблице
                    db.clearTable(db.TABLE_NOTIFICATIONS);

                    for (int i = 0; i < array.length(); i++) {
                        object = array.getJSONObject(i);

                        Notification notification = new Notification();
                        notification.setmId(object.getInt("Notification_id"));
                        notification.setmSenderId(object.getInt("Sender"));
                        notification.setmContent(object.getString("Content"));
                        //проверка на значение в поле, если оно null, то ставим 0 (так как это int, при null вылетает ошибка)
                        if (object.get("Is_read").equals(null)) {
                            notification.setmISRead(0);
                        } else {
                            notification.setmISRead(1);
                        }
                        db.addNotification(notification);
                    }
                } else {}

                //сохранение предметов студента

                //проверка на наличие необходимых данные в ответе сервера
                if (!new JSONObject(response).get("subjects").equals(null)) {
                    array = new JSONObject(response).getJSONArray("subjects");

                    //удаление имеющихся данных в таблице
                    db.clearTable(db.TABLE_PERSONAL_SUBJECTS);

                    for (int i = 0; i < array.length(); i++) {
                        object = array.getJSONObject(i);

                        Subject subject = new Subject();
                        subject.setmId(object.getInt("Subject_id"));
                        subject.setmShortTitle(object.getString("Short_title"));
                        subject.setmFullTitle(object.getString("Full_title"));
                        subject.setmLecturerId(object.getInt("Lecturer_personal"));

                        db.addPersonalSubject(subject);
                    }
                } else {}

                //сохранение расписания студента

                //проверка на наличие необходимых данные в ответе сервера
                if (!new JSONObject(response).get("week").equals(null)) {
                    array = new JSONObject(response).getJSONArray("week");

                    //удаление имеющихся данных в таблице
                    db.clearTable(db.TABLE_WEEK);

                    for (int i = 0; i < array.length(); i++) {
                        object = array.getJSONObject(i);

                        Schedule schedule = new Schedule();
                        schedule.setmId(object.getInt("id"));
                        schedule.setmKindOfWeek(object.getInt("Kind_of_week"));
                        schedule.setmDayOfWeek(object.getInt("Day_of_week"));
                        schedule.setmNumberOfSubject(object.getInt("Number_of_subject"));
                        schedule.setmSubjectId(object.getInt("Subject"));
                        schedule.setmTypeOfSubject(object.getString("Type_of_subject"));
                        schedule.setmRoomNumber(object.getString("Room_number"));

                        db.addSchedule(schedule);
                    }
                } else {}

                //сохранение общей информации

                //проверка на наличие необходимых данные в ответе сервера
                if (!new JSONObject(response).get("global").equals(null)) {
                    object = new JSONObject(response).getJSONArray("global").getJSONObject(0);

                    //удаление имеющихся данных в таблице
                    db.clearTable(db.TABLE_GLOBAL);

                    db.addGlobalInfo(object.getInt("id"),object.getString("Start_date"),object.getInt("Number_of_weeks"));

                } else {}

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


}
