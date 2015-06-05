package com.medibank.app;

import com.medibank.StartApp;
import com.medibank.entities.Position;
import com.medibank.entities.SoccerPitch;
import com.medibank.entities.Trainee;
import com.medibank.exceptions.InvalidCommandException;
import com.medibank.exceptions.InvalidInputException;
import com.medibank.exceptions.InvalidPositionException;
import org.junit.After;
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

/**
 * Unit tests for FitBits Implementation class.
 * @author nareshm
 */
@RunWith(PowerMockRunner.class)
public class FitBitsImplTest {

    private FitBitsImpl spyFitBits;

    @Mock
    private BufferedReader mockInputStream;

    @Mock
    private SoccerPitch mockSoccerPitch;

    private Trainee trainee;

    @Before
    public void setUp() throws Exception {
        spyFitBits = PowerMockito.spy(new FitBitsImpl(mockInputStream));
        // mock private soccerPitch and initialize it null
        MemberModifier
                .field(FitBitsImpl.class, "soccerPitch").set(
                null, null);
        spyFitBits.setInputType(StartApp.InputType.FILE);
        trainee = new Trainee(spyFitBits);
    }

    @After
    public void tearDown() throws Exception {
        spyFitBits = null;

    }

    @Test
    public void testSuccessScenario1() throws Exception {
        when(mockInputStream.readLine()).thenReturn("5 5").thenReturn("1 2 N").thenReturn("LMLMLMLMM");
        trainee.startSession();
        assertNotNull(trainee);
        Position expectedPosition = new Position(1, 3, Directions.N);
        assertEquals("Final Coordinates should be equal", expectedPosition, trainee.getCurrentPosition());
    }

    @Test
    public void testSuccessScenario2() throws Exception {
        when(mockInputStream.readLine()).thenReturn("5 5").thenReturn("3 3 E").thenReturn("MMRMMRMRRM");
        trainee.startSession();
        Position expectedPosition = new Position(5, 1, Directions.E);
        assertEquals("Final Coordinates should be equal", expectedPosition, trainee.getCurrentPosition());
    }

    @Test
    public void testSuccessScenario3() throws Exception {
        when(mockInputStream.readLine()).thenReturn("15 15").thenReturn("9 3 E").thenReturn("MMRRRRRRRRRRRRRRRRRRRRRRL");
        trainee.startSession();
        Position expectedPosition = new Position(11, 3, Directions.S);
        assertEquals("Final Coordinates should be equal", expectedPosition, trainee.getCurrentPosition());
    }

    @Test
    public void testSuccessScenario4() throws Exception {
        when(mockInputStream.readLine()).thenReturn("1 1").thenReturn("1 1 S").thenReturn("M");
        trainee.startSession();
        Position expectedPosition = new Position(1, 0, Directions.S);
        assertEquals("Final Coordinates should be equal", expectedPosition, trainee.getCurrentPosition());
    }

    @Test
    public void testSuccessScenario5() throws Exception {
        when(mockInputStream.readLine()).thenReturn("1 1").thenReturn("1 0 W").thenReturn("M");
        trainee.startSession();
        Position expectedPosition = new Position(0, 0, Directions.W);
        assertEquals("Final Coordinates should be equal", expectedPosition, trainee.getCurrentPosition());
    }

    @Test
    public void testSuccessScenarioTwoAgents() throws Exception {
        when(mockInputStream.readLine()).thenReturn("5 5").thenReturn("1 0 W").thenReturn("M");
        Trainee trainee1 = new Trainee(spyFitBits);
        trainee1.startSession();
        Position expectedPosition = new Position(0, 0, Directions.W);
        assertEquals("Final Coordinates should be equal", expectedPosition, trainee1.getCurrentPosition());
        when(mockInputStream.readLine()).thenReturn("3 3 E").thenReturn("MMRMMRMRRM");//not need to return soccer size
        Trainee trainee2 = new Trainee(spyFitBits);
        trainee2.startSession();
        Position expectedPosition2 = new Position(5, 1, Directions.E);
        assertEquals("Final Coordinates should be equal", expectedPosition2, trainee2.getCurrentPosition());
    }

    @Test(expected = InvalidPositionException.class)
    public void testInputStartingPositionNotInRange() throws Exception {
        when(mockInputStream.readLine()).thenReturn("5 5").thenReturn("6 3 E").thenReturn("MMRMMRMRRM");//here value 6 3 E is not in range
        trainee.startSession();
    }

    @Test(expected = InvalidInputException.class)
    public void testInputStartingPositionInvalid() throws Exception {
        when(mockInputStream.readLine()).thenReturn("5 5").thenReturn("E E").thenReturn("MMRMMRMRRM");//here value 6 3 E is not in range
        trainee.startSession();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInputCommandsInvalid() throws Exception {
        when(mockInputStream.readLine()).thenReturn("5 5").thenReturn("4 4 E").thenReturn("mfdfd");//here value 6 3 E is not in range
        trainee.startSession();
    }

    @Test(expected = InvalidCommandException.class)
    public void testInputCommandsNotInRange() throws Exception {
        when(mockInputStream.readLine()).thenReturn("5 5").thenReturn("3 3 E").thenReturn("MMMMMMMMMMMMMMM");
        trainee.startSession();
    }

    @Test(expected = InvalidInputException.class)
    public void testInputSoccerPitchInvalid() throws Exception {
        when(mockInputStream.readLine()).thenReturn("533335").thenReturn("6 3 E").thenReturn("MMRMMRMRRM");//here value 6 3 E is not in range
        trainee.startSession();
    }

    @Test(expected = InvalidInputException.class)
    public void testInputSoccerPitchInvalidScenario1() throws Exception {
        when(mockInputStream.readLine()).thenReturn("0 0").thenReturn("6 3 E").thenReturn("MMRMMRMRRM");//here value 6 3 E is not in range
        trainee.startSession();
    }

    @Test(expected = InvalidInputException.class)
    public void testInputSoccerPitchInvalidData() throws Exception {
        when(mockInputStream.readLine()).thenReturn("53 Y3335").thenReturn("6 3 E").thenReturn("MMRMMRMRRM");//here value 6 3 E is not in range
        trainee.startSession();
    }


    @Test
    public void testChangePosition() throws Exception {
        // mock private field/variable
        MemberModifier
                .field(FitBitsImpl.class, "soccerPitch").set(
                mockSoccerPitch, mockSoccerPitch);
        when(mockSoccerPitch.getY()).thenReturn(5);
        when(mockSoccerPitch.getX()).thenReturn(5);
        Trainee mockTrainee = new Trainee(spyFitBits);
        Position mockCurrentPosition = new Position(2, 2, Directions.E);
        mockTrainee.setCurrentPosition(mockCurrentPosition);
        Whitebox.invokeMethod(spyFitBits, "changePosition", mockTrainee);
        assertNotNull(mockTrainee);
        assertEquals("Position should be equal", new Position(3, 2, Directions.E), mockTrainee.getCurrentPosition());
    }
}
