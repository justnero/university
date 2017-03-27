package ru.justnero.study.s6.cg.e4.util.algorithm;


import java.util.Arrays;

public class FloodFiller {

    //    protected float[] screenBuffer;
    public static final int rgbNumber = 3;
    //    int screenBuffer[][], width, height;
    int width = 600, height = 600;
//    public GL2 gl;

    //    protected Color temp = new Color(1f, 1f, 1f);
    float tempCoordinate[] = new float[2];
    float tempColor[] = new float[3];
    float newColor[] = new float[3];
    private float[] screenBuffer;

    //    public void setScreenBuffer(float[] screenBuffer) {
//        this.screenBuffer = screenBuffer;
//    }
//
//
    protected void setColor(int x, int y, float[] color) {

        screenBuffer[(y * width + x) * rgbNumber] = color[0];
        screenBuffer[(y * width + x) * rgbNumber + 1] = color[1];
        screenBuffer[(y * width + x) * rgbNumber + 2] = color[2];
//        gl.glWindowPos2i(x, y);
//        gl.glDrawPixels(1, 1,
//                GL_RGB, GL.GL_FLOAT,
//                FloatBuffer.wrap(color));
    }


    protected float[] getColor(int x, int y) {
//        Color temp = new Color(
//                screenBuffer[(y * width + x) * rgbNumber + 0],
//                screenBuffer[(y * width + x) * rgbNumber + 1],
//                screenBuffer[(y * width + x) * rgbNumber + 2]
//        );

        tempColor[0] = screenBuffer[(y * width + x) * rgbNumber];
        tempColor[1] = screenBuffer[(y * width + x) * rgbNumber + 1];
        tempColor[2] = screenBuffer[(y * width + x) * rgbNumber + 2];

//        gl.glReadBuffer(GL_FRONT);
//        gl.glReadPixels(x, y, 1, 1, GL_RGB, GL_FLOAT, FloatBuffer.wrap(tempColor));
        return tempColor;
//        return temp;
    }

    public synchronized void floodFillScanline(int x, int y, float[] newColor) {
        if (Arrays.equals(getColor(x, y), newColor)) return;
        this.newColor = newColor;

        int xLeft = x, xRight = x + 1;

        while (xRight < width && !Arrays.equals(getColor(xRight, y), newColor)) {
            setColor(xRight, y, newColor);
            xRight++;
        }

        while (xLeft >= 0 && !Arrays.equals(getColor(xLeft, y), newColor)) {
            setColor(xLeft, y, newColor);
            xLeft--;
        }

        xRight--;
        xLeft++;

        if (!(xLeft > xRight)) {
            floodFillScanline((xRight + xLeft) / 2, y - 1, false, newColor);
            floodFillScanline((xRight + xLeft) / 2, y + 1, true, newColor);
        }
    }

    public void floodFillScanline(int x, int y, boolean up, float[] newColor) {
        if (Arrays.equals(getColor(x, y), newColor)) return;
        this.newColor = newColor;

        int xLeft = x, xRight = x + 1;

        while (xRight < width && !Arrays.equals(getColor(xRight, y), newColor)) {
            setColor(xRight, y, newColor);
            xRight++;
        }

        while (xLeft >= 0 && !Arrays.equals(getColor(xLeft, y), newColor)) {
            setColor(xLeft, y, newColor);
            xLeft--;
        }

        xRight--;
        xLeft++;

        if (!(xLeft > xRight)) {
            floodFillScanline((xRight + xLeft) / 2, y + (up ? 1 : -1), newColor);
        }
    }

    public float[] getScreenBuffer() {
        return screenBuffer;
    }

    public void setScreenBuffer(float[] screenBuffer) {
        this.screenBuffer = screenBuffer;
    }

//    void floodFillScanlineStack(int x, int y, int newColor, int oldColor)
//    {
//        if(oldColor == newColor) return;
//        emptyStack();
//
//        int x1;
//        bool spanAbove, spanBelow;
//
//        if(!push(x, y)) return;
//
//        while(pop(x, y))
//        {
//            x1 = x;
//            while(x1 >= 0 && screenBuffer[y][x1] == oldColor) x1--;
//            x1++;
//            spanAbove = spanBelow = 0;
//            while(x1 < width && screenBuffer[y][x1] == oldColor )
//            {
//                screenBuffer[y][x1] = newColor;
//                if(!spanAbove && y > 0 && screenBuffer[y - 1][x1] == oldColor)
//                {
//                    if(!push(x1, y - 1)) return;
//                    spanAbove = 1;
//                }
//                else if(spanAbove && y > 0 && screenBuffer[y - 1][x1] != oldColor)
//                {
//                    spanAbove = 0;
//                }
//                if(!spanBelow && y < height - 1 && screenBuffer[y + 1][x1] == oldColor)
//                {
//                    if(!push(x1, y + 1)) return;
//                    spanBelow = 1;
//                }
//                else if(spanBelow && y < height - 1 && screenBuffer[y + 1][x1] != oldColor)
//                {
//                    spanBelow = 0;
//                }
//                x1++;
//            }
//        }
//    }
}