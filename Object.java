package com.example.f1;

public class Object {
    protected float positionX;
    protected float positionY;
    protected float vitesseXi=0, vitesseYi=0, vitesseXf=0, vitesseYf=0;

    public Object(float positionX, float positionY){
        this.positionX=positionX;
        this.positionY=positionY;
    }

    public void addVitesseX(float vitesse){
        vitesseXf+=vitesse;
    }

    public void addVitesseY(float vitesse){
        vitesseYf+=vitesse;
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
