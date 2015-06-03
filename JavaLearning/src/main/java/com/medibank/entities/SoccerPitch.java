package com.medibank.entities;

/**
 * Created by NARESHM on 3/06/2015.
 */
public class SoccerPitch {

    int x,y;
    public SoccerPitch(int x,int y){
        this.x=x;
        this.y=y;
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

    @Override
    public String toString() {
        return "SoccerPitch{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
