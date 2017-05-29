package com.example.vakery.ics.Application.Functional;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class LocalSettingsFile {
    public  static final String APP_PREFERENCES = "icsSettings";// это будет именем файла настроек
    public static final String APP_PREFERENCES_NAME = "Name";
    public static final String APP_PREFERENCES_SURNAME = "Surname";
    public static final String APP_PREFERENCES_GROUP = "Group";
    public static final String APP_PREFERENCES_STUDENT_ID = "StudentId";
    public static final String APP_PREFERENCES_GROUP_ID = "GroupId";
    private final static SharedPreferences mSettings;//создание переменной, необходимой для работы с файлом настройки


    static {
        mSettings = Vars.getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);// инициализация переменной
    }


    /***
     * Запись информации о пользователе в файл
     * @param name имя
     * @param surname фамилия
     * @param group учебная группа студента
     */
    public static void setUserInfo(String name, String surname, String group){
        SharedPreferences.Editor editor = mSettings.edit();// вызов обьекта editor для измненения параметров настроек
        editor.putString(APP_PREFERENCES_NAME, name);
        editor.putString(APP_PREFERENCES_SURNAME, surname);
        editor.putString(APP_PREFERENCES_GROUP, group);
        editor.apply();// метод, сохраняющий добавленные данные
    }


    /***
     * Запись детальной информации о польователе в файл
     * @param studentId уникальный идентификатор студетна в бд сервера
     * @param groupId уникальный идентификатор группы студетна в бд сервера
     */
    public static void setUserInfo(int studentId, int groupId){
        SharedPreferences.Editor editor = mSettings.edit();// вызов обьекта editor для измненения параметров настроек
        editor.putInt(APP_PREFERENCES_STUDENT_ID, studentId);
        editor.putInt(APP_PREFERENCES_GROUP_ID, groupId);
        editor.apply();// метод, сохраняющий добавленные данные
    }


    /***
     * удаление данных о пользователе
     */
    public static void clearUserInfo(){
        SharedPreferences.Editor editor = mSettings.edit();// вызов обьекта editor для измненения параметров настроек
        editor.remove(APP_PREFERENCES_NAME);
        editor.remove(APP_PREFERENCES_SURNAME);
        editor.remove(APP_PREFERENCES_GROUP);
        editor.remove(APP_PREFERENCES_STUDENT_ID);
        editor.remove(APP_PREFERENCES_GROUP_ID);
        editor.apply();// метод, сохраняющий добавленные данные
    }


    public static String getUserName(){
        return mSettings.getString(APP_PREFERENCES_NAME,"");
    }


    public static String getUserSurname(){
        return mSettings.getString(APP_PREFERENCES_SURNAME,"");
    }


    public static String getUserGroup(){
        return mSettings.getString(APP_PREFERENCES_GROUP,"");
    }

    public static int getUserCourse(){
        int course = 0;
        try {
            course = GregorianCalendar.getInstance().get(Calendar.YEAR) - Integer.valueOf("20"+getUserGroup().substring(3,5));
        }catch (Exception e){
            e.printStackTrace();
        }
        return course;
    }


    public static int getUserId(){
        return mSettings.getInt(APP_PREFERENCES_STUDENT_ID, -1);
    }


    public static int getGroupId(){
        return mSettings.getInt(APP_PREFERENCES_GROUP_ID, -1);
    }


    /***
     * Проверка на наличие уже зарегастрированного пользователя
     * @return true - пользователь есть, false - нет
     */
    public static boolean isUserInfo(){
        if (mSettings.contains(APP_PREFERENCES_NAME)){
            return true;
        }else {
            return false;
        }
    }


    /***
     * Проверка на корректность введенных данных и обнаружение пользователя на веб сервере
     * @return true - пользователь найден в базе сервера, false - нет
     */
    public static boolean isSuchStudent(){
        if (mSettings.getInt(APP_PREFERENCES_STUDENT_ID, -1) > 0){//если есть id студента (если студента нет в бд, то его id = -1)
            return true;
        }else {
            return false;
        }
    }


}
