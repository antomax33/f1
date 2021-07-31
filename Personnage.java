package com.example.f1;

import com.example.f1.graphisme.Triangle;

public class Personnage {
    Triangle triangle;
    float positionX, positionY;
    public final float VITESSE = 0.0002f;

    public Personnage(){
        positionX=0f;
        positionY=0f;
        triangle = new Triangle(0.2f, 0f, 0.5f, 0.8f, 0.8f, 0f, 128, 23, 37);
    }

    void draw(float []mvpMatrix){
        triangle.draw(mvpMatrix);
    }

    void setColor(int r, int g, int b){
        triangle.setColor(r, g, b);
    }

    void deplacement(float x, float y){
        positionX+=x;
        positionY+=y;
    }

    void setPositionX(float x){
        positionX=x;
    }
    void setPositionY(float y){
        positionY=y;
    }

    float getPositionX(){
        return positionX;
    }

    float getPositionY(){
        return positionY;
    }
}
