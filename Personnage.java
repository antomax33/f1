package com.example.f1;

import android.util.Log;

import com.example.f1.graphisme.Triangle;

public class Personnage {
    public final float ACCELERATION = 6f;
    public final float VITESSEFREINAGE = ACCELERATION*0.4f;
    public final float VITESSEMINIMAL = 0.02f;
    public final float VITESSEMAXIMAL= 10f;
    public final float ACCELERATIONGRAVITE = -9.81f;
    public final float DECALAGEDROITE = 1f;
    public final float DECALAGEGAUCHE = 0f;
    public final float DECALAGEHAUT = 1f;
    public final int BORDDEMAPX = 10;
    public final int BORDDEMAPY = 8;
    public final float COEFFICIENTREBOND = 0.2f;

    private Triangle triangle;
    private float positionX=0, positionY=0;
    private float vitesseXi=0, vitesseYi=0, vitesseXf=0, vitesseYf=0;

    private boolean accelerer=false;
    private boolean directionDroite=true;
    private long timeLastUpdate=0;
    private Game game;

    public Personnage(){
        triangle = new Triangle(DECALAGEGAUCHE, 0f, 0.5f, DECALAGEHAUT, DECALAGEDROITE, 0f, 128, 23, 37);
    }

    public void physique(boolean bCentre, boolean bBas, boolean bhaut, boolean bGauche, boolean bDroite){

        /*
        Description: calcul la position du personnage en fonction
        des forces appliqués dessus ou des blocks

        1. Difference temps

        2. Accélération

        3. vitesse

        4. Blocks

        5. deplacement

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
        timeLastUpdate=System.currentTimeMillis();


        // 2. Accélération
        float accelerationX=0f, accelerationY=0f;

        if(accelerer){
            if(directionDroite){
                accelerationX=ACCELERATION;
            }else{
                accelerationX=-ACCELERATION;
            }
        }

        //si pas bloc en dessous alors gravitation
        if(!bBas){
            accelerationY=ACCELERATIONGRAVITE;
        }

        // 3. vitesse
        vitesseXf = (float) (vitesseXi + differenceTime * accelerationX);
        vitesseYf = (float) (vitesseYi + differenceTime * accelerationY);

        // 4. Blocks
        int coincerBlock = 0;
        if(bCentre){
            coincerBlock = coincerBlock(new boolean[]{bhaut, bGauche, bBas, bDroite});
        }

        // 5. Deplacement
        float deplacementX = (float) ((vitesseXf+vitesseXi)*differenceTime/2);
        float deplacementY = (float) ((vitesseYf+vitesseYi)*differenceTime/2);

       if(coincerBlock!=0){
           switch (coincerBlock){
               case 1:
                   positionY = (float) Math.ceil(positionY);
                   deplacementY=0f;
                   if(vitesseYf<0){
                       vitesseYf*=-COEFFICIENTREBOND;
                   }
                   break;
               case 2:
                   positionX = (float) Math.floor(positionX);
                   deplacementX=0f;
                   if(vitesseXf>0) {
                       vitesseXf *= -COEFFICIENTREBOND;
                   }
                       break;
               case 3:
                   positionY = (float) Math.floor(positionY);
                   deplacementY=0f;
                   if(vitesseYf>0){
                       vitesseYf*=-COEFFICIENTREBOND;
                   }
                   break;
               case 4:
                   positionX = (float) Math.ceil(positionX);
                   deplacementX=0f;
                   if(vitesseXf<0){
                       vitesseXf*=-COEFFICIENTREBOND;
                   }
                   break;
           }
       }else{
           if(bBas){
               positionY = (float) Math.ceil(positionY);
               deplacementY=0;
               vitesseYf=0;
               /*if(vitesseYf<0){
                   vitesseYf*=-COEFFICIENTREBOND;
               }*/
           }
           if(bDroite){
               Log.i("Triangle moa", "Triangle moa rebond droite, position " + positionX);
               positionX = (float) Math.floor(positionX);
               //deplacementX=0f;
               if(vitesseXf>0){
                   vitesseXf*=-COEFFICIENTREBOND;
               }
               Log.i("Triangle moa", "Triangle moa rebond droite, vitesseXf " + vitesseXf + " vitesseXi " + vitesseXi);

           }
           /*if(bhaut){
               positionY = (float) Math.floor(positionY);
               deplacementY=0f;
               if(vitesseYf>0){
                   vitesseYf*=-COEFFICIENTREBOND;
               }
           }*/
           if(bGauche){
               Log.i("Triangle moa", "Triangle moa rebond gauche, position " + positionX);
               positionX = (float) Math.ceil(positionX);
               //deplacementX=0f;
               if(vitesseXf<0){
                   vitesseXf*=-COEFFICIENTREBOND;
               }
               Log.i("Triangle moa", "Triangle moa rebond gauche, vitesse " + vitesseXf);
           }
       }

       positionX+=deplacementX;
       positionY+=deplacementY;

        // 6. Limite position, vitesse
        // vitesse X
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




    int coincerBlock(boolean[] block){
        //block contient: true si il y a un block. 0,1,2,3 => haut, gauche, bas, droite

        //si personnage dans un block alors sortir ou il est le plus proche et ou il n'y a pas de block
        float decimalX = positionX - (int)(Math.floor(positionX));
        float decimalY = positionY - (int)(Math.floor(positionY));

        boolean gauche=true;
        boolean bas=true;
        if(decimalX>0.5f){
            gauche=false;
            decimalX=1-decimalX;
        }

        if(decimalY>0.5f){
            bas=false;
            decimalY=1-decimalY;
        }

        // DecimalX est la distance entre le bord le plus proche en x
        // Même chose pour decimalY

        int ordre[] = new int[4];
        boolean prioriteX = decimalX<decimalY;

        if(gauche && bas){
            //gauche et bas
            if(prioriteX){
                ordre[0]=2;
                ordre[1]=3;
            }else {
                ordre[0]=3;
                ordre[1]=2;
            }
        }else if(gauche){
            //gauche et haut
            if (prioriteX){
                ordre[0]=2;
                ordre[1]=1;
            }else {
                ordre[0]=1;
                ordre[1]=2;
            }
        }else if(bas){
            //droite et bas
            if(prioriteX){
                ordre[0]=4;
                ordre[1]=3;
            }else {
                ordre[0]=3;
                ordre[1]=4;
            }
        }else{
            //droite et haut
            if(prioriteX){
                ordre[0]=4;
                ordre[1]=1;
            }else {
                ordre[0]=1;
                ordre[1]=4;
            }
        }
        if(ordre[1]>2){
            ordre[2]=ordre[1]-2;
        }else{
            ordre[2]=ordre[1]+2;
        }
        if(ordre[2]>2){
            ordre[4]=ordre[2]-2;
        }else{
            ordre[4]=ordre[2]+2;
        }

        for(int i=0;i<4;i++){
            if(!block[ordre[i]-1]){
                return ordre[i];
            }
        }


        Log.e("Triangle moa", "Triangle moa erreur coincer block aucune reponse qui fonctionne");
        return 5;
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
