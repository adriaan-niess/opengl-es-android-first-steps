package com.adriaan_niess.cube.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtil {
    public static FloatBuffer floatArrayToBuffer(float[] data) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(data.length * Float.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(data);
        floatBuffer.flip();
        return floatBuffer;
    }

    public static IntBuffer intArrayToBuffer(int[] data) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(data.length * Integer.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(data);
        intBuffer.flip();
        return intBuffer;
    }
}
