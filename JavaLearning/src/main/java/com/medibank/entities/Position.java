package com.medibank.entities;

import com.medibank.app.FitBit;

/**
 * Created by NARESHM on 3/06/2015.
 */
public class Position {
    int x,y;
    FitBit.Directions direction;
    public Position(int x,int y,FitBit.Directions direction){
        this.x=x;
        this.y=y;
        this.direction=direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public FitBit.Directions getDirection() {
        return direction;
    }

    public void setDirection(FitBit.Directions direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", direction=" + direction +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        if (y != position.y) return false;
        if (direction != position.direction) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }
}
