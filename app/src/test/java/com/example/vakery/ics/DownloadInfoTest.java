package com.example.vakery.ics;

import android.content.Context;

import com.example.vakery.ics.Application.Functional.DownloadInfo;

import org.junit.Test;
import org.mockito.Mockito;


import static org.mockito.Mockito.*;


public class DownloadInfoTest {

    DownloadInfo mockDownloadInfo  = mock(DownloadInfo.class);
    Context mockContext = mock(Context.class);

    @Test
    public void testMethodsCall(){
        mockDownloadInfo.updateInfo();
        mockDownloadInfo.checkForInformation();
        when(mockDownloadInfo.checkInternetConnection(mockContext)).thenReturn(true);

        verify(mockDownloadInfo).updateInfo();
        verify(mockDownloadInfo).checkForInformation();
        Mockito.doReturn(true).when(mockDownloadInfo).checkInternetConnection(mockContext);
    }

}
