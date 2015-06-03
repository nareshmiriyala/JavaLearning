package com.medibank.entities;

import com.medibank.app.FitBit;

import java.util.List;

/**
 * Created by NARESHM on 3/06/2015.
 */
public class Trainee {
    private Position startPosition;
    private Position endPosition;
    private Position currentPosition;
    private List<FitBit.COMMANDS> commands;

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

    public Position getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Position endPosition) {
        this.endPosition = endPosition;
    }

    public List<FitBit.COMMANDS> getCommands() {
        return commands;
    }

    public void setCommands(List<FitBit.COMMANDS> commands) {
        this.commands = commands;
    }

    @Override
    public String toString() {
        return "Trainee{" +
                "currentPosition=" + currentPosition +
                '}';
    }
}
