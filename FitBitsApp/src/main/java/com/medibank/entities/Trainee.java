package com.medibank.entities;

import com.medibank.app.Commands;
import com.medibank.app.FitBits;
import com.medibank.exceptions.InvalidCommandException;
import com.medibank.exceptions.InvalidInputException;
import com.medibank.exceptions.InvalidPositionException;

import java.io.IOException;
import java.util.List;

/**
 * Trainee class represents Medibank member who uses fitbits.
 * @author nareshm
 * */
public class Trainee {
    private Position startPosition;
    private Position endPosition;
    private Position currentPosition;
    private List<Commands> Commands;
    private FitBits fitBits;

    public Trainee(FitBits fitBits){
        this.fitBits=fitBits;
    }

    public Position getCurrentPosition() {
        //set start position as current position if its null
        if (currentPosition == null) {
            currentPosition = startPosition;
        }
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public List<Commands> getCommands() {
        return Commands;
    }

    public void setCommands(List<Commands> Commands) {
        this.Commands = Commands;
    }

    public void startSession() throws InvalidCommandException, InvalidPositionException, InvalidInputException, IOException {
        fitBits.start(this);
    }

    @Override
    public String toString() {
        return "Trainee{" +
                "currentPosition=" + currentPosition +
                '}';
    }
}
