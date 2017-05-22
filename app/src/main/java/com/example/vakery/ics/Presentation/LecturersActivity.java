package com.example.vakery.ics.Presentation;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.vakery.ics.Domain.DB.DatabaseHandler;
import com.example.vakery.ics.Application.Functional.MyToolbar;
import com.example.vakery.ics.Application.Functional.Vars;
import com.example.vakery.ics.Application.PagerAdapters.LecturersPagerAdapter;
import com.example.vakery.ics.R;


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

        db = new DatabaseHandler();

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
