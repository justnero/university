package ru.justnero.study.s6.cg.e5.main;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import ru.justnero.study.s6.cg.e2.main.figures.Axis;
import ru.justnero.study.s6.cg.e2.util.Scene;

public class Lab5Scene extends Scene {
    protected final static float baseDx = 0.05f;
    protected final static float baseDAngle = 0.02f;
    protected float coordinateSize = 11;
    protected Axis axis = new Axis();
    protected Function function1 = new Function(1, 3, -4);
    protected AngleFunction function2 = new AngleFunction(3);
    protected float speed = 1f;
    protected boolean animation = false;
    protected boolean firstFunctionSelected = true;

    public Lab5Scene() {
        function1.setxBegin(-(coordinateSize-1));
        function1.setxEnd(coordinateSize-1);
        function1.setDx(baseDx * speed);

        function2.setBeginT(-(coordinateSize-1));
        function2.setEndT(coordinateSize);
        function2.setDt(baseDAngle);
    }

    public Function getFunction1() {
        return function1;
    }

    public AngleFunction getFunction2() {
        return function2;
    }

    public void setCoordinateSize(float coordinateSize) {
        this.coordinateSize = coordinateSize;
//        function1.setxBegin(-coordinateSize);
//        function1.setxEnd(coordinateSize);
//        function2.setBeginT(-coordinateSize);
//        function2.setEndT(coordinateSize);
    }

    public void setFirstFunctionSelected(boolean firstFunctionSelected) {
        this.firstFunctionSelected = firstFunctionSelected;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
        function1.setDx(baseDx * speed);
        function2.setDt(baseDAngle * speed);
    }

    public void setAnimation(boolean animation) {
        this.animation = animation;

        if (animation) {
            if (firstFunctionSelected) {
                if (!function1.hasNext()) {
                    function1.reset();
                }
            } else {
                if (!function2.hasNext()) {
                    function2.reset();
                }
            }
        }
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        if (animation) {
            if (firstFunctionSelected) {
                function1.nextFrame();
            } else {
                function2.nextFrame();
            }
        }

        if (firstFunctionSelected) {
            function1.display(drawable, coordinateSize, false);
        } else {
            function2.display(drawable, coordinateSize, false);
        }

        axis.setCoordinateSize(coordinateSize);
        axis.display(drawable);
    }

    public void reset() {
        animation = false;
        if (firstFunctionSelected) {
            function1.reset();
        } else {
            function2.reset();
        }
    }


    public void setFunctionLimits(float value) {
        function1.setxBegin(-value);
        function1.setxEnd(value);

        function2.setBeginT(-value);
        function2.setEndT(value);
    }
}
