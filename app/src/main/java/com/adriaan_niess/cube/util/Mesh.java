package com.adriaan_niess.cube.util;

import static android.opengl.GLES30.*;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh {
    public static int MAX_ATTRIBUTES = 16;

    private int vao;        // Vertex array
    private int[] vbos;     // Maps attribute ID to VBO ID
    private int ibo;        // Index buffer
    private int iboLength;  // Number of indices stored in IBO

    public Mesh() {
        int vaos[] = new int[1];
        glGenVertexArrays(1, vaos, 0);
        vao = vaos[0];

        // Initialize VBO IDs
        vbos = new int[MAX_ATTRIBUTES];
        for (int i = 0; i < vbos.length; i++) {
            vbos[i] = 0;
        }

        iboLength = 0;
    }

    public void bind() {
        glBindVertexArray(vao);
    }

    public void unbind() {
        glBindVertexArray(0);
    }

    public void storeDataInArrayBuffer(int attribId, float[] data, int vertexDim) {
        if (attribId < 0 || attribId >= MAX_ATTRIBUTES) {
            throw new IllegalArgumentException("Invalid attribute ID");
        } else if (vbos[attribId] > 0) { // Delete previous buffer
            glDeleteBuffers(1, vbos, attribId);
        }

        glGenBuffers(1, vbos, attribId);
        glBindBuffer(GL_ARRAY_BUFFER, vbos[attribId]);

        FloatBuffer floatBuffer = BufferUtil.floatArrayToBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, floatBuffer.capacity() * Float.BYTES, floatBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(attribId, vertexDim, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(attribId);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void storeDataInIndexBuffer(int[] data) {
        int[] iboArray = new int[1];
        iboArray[0] = ibo;
        if (ibo > 0) {
            glDeleteBuffers(1, iboArray, 0);
        }

        glGenBuffers(1, iboArray, 0);
        ibo = iboArray[0];
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);

        IntBuffer intBuffer = BufferUtil.intArrayToBuffer(data);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, intBuffer.capacity() * Integer.BYTES, intBuffer, GL_STATIC_DRAW);
        iboLength = data.length;

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void draw() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glDrawElements(GL_TRIANGLES, iboLength, GL_UNSIGNED_INT, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }
}
