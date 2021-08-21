package com.example.f1;

import android.opengl.Matrix;

import com.example.f1.graphisme.Round;
import com.example.f1.graphisme.Square;

public class Balle extends Object{
    private final static int color = 0xffaaaaaa;
//    Square balleFoot;
    Round balleFoot;

    public Balle(Game game){
        super(0, 0, game);
        //balleFoot = new Square(-0.25f, -0.25f, 0.5f, 0.5f,135, 42, 12);
        balleFoot = new Round(0, 0, 1, 240, 20, 20);
    }

    public void draw(float[] mvpMatrix){
        Matrix.translateM(mvpMatrix, 0, positionX, positionY, 0);
        balleFoot.draw(mvpMatrix);
    }
}
