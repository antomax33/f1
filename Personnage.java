package com.example.f1;

import android.util.Log;

import com.example.f1.graphisme.Triangle;

public class Personnage {
    public final float VITESSEACCELERATION = 0.0006f;
    public final float VITESSEFREINAGE = VITESSEACCELERATION*0.4f;
    public final float VITESSEMINIMAL = 0.02f;
    public final float ACCELERATIONGRAVITE = 0.00981f;

    private Triangle triangle;
    private float positionX, positionY;
    private float vitesseXi=0, vitesseYi=0, vitesseXf=0, vitesseYf=0;

    private boolean accelerer=false;
    private boolean directionDroite=true;
    private long timeLastUpdate=0;

    public Personnage(){
        positionX=0f;
        positionY=0f;
        triangle = new Triangle(0.2f, 0f, 0.5f, 0.8f, 0.8f, 0f, 128, 23, 37);
    }

    public void physique(){
        float accelerationX=0f, accelerationY=0f;

        //TODO enlever
        if(positionX>2){
            accelerationY=ACCELERATIONGRAVITE;
        }else if(positionX<-2){
            accelerationY=-ACCELERATIONGRAVITE;
        }


        if(timeLastUpdate==0){
            timeLastUpdate=System.currentTimeMillis();
            return;
        }

        long timeNow = System.currentTimeMillis();
        long differenceTimeMilliseconde = timeLastUpdate-timeNow;
        float differenceTime = (float)(differenceTimeMilliseconde/1000.0f);

        if(accelerer){
            if(directionDroite){
                accelerationX=-VITESSEACCELERATION;
            }else{
                accelerationX=VITESSEACCELERATION;
            }
        }else{
            if(vitesseXi>VITESSEMINIMAL){
                accelerationX=VITESSEFREINAGE;
            }else if(vitesseXi<-VITESSEMINIMAL){
                accelerationX=-VITESSEFREINAGE;
            }else{
                accelerationX=0;
                vitesseXf=0;
            }
        }

        vitesseXf+=differenceTime*accelerationX;
        vitesseYf+=differenceTime*accelerationY;

        positionX += (vitesseXf+vitesseXi)/2.0f;
        positionY += (vitesseYf+vitesseYi)/2.0f;

        if(positionX>10){
            positionX=10;
            vitesseXf=0;
        }else if(positionX<-10){
            positionX=-10;
            vitesseXf=0;
        }

        if(positionY>2){
            positionY=2;
            vitesseYf=0;
        }else if(positionY<-2){
            positionY=-2;
            vitesseYf=0;
        }


        //Log.i("Triangle moa", "Triangle moa position: (" + positionX + ";" + positionY + ") vitesse: (" + vitesseXf + ";" + vitesseYf + ")");

        vitesseXi=vitesseXf;
        vitesseYi=vitesseYf;
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

    void setAccelerer(boolean b){accelerer=b;}
    void setDirectionDroite(boolean b){directionDroite=b;}

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
