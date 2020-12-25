package com.adriaan_niess.cube;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.AttributeSet;

import com.adriaan_niess.cube.util.Cube;
import com.adriaan_niess.cube.util.Entity;
import com.adriaan_niess.cube.util.Mesh;
import com.adriaan_niess.cube.util.ShaderProgram;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES30.*;

public class CubeView extends GLSurfaceView {
    Entity cube;
    float[] viewMx = new float[16];
    float[] projMx = new float[16];
    long timeLastFrame;

    public CubeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3); // OpenGL ES version 3.0
        setRenderer(new Renderer());
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    public class Renderer implements GLSurfaceView.Renderer {
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            glClearColor(0f, 0f, 0f, 1f);
            glEnable(GL_DEPTH_TEST);

            // Setup camera
            Matrix.setLookAtM(viewMx, 0, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 1.0f, 0.0f);

            cube = Cube.create(getContext());
            cube.setPosition(0f, 0f, 10f);
            cube.setScale(3f, 3f, 3f);
            cube.setRotation(0f, 45f, 0f);

            timeLastFrame = System.currentTimeMillis();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            glViewport(0, 0, width, height);

            float aspectRatio = (float)width / (float)height;
            Matrix.frustumM(projMx, 0, -aspectRatio, aspectRatio, -1f, 1f, 2f, 20f);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            long elapsedTimeMillis = timeLastFrame - System.currentTimeMillis();
            float delta = (float)elapsedTimeMillis / 1000f;
            timeLastFrame = System.currentTimeMillis();

            cube.update(delta);
            cube.draw(viewMx, projMx);
        }
    }
}
