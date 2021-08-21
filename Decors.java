package com.example.f1;

import static android.app.PendingIntent.getActivity;

import android.content.res.Resources;
import android.util.Log;

import com.example.f1.graphisme.Square;

public class Decors {
    int grey[]={0xff888888,0xffaaaaaa};
    private final static int LONGUEUR = 5;
    private final static int LARGEUR = 8;
    private final static int AIR = LARGEUR*LONGUEUR;

    private final static int TAILLECARRE = 3;
    Square sol[];
    public Decors(){
        sol = new Square[AIR];
        for(int i=0;i<LONGUEUR;i++){
            for(int j=0;j<LARGEUR;j++){
                Log.i("Triangle moa", "Triangle moa j+lar*i = " + (j+LARGEUR*i));
               sol[j+LARGEUR*i] = new Square((float)(i*TAILLECARRE-TAILLECARRE*LONGUEUR/2.0f),(float)(j*TAILLECARRE-TAILLECARRE*LARGEUR/2.0f), TAILLECARRE, TAILLECARRE,(grey[(i+j)%2]>>16) & 0xff, (grey[(i+j)%2]>>8)& 0xff, (grey[(i+j)%2])& 0xff);
                Log.i("Triangle moa", "Triangle moa j+lar*i 2 = " + (j+LARGEUR*i));
            }
        }
        //sol = new Square(0, 0, 2, 2, (grey>>16) & 0xff, (grey>>8)& 0xff, (grey)& 0xff);
        //(grey[(i+j)%2]>>16) & 0xff, (grey[(i+j)%2]>>8)& 0xff, (grey[(i+j)%2])& 0xff);
    }

    void draw(float[] mvpMatrix){
        for(int i=0;i<AIR;i++){
            sol[i].draw(mvpMatrix);
        }
    }
}