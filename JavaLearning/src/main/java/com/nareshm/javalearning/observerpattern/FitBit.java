package com.nareshm.javalearning.observerpattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by NARESHM on 3/06/2015.
 */
public class FitBit {
    public enum Directions{N,E,W,S}
    public enum COMMANDS{L,R,M}
    private static Logger logger= Logger.getLogger(FitBit.class.getName());
    private static SoccerPitch soccerPitch=null;

        public static void main(String[] args) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter Coordinates of Pitch:");
            String pitch = br.readLine();
            String[] pitchData = getSplitData(pitch);
            soccerPitch =new SoccerPitch(Integer.parseInt(pitchData[0]),Integer.parseInt(pitchData[1]));
            System.out.print("Enter Trainee Position:");
            String traineePosition = br.readLine();
            String[] traineePositionData=getSplitData(traineePosition);
            Trainee trainee=new Trainee();
            Position startPosition=new Position(Integer.parseInt(traineePositionData[0]),Integer.parseInt(traineePositionData[1]),traineePositionData[1]);
            trainee.setStartPosition(startPosition);
            System.out.print("Enter Trainee Instructions::");
            String instructions=br.readLine();
        }

    private static String[] getSplitData(String pitch) {
        String [] data=pitch.split("\\s");
        logger.log(Level.INFO, "Data split: {0}", Arrays.toString(data));
        return data;
    }

}
