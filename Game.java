package com.example.f1;

import android.opengl.Matrix;

import com.example.f1.graphisme.ui;

public class Game {
    public final static float ZOOM = 0.3f;
    public Personnage personnage;
    Decors decors;
    ui ui;

    public Game(){
        personnage = new Personnage();
        decors = new Decors();

        ui = new ui();
    }

    void draw(float[] mScreen){
        physique();
        float mView[] = new float[16];
        float mDecors[] = new float[16];
        for(int i=0;i<16;i++){
            mView[i]=mScreen[i];
        }

        Matrix.scaleM(mView,0, ZOOM, ZOOM, ZOOM);

        for(int i=0;i<16;i++){
            mDecors[i]=mView[i];
        }
        float angle = personnage.getAngle();
        if(angle!=0){
            Matrix.rotateM(mDecors, 0, angle, 0, 0, 1);
        }
        Matrix.translateM(mDecors, 0, -personnage.getPositionX(), -personnage.getPositionY(), -1);

        //Matrix.rotateM(mDecors, 0, -20, 1, 0, 0);
        //Matrix.perspectiveM(mDecors, 0, 5f, 1.2f, 0.1f, 200f);
        decors.draw(mDecors);
        personnage.draw(mView);
        ui.onDraw(mScreen);
    }

    void physique(){
       personnage.physique();
    }

    public void setPersonnageAcceleration(int a){
       personnage.setAccelerer(a);
    }

    public void setPersonnageRotation(float a){
        personnage.setAngle(a);
    }
}
