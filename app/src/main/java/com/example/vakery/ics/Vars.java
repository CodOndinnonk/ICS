package com.example.vakery.ics;


import android.util.Log;

public class Vars {

    static final String myLog = "myLog";

    public static final int WEEK_ODD = 1;
    public static final int WEEK_EVEN = 2;

    public static int currentKindOfWeek = 0;//текущий тип недели, отображаемый в расписании

    public static void setCurrentKindOfWeek(int setCurrentKindOfWeek) {
        Vars.currentKindOfWeek = setCurrentKindOfWeek;
        Log.d(myLog, "Vars.currentKindOfWeek = " + currentKindOfWeek);
    }

    public static int getCurrentKindOfWeek() {
        return currentKindOfWeek;
    }
}
