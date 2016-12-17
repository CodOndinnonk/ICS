package com.example.vakery.ics.PagerAdapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.vakery.ics.ScheduleListFragment;

import java.util.ArrayList;

public class SchedulePagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> mFragments;//лист с страницами пейджера (днями недели), используется для обновления необходимых страниц


    public SchedulePagerAdapter(FragmentManager mgr) {
        super(mgr);
        mFragments = new ArrayList<>(7);//7 так как 7 дней недели
        for (int i = 0; i < 7; i++) {
            //создаем дни недели со всем необходимым контентом
            mFragments.add(ScheduleListFragment.newInstance(i));
        }
    }


    @Override
    public int getCount() {
        return(7);
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }


    //переопределенный метод, изменяющий элементы  стоящие радом с текущим(слево и справо), передается позиция элемента, который на экране
    public void notifyDataSetChanged(int position) {
       //если есть элемент слева (текущий день не понедельник)
        if (position - 1 >= 0) {
            //вызываем обновление фрагмента
            ((ScheduleListFragment) mFragments.get(position - 1)).fillList();
        }
        //если есть элемент справа (текущий день не воскресенье)
        if (position + 1 < 7) {
            //вызываем обновление фрагмента
            ((ScheduleListFragment) mFragments.get(position + 1)).fillList();
        }
        //вызов встроенного метода, сообщающего об изменении данных (обновляет пейджер)
        notifyDataSetChanged();
    }
}