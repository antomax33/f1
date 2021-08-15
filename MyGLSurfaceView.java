package com.example.f1;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;

import androidx.constraintlayout.widget.ConstraintSet;

public class MyGLSurfaceView extends GLSurfaceView {
    public final float SENSIBILITE = 16f;

    public final static float SEPARTATIONBOUTTONA = 0.16f;
    public final static float SEPARTATIONBOUTTONB = 0.32f;
    public final static float SEPARTATIONBOUTTONC = 0.48f;
    public final static float SEPARTATIONBOUTTOND = 0.70f;
    public final static float SEPARTATIONBOUTTONE = 0.85f;
    private final MyGLRenderer renderer;
    private Game game;

    private int width;
    private int height;

    //sensor
    SensorManager sm;
    Sensor sensor;


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

        sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //setRequestedOrientation "Landsacpe"

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
            if(touchX<SEPARTATIONBOUTTONA){
                // btn couleur 1
            }else if(touchX<SEPARTATIONBOUTTONB){
                //btn couleur 2
            }else if(touchX<SEPARTATIONBOUTTONC){
                //btn couleur 3
            }else if(touchX<SEPARTATIONBOUTTOND){
                //btn gauche reculer
                game.setPersonnageAcceleration(-1);
            }else if(touchX<SEPARTATIONBOUTTONE){
                //btn milieu avancer
                game.setPersonnageAcceleration(1);
            }else{
                //btn droite accelerer
                game.setPersonnageAcceleration(2);
            }
        }else if(e.getAction()==MotionEvent.ACTION_UP){
            Log.i("Triangle moa", "Triangle moa action up");
            //relacher gas
            game.setPersonnageAcceleration(0);
        }

       if(touchX<SEPARTATIONBOUTTONC){
           //juste click, pas besoin de action down
           return false;
       }else {
           //pas juste click, besoin de action down
           return true;
       }
        //return false;
    }


    @Override
    public void onResume(){
        super.onResume();
        sm.registerListener(gyroListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause(){
        super.onPause();
        sm.unregisterListener(gyroListener);
    }

    public SensorEventListener gyroListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            //float x = event.values[0]*SENSIBILITE;
            float y = event.values[1]*SENSIBILITE;
            //float z = -event.values[2]*SENSIBILITE;

            //Log.i("Triangle moa", "Triangle moa sensor (" + x + ";" + y + ";" + z + ")");
            game = renderer.getGame();
            if(game!=null){
                game.setPersonnageRotation(y);

            }
        }

    };

}
