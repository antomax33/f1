package com.example.f1;

import static android.app.PendingIntent.getActivity;

import android.content.res.Resources;
import android.util.Log;

import com.example.f1.graphisme.Square;

public class Decors {
    int grey=0xff888888;
    int blocks[][]={{-1,1},{-1,2},{-1,3},{0,0},{1,0},{2,-1},{-9,0},{-8,0},{-6,0},{-4,0},{-2,0},{3,0},{5,0},{7,0},{9,0}};
    Square sol[] = new Square[blocks.length];
    public Decors(){
        for(int i=0;i < blocks.length; i++){
            sol[i] = new Square(blocks[i][0], blocks[i][1], grey);
        }
    }

    void draw(float[] mvpMatrix){
        for(int i=0;i< blocks.length; i++){
            sol[i].draw(mvpMatrix);
        }
    }
}
