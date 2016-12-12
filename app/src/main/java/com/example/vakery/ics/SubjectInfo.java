package com.example.vakery.ics;

        import android.annotation.TargetApi;
        import android.app.Activity;
        import android.content.Intent;
        import android.database.Cursor;
        import android.os.Build;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.transition.Explode;
        import android.transition.Fade;
        import android.transition.Slide;
        import android.transition.TransitionInflater;
        import android.util.Log;
        import android.view.View;
        import android.view.inputmethod.InputMethodManager;
        import android.widget.AdapterView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.mikepenz.iconics.typeface.FontAwesome;
        import com.mikepenz.materialdrawer.Drawer;
        import com.mikepenz.materialdrawer.model.DividerDrawerItem;
        import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
        import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
        import com.mikepenz.materialdrawer.model.interfaces.Badgeable;
        import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
        import com.mikepenz.materialdrawer.model.interfaces.Nameable;

        import Entities.SubjectForList;

public class SubjectInfo extends AppCompatActivity {
    final String myLog = "myLog";
    DatabaseHandler db;
    int subjectId;
    int lecturerId;
    TextView subjectName, type, room, lecturerInfo;
    private Drawer.Result drawerResult = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_info);

        // Инициализируем Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Инициализируем Navigation Drawer
        drawerResult = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_notifications).withIcon(FontAwesome.Icon.faw_bell).withBadge("1").withIdentifier(1),
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
                        InputMethodManager inputMethodManager = (InputMethodManager) SubjectInfo.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(SubjectInfo.this.getCurrentFocus().getWindowToken(), 0);
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
                           Toast.makeText(SubjectInfo.this, SubjectInfo.this.getString(((Nameable) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(SubjectInfo.this, SubjectInfo.this.getString(((SecondaryDrawerItem) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                })
                .build();

        //заполнение полей
        subjectName = (TextView)findViewById(R.id.subjectInfoSubjectName);
        type = (TextView)findViewById(R.id.subjectInfoType);
        room = (TextView)findViewById(R.id.subjectInfoRoom);
        lecturerInfo = (TextView)findViewById(R.id.subjectInfoLecturer);

        lecturerInfo.setOnClickListener(onLecturerClickListener);

        db = new DatabaseHandler(this);
        Intent intent = getIntent();
        //извлекаем переданное id для получения данных
        subjectId = intent.getIntExtra("id",999);

        setInfo();
    }





    public void setInfo(){
        Cursor cursor = db.getSubject(subjectId);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    lecturerId = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_LECTURER_ID));

                    subjectName.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_FULL_TITLE)));
                    type.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_TYPE_OF_SUBJECT)));
                    room.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_ROOM_NUMBER)));
                    String lecturer = cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SURNAME)) + " " +
                            cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_NAME)) + " " +
                            cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PATRONYMIC));
                    lecturerInfo.setText(lecturer);

                } while (cursor.moveToNext());
            }
        } else {
            Log.d(myLog, "Cursor is null");
        }
    }

    View.OnClickListener onLecturerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent go = new Intent(getApplicationContext(), LecturerInfo.class);
            //передаем с интентом id выбранного преподавателя
            go.putExtra("id", lecturerId);
            startActivity(go);
        }
    };


}
