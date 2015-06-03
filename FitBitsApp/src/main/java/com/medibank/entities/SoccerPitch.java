package com.medibank.entities;

/**
 * Created by NARESHM on 3/06/2015.
 */
public class SoccerPitch {

    private int x;
    private int y;

    public SoccerPitch(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "SoccerPitch{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
