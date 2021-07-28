package com.example.f1;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.glGetError;

public class Triangle {

    private int positionHandle;
    private int colorHandle;

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStrdie = COORDS_PER_VERTEX * 4;
    private final int mProgram;
    private int vPMatrixHandle;

    private final String vertexShaderCode =
            //This matrix member variable provides a hook to manipulate
            //the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "void main(){" +
            //The matrix must be included as a modifier of gl_position
            //note that the uMVPMatrix factor *must be first* in order
            //for the matrix multiplication product to be correct.
            "   gl_Position = uMVPMatrix * vPosition;" +
            "}";



    private final String fragmentShaderCode =
            "precision mediump float;"+
            "uniform vec4 vColor;"+
            "void main() {"+
            "   gl_FragColor = vColor;"+
            "}";


    private FloatBuffer vertexBuffer;

    //number of coordinate per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] = {
            0.0f, 0.62f, 0.0f,//top
            -0.5f, -0.3f, 0.0f,//bottom left
            0.5f, -0.3f, 0.0f//bottom right
    };

    //set color with red green blue and alpha values
    float color[] = {0.64f, 0.76f, 0.23f, 0.8f};

    public Triangle() {
        //initialize vertex byte buffer for shape coordinate
        ByteBuffer bb = ByteBuffer.allocateDirect(
                //number of coordinate values * 4 bytes per float
                triangleCoords.length*4);
        //use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        //create a floating point buffer from byteBuffer
        vertexBuffer = bb.asFloatBuffer();
        //add the coordinates to the floatBuffer
        vertexBuffer.put(triangleCoords);
        //set the buffer to read the first coordinate
        vertexBuffer.position(0);


        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        Log.i("Triangle moa", "Triangle moa vertexShader "+vertexShader);
        Log.i("Triangle moa", "Triangle moa fragmentShader "+fragmentShader);

        //create empty OpenGL ES program
        mProgram = GLES20.glCreateProgram();
        //add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);
        //add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);
        //creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);


    }

    public void draw(float[] mvpMatrix){

        GLES20.glUseProgram(mProgram);//GL_INVALID_OPERATION

        //get handle to vertex shader's vPosition member
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");


        //Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(positionHandle);

        //prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                                        GLES20.GL_FLOAT, false,
                                        vertexStrdie, vertexBuffer);


        //...........................................................................

        //get handle to fragment shader's vColor member
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        //set color for drawing the triangle
        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        //get handle to shape's transformation matrix
        vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        //pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0);

        //draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        //Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);
    }

}
