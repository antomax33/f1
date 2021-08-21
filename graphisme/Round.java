package com.example.f1.graphisme;

public class Round {
    private final static int nbrCote = 10;
    private Triangle triangle[] = new Triangle[10];

    public Round(float x, float y, float rayon, int r, int g, int b){
        float angle = (float) (2*Math.PI/nbrCote);
        for(int i=0;i<nbrCote;i++){
            float angle1, angle2;
            angle1=angle*i;
            angle2=angle*(i+1);

            triangle[i] = new Triangle(x, y, (float)(x+Math.cos(angle1)*rayon/2.0), (float)(y+Math.sin(angle1)*rayon/2.0), (float)(x+Math.cos(angle2)*rayon/2.0), (float)(y+Math.sin(angle2)*rayon/2.0), r, g, b);
        }
    }


    public void draw(float[] mvpMatrix){
        for(int i=0;i<nbrCote;i++){
            triangle[i].draw(mvpMatrix);
        }
    }
}
/*
package com.example.f1.graphisme;

import android.graphics.Color;

public class Square {
    Triangle triangle1, triangle2;
    public Square(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, int r, int g, int b){
        triangle1 = new Triangle(x1, y1, x2, y2, x3, y3, r, g, b);
        triangle2 = new Triangle(x1, y1, x3, y3, x4, y4, r, g, b);
    }

    public Square(float x, float y, float width, float height, int r, int g, int b){
        this(x, y, x, y+height, x+width, y+height, x+width, y, r, g, b);
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

 */