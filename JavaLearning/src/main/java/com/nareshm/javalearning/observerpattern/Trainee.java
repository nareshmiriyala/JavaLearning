package com.nareshm.javalearning.observerpattern;

/**
 * Created by NARESHM on 3/06/2015.
 */
public class Trainee {
    private Position startPosition;
    private Position endPosition;
    private Position currentPosition;
    public Position getCurrentPosition() {
        //set start position as current position if its null
        if(currentPosition==null){
            return startPosition;
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
}
