package com.example.vakery.ics;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.vakery.ics.Functional.Vars;

public  class LocalSettingsFile {
    public  static final String APP_PREFERENCES = "icsSettings";// это будет именем файла настроек
    public static final String APP_PREFERENCES_NAME = "Name"; // логин
    public static final String APP_PREFERENCES_SURNAME = "Surname"; // логин
    public static final String APP_PREFERENCES_GROUP = "Group"; // логин
    private final static SharedPreferences mSettings;//создание переменной, необходимой для работы с файлом настройки


    static {
        mSettings = Vars.getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);// инициализация переменной
    }


    public static void setUserInfo(String name, String surname, String group){
        SharedPreferences.Editor editor = mSettings.edit();// вызов обьекта editor для измненения параметров настроек
        editor.putString(APP_PREFERENCES_NAME, name);
        editor.putString(APP_PREFERENCES_SURNAME, surname);
        editor.putString(APP_PREFERENCES_GROUP, group);
        editor.apply();// метод, сохраняющий добавленные данные
    }


    public static void clearUserInfo(){
        SharedPreferences.Editor editor = mSettings.edit();// вызов обьекта editor для измненения параметров настроек
        editor.remove(APP_PREFERENCES_NAME);
        editor.remove(APP_PREFERENCES_SURNAME);
        editor.remove(APP_PREFERENCES_GROUP);
        editor.apply();// метод, сохраняющий добавленные данные
    }


    /***
     * проверка на наличие уже зарегастрированного пользователя
     * @return true - пользователь есть, false - нет
     */
    public static boolean isUserInfo(){
        if (mSettings.contains(APP_PREFERENCES_NAME)){
            return true;
        }else {
            return false;
        }
    }


}
