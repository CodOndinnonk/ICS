package com.example.vakery.ics;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Badgeable;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;

import Entities.Lecturer;
import Entities.SubjectForList;

public class LecturersActivity extends AppCompatActivity {
    private Drawer.Result drawerResult = null;
    final String myLog = "myLog";
    ListView lvLecturers;
    DatabaseHandler db;
    ArrayList<Lecturer> listOfLecturers = new ArrayList<Lecturer>();
    LecturersListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturers);

        // Инициализируем Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHandler(this);

        // Инициализируем Navigation Drawer
        drawerResult = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_notifications).withIcon(FontAwesome.Icon.faw_bell).withBadge("1").withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_schedule).withIcon(FontAwesome.Icon.faw_tasks),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_marks).withIcon(FontAwesome.Icon.faw_file),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_visiting).withIcon(FontAwesome.Icon.faw_eye),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_lectors).withIcon(FontAwesome.Icon.faw_male),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_subjects).withIcon(FontAwesome.Icon.faw_university),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_programs).withIcon(FontAwesome.Icon.faw_keyboard_o),
                        new PrimaryDrawerItem(),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_exit).withIcon(FontAwesome.Icon.faw_sign_out),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_info).withIcon(FontAwesome.Icon.faw_question))
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Скрываем клавиатуру при открытии Navigation Drawer
                        InputMethodManager inputMethodManager = (InputMethodManager) LecturersActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(LecturersActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }
                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    // Обработка клика
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            if(getApplicationContext().getString(((Nameable) drawerItem).getNameRes()).equals(getString(R.string.drawer_item_lectors))){
                                Intent intent = new Intent(getApplicationContext(), LecturersActivity.class);
                                startActivity(intent);
                            }
                            if(getApplicationContext().getString(((Nameable) drawerItem).getNameRes()).equals(getString(R.string.drawer_item_schedule))){
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }

    Toast.makeText(getApplicationContext(), getApplicationContext().getString(((Nameable) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();
                        }
                        if (drawerItem instanceof Badgeable) {
                            Badgeable badgeable = (Badgeable) drawerItem;
                            if (badgeable.getBadge() != null) {
                                // учтите, не делайте так, если ваш бейдж содержит символ "+"
                                try {
                                    int badge = Integer.valueOf(badgeable.getBadge());
                                    if (badge > 0) {
                                        drawerResult.updateBadge(String.valueOf(badge - 1), position);
                                    }
                                } catch (Exception e) {
                                    Log.d(myLog, "Не нажимайте на бейдж, содержащий плюс! :)");
                                }
                            }
                        }
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    // Обработка длинного клика, например, только для SecondaryDrawerItem
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof SecondaryDrawerItem) {
                            Toast.makeText(LecturersActivity.this, LecturersActivity.this.getString(((SecondaryDrawerItem) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                })
                .build();


        lvLecturers = (ListView)findViewById(R.id.lecturersList);

        //слушатель нажатия на пункт списка преподавателей
        lvLecturers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent go = new Intent(getApplicationContext(), LecturerInfo.class);
                //передаем с интентом id выбранного преподавателя
                go.putExtra("id", listOfLecturers.get(position).getmId());
                startActivity(go);
            }
        });

        fillList();

        //назначаем адаптер для списка с расписанием
        listAdapter = new LecturersListAdapter(this, listOfLecturers);
        lvLecturers.setAdapter(listAdapter);


    }


    //заполнение списка предметов
    public void fillList(){
        //очищаем от предыдущих данных, на случай обновления
        listOfLecturers.clear();

        Cursor cursor = db.getAllLecturers();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Lecturer lecturer = new Lecturer();

                    lecturer.setmId((cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_LECTURER_ID))));
                    lecturer.setmPhoto((cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PHOTO_URL))));
                    lecturer.setmSurname((cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SURNAME))));
                    lecturer.setmName((cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_NAME))));
                    lecturer.setmPatronymic((cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PATRONYMIC))));
                    lecturer.setmContacts((cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_CONTACTS))));

                    listOfLecturers.add(lecturer);
                } while (cursor.moveToNext());
            }
        } else {
            Log.d(myLog, "Cursor is null");
        }


    }

}
