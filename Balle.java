package com.example.f1;

import android.opengl.Matrix;

import com.example.f1.graphisme.Square;

public class Balle extends Object{
    private final static int color = 0xffaaaaaa;
    Square balleFoot;

    public Balle(){
        super(0, 0);
        balleFoot = new Square(-0.25f, -0.25f, 0.5f, 0.5f,135, 42, 12);
    }

    public void draw(float[] mvpMatrix){
        Matrix.translateM(mvpMatrix, 0, positionX, positionY, 0);
        balleFoot.draw(mvpMatrix);
    }
}
