package com.medibank.app;

import com.medibank.entities.Position;
import com.medibank.entities.SoccerPitch;
import com.medibank.entities.Trainee;
import com.medibank.exceptions.InvalidCommandException;
import com.medibank.exceptions.InvalidInputException;
import com.medibank.exceptions.InvalidPositionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by NARESHM on 3/06/2015.
 */
public class FitBits {
    public enum Directions {N, E, W, S}

    public enum COMMANDS {L, R, M}

    private List<Directions> rightDirectionsOrder = null;
    private List<Directions> leftDirectionsOrder = null;
    private BufferedReader bufferedReader = null;

    public FitBits(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    private static Logger logger = Logger.getLogger(FitBits.class.getName());
    private static SoccerPitch soccerPitch = null;

    public Trainee startSession() throws InvalidCommandException, InvalidPositionException, IOException, InvalidInputException {
        Trainee trainee = null;
        rightDirectionsOrder = createDirectionsOrder();
        leftDirectionsOrder = new ArrayList<>(rightDirectionsOrder);
        //reverse the order
        Collections.reverse(leftDirectionsOrder);
        trainee = readInput();
        doCalibrate(trainee);
        System.out.println(trainee);
        return trainee;

    }

    private static List<Directions> createDirectionsOrder() {
        List<Directions> directions = new ArrayList<>();
        directions.add(Directions.N);
        directions.add(Directions.E);
        directions.add(Directions.S);
        directions.add(Directions.W);
        return directions;
    }


    private void doCalibrate(Trainee trainee) throws InvalidCommandException, InvalidPositionException {
        if (trainee != null) {
            List<COMMANDS> commands = trainee.getCommands();

            for (COMMANDS command : commands) {
                System.out.println("command value " + command);
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
                System.out.println("Changed position values:" + trainee);
            }

        }
    }

    private void changePosition(Trainee trainee) throws InvalidPositionException, InvalidCommandException {
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

    private static void changeDirection(Trainee trainee, List<Directions> order) {
        Directions currentDirection = trainee.getCurrentPosition().getDirection();
        Directions newDirection = getNewDirection(currentDirection, order);
        System.out.println("Changed Direction:" + newDirection);
        if (newDirection != null) {
            trainee.getCurrentPosition().setDirection(newDirection);
        }
    }


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

    private Trainee readInput() throws IOException, InvalidPositionException, InvalidInputException {

        System.out.print("Enter Coordinates of Pitch:");
        String pitch = bufferedReader.readLine();
        String[] pitchData = getSplitData(pitch, "\\s");
        if (pitchData.length != 2 ) {
            throw new InvalidInputException("Input soccer pitch size is invalid");
        } else {
            try {
                int xValue=Integer.parseInt(pitchData[0]);
                int yValue=Integer.parseInt(pitchData[1]);
                if((xValue==0 && yValue==0)||xValue<0 ||yValue<0){
                    throw new InvalidInputException("Input soccer pitch value is invalid");
                }
                soccerPitch = new SoccerPitch(xValue,yValue);
            } catch (NumberFormatException e) {
                throw new InvalidInputException("Input soccer pitch is invalid " + e.getMessage());
            }
        }
        System.out.print("Enter Trainee Position:");
        String traineePosition = bufferedReader.readLine();
        String[] traineePositionData = getSplitData(traineePosition, "\\s");
        Trainee trainee = new Trainee();
        try {
            if (Integer.parseInt(traineePositionData[0]) > soccerPitch.getX() || Integer.parseInt(traineePositionData[1]) > soccerPitch.getY()) {
                throw new InvalidPositionException("Input position is invalid,value shouldn't exceed the soccer pitch size");
            }
            Position startPosition = new Position(Integer.parseInt(traineePositionData[0]), Integer.parseInt(traineePositionData[1]), Directions.valueOf(traineePositionData[2]));
            trainee.setStartPosition(startPosition);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Input trainee position is invalid " + e.getMessage());
        }
        System.out.print("Enter Trainee Instructions:");
        String instructions = bufferedReader.readLine();
        String[] instructionsData = getSplitData(instructions, "");
        List<COMMANDS> commandsList = new ArrayList<>();

        for (String command : instructionsData) {
            commandsList.add(COMMANDS.valueOf(command));
        }
        trainee.setCommands(commandsList);
        return trainee;
    }

    private static String[] getSplitData(String pitch, String pattern) {
        String[] data = pitch.split(pattern);
//        logger.log(Level.INFO, "Data split: {0}", Arrays.toString(data));
        System.out.println("Data split:" + Arrays.toString(data));
        return data;
    }

}
