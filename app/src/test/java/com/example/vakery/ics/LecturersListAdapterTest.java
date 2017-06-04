package com.example.vakery.ics;


import android.content.Context;

import com.example.vakery.ics.Application.Functional.MyToolbar;
import com.example.vakery.ics.Application.ListAdapters.LecturersListAdapter;
import com.example.vakery.ics.Domain.Entities.Lecturer;

import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LecturersListAdapterTest {

    LecturersListAdapter mock  = mock(LecturersListAdapter.class);

    @Test
    public void testMethodsCall(){
        mock.getCount();

        verify(mock,atLeastOnce()).getCount();
    }
}
