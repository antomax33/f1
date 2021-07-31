package com.example.f1;

import com.example.f1.graphisme.Square;

public class Decors {
    Square sol;
    public Decors(){
        sol = new Square(0, 0, 2, 1, 128, 128, 128);
    }

    void draw(float[] mvpMatrix){
        sol.draw(mvpMatrix);
    }
}
