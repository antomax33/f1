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
        boolean centre=false, dessous=false, dessus=false, gauche=false, droite=false;
        int x,y;
        float persoX, persoY;
        float decimalX, decimalY;
        persoX = personnage.getPositionX();
        persoY = personnage.getPositionY()+1;//decalage car personnage en position 0,0  equivaut block en 0,1

        decimalX = persoX - (int)(Math.floor(persoX));
        decimalY = persoY - (int)(Math.floor(persoY));

        //verifier si personnage est dans un bloc (le centre du triangle)
        x = (int) Math.floor(persoX + 0.5f);
        y = (int) Math.floor(persoY + 0.5f);
        centre = decors.getBlock(x, y);

        if(decimalX>0.5f){
            //touche l'eventuel block a gauche
            gauche = decors.getBlock(x-1, y);
            if(decimalY>0.5f || decimalY < 0.01f){
                //touche bloc en dessous et celui dessous gaucher
                dessous = decors.getBlock(x-1, y-1) || decors.getBlock(x, y-1);

            }else{
                //touche block en dessus et celui dessus gauche
                dessus = decors.getBlock(x-1, y+1) || decors.getBlock(x, y+1);
            }
        }else{
            //touche l'eventuel block a droite
            droite = decors.getBlock(x+1, y);
            if(decimalY>0.5f || decimalY < 0.01f){
                //touche bloc en dessous et celui dessous droite
                dessous = decors.getBlock(x+1, y-1) || decors.getBlock(x, y-1);

            }else{
                //touche block en dessus et celui dessus droite
                dessus = decors.getBlock(x+1, y+1) || decors.getBlock(x, y+1);
            }
        }


        if(dessous){
            Log.i("Triangle moa", "Triangle moa block dessous");
        }
        if(gauche){
            Log.i("Triangle moa", "Triangle moa block gauche");
        }
        if(droite){
            Log.i("Triangle moa", "Triangle moa block droite");
        }
        if(dessus){
            Log.i("Triangle moa", "Triangle moa block dessus");
        }
        if(centre){
            Log.i("Triangle moa", "Triangle moa block centre");
        }
        personnage.physique(centre, dessous, dessus, gauche, droite);
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
