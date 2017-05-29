package com.example.vakery.ics.Domain.Repositories;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.vakery.ics.Domain.DB.DatabaseHandler;
import com.example.vakery.ics.Domain.Entities.Notification;



public class NotificationFactory {

    /***
     * Добавление уведомления в локальную бд
     * @param notification
     */
    public static void addNotification(Notification notification){//дописать все поля
        SQLiteDatabase db = new DatabaseHandler().getWritableDatabase();//формат работы с БД
        ContentValues values = new ContentValues();//создание переменной, позволяющей создать шаблот "записи" и заполниять его для добавления в БД
        values.put(DatabaseHandler.KEY_NOTIFICATION_ID, notification.getmId());
        values.put(DatabaseHandler.KEY_SENDER, notification.getmSenderId());
        values.put(DatabaseHandler.KEY_CONTENT, notification.getmContent());
        values.put(DatabaseHandler.KEY_IS_READ, notification.getmISRead());

        db.insert(DatabaseHandler.TABLE_NOTIFICATIONS, null, values);//добавление в таблицу шаблона, заполненного ранее
        db.close();
    }
}
