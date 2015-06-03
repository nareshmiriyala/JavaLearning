package com.medibank.app;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedReader;
import java.io.InputStream;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class FitBitTest {

    private FitBit fitBit;

    @Mock
    BufferedReader mockInputStream;

    @Before
    public void setUp() throws Exception {
       fitBit=new FitBit(mockInputStream);
    }
    @Test
    public void testLogic()throws Exception{
        when(mockInputStream.readLine()).thenReturn("5 5").thenReturn("1 2 N").thenReturn("LMLMLMLMM");
        fitBit.startSession();
    }
}