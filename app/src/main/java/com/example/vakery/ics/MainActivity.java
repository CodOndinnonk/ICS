package com.example.vakery.ics;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.eftimoff.viewpagertransformers.DepthPageTransformer;
import com.example.vakery.ics.DB.DatabaseHandler;
import com.example.vakery.ics.Functional.DownloadInfo;
import com.example.vakery.ics.Functional.Login;
import com.example.vakery.ics.Functional.MyToolbar;
import com.example.vakery.ics.Functional.Vars;
import com.example.vakery.ics.PagerAdapters.SchedulePagerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import com.example.vakery.ics.Entities.Lecturer;


public class MainActivity extends MyToolbar {//наследуемся от MyToolbar, он выполняет все от AppCompatActivity и имеет метод создания Toolbar(createToolbar(String title))
    final String myLog = "myLog";
    DatabaseHandler db;
    FragmentTransaction fragmentTransaction;
    ViewPager pager;
    private SchedulePagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //задаем контекст прилижения в переменную, чтоб можно было с ним работать из любого класса
        Vars.setContext(getApplicationContext());

        //проверка на наличие информации о пользователе, если ее нет, выводим диалоговое окно для ее добавления
        if(! LocalSettingsFile.isUserInfo()){
            Login myDialogFragment = new Login();
            FragmentManager manager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            myDialogFragment.show(transaction, "dialog");
        }else {}

        //название активити отображаемое в Toolbar
        String activityTitle = getString(R.string.drawer_item_schedule);

        //создание Toolbar
        this.createToolbar(activityTitle);

        db = new DatabaseHandler();

        //настройка пейджера
        fragmentTransaction = getFragmentManager().beginTransaction();
        pager=(ViewPager)findViewById(R.id.pager);

        adapter = new SchedulePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        //устанавливаем анимацию перелистывания пейджера(доп библиотека)
        pager.setPageTransformer(true,new DepthPageTransformer());
        // определяем текущий день недели, чтоб поставить нужную стр
        Calendar calendar = Calendar.getInstance();
        // берем текущий день -1, так как календарь считает с воскресенья, а нам надо с понедельника
        int currentDayForPage = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        //если наш день =0, то такого дна нет (вс = 1 - 1 = 0 ) то пишем что вс = 7 день
        if(currentDayForPage < 1){currentDayForPage = 7;}
        // задаем какую стр по порядку показывать. отнимаем -1 потому, что список дней начинается с 0
        pager.setCurrentItem(currentDayForPage - 1);

        //так как расписание пар по времени не меняется часто, то заполняем его только при включении приложения для экономии действий
        Vars.fillTimeList(this);

        //checkForInformation();
    }




    //переопреднеленный метод(стандартный не работает), который обновляет данные в страницах пейджера
    public void notifyAdapter(int position) {
        adapter.notifyDataSetChanged(position);
    }


}






