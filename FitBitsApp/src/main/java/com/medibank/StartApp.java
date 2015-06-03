package com.medibank;

import com.medibank.app.FitBits;
import com.medibank.exceptions.InvalidCommandException;
import com.medibank.exceptions.InvalidPositionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by NARESHM on 3/06/2015.
 */
public class StartApp {
    public static void main(String[] args) {
        FitBits fitBits = new FitBits(new BufferedReader(new InputStreamReader(System.in)));
        try {
            fitBits.startSession();
        } catch (InvalidCommandException e) {
            e.printStackTrace();
        } catch (InvalidPositionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
