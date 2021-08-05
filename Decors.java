package com.example.f1;

import static android.app.PendingIntent.getActivity;

import android.content.res.Resources;
import android.util.Log;

import com.example.f1.graphisme.Square;

public class Decors {
    int grey=0xff888888;
    int blocks[][]={{0,0},{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0},{8,0},{9,0},{-1,-3},{-3,-1},{-3,0},{-2,-4},{-9,-5},{3,-5},{-8,-6},{-7,-6},{-6,-6},{-5,-6},{-4,-6},{-3,-6},{-2,-6},{-1,-6},{0,-6},{1,-6},{2,-6},{2,-6},{3,-6},{4,-6},{5,-6}};
    //int blocks[][] = {{0,0}};
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

    boolean getBlock(int x, int y){
        for(int i=0;i<blocks.length;i++){
            if(blocks[i][0]==x && blocks[i][1]==y){
                return true;
            }
        }
        return false;
    }
}
