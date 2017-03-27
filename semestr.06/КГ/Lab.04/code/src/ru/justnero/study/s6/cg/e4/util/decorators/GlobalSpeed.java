package ru.justnero.study.s6.cg.e4.util.decorators;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import java.util.ArrayList;
import java.util.List;

import ru.justnero.study.s6.cg.e2.main.Matrix3f;
import ru.justnero.study.s6.cg.e4.main.Lab4Scene;
import ru.justnero.study.s6.cg.e4.util.Complex;
import ru.justnero.study.s6.cg.e4.util.Vector2D;
import ru.justnero.study.s6.cg.e4.util.algorithm.FloodFiller;

public class GlobalSpeed<T extends Complex<T>>
        extends TransformByVector<T> {

    protected final static int screenSize = 600;
    protected static FloodFiller floodFiller = new FloodFiller();
    protected static float[] screenBuffer = new float[screenSize * screenSize * 3];
    public final Lab4Scene.GlobalParameters globalParameters;
    List<ru.justnero.study.s6.cg.e2.main.figures.Vertex3f> list = new ArrayList<>();

    public GlobalSpeed(TransformByVector<T> figure, Lab4Scene.GlobalParameters globalParameters) {
        this((T) figure, figure.getVelocity(), globalParameters);
    }

    public GlobalSpeed(T figure, Vector2D vector2D, Lab4Scene.GlobalParameters globalParameters) {
        super(figure, vector2D);
        this.globalParameters = globalParameters;
    }

    @Override
    public void transform(Matrix3f translateMatrix, T result) {
        if (globalParameters.value == 0 || !globalParameters.animation) {
            return;
        }

        velocity.multiply(globalParameters.value);

        super.transform(translateMatrix, result);

        velocity.multiply(1f / globalParameters.value);
    }

    @Override
    public void display(GLAutoDrawable drawable, float coordinateSize, boolean fill) {
        final GL2 gl = drawable.getGL().getGL2();
        super.display(drawable, coordinateSize, fill);
    }
}
