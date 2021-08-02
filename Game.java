package com.example.f1;

import android.opengl.Matrix;
import android.util.Log;

public class Game {
    public Personnage personnage;
    Decors decors;
    long oldTime=0;
    float[] mPersonnage;
    float[] mDecors;

    public Game(){
        personnage = new Personnage();
        decors = new Decors();

        mPersonnage = new float[16];
        mDecors = new float[16];
    }

    void draw(float[] mPersonnage){
        physique();
        for(int i=0;i<16;i++){
            mDecors[i]=mPersonnage[i];
        }
        //Log.i("Triangle moa", "Triangle moa differenceTime "+differenceTime+" distance "+ distance);
        Matrix.translateM(mDecors, 0, -personnage.getPositionX(), -personnage.getPositionY(), 0);

        decors.draw(mDecors);
        personnage.draw(mPersonnage);
    }

    void physique(){
        personnage.physique();
    }

    public void bouttonGauche(){
        Log.i("Triangle moa", "Triangle moa btn g");
       personnage.setAccelerer(true);
       personnage.setDirectionDroite(false);
    }

    public void bouttonDroite(){
        Log.i("Triangle moa", "Triangle moa btn d");
        personnage.setAccelerer(true);
        personnage.setDirectionDroite(true);
    }

    public void bouttonLache(){
        Log.i("Triangle moa", "Triangle moa btn relache");
       personnage.setAccelerer(false);
    }

    public void bouttonCouleur(int couleur){
        Log.i("Triangle moa", "Triangle moa btn couleur");
        switch (couleur){
            case 1:
                personnage.setColor(128, 100, 12);
               break;
            case 2:
                personnage.setColor(128, 100, 120);
                break;

            case 3:
                personnage.setColor(0, 120, 240);
                break;
        }
    }


}
