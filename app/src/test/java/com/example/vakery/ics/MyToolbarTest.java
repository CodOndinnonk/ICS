package com.example.vakery.ics;


import android.content.Context;

import com.example.vakery.ics.Application.Functional.DownloadInfo;
import com.example.vakery.ics.Application.Functional.MyToolbar;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MyToolbarTest {

    MyToolbar mock  = mock(MyToolbar.class);

    @Test
    public void testMethodsCall(){
        mock.prepareForExit();
        mock.createToolbar("123");

        verify(mock,atLeastOnce()).prepareForExit();
        verify(mock).createToolbar("123");
    }
}
