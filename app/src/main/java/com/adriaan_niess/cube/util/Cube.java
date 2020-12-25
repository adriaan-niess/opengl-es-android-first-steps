package com.adriaan_niess.cube.util;

import android.content.Context;

import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_VERTEX_SHADER;

public class Cube extends Entity {
    private float rotX = 0f;
    private float rotY = 0f;

    public static Cube create(Context context) {
        // Create cube mesh
        ShaderProgram cubeShader = new ShaderProgram.Builder(context)
                .attachShader(GL_VERTEX_SHADER, "shaders/cube.vert")
                .attachShader(GL_FRAGMENT_SHADER, "shaders/cube.frag")
                .linkProgram();
        float[] vertices = {
                // Back
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                // Front
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                // Bottom
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                // Top
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                -0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                // Left
                -0.5f, -0.5f, -0.5f,
                -0.5f, 0.5f, -0.5f,
                -0.5f, -0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,
                // Right
                0.5f, -0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, 0.5f, 0.5f
        };
        float[] colors = {
                // Back
                0f, 0f, 1f,
                0f, 0f, 1f,
                0f, 0f, 1f,
                0f, 0f, 1f,
                // Front
                0f, 1f, 0f,
                0f, 1f, 0f,
                0f, 1f, 0f,
                0f, 1f, 0f,
                // Bottom
                0f, 1f, 1f,
                0f, 1f, 1f,
                0f, 1f, 1f,
                0f, 1f, 1f,
                // Top
                1f, 0f, 0f,
                1f, 0f, 0f,
                1f, 0f, 0f,
                1f, 0f, 0f,
                // Left
                1f, 0f, 1f,
                1f, 0f, 1f,
                1f, 0f, 1f,
                1f, 0f, 1f,
                // Right
                1f, 1f, 0f,
                1f, 1f, 0f,
                1f, 1f, 0f,
                1f, 1f, 0f
        };
        float[] normals = {
                // Back
                0f, 0f, -1f,
                0f, 0f, -1f,
                0f, 0f, -1f,
                0f, 0f, -1f,
                // Front
                0f, 0f, 1f,
                0f, 0f, 1f,
                0f, 0f, 1f,
                0f, 0f, 1f,
                // Bottom
                0f, -1f, 0f,
                0f, -1f, 0f,
                0f, -1f, 0f,
                0f, -1f, 0f,
                // Top
                0f, 1f, 0f,
                0f, 1f, 0f,
                0f, 1f, 0f,
                0f, 1f, 0f,
                // Left
                -1f, 0f, 0f,
                -1f, 0f, 0f,
                -1f, 0f, 0f,
                -1f, 0f, 0f,
                // Right
                1f, 0f, 0f,
                1f, 0f, 0f,
                1f, 0f, 0f,
                1f, 0f, 0f
        };
        float[] texCoords = {
                // Back
                0.75f, 0.75f,
                1f, 0.75f,
                0.75f, 0.5f,
                1f, 0.5f,
                // Front
                0.25f, 0.75f,
                0.5f, 0.75f,
                0.25f, 0.5f,
                0.5f, 0.5f,
                // Bottom
                0.25f, 0.5f,
                0.5f, 0.5f,
                0.25f, 0.25f,
                0.5f, 0.25f,
                // Top
                0.25f, 1f,
                0.5f, 1f,
                0.25f, 0.75f,
                0.5f, 0.75f,
                // Left
                0f, 0.75f,
                0f, 0.5f,
                0.25f, 0.75f,
                0.25f, 0.5f,
                // Right
                0.5f, 0.75f,
                0.5f, 0.5f,
                0.75f, 0.75f,
                0.75f, 0.5f,
        };
        int[] cubeIndices = {
                // Back
                0, 1, 2, 1, 2, 3,
                // Front
                4, 5, 6, 5, 6, 7,
                // Bottom
                8, 9, 10, 9, 10, 11,
                // Top
                12, 13, 14, 13, 14, 15,
                // Left
                16, 17, 18, 17, 18, 19,
                // Right
                20, 21, 22, 21, 22, 23
        };
        Mesh cubeMesh = new Mesh();
        cubeMesh.bind();
        cubeMesh.storeDataInArrayBuffer(0, vertices, 3);
        cubeMesh.storeDataInArrayBuffer(1, colors, 3);
        cubeMesh.storeDataInArrayBuffer(2, texCoords, 2);
        cubeMesh.storeDataInArrayBuffer(3, normals, 3);
        cubeMesh.storeDataInIndexBuffer(cubeIndices);
        cubeMesh.unbind();

        // Create texture
        Texture2d texture = Texture2d.create(context, "textures/cube.png");

        return new Cube(cubeMesh, cubeShader, texture);
    }

    private Cube(Mesh mesh, ShaderProgram shader, Texture2d texture) {
        super(mesh, shader, texture);
    }

    @Override
    public void update(float delta) {
        rotX += delta * 0.2 * 360f;
        rotY += delta * 0.3 * 360f;
        setRotation(rotX, rotY, 0f);
    }
}
