package com.example.vakery.ics.Functional;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;
import com.example.vakery.ics.LecturersActivity;
import com.example.vakery.ics.LocalSettingsFile;
import com.example.vakery.ics.MainActivity;
import com.example.vakery.ics.MarksActivity;
import com.example.vakery.ics.ProgramsActivity;
import com.example.vakery.ics.R;
import com.example.vakery.ics.SubjectsActivity;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Badgeable;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

public class MyToolbar extends AppCompatActivity {//наследуемся от AppCompatActivity для того, чтоб можно было определить все методы активити(создание меню,"назад"...)
    final String myLog = "myLog";
    private Drawer.Result drawerResult = null;


    //создание Toolbar(меню), используемого на всех активити
    public void createToolbar(String title)  {

        // Инициализируем Toolbar, mActivity.findViewById потому, то ищем в определенном активити(его передаем в конструктор)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //устанавливаем название активити(передается в конструктор)
        toolbar.setTitle(title);
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
                        new PrimaryDrawerItem().withName(R.string.drawer_item_schedule).withIcon(FontAwesome.Icon.faw_tasks),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_marks).withIcon(FontAwesome.Icon.faw_file),
                        // new PrimaryDrawerItem().withName(R.string.drawer_item_visiting).withIcon(FontAwesome.Icon.faw_eye),
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
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
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
                            //реализация переходов по нажатию
                            if(Vars.getContext().getString(((Nameable) drawerItem).getNameRes()).equals(Vars.getContext().getString(R.string.drawer_item_lectors))){
                                Intent intent = new Intent(Vars.getContext(), LecturersActivity.class);
                                startActivity(intent);
                            }
                            if(Vars.getContext().getString(((Nameable) drawerItem).getNameRes()).equals(Vars.getContext().getString(R.string.drawer_item_schedule))){
                                Intent intent = new Intent(Vars.getContext(), MainActivity.class);
                                startActivity(intent);
                            }
                            if(Vars.getContext().getString(((Nameable) drawerItem).getNameRes()).equals(Vars.getContext().getString(R.string.drawer_item_exit))){
                                prepareForExit();
                            }
                            if(Vars.getContext().getString(((Nameable) drawerItem).getNameRes()).equals(Vars.getContext().getString(R.string.drawer_item_subjects))){
                                Intent intent = new Intent(Vars.getContext(), SubjectsActivity.class);
                                startActivity(intent);
                            }
                            if(Vars.getContext().getString(((Nameable) drawerItem).getNameRes()).equals(Vars.getContext().getString(R.string.drawer_item_marks))){
                                Intent intent = new Intent(Vars.getContext(), MarksActivity.class);
                                startActivity(intent);
                            }
                            if(Vars.getContext().getString(((Nameable) drawerItem).getNameRes()).equals(Vars.getContext().getString(R.string.drawer_item_programs))){
                                Intent intent = new Intent(Vars.getContext(), ProgramsActivity.class);
                                startActivity(intent);
                            }
                            Toast.makeText(Vars.getContext(), Vars.getContext().getString(((Nameable) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(Vars.getContext(), Vars.getContext().getString(((SecondaryDrawerItem) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                })
                .build();
    }


    //срабатывает принажатии "ВЫХОД", выводится диалог с подтверждением удаления данных
    public void prepareForExit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.drawer_item_exit)
                .setMessage(R.string.asking_info_for_exit)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LocalSettingsFile.clearUserInfo();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }


    @Override
    public void onBackPressed() {
        // Закрываем Navigation Drawer по нажатию системной кнопки "Назад" если он открыт
        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
                //если меню небыло открыто(выполняем действие НАЗАД)
                super.onBackPressed();
            }
    }


    // создание меню для Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_manu, menu);
        return true;
    }


    // обработка выбора пункта меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.refreshInfo) {
            Log.d(myLog,"Обновление данных");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
