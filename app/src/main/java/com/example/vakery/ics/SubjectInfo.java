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

public class SubjectInfo extends AppCompatActivity {
    final String myLog = "myLog";
    DatabaseHandler db;
    int subjectId;
    int kindOfSubject;//0-персональный предмет(из расписания), 1-предмет кафедры, взятый из списка всех предметов
    int lecturerId;
    TextView subjectName, lecturerInfo, type, roomOrSemesterText,roomOrSemester , extraInfoText, extraInfo ;
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
        lecturerInfo = (TextView)findViewById(R.id.subjectInfoLecturer);
        type = (TextView)findViewById(R.id.subjectInfoType);
        roomOrSemesterText = (TextView)findViewById(R.id.subjectInfoRoomOrSemesterInfoText);
        roomOrSemester = (TextView)findViewById(R.id.subjectInfoRoomOrSemester);
        extraInfoText = (TextView)findViewById(R.id.subjectInfoExtraInfoText);
        extraInfo = (TextView)findViewById(R.id.subjectInfoExtraInfo);

        lecturerInfo.setOnClickListener(onLecturerClickListener);

        db = new DatabaseHandler(this);
        Intent intent = getIntent();
        //извлекаем переданное id для получения данных
        Cursor cursor = null;
        if(intent.getStringExtra("subjectKind").equals("personal")) {
            kindOfSubject = 0;//0-персональный предмет(из расписания)
            roomOrSemesterText.setText(R.string.room_of_subject);
            extraInfoText.setEnabled(false);
            extraInfoText.setVisibility(View.INVISIBLE);
            extraInfo.setEnabled(false);
            extraInfo.setVisibility(View.INVISIBLE);
            subjectId = intent.getIntExtra("id", 999);
            Log.d(myLog,"SubjectInfo предмет = personal  id =" + subjectId);
            cursor = db.getPersonalSubject(subjectId);
        }else if(intent.getStringExtra("subjectKind").equals("ICS")){
            kindOfSubject = 1;//1-предмет кафедры, взятый из списка всех предметов
            roomOrSemesterText.setText(R.string.semesters);
            extraInfoText.setEnabled(true);
            extraInfoText.setVisibility(View.VISIBLE);
            extraInfoText.setText(R.string.extra_info);
            extraInfo.setEnabled(true);
            extraInfo.setVisibility(View.VISIBLE);
            subjectId = intent.getIntExtra("id", 999);
            Log.d(myLog,"SubjectInfo предмет = ICS  id =" + subjectId);
            cursor = db.getICSSubject(subjectId);
        }
        if(cursor != null)
        setInfo(cursor);
    }



    public void setInfo(Cursor cursor){
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    if(kindOfSubject == 0) {//из расписания
                        lecturerId = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_LECTURER_ID));
                        subjectName.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_FULL_TITLE)));
                        String lecturer = cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SURNAME)) + " " +
                                cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_NAME)) + " " +
                                cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PATRONYMIC));
                        lecturerInfo.setText(lecturer);
                        type.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_TYPE_OF_SUBJECT)));
                        roomOrSemester.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_ROOM_NUMBER)));
                    }else if (kindOfSubject == 1) {//из списка всех предметов кафедры
                        lecturerId = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_LECTURER_ID));
                        subjectName.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_TITLE)));
                        String lecturer = cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SURNAME)) + " " +
                                cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_NAME)) + " " +
                                cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PATRONYMIC));
                        lecturerInfo.setText(lecturer);
                        type.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_KIND)));
                        roomOrSemester.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SEMESTERS)));
                        extraInfo.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SUBJECT_INFO)));
                    }
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
