package com.example.vakery.ics;


import com.example.vakery.ics.Application.ListAdapters.LecturersListAdapter;
import com.example.vakery.ics.Application.ListAdapters.SubjectsListAdapter;

import org.junit.Test;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SubjectsListAdapterTest {

    SubjectsListAdapter mock  = mock(SubjectsListAdapter.class);

    @Test
    public void testMethodsCall(){
        mock.getCount();

        verify(mock,atLeastOnce()).getCount();
    }
}
