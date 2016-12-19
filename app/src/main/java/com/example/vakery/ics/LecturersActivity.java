package com.example.vakery.ics;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.vakery.ics.DB.DatabaseHandler;
import com.example.vakery.ics.Functional.MyToolbar;
import com.example.vakery.ics.Functional.Vars;
import com.example.vakery.ics.PagerAdapters.LecturersPagerAdapter;


public class LecturersActivity extends MyToolbar {
    final String myLog = "myLog";
    DatabaseHandler db;
    //название активити отображаемое в Toolbar
    String activityTitle = Vars.getContext().getString(R.string.drawer_item_lectors);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturers);

        //создание Toolbar
        this.createToolbar(activityTitle);

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


}
