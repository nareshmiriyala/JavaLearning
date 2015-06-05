package com.medibank.app;

import com.medibank.StartApp;
import com.medibank.entities.Trainee;
import com.medibank.exceptions.InvalidCommandException;
import com.medibank.exceptions.InvalidInputException;
import com.medibank.exceptions.InvalidPositionException;

import java.io.IOException;

/**
 * Interface of fitbits device.
 * @author nareshm
 */
public interface FitBits {
    void setInputType(StartApp.InputType inputType);

    void start(Trainee trainee) throws InvalidCommandException, InvalidPositionException, IOException, InvalidInputException;

    void doCalibrate(Trainee trainee) throws InvalidCommandException, InvalidPositionException;

    void changePosition(Trainee trainee) throws InvalidPositionException, InvalidCommandException;

    Trainee readTraineeData(Trainee trainee) throws IOException, InvalidPositionException, InvalidInputException;
}
