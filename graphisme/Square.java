package com.example.f1.graphisme;

public class Square {
    Triangle triangle1, triangle2;
    public Square(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, int c1, int c2, int c3){
        triangle1 = new Triangle(x1, y1, x2, y2, x3, y3, c1, c2, c3);
        triangle2 = new Triangle(x1, y1, x3, y3, x4, y4, c1, c2, c3);
    }

    public Square(int x, int y, int width, int height, int c1, int c2, int c3){
        triangle1 = new Triangle(x, y, x, y-height, x+width, y-height, c1, c2, c3);
        triangle2 = new Triangle(x, y, x+width, y-height, x+width, y, c1, c2, c3);
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
