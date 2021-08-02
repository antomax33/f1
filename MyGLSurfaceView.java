package com.example.f1;

import android.content.Context;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;

import androidx.constraintlayout.widget.ConstraintSet;

public class MyGLSurfaceView extends GLSurfaceView {
    public final float separtationBouttonA = 0.16f;
    public final float separtationBouttonB = 0.32f;
    public final float separtationBouttonC = 0.48f;
    public final float separtationBouttonD = 0.70f;
    public final float separtationBouttonE = 0.85f;
    private final MyGLRenderer renderer;
    private Game game;

    private int width;
    private int height;



    public MyGLSurfaceView(Context context) {
        super(context);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width=size.x;
        height=size.y;
        Log.i("Triangle moa", "Triangle moa size ("+width+";"+height+")");

        //create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        renderer = new MyGLRenderer();

        //set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        //Log.i("Triangle moa", "Triangle moa on TouchEvent debut");
        float touchX = (float)(e.getX()/width);
        float touchY = (float)(e.getY()/height);
        game = renderer.getGame();//besoin de l'initialiser a chaque fois sinon crash
        if(e.getAction()==MotionEvent.ACTION_DOWN){
            Log.i("Triangle moa", "Triangle moa on TouchEvent down (" + touchX + ";" + touchY);
            //game.buttonTouch(touchX, touchY);
            if(touchX<separtationBouttonA){
                // btn couleur 1
                game.bouttonCouleur(1);
            }else if(touchX<separtationBouttonB){
                //btn couleur 2
                game.bouttonCouleur(2);
            }else if(touchX<separtationBouttonC){
                //btn couleur 3
                game.bouttonCouleur(3);
            }else if(touchX<separtationBouttonD){
                //espace
            }else if(touchX<separtationBouttonE){
                //btn gauche
                game.bouttonGauche();
            }else{
                //btn droite
                game.bouttonDroite();
            }
        }else if(e.getAction()==MotionEvent.ACTION_UP){
            Log.i("Triangle moa", "Triangle moa action up");
            game.bouttonLache();
        }

       if(touchX<separtationBouttonC){
           //juste click, pas besoin de action down
           return false;
       }else {
           //pas juste click, besoin de action down
           return true;
       }
        //return false;
    }



}
