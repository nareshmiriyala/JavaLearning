package com.medibank;

import com.medibank.app.FitBits;
import com.medibank.exceptions.InvalidCommandException;
import com.medibank.exceptions.InvalidInputException;
import com.medibank.exceptions.InvalidPositionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by NARESHM on 3/06/2015.
 */
public class StartApp {
    public enum InputType {CONSOLE, FILE}

    private static FitBits fitBits = null;

    public static void main(String[] args) {
        InputStream inputStream = null;
        try {
            inputStream = StartApp.class.getClassLoader().getResourceAsStream("input.properties");
            fitBits = getInputChannel(inputStream);
            while (true) {
                synchronized (fitBits) {
                    fitBits.startSession();
                }
            }
        } catch (InvalidCommandException | InvalidPositionException | IOException e) {
            e.printStackTrace();
        } catch (InvalidInputException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static FitBits getInputChannel(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("=======FitBitsApp Options ==========");
        System.out.print("1 to read from property file \n" +
                "2 from console\n" +
                "3 to Exit\n" + "Enter Input:");
        do {
            String s = br.readLine();
            switch (s) {
                case "1":
                    fitBits = new FitBits(new BufferedReader(new InputStreamReader(inputStream)));
                    fitBits.setInputType(InputType.FILE);
                    break;
                case "2":
                    fitBits = new FitBits(new BufferedReader(new InputStreamReader(System.in)));
                    fitBits.setInputType(InputType.CONSOLE);
                    break;
                case "3":
                    System.exit(0);
                    break;
                default:
                    System.out.print("Invalid input,enter correct value:");
            }
        } while (fitBits == null);
        return fitBits;
    }

}
