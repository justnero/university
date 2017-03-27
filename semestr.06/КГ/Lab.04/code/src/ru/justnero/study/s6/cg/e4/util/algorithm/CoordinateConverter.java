package ru.justnero.study.s6.cg.e4.util.algorithm;

public final class CoordinateConverter {
    private CoordinateConverter() {
    }

    public static void fromScreenToReal(int x, int y, float[] result) {
        fromScreenToReal(x, y, 600, result);
    }

    public static void fromScreenToReal(int x, int y, int screenSize, float[] result) {
        fromScreenToReal(x, y, screenSize, screenSize, false, result);
    }

    public static void fromScreenToReal(int x, int y, int width, int height, boolean yDown, float[] result) {
        float xTemp, yTemp;

        xTemp = 2 * x / (float) width;
        if (yDown) {
            yTemp = 2 * (1 - y / (float) height);
        } else {
            yTemp = 2 * y / (float) height;
        }

        xTemp -= 1;
        yTemp -= 1;

        result[0] = xTemp;
        result[1] = yTemp;
    }
}
