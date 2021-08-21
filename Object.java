package com.example.f1;

import android.util.Log;

public class Object {
    protected Game game;
    protected float positionX;
    protected float positionY;
    protected long timeLastUpdate=0;
    protected float vitesseXi=0, vitesseYi=0, vitesseXf=0, vitesseYf=0;

    public Object(float positionX, float positionY, Game game){
        this.positionX=positionX;
        this.positionY=positionY;
        this.game=game;
    }


    public void physique(){

        // 1. Time
        long timeNow = System.currentTimeMillis();
        if(timeLastUpdate==0){
            timeLastUpdate=timeNow;
            return;
        }

        long differenceTimeMillis = timeNow-timeLastUpdate;
        float differenceTime = differenceTimeMillis/1000.0f;

        // 2. Acceleration
        float accelerationX=-0.2f*vitesseXi, accelerationY=-0.2f*vitesseYi;

        vitesseXf=vitesseXi+accelerationX*differenceTime;
        vitesseYf=vitesseYi+accelerationY*differenceTime;

        float deplacementX = (float) ((vitesseXf+vitesseXi)*differenceTime/2.0);
        float deplacementY = (float) ((vitesseYf+vitesseYi)*differenceTime/2.0);

        positionX+=deplacementX;
        positionY+=deplacementY;



        //vitesse min
        if(Math.pow(vitesseXf,2) + Math.pow(vitesseYf,2)<game.VITESSEMINIMALAUCARRE){
            vitesseXf=0;
            vitesseYf=0;
        }

        //positionX
        if(positionX>game.BORDDEMAPX){
            positionX=game.BORDDEMAPX;
            vitesseXf*=-game.COEFFICIENTREBOND;
        }else if(positionX<-game.BORDDEMAPX){
            positionX=-game.BORDDEMAPX;
            vitesseXf*=-game.COEFFICIENTREBOND;
        }
        //positionY
        if(positionY>game.BORDDEMAPY){
            positionY=game.BORDDEMAPY;
            vitesseYf*=-game.COEFFICIENTREBOND;
        }else if(positionY<-game.BORDDEMAPY){
            positionY=-game.BORDDEMAPY;
            vitesseYf=0;
        }

        // TODO pas rester coincer dans mur


        vitesseXi=vitesseXf;
        vitesseYi=vitesseYf;

        Log.i("Triangle moa", "Triangle moa physique object vitesse " + vitesseXi+";"+vitesseYi);

        timeLastUpdate=timeNow;
    }

    public void addVitesseX(float vitesse){
        vitesseXi+=vitesse;
    }

    public void addVitesseY(float vitesse){
        vitesseYi+=vitesse;
    }

    public float getVitesseX(){
        return vitesseXf;
    }

    public float getVitesseY(){
        return vitesseYf;
    }

    public float getPositionX(){
        return positionX;
    }

    public float getPositionY(){
        return positionY;
    }


}
