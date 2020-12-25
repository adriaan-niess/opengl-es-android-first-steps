package com.adriaan_niess.cube.util;

import static android.opengl.GLES30.*;

import android.content.Context;
import android.graphics.Shader;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class ShaderProgram {
    private static final String TAG = ShaderProgram.class.getName();
    private int program;

    private static String fileToString(Context context, String path) {
        StringBuilder file = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(path)));
            String line;
            while((line = reader.readLine()) != null){
                file.append(line + "\n");
            }
            reader.close();
            return file.toString();
        } catch(IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to load file " + path);
            return null;
        }
    }

    private static int compileShader(Context context, int shaderType, String path) {
        // Load shader source
        String shaderSource = fileToString(context, path);

        // Compile shader
        int shader = glCreateShader(shaderType);
        glShaderSource(shader, shaderSource);
        glCompileShader(shader);

        // Check for compilation errors
        int[] compiled = new int[1];
        glGetShaderiv(shader, GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            Log.e(TAG, "Failed to compile shader " + path + ": ");
            Log.e(TAG, glGetShaderInfoLog(shader));
        }

        return shader;
    }

    private ShaderProgram() {
        program = glCreateProgram();
    }

    public void bind() {
        glUseProgram(program);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void setUniformMatrix4(String name, float[] value) {
        int location = glGetUniformLocation(program, name);
        FloatBuffer floatBuffer = BufferUtil.floatArrayToBuffer(value);
        glUniformMatrix4fv(location, 1, false, value, 0);
    }

    public void setUniform1i(String name, int value) {
        int location = glGetUniformLocation(program, name);
        glUniform1i(location, value);
    }

    public static class Builder {
        private ShaderProgram shaderProgram;
        private Context context;

        public Builder(Context context) {
            shaderProgram = new ShaderProgram();
            this.context = context;
        }

        public Builder attachShader(int shaderType, String path) {
            int shader = compileShader(context, shaderType, path);
            glAttachShader(shaderProgram.program, shader);
            return this;
        }

        public ShaderProgram linkProgram() {
            glLinkProgram(shaderProgram.program);

            // Check if linking failed
            int linkStatus[] = new int[1];
            glGetProgramiv(shaderProgram.program, GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GL_TRUE) {
                String log = glGetProgramInfoLog(shaderProgram.program);
                Log.e(TAG,"Failed to link shader program: ");
                Log.e(TAG, log);
            }

            return shaderProgram;
        }
    }
}
