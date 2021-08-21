package com.example.f1;

import android.util.Log;

import com.example.f1.graphisme.Triangle;

public class Personnage extends Object{
    public final float ACCELERATION = 6f;
    public final float VITESSEFREINAGE = ACCELERATION*0.2f;
    public final float ACCELERATIONBOOST = ACCELERATION*7f;

    public final float VITESSEMINIMALAUCARRE = 0.002f;
    public final float VITESSEMAXIMAL= 10f;
    public final float ACCELERATIONGRAVITE = -9.81f;
    public final float DECALAGEDROITE = 1f;
    public final float DECALAGEGAUCHE = 0f;
    public final float DECALAGEHAUT = 1f;
    public final float BORDDEMAPX = 7f;
    public final float BORDDEMAPY = 11.5f;
    public final float COEFFICIENTREBOND = 0.2f;
    public final float COEFFICIENTPNEU = 0.9f;

    private Triangle triangle;
    private float angle=0;
    private float angleActuel=0;

    private int accelerer=0;// -1 arret, 0 rien, 1 avancer, 2 boost
    private long timeLastUpdate=0;
    private Game game;

    public Personnage(){
        super(0,-3);
        triangle = new Triangle(-0.5f, -0.5f, 0f, 0.5f, 0.5f, -0.5f, 128, 23, 37);
    }

    public void physique(Balle balle){

        /*
        Description: calcul la position du personnage

        1. Difference temps

        2. Accélération et angle

        3. Vitesse

        4. Balle

        5. Deplacement

        6. Limite position, vitesse

        7. Maj ancienne vitesse
        */


        // 1. Différence temps
        if(timeLastUpdate==0){
            timeLastUpdate=System.currentTimeMillis();
            return;
        }

        long timeNow = System.currentTimeMillis();
        long differenceTimeMilliseconde = timeNow-timeLastUpdate;
        double differenceTime = differenceTimeMilliseconde/1000.0f;
        timeLastUpdate=timeNow;


        // 2. Accélération et angle

        angle+=angleActuel*differenceTime;

        float accelerationX=0f, accelerationY=0f;

        switch (accelerer){
            case -1:
                accelerationX = (float) (-Math.sin(Math.toRadians(angle))*ACCELERATION);
                accelerationY = (float) (-Math.cos(Math.toRadians(angle))*ACCELERATION);
                Log.i("Triangle moa", "Triangle moa v accelerer 1 (" + accelerationX + ";" + accelerationY + ")");
                break;
            case 0:
                accelerationX = -vitesseXi*VITESSEFREINAGE;
                accelerationY = -vitesseYi*VITESSEFREINAGE;
                break;
            case 1:
                accelerationX = (float) (Math.sin(Math.toRadians(angle))*ACCELERATION);
                accelerationY = (float) (Math.cos(Math.toRadians(angle))*ACCELERATION);
                break;
            case 2:
                accelerationX = (float) (Math.sin(Math.toRadians(angle))*ACCELERATIONBOOST);
                accelerationY = (float) (Math.cos(Math.toRadians(angle))*ACCELERATIONBOOST);
                break;
            default:
                Log.e("Triangle moa", "Triangle moa valeur de accelerer impossible. -1 reculer, 0 rien, 1 accelerer, 2 boost");
        }


        // 3. vitesse
        vitesseXf = (float) (vitesseXi + differenceTime * accelerationX);
        vitesseYf = (float) (vitesseYi + differenceTime * accelerationY);

        // Corriger trajectoire enlever drift
        while(angle<-180){angle+=360;}
        while(angle>180){angle-=360;}
        float direction = (float) (Math.toDegrees(Math.atan2(vitesseXf,vitesseYf)));
        float difference = direction - angle;

        float vitesse = (float) Math.sqrt(Math.pow(vitesseXf,2) + Math.pow(vitesseYf,2));
        vitesse*=Math.cos(Math.toRadians(difference));
        vitesseXf = (float) Math.sin(Math.toRadians(angle))*vitesse;
        vitesseYf = (float) Math.cos(Math.toRadians(angle))*vitesse;


        // 4. Balle
        float positionBalleX = balle.getPositionX();
        float positionBalleY = balle.getPositionY();
        float distanceBalleJoueur = (float) Math.sqrt(Math.pow(positionBalleX-positionX, 2) + Math.pow(positionBalleY-positionY,2));
        if(distanceBalleJoueur<1){
            Log.i("Triangle moa", "Triangle moa balle touche");
            float vitesseBalleX = balle.getVitesseX();
            float vitesseBalleY = balle.getVitesseY();
            float directionDepart = (float) Math.atan2(positionBalleX-positionX,positionBalleY-positionY);
            balle.addVitesseX((float) Math.sin(directionDepart));
            balle.addVitesseY((float) Math.cos(directionDepart));
        }

        // 5. Deplacement
        float deplacementX = (float) ((vitesseXf+vitesseXi)*differenceTime/2.0f);
        float deplacementY = (float) ((vitesseYf+vitesseYi)*differenceTime/2.0f);

        Log.i("Triangle moa", "Triangle moa v deplacement " + deplacementX + ";" + deplacementY);

       positionX+=deplacementX;
       positionY+=deplacementY;

        // 6. Limite position, vitesse
        // vitesse X max
        if(vitesseXf>VITESSEMAXIMAL){
            vitesseXf=VITESSEMAXIMAL;
        }else if(vitesseXf<-VITESSEMAXIMAL){
            vitesseXf=-VITESSEMAXIMAL;
        }
        // vitesse Y
        if(vitesseYf>VITESSEMAXIMAL){
            vitesseYf=VITESSEMAXIMAL;
        }else if(vitesseYf<-VITESSEMAXIMAL){
            vitesseYf=-VITESSEMAXIMAL;
        }

        //vitesse min
        if(Math.pow(vitesseXf,2) + Math.pow(vitesseYf,2)<VITESSEMINIMALAUCARRE){
            vitesseXf=0;
            vitesseYf=0;
        }

        //positionX
        if(positionX>BORDDEMAPX){
            positionX=BORDDEMAPX;
            vitesseXf*=-COEFFICIENTREBOND;
        }else if(positionX<-BORDDEMAPX){
            positionX=-BORDDEMAPX;
            vitesseXf*=-COEFFICIENTREBOND;
        }
        //positionY
        if(positionY>BORDDEMAPY){
            positionY=BORDDEMAPY;
            vitesseYf*=-COEFFICIENTREBOND;
        }else if(positionY<-BORDDEMAPY){
            positionY=-BORDDEMAPY;
            vitesseYf=0;
        }

        // 7. Maj ancienne vitesse
        vitesseXi=vitesseXf;
        vitesseYi=vitesseYf;

    }

    void draw(float []mvpMatrix){
        triangle.draw(mvpMatrix);
    }

    void setColor(int r, int g, int b){
        triangle.setColor(r, g, b);
    }

    void setAccelerer(int a){accelerer=a; }

    void setPositionX(float x){
        positionX=x;
    }
    void setPositionY(float y){
        positionY=y;
    }

    public void setAngle(float a) {
        angleActuel=a;
    }
    public float getAngle(){
        return angle;
    }
}
