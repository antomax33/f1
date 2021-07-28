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

    private Triangle mTriangle;
    private final float[] vPMatrix = new float[16];
    private final float[] porjectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] rotationMatrix = new float[16];

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //Set the background frame color
        GLES20.glClearColor(119f/255f, 181f/255f, 254f/255, 1.0f);

        // initialize a triangle
        mTriangle = new Triangle();

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0,0, width, height);

        float ratio = (float) width/height;

        //this projection matrix is applied to object coordinates
        //in th onDrawFrame() method
        Matrix.frustumM(porjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        float[] scratch = new float[16];

        //Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        //Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0,0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        Matrix.multiplyMM(vPMatrix, 0, porjectionMatrix, 0, viewMatrix, 0);

        //Create a rotation transformation for the triangle
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int)time);
        Matrix.setRotateM(rotationMatrix, 0, angle, 0, 0, -1.0f);


        Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0);



        mTriangle.draw(scratch);//vPMatrix si marche pas



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

    public volatile float mAngle;

    public float getAngle(){
        return mAngle;
    }

    public void setAngle(float angle){
        mAngle=angle;
    }
}


