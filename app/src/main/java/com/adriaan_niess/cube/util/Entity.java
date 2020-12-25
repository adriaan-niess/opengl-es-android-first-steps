package com.adriaan_niess.cube.util;

import static android.opengl.GLES30.*;

import android.opengl.Matrix;
import android.util.Log;

public class Entity {
    private Mesh mesh;
    private ShaderProgram shader;
    private Texture2d texture;
    private float[] scale = {1f, 1f, 1f};
    private float[] position = {0f, 0f, 0f};
    private float[] rotate = {0f, 0f, 0f};
    private float[] modelMx = new float[16];

    public Entity(Mesh mesh, ShaderProgram shader, Texture2d texture) {
        this.mesh = mesh;
        this.shader = shader;
        this.texture = texture;
        rebuildModelMx();
    }

    public void setScale(float x, float y, float z) {
        scale[0] = x; scale[1] = y; scale[2] = z;
        rebuildModelMx();
    }

    public void setRotation(float x, float y, float z) {
        rotate[0] = x; rotate[1] = y; rotate[2] = z;
        rebuildModelMx();
    }

    public void setPosition(float x, float y, float z) {
        position[0] = x; position[1] = y; position[2] = z;
        rebuildModelMx();
    }

    private void rebuildModelMx() {
        Matrix.setIdentityM(modelMx, 0);
        Matrix.translateM(modelMx, 0, position[0], position[1], position[2]);
        Matrix.rotateM(modelMx, 0, rotate[0], 1f, 0f, 0f);
        Matrix.rotateM(modelMx, 0, rotate[1], 0f, 1f, 0f);
        Matrix.rotateM(modelMx, 0, rotate[2], 0f, 0f, 1f);
        Matrix.scaleM(modelMx, 0, scale[0], scale[1], scale[2]);
    }

    private void setupShader(float[] viewMx, float[] projMx) {
        float[] modelViewMx = new float[16];
        float[] normalMx = new float[16];
        float[] mvpMx = new float[16];
        float[] tmpMx = new float[16];

        // Compute mvp- and normal matrix
        Matrix.multiplyMM(modelViewMx, 0,
                viewMx, 0,
                modelMx, 0);
        Matrix.multiplyMM(mvpMx, 0,
                projMx, 0,
                modelViewMx, 0);
        Matrix.invertM(tmpMx, 0,
                modelViewMx, 0);
        Matrix.transposeM(normalMx, 0,
                tmpMx, 0);

        shader.setUniformMatrix4("mvpMx", mvpMx);
        shader.setUniformMatrix4("normalMx", normalMx);
        shader.setUniform1i("tex", 0);
    }

    public void update(float delta) {
        // Empty method stub
    }

    public void draw(float[] viewMx, float[] projMx) {
        shader.bind();
        mesh.bind();
        if (texture != null) {
            glActiveTexture(GL_TEXTURE0);
            texture.bind();
        }

        setupShader(viewMx, projMx);
        mesh.draw();

        if (texture != null) {
            texture.unbind();
        }
        mesh.unbind();
        shader.unbind();
    }
}
