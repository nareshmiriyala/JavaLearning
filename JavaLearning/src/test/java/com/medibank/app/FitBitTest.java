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

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class FitBitTest {

    private FitBit spyFitBit;

    @Mock
    BufferedReader mockBufferedReader;

    @Mock
    private System mockSystem;

    @Before
    public void setUp() throws Exception {

        spyFitBit=PowerMockito.spy(new FitBit());
    }
    @Test
    public void testLogic()throws Exception{
        when(mockBufferedReader.readLine()).thenReturn("5 5").thenReturn("1 2 N").thenReturn("LMLMLMLMM");
        MemberMatcher.field(FitBit.class, "bufferedReader").set(spyFitBit, mockBufferedReader);
        spyFitBit.startSession();
    }
}