package com.example.vakery.ics;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vakery.ics.DB.DatabaseHandler;
import com.example.vakery.ics.Functional.MyToolbar;
import com.example.vakery.ics.PagerAdapters.LecturersPagerAdapter;


public class LecturersActivity extends AppCompatActivity {
    final String myLog = "myLog";
    DatabaseHandler db;
    MyToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturers);

        //создание Toolbar
        toolbar = new MyToolbar(this);

        db = new DatabaseHandler(this);

        // определяем фрагмент для пейджера
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        //ставим адаптер на пейджер
        viewPager.setAdapter(new LecturersPagerAdapter(getSupportFragmentManager()));
        //определяем фрагмент "вкладок"
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        //устанавливаем привязку к ранее объявленому пейджеру, чтоб они были связаны
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onBackPressed() {
        // сообщаем в MyToolbar что нажали НАЗАД, если меню открыто, то оно свернется и вернет false, если меню не открыто, то вернется true
        if(toolbar.onBackPressed()){
            //если менб небыло открыто(выполняем действие НАЗАД)
            super.onBackPressed();
        }
    }


}
