package com.example.vakery.ics;


import android.database.sqlite.SQLiteDatabase;

import com.example.vakery.ics.Domain.DB.DatabaseHandler;

import org.junit.Test;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DatabaseHandlerTest {

    DatabaseHandler mockDatabaseHandler = mock(DatabaseHandler.class);
    SQLiteDatabase mockSqLiteDatabase = mock(SQLiteDatabase.class);

    @Test
    public void testMethodsCall(){
        mockDatabaseHandler.onCreate(mockSqLiteDatabase);

        verify(mockDatabaseHandler,atLeastOnce()).onCreate(mockSqLiteDatabase);
    }
}
