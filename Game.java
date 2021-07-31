package com.example.f1;

import android.opengl.Matrix;

public class Game {
    Personnage personnage;
    Decors decors;
    long oldTime=0;
    float[] mPersonnage;
    float[] mDecors;
    long avancerDernierTemps;
    boolean avancer=true;
    boolean deplacement=false;

    public Game(){
        personnage = new Personnage();
        decors = new Decors();

        mPersonnage = new float[16];
        mDecors = new float[16];
    }

    void draw(float[] mPersonnage){

        if(oldTime==0){
            oldTime=System.currentTimeMillis();
        }
        long timeNow = System.currentTimeMillis();
        int differenceTime = (int) (timeNow-oldTime);
        //Log.i("Triangle moa", "Triangle moa difference time "+differenceTime + " oldTime " + oldTime + " current " + timeNow);
        float distance = (float) (differenceTime*5.0/10000.0);

        for(int i=0;i<16;i++){
            mDecors[i]=mPersonnage[i];
        }
        //Log.i("Triangle moa", "Triangle moa differenceTime "+differenceTime+" distance "+ distance);
        Matrix.translateM(mDecors, 0, -personnage.getPositionX(), 0, 0);



        decors.draw(mDecors);
        personnage.draw(mPersonnage);
    }

    public void buttonTouch(float x, float y){
        if (y>0.85f){
            if(x<0.25f){
                personnage.setColor(255, 0, 0);
            }else if(x<0.50f){
                personnage.setColor(0, 255, 0);
            }else if(x<0.75f){
                personnage.setColor(0, 0, 255);
            }
        }
    }

    public void avancerCommencer(){
        avancerDernierTemps=System.currentTimeMillis();
        avancer=true;
        deplacement=true;
    }

    public void reculerCommencer(){
        avancerDernierTemps=System.currentTimeMillis();
        avancer=false;
        deplacement=true;
    }

    public void continuerDeplacement(){
        if(!deplacement){
            return;
        }
        long tempsImmediat = System.currentTimeMillis();
        long differenceTemps=avancerDernierTemps-tempsImmediat;
        float deplacement=differenceTemps*personnage.VITESSE;
        if(!avancer){
            deplacement*=-1;
        }
        personnage.deplacement(deplacement, 0);
    }

    public void arreterDeplacement(){
        //dernier mouvement. potentiellement a supprimer
        continuerDeplacement();
        //arret
        deplacement=false;
        avancerDernierTemps=0;
    }

}
