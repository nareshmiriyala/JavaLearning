package com.medibank.app;

import com.medibank.StartApp;
import com.medibank.entities.Position;
import com.medibank.entities.SoccerPitch;
import com.medibank.entities.Trainee;
import com.medibank.exceptions.InvalidCommandException;
import com.medibank.exceptions.InvalidInputException;
import com.medibank.exceptions.InvalidPositionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fitbits class Implementations
 * <p>
 * This class will read the input value and do the trainee fitbits calibration.
 * Input :
 * 1)Upper-right coordinates of the pitch. ex: 5 5
 * 2) Trainee position by coach. ex: 3 3 N
 * 3)Series of instructions from coach telling the trainee how to move on the pitch.
 * ex: MLMRLMR
 * <p>
 * </p>
 * @author nareshm
 */
public class FitBitsImpl implements FitBits {

    private static final Level LEVEL = Level.FINE;
    private static List<Directions> rightDirectionsOrder = null;
    private static List<Directions> leftDirectionsOrder = null;
    private BufferedReader bufferedReader = null;

    private StartApp.InputType inputType;

    @Override
    public void setInputType(StartApp.InputType inputType) {
        this.inputType = inputType;
    }

    public FitBitsImpl(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    private static final Logger logger = Logger.getLogger(FitBitsImpl.class.getName());
    private static SoccerPitch soccerPitch = null;

    /**
     * Start the trainee fitbits
     *
     * @param trainee
     * @throws InvalidCommandException
     * @throws InvalidPositionException
     * @throws IOException
     * @throws InvalidInputException
     */
    @Override
    public void start(Trainee trainee) throws InvalidCommandException, InvalidPositionException, IOException, InvalidInputException {
        configureDirections();
        trainee = readTraineeData(trainee);
        doCalibrate(trainee);
        System.out.println("Final trainee " + trainee.getCurrentPosition());

    }

    /**
     * Configure the directions order.
     */
    private void configureDirections() {
        rightDirectionsOrder = createDirectionsOrder();
        leftDirectionsOrder = new ArrayList<>(rightDirectionsOrder);
        //reverse the order for left direction
        Collections.reverse(leftDirectionsOrder);
    }

    /**
     * Creates the right direction order.
     * ex: 90 right of N is E and W is N etc
     *
     * @return list of directions
     */
    private static List<Directions> createDirectionsOrder() {
        List<Directions> directions = new ArrayList<>();
        directions.add(Directions.N);
        directions.add(Directions.E);
        directions.add(Directions.S);
        directions.add(Directions.W);
        return directions;
    }

    /**
     * Start the trainee fitbits calibration.
     *
     * @param trainee
     * @throws InvalidCommandException
     * @throws InvalidPositionException
     */
    @Override
    public void doCalibrate(Trainee trainee) throws InvalidCommandException, InvalidPositionException {
        if (trainee != null) {
            List<Commands> Commands = trainee.getCommands();

            for (Commands command : Commands) {
                logger.log(Level.INFO, "Command {0}", command);
                switch (command) {
                    case L:
                        changeDirection(trainee, leftDirectionsOrder);
                        break;
                    case R:
                        changeDirection(trainee, rightDirectionsOrder);
                        break;
                    case M:
                        changePosition(trainee);
                        break;
                    default:
                        throw new InvalidCommandException("Invalid command");
                }
                logger.log(LEVEL, "Changed position values {0}", trainee);
            }

        }
    }

    /**
     * Change the position of trainee if the command is M
     *
     * @param trainee
     * @throws InvalidPositionException
     * @throws InvalidCommandException
     */
    @Override
    public void changePosition(Trainee trainee) throws InvalidPositionException, InvalidCommandException {
        if (trainee != null) {
            int y = trainee.getCurrentPosition().getY();
            int x = trainee.getCurrentPosition().getX();
            Directions currentDirections = trainee.getCurrentPosition().getDirection();
            switch (currentDirections) {
                case N:
                    if (y + 1 > soccerPitch.getY()) {
                        throw new InvalidPositionException("Invalid command ,cant move end of soccer pitch");
                    } else {
                        trainee.getCurrentPosition().setY(y + 1);
                    }
                    break;
                case W:
                    if ((x - 1) < 0) {
                        throw new InvalidPositionException("Invalid command ,cant move end of soccer pitch");
                    } else {
                        trainee.getCurrentPosition().setX(x - 1);
                    }
                    break;
                case S:
                    if ((y - 1) < 0) {
                        throw new InvalidPositionException("Invalid command ,cant move end of soccer pitch");
                    } else {
                        trainee.getCurrentPosition().setY(y - 1);
                    }
                    break;
                case E:
                    if (x + 1 > soccerPitch.getX()) {
                        throw new InvalidCommandException("Invalid command ,cant move end of soccer pitch");
                    } else {
                        trainee.getCurrentPosition().setX(x + 1);
                    }
                    break;

            }
        }
    }

    /**
     * Change the direction of trainee if the command is L or R
     *
     * @param trainee -input trainee to change direction
     * @param order   - order can be left or right
     */
    public static void changeDirection(Trainee trainee, List<Directions> order) {
        Directions currentDirection = trainee.getCurrentPosition().getDirection();
        Directions newDirection = getNewDirection(currentDirection, order);
        logger.log(Level.INFO, "Changed Direction: {0}", newDirection);
        if (newDirection != null) {
            trainee.getCurrentPosition().setDirection(newDirection);
        }
    }

    /**
     * Get the new direction of the trainee on the basis of input order.
     *
     * @param currentDirection
     * @param order            - order can be left or right
     * @return new directions of trainee
     */
    private static Directions getNewDirection(Directions currentDirection, List<Directions> order) {
        Iterator it = order.iterator();
        while (it.hasNext()) {
            Directions d = (Directions) it.next();
            if (currentDirection.compareTo(d) == 0) {
                if (it.hasNext()) {
                    return (Directions) it.next();
                } else {
                    return order.get(0);
                }
            }
        }
        return null;
    }

    /**
     * Read the trainee commands ,coordinates and soccer pitch co-ordinates from coach.
     *
     * @param trainee
     * @return trainee
     * @throws IOException
     * @throws InvalidPositionException
     * @throws InvalidInputException
     */
    @Override
    public Trainee readTraineeData(Trainee trainee) throws IOException, InvalidPositionException, InvalidInputException {
        readSoccerPitchCoordinates();
        readTraineePosition(trainee);
        readCoachInstructions(trainee);
        return trainee;
    }

    /**
     * Read coach line of instructions telling the trainee how to move on the pitch.
     *
     * @param trainee
     * @throws IOException
     * @throws InvalidInputException
     */
    private void readCoachInstructions(Trainee trainee) throws IOException, InvalidInputException {
        if (isConsoleInput()) {
            System.out.println("Enter Trainee Instructions:");
        }
        String instructions = validateInput();
        String[] instructionsData = getSplitData(instructions, "");
        List<Commands> CommandsList = new ArrayList<>();

        for (String command : instructionsData) {
            CommandsList.add(Commands.valueOf(command));
        }
        trainee.setCommands(CommandsList);
    }

    /**
     * Read trainee position from coach.
     *
     * @param trainee
     * @throws IOException
     * @throws InvalidInputException
     * @throws InvalidPositionException
     */
    private void readTraineePosition(Trainee trainee) throws IOException, InvalidInputException, InvalidPositionException {
        if (isConsoleInput()) {
            System.out.println("Enter Trainee Position:");
        }
        String traineePosition = validateInput();
        String[] traineePositionData = getSplitData(traineePosition, "\\s");
        try {
            if (Integer.parseInt(traineePositionData[0]) > soccerPitch.getX() || Integer.parseInt(traineePositionData[1]) > soccerPitch.getY()) {
                throw new InvalidPositionException("Input position is invalid,value shouldn't exceed the soccer pitch size");
            }
            Position startPosition = new Position(Integer.parseInt(traineePositionData[0]), Integer.parseInt(traineePositionData[1]), Directions.valueOf(traineePositionData[2]));
            trainee.setStartPosition(startPosition);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Input trainee position is invalid " + e.getMessage());
        }
    }

    /**
     * Read the soccer pitch co-ordinates.
     *
     * @throws IOException
     * @throws InvalidInputException
     */
    private void readSoccerPitchCoordinates() throws IOException, InvalidInputException {
        if (soccerPitch == null) {
            if (isConsoleInput()) {
                System.out.println("Enter Coordinates of Pitch:");
            }
            String pitch = validateInput();
            String[] pitchData = getSplitData(pitch, "\\s");
            if (pitchData.length != 2) {
                throw new InvalidInputException("Input soccer pitch size is invalid");
            } else {
                try {
                    int xValue = Integer.parseInt(pitchData[0]);
                    int yValue = Integer.parseInt(pitchData[1]);
                    if ((xValue == 0 && yValue == 0) || xValue < 0 || yValue < 0) {
                        throw new InvalidInputException("Input soccer pitch value is invalid");
                    }
                    soccerPitch = new SoccerPitch(xValue, yValue);
                } catch (NumberFormatException e) {
                    throw new InvalidInputException("Input soccer pitch is invalid " + e.getMessage());
                }
            }
        }
    }

    /* verify if its console input
     */
    private boolean isConsoleInput() {
        return inputType.equals(StartApp.InputType.CONSOLE);
    }

    /**
     * Validate the input data entered
     *
     * @return
     * @throws IOException
     * @throws InvalidInputException
     */
    private String validateInput() throws IOException, InvalidInputException {
        String line = bufferedReader.readLine();
        if (line == null || line.isEmpty()) {
            if (isConsoleInput())
                throw new InvalidInputException("Console Input is invalid.");
            else if (inputType.equals(StartApp.InputType.FILE)) {
                System.out.println("End of File.");
                System.exit(0);
            }
        }
        return line;
    }

    /**
     * Split the input data using the pattern
     *
     * @param pitch
     * @param pattern
     * @return
     */
    private static String[] getSplitData(String pitch, String pattern) {
        return pitch.split(pattern);
    }

}
