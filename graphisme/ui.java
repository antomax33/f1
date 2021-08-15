package com.example.f1.graphisme;

import com.example.f1.MyGLSurfaceView;

public class ui {
    Square btns[];

    private final static float DECALAGE = 0.02f;
    private final static float MINHEIGHT = DECALAGE;
    private final static float MAXHEIGHT = 0.3f;
    public ui(){
        float placeBtn[] = {0,MyGLSurfaceView.SEPARTATIONBOUTTONA, MyGLSurfaceView.SEPARTATIONBOUTTONB, MyGLSurfaceView.SEPARTATIONBOUTTONC, MyGLSurfaceView.SEPARTATIONBOUTTOND, MyGLSurfaceView.SEPARTATIONBOUTTONE, 1.0f};
        btns = new Square[6];

        for(int i=0;i < placeBtn.length-1; i++){
            btns[i] = new Square(placeBtn[i]+DECALAGE, MAXHEIGHT, placeBtn[i]+DECALAGE, MINHEIGHT, placeBtn[i+1]-DECALAGE, MINHEIGHT, placeBtn[i+1]-DECALAGE, MAXHEIGHT,  200, 200, 200);
        }

    }


    public void onDraw(float[] mvpMatrix){
        float matrix[] =   {2,0,0,0,
                            0,1,0,0,
                            0,0,1,0,
                            -1,-0.8f,0,1};
        for(int i=0;i < btns.length;i++){
            btns[i].draw(matrix);
        }
    }
}
