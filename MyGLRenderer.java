package com.example.f1;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.glGetError;
import static android.opengl.GLES20.glGetShaderiv;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    /*private Triangle vehicule;
    private Square sol, sol2;*/
    private long oldTime=0;
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private Game game;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //Set the background frame color
        GLES20.glClearColor(119f/255f, 181f/255f, 254f/255, 1.0f);

        game = new Game();


    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0,0, width, height);

        float ratio = (float) width/height;

        //this projection matrix is applied to object coordinates
        //in th onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -2, 1, 1, 12);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        float[] mBase = new float[16];


        //Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        //Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0,-1, 3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        Matrix.multiplyMM(mBase, 0, projectionMatrix, 0, viewMatrix, 0);


        game.draw(mBase);
    }


    public static int loadShader(int type, String shaderCode){
        //create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        //or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        int[] compiled = new int[1];
        GLES20.glGetShaderiv(shader, GL_COMPILE_STATUS, compiled, 0);

        if(compiled[0]!=1){
            Log.e("Triangle moa", "Triangle moa compilation shader error");
        }else{
            Log.i("Triangle moa", "Triangle moa compilation shader sucessful");
        }

        return shader;
    }

    public Game getGame(){
        return game;
    }
}


