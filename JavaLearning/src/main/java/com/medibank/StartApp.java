package com.medibank;

import com.medibank.app.FitBit;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by NARESHM on 3/06/2015.
 */
public class StartApp {
    public static void main(String[] args) {
        FitBit fitBit = new FitBit(new BufferedReader(new InputStreamReader(System.in)));
        fitBit.startSession();
    }
}
