package com.adriaan_niess.cube.util;

import android.graphics.Bitmap;

public class BitmapUtil {
    public static Bitmap flipY(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap flipped = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                flipped.setPixel(x, y, bitmap.getPixel(x, height - y - 1));
            }
        }
        return flipped;
    }
}
