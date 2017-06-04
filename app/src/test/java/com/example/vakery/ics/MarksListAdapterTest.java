package com.example.vakery.ics;


import com.example.vakery.ics.Application.ListAdapters.LecturersListAdapter;
import com.example.vakery.ics.Application.ListAdapters.MarksListAdapter;

import org.junit.Test;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MarksListAdapterTest {

    MarksListAdapter mock  = mock(MarksListAdapter.class);

    @Test
    public void testMethodsCall(){
        mock.getCount();

        verify(mock,atLeastOnce()).getCount();
    }
}
