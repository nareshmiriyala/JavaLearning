package com.medibank.entities;

/**
 * Represents the soccer pitch where trainees play soccer.
 * @author nareshm
 */
public class SoccerPitch {

    private final int x;
    private final int y;

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
