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
    public final float separtationBoutton = 0.4f;
    public final float separtationBouttonGD = 0.8f;
    private final MyGLRenderer renderer;
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float previousX;
    private float previousY;
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
        float touchX = (float)(e.getX()/width);
        float touchY = (float)(e.getY()/height);
        if(e.getAction()==MotionEvent.ACTION_DOWN){
            Log.i("Triangle moa", "Triangle moa on TouchEvent down (" + touchX + ";" + touchY);
            game = renderer.getGame();
            //game.buttonTouch(touchX, touchY);
            if(touchX<separtationBouttonGD){
                game.avancerCommencer();
            }else{
                game.reculerCommencer();
            }
        }else if(e.getAction()==MotionEvent.ACTION_UP){
            Log.i("Triangle moa", "Triangle moa action up");
            game.arreterDeplacement();
        }else{
            game.continuerDeplacement();
        }

       if(touchX<separtationBoutton){
           //juste click, pas besoin de action down
           return false;
       }else {
           //pas juste click, besoin de action down
           return true;
       }
    }



}
