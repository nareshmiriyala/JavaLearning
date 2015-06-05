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
    /**
     * Set input type,accepts enum input value (console/file)
     * @param inputType
     */
    void setInputType(StartApp.InputType inputType);

    /**
     * Start the fitbit app of the trainee.
     * @param trainee
     * @throws InvalidCommandException
     * @throws InvalidPositionException
     * @throws IOException
     * @throws InvalidInputException
     */
    void start(Trainee trainee) throws InvalidCommandException, InvalidPositionException, IOException, InvalidInputException;

    /**
     * Do the fitbit calibration on the basis of coach input.
     * @param trainee
     * @throws InvalidCommandException
     * @throws InvalidPositionException
     */
    void doCalibrate(Trainee trainee) throws InvalidCommandException, InvalidPositionException;

    /**
     * Change the fitbits trainee position
     * @param trainee
     * @throws InvalidPositionException
     * @throws InvalidCommandException
     */
    void changePosition(Trainee trainee) throws InvalidPositionException, InvalidCommandException;

    /**
     * Read the trainee data from coach
     * @param trainee
     * @return
     * @throws IOException
     * @throws InvalidPositionException
     * @throws InvalidInputException
     */
    Trainee readTraineeData(Trainee trainee) throws IOException, InvalidPositionException, InvalidInputException;
}
