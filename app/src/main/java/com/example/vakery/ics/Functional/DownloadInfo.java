package com.example.vakery.ics.Functional;


import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.vakery.ics.R;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.example.vakery.ics.Entities.Lecturer;

public class DownloadInfo {
    Context mContext;
    final String myLog = "myLog";

///////////////////////////////////////////////////////////////////////////////////////////////////
   //некорректная работа
    public DownloadInfo(Context context){
        mContext = context;
        prepareForLoadingImg();
    }

    public void prepareForLoadingImg() {
        Executor downloadExecutor = Executors.newFixedThreadPool(5);
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        int memClass = am.getMemoryClass();
        final int memoryCacheSize = 1024 * 1024 * memClass / 16;
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(android.R.color.transparent)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .cacheInMemory(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext.getApplicationContext())
                .taskExecutor(downloadExecutor)
                .memoryCache(new UsingFreqLimitedMemoryCache(memoryCacheSize)) // 2 Mb
                .imageDownloader(new BaseImageDownloader(mContext, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)
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
                        Toast.makeText(mContext, R.string.loading_error, Toast.LENGTH_SHORT).show();
                        Log.d(myLog, "File not found: " + e.getMessage());
                    } catch (IOException e) {
                        Log.d(myLog, "Error accessing file: " + e.getMessage());
                    }
                }
            });
        }

    }


}
