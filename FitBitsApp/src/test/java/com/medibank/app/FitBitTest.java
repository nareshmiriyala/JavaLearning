package com.medibank.app;

import com.medibank.entities.Position;
import com.medibank.entities.Trainee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class FitBitTest {

    private FitBit fitBit;

    @Mock
    BufferedReader mockInputStream;

    @Before
    public void setUp() throws Exception {
        fitBit = new FitBit(mockInputStream);
    }

    @Test
    public void testLogicScenario1() throws Exception {
        when(mockInputStream.readLine()).thenReturn("5 5").thenReturn("1 2 N").thenReturn("LMLMLMLMM");
        Trainee trainee = fitBit.startSession();
        assertNotNull(trainee);
        Position expectedPosition = new Position(1, 3, FitBit.Directions.N);
        assertEquals("Final Coordinates should be equal", expectedPosition, trainee.getCurrentPosition());
    }

    @Test
    public void testLogicScenario2() throws Exception {
        when(mockInputStream.readLine()).thenReturn("5 5").thenReturn("3 3 E").thenReturn("MMRMMRMRRM");
        Trainee trainee = fitBit.startSession();
        Position expectedPosition = new Position(5, 1, FitBit.Directions.E);
        assertEquals("Final Coordinates should be equal", expectedPosition, trainee.getCurrentPosition());
    }
}