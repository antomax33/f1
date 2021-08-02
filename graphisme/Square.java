package com.example.f1.graphisme;

import android.graphics.Color;

public class Square {
    Triangle triangle1, triangle2;
    public Square(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, int r, int g, int b){
        triangle1 = new Triangle(x1, y1, x2, y2, x3, y3, r, g, b);
        triangle2 = new Triangle(x1, y1, x3, y3, x4, y4, r, g, b);
    }

    public Square(int x, int y, int width, int height, int r, int g, int b){
        this(x, y, x, y-height, x+width, y-height, x+width, y, r, g, b);
    }

    public Square(int x, int y, int color){
        this(x, y, 1, 1, (color >> 16)& 0xff, (color >> 8)& 0xff,(color)& 0xff);
    }


    public void draw(float[] mvpMatrix){
        triangle1.draw(mvpMatrix);
        triangle2.draw(mvpMatrix);
    }

    public void setColor(int r, int g, int b){
        triangle1.setColor(r, g, b);
        triangle2.setColor(r, g, b);
    }
}
