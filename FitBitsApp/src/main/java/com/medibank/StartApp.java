package com.medibank;

import com.medibank.app.FitBits;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by NARESHM on 3/06/2015.
 */
public class StartApp {
    public static void main(String[] args) {
        FitBits fitBits = new FitBits(new BufferedReader(new InputStreamReader(System.in)));
        fitBits.startSession();
    }
}
