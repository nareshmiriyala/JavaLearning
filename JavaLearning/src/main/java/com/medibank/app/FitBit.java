package com.medibank.app;

import com.medibank.entities.Position;
import com.medibank.entities.SoccerPitch;
import com.medibank.entities.Trainee;
import com.medibank.exceptions.InvalidCommandException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by NARESHM on 3/06/2015.
 */
public class FitBit {
    public enum Directions{N,E,W,S}
    public enum COMMANDS{L,R,M}
    private  List<Directions> rightDirectionsOrder =null;
    private  List<Directions> leftDirectionsOrder =null;
     BufferedReader bufferedReader =null;

    private static Logger logger= Logger.getLogger(FitBit.class.getName());
    private static SoccerPitch soccerPitch=null;
    public FitBit(){}

        public void startSession(){
            Trainee trainee = null;
            rightDirectionsOrder = createDirectionsOrder();
            leftDirectionsOrder=new ArrayList<>(rightDirectionsOrder);
            //reverse the order
            Collections.reverse(leftDirectionsOrder);
            try {
                trainee = readInput();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                doCalibrate(trainee);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(trainee);

        }

    private static List<Directions> createDirectionsOrder() {
        List<Directions> directions= new ArrayList<>();
        directions.add(Directions.N);
        directions.add(Directions.E);
        directions.add(Directions.S);
        directions.add(Directions.W);
        return directions;
    }


    private  void doCalibrate(Trainee trainee) throws Exception {
        if(trainee!=null){
            List<COMMANDS> commands = trainee.getCommands();

            for(COMMANDS command:commands){
                System.out.println("command value:"+command);
                switch (command){
                    case L :
                        changeDirection(trainee,leftDirectionsOrder);
                        break;
                    case R :
                        changeDirection(trainee,rightDirectionsOrder);
                        break;
                    case M:
                        changePosition(trainee);
                        break;
                     default:
                         throw new InvalidCommandException("Invalid command");
                }
                System.out.println("Changed position values:"+trainee);
            }

        }
    }

    private  void changePosition(Trainee trainee) throws InvalidCommandException {
        if(trainee!=null){
            int y = trainee.getCurrentPosition().getY();
            int x = trainee.getCurrentPosition().getX();
            Directions currentDirections=trainee.getCurrentPosition().getDirection();
            switch (currentDirections) {
                case N:
                    if(y+1>soccerPitch.getY()) {
                        throw new InvalidCommandException("Invalid command ,cant move end of soccer pitch");
                    }else {
                        trainee.getCurrentPosition().setY(y + 1);
                    }
                    break;
                case W:
                    if (x > 0)
                        trainee.getCurrentPosition().setX(x - 1);
                    break;
                case S:
                    if (y > 0)
                        trainee.getCurrentPosition().setY(y - 1);
                    break;
                case E:
                    if(x+1>soccerPitch.getX()) {
                        throw new InvalidCommandException("Invalid command ,cant move end of soccer pitch");
                    }else {
                        trainee.getCurrentPosition().setX(x + 1);
                    }
                    break;

            }
        }
    }

    private static void changeDirection(Trainee trainee,List<Directions> order) {
        Directions currentDirection = trainee.getCurrentPosition().getDirection();
        Directions newDirection = getNewDirection(currentDirection,order);
        System.out.println("Changed Direction:"+newDirection);
        if(newDirection!=null){
            trainee.getCurrentPosition().setDirection(newDirection);
        }
    }


    private static Directions getNewDirection(Directions currentDirection,List<Directions> order) {
        Iterator it= order.iterator();
        while(it.hasNext()){
            Directions d= (Directions) it.next();
            if(currentDirection.compareTo(d)==0){
                if(it.hasNext()){
                    return (Directions) it.next();
                }else {
                   return order.get(0);
                }
            }
        }
        return null;
    }

    private Trainee readInput() throws IOException {
         bufferedReader =new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter Coordinates of Pitch:");
        String pitch = bufferedReader.readLine();
        String[] pitchData = getSplitData(pitch,"\\s");
        soccerPitch =new SoccerPitch(Integer.parseInt(pitchData[0]),Integer.parseInt(pitchData[1]));
        System.out.print("Enter Trainee Position:");
        String traineePosition = bufferedReader.readLine();
        String[] traineePositionData=getSplitData(traineePosition,"\\s");
        Trainee trainee = new Trainee();
        Position startPosition=new Position(Integer.parseInt(traineePositionData[0]),Integer.parseInt(traineePositionData[1]),Directions.valueOf(traineePositionData[2]));
        trainee.setStartPosition(startPosition);
        System.out.print("Enter Trainee Instructions:");
        String instructions= bufferedReader.readLine();
        String[] instructionsData = getSplitData(instructions, "");
        List<COMMANDS>  commandsList=new ArrayList<>();
        for(String command:instructionsData) {
            commandsList.add(COMMANDS.valueOf(command));
        }
        trainee.setCommands(commandsList);
        return trainee;
    }

    private static String[] getSplitData(String pitch,String pattern) {
        String [] data=pitch.split(pattern);
//        logger.log(Level.INFO, "Data split: {0}", Arrays.toString(data));
        System.out.println("Data split:"+ Arrays.toString(data));
        return data;
    }

}
