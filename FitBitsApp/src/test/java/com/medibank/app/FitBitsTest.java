package com.medibank.app;

import com.medibank.entities.Position;
import com.medibank.entities.SoccerPitch;
import com.medibank.entities.Trainee;
import com.medibank.exceptions.InvalidCommandException;
import com.medibank.exceptions.InvalidInputException;
import com.medibank.exceptions.InvalidPositionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.BufferedReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class FitBitsTest {

    private FitBits spyFitBits;

    @Mock
    private BufferedReader mockInputStream;

    private Position mockCurrentPosition;

    private Trainee mockTrainee;

    @Mock
    private SoccerPitch mockSoccerPitch;

    @Before
    public void setUp() throws Exception {
        spyFitBits = PowerMockito.spy(new FitBits(mockInputStream));
    }

    @Test
    public void testLogicScenario1() throws Exception {
        when(mockInputStream.readLine()).thenReturn("5 5").thenReturn("1 2 N").thenReturn("LMLMLMLMM");
        Trainee trainee = spyFitBits.startSession();
        assertNotNull(trainee);
        Position expectedPosition = new Position(1, 3, FitBits.Directions.N);
        assertEquals("Final Coordinates should be equal", expectedPosition, trainee.getCurrentPosition());
    }

    @Test
    public void testLogicScenario2() throws Exception {
        when(mockInputStream.readLine()).thenReturn("5 5").thenReturn("3 3 E").thenReturn("MMRMMRMRRM");
        Trainee trainee = spyFitBits.startSession();
        Position expectedPosition = new Position(5, 1, FitBits.Directions.E);
        assertEquals("Final Coordinates should be equal", expectedPosition, trainee.getCurrentPosition());
    }

    @Test(expected = InvalidPositionException.class)
    public void testInputStartingPositionNotInRange() throws Exception {
        when(mockInputStream.readLine()).thenReturn("5 5").thenReturn("6 3 E").thenReturn("MMRMMRMRRM");//here value 6 3 E is not in range
        Trainee trainee = spyFitBits.startSession();
    }

    @Test(expected = InvalidInputException.class)
    public void testInputStartingPositionInvalid() throws Exception {
        when(mockInputStream.readLine()).thenReturn("5 5").thenReturn("E E").thenReturn("MMRMMRMRRM");//here value 6 3 E is not in range
        Trainee trainee = spyFitBits.startSession();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInputCommandsInvalid() throws Exception {
        when(mockInputStream.readLine()).thenReturn("5 5").thenReturn("4 4 E").thenReturn("mfdfd");//here value 6 3 E is not in range
        Trainee trainee = spyFitBits.startSession();
    }
    @Test(expected = InvalidCommandException.class)
    public void testInputCommandsNotInRange() throws Exception {
        when(mockInputStream.readLine()).thenReturn("5 5").thenReturn("3 3 E").thenReturn("MMMMMMMMMMMMMMM");
        Trainee trainee = spyFitBits.startSession();
        Position expectedPosition = new Position(5, 1, FitBits.Directions.E);
        assertEquals("Final Coordinates should be equal", expectedPosition, trainee.getCurrentPosition());
    }

    @Test(expected = InvalidInputException.class)
    public void testInputSoccerPitchInvalid() throws Exception {
        when(mockInputStream.readLine()).thenReturn("533335").thenReturn("6 3 E").thenReturn("MMRMMRMRRM");//here value 6 3 E is not in range
        Trainee trainee = spyFitBits.startSession();
    }

    @Test(expected = InvalidInputException.class)
    public void testInputSoccerPitchInvalidData() throws Exception {
        when(mockInputStream.readLine()).thenReturn("53 Y3335").thenReturn("6 3 E").thenReturn("MMRMMRMRRM");//here value 6 3 E is not in range
        Trainee trainee = spyFitBits.startSession();
    }


    @Test
    public void testChangePosition() throws Exception {
        // mock private field/variable
        MemberModifier
                .field(FitBits.class, "soccerPitch").set(
                mockSoccerPitch, mockSoccerPitch);
        when(mockSoccerPitch.getY()).thenReturn(5);
        when(mockSoccerPitch.getX()).thenReturn(5);
        mockTrainee = new Trainee();
        mockCurrentPosition = new Position(2, 2, FitBits.Directions.E);
        mockTrainee.setCurrentPosition(mockCurrentPosition);
        Whitebox.invokeMethod(spyFitBits, "changePosition", mockTrainee);
        assertNotNull(mockTrainee);
        assertEquals("Position should be equal", new Position(3, 2, FitBits.Directions.E), mockTrainee.getCurrentPosition());
    }
}
