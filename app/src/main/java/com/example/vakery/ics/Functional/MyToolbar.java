package com.example.vakery.ics.Functional;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;
import com.example.vakery.ics.LecturersActivity;
import com.example.vakery.ics.LocalSettingsFile;
import com.example.vakery.ics.MainActivity;
import com.example.vakery.ics.MarksActivity;
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

public class MyToolbar  {
    final String myLog = "myLog";
    private Drawer.Result drawerResult = null;
    Activity mActivity;


    //создание Toolbar(меню), используемого на всех активити
    public MyToolbar(final Activity activity){
        mActivity = activity;

        // Инициализируем Toolbar, mActivity.findViewById потому, то ищем в определенном активити(его передаем в конструктор)
        Toolbar toolbar = (Toolbar) mActivity.findViewById(R.id.toolbar);
        //((AppCompatActivity) mActivity), так как мы не в активити, и нужно получить методы активити, поэтому приводим переданную активность к AppCompatActivity
        ((AppCompatActivity) mActivity).setSupportActionBar(toolbar);
        ((AppCompatActivity) mActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Инициализируем Navigation Drawer
        drawerResult = new Drawer()
                .withActivity(activity)
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
                        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
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
                                Vars.getContext().startActivity(intent);
                            }
                            if(Vars.getContext().getString(((Nameable) drawerItem).getNameRes()).equals(Vars.getContext().getString(R.string.drawer_item_schedule))){
                                Intent intent = new Intent(Vars.getContext(), MainActivity.class);
                                Vars.getContext().startActivity(intent);
                            }
                            if(Vars.getContext().getString(((Nameable) drawerItem).getNameRes()).equals(Vars.getContext().getString(R.string.drawer_item_exit))){
                                prepareForExit();
                            }
                            if(Vars.getContext().getString(((Nameable) drawerItem).getNameRes()).equals(Vars.getContext().getString(R.string.drawer_item_subjects))){
                                Intent intent = new Intent(Vars.getContext(), SubjectsActivity.class);
                                Vars.getContext().startActivity(intent);
                            }
                            if(Vars.getContext().getString(((Nameable) drawerItem).getNameRes()).equals(Vars.getContext().getString(R.string.drawer_item_marks))){
                                Intent intent = new Intent(Vars.getContext(), MarksActivity.class);
                                Vars.getContext().startActivity(intent);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
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


    //метод, вызывается в активити для сворачивания меню по нажаию НАЗАД, если оно открыто
    public boolean onBackPressed(){
        // Закрываем Navigation Drawer по нажатию системной кнопки "Назад" если он открыт
        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
            //false - не надо делать откат назад, а закрываем меню
            return false;
        } else {
            //true - меню не открыто и выполняем откат назад
            return true;
        }
    }


}
