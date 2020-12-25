package com.adriaan_niess.cube.util;

import static android.opengl.GLES30.*;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import java.io.IOException;

public class Texture2d {
    private static final String TAG = Texture2d.class.getName();
    private int texture;
    private int target;

    public Texture2d(Bitmap bitmap) {
        // Gen texture
        int[] textures = new int[1];
        glGenTextures(1, textures, 0);
        texture = textures[0];

        glBindTexture(GL_TEXTURE_2D, texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, GL_RGBA, bitmap, 0);
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public static Texture2d create(Context context, String path) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getAssets().open(path));
            bitmap = BitmapUtil.flipY(bitmap);
        } catch(IOException e) {
            Log.e(TAG, "Failed to load image " + path);
        }
        return new Texture2d(bitmap);
    }
}
