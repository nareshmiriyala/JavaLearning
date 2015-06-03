package com.medibank.app;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class FitBitTest {

    private FitBit fitBit;

    @Before
    public void setUp() throws Exception {
        fitBit=new FitBit();

    }
    @Test
    public void testLogic()throws Exception{
        fitBit.startSession();
    }
}