package com.example.vakery.ics.Functional;


import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.vakery.ics.DB.DatabaseHandler;
import com.example.vakery.ics.R;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.example.vakery.ics.Entities.Lecturer;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class DownloadInfo  {
    final String myLog = "myLog";
    DatabaseHandler db = new DatabaseHandler();

///////////////////////////////////////////////////////////////////////////////////////////////////
   //некорректная работа
    public DownloadInfo(){
        //prepareForLoadingImg();
    }

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


    public void checkForInformation() {
        final ArrayList<Lecturer> listOfLecturers = new ArrayList<Lecturer>();
        ArrayList<Integer> lecturersId = db.getLecturersId();

        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(myLog, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }

        //проверяем существует ли папка для хранения фото, берем ссылку на папку (File) из Vars
        if (! Vars.getImageFileDir().exists()){
            Log.d(myLog, "директории нет, создаем ее");
            // создаем каталог
            Vars.getImageFileDir().mkdirs();
        }else {}

        for (int i = 0; i < lecturersId.size(); i++) {
            //формируем имя файла для проверки его наличия
            String name = "lecturer_" + lecturersId.get(i).toString() + ".png";
            try {
                //проверяем наличие фотографии в папке
                if (!new File(Vars.getImageFileDir() + File.separator + name).exists()) {
                    //создаем экземпляр преподавателя с id и Photo_url
                    listOfLecturers.add(new Lecturer(db.getLecturer(lecturersId.get(i)).getmId(),db.getLecturer(lecturersId.get(i)).getmPhoto()));
                } else {
                }
            }catch (Exception e) {
                Log.d(myLog, "Ошибка проверки наличия фото");
            }
        }
        //если лист с преподавателями, которых надо скачать не пустой
        if(listOfLecturers.size() > 0) {
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
    }


    public boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        return false;
    }


    public boolean loadInfoWithoutLogin(){

        Thread thread = new Thread(null, doBackgroundThreadProcessing,
                "Background");
        thread.start();

        return true;
    }

    private Runnable doBackgroundThreadProcessing = new Runnable() {
        public void run() {
            String loadLink = "http://onpu-iks.pe.hu/php/api/getDataWithoutLogin.php";
            try {
                DatabaseHandler db = new DatabaseHandler();
                JSONArray array;
                DefaultHttpClient httpClient = new DefaultHttpClient();
                ResponseHandler responseHandler = new BasicResponseHandler();
                HttpGet http = new HttpGet(loadLink);
                //получаем ответ от сервера
                String response = (httpClient.execute(http, responseHandler)).toString();

                if( new JSONObject(response).get("ics_subjects").equals(null)){
                    int test = 0;
                }else {
                    array = new JSONObject(response).getJSONArray("ics_subjects");
                    for (int i = 0; i < array.length(); i++) {
                        //сделать сохранение предмета
                        int test = 0;

                    }

                }


                array = new JSONObject(response).getJSONArray("lecturers");
                if (array == null) {
                } else {
                    for (int i = 0; i < array.length(); i++) {

                        //сделать метод очищающий таблицу

                        JSONObject object = array.getJSONObject(i);

                        Lecturer lecturer = new Lecturer();
                        lecturer.setmId(object.getInt("Lecturer_id"));
                        lecturer.setmPhoto(object.getString("Photo"));
                        lecturer.setmSurname(object.getString("Surname"));
                        lecturer.setmName(object.getString("Name"));
                        lecturer.setmPatronymic(object.getString("Patronymic"));
                        lecturer.setmContacts(object.getString("Contacts"));

                        //проверка на значение в поле, если оно null, то ставим 0 (так как это int, при null вылетает ошибка)
                        if(object.get("ICS").equals(null)){
                            lecturer.setmIsICS(0);
                        }else {
                            lecturer.setmIsICS(1);
                        }

                        db.addLecturer(lecturer);

                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };




}
