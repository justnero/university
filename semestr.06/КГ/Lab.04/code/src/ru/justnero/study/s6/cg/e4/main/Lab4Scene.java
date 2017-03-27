package ru.justnero.study.s6.cg.e4.main;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import java.awt.*;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import ru.justnero.study.s6.cg.e2.main.Matrix3f;
import ru.justnero.study.s6.cg.e2.util.Scene;
import ru.justnero.study.s6.cg.e4.util.Complex;
import ru.justnero.study.s6.cg.e4.util.Vector2D;
import ru.justnero.study.s6.cg.e4.util.algorithm.FloodFiller;
import ru.justnero.study.s6.cg.e4.util.decorators.AutoApplyTransform;
import ru.justnero.study.s6.cg.e4.util.decorators.FigureDecorator;
import ru.justnero.study.s6.cg.e4.util.decorators.GlobalSpeed;
import ru.justnero.study.s6.cg.e4.util.decorators.InBoundMovement;

import static com.jogamp.opengl.GL.GL_FLOAT;
import static com.jogamp.opengl.GL.GL_FRONT;
import static com.jogamp.opengl.GL.GL_RGB;
import static com.jogamp.opengl.GL.GL_UNPACK_ALIGNMENT;
import static com.jogamp.opengl.GL2ES2.GL_UNPACK_SKIP_PIXELS;
import static com.jogamp.opengl.GL2ES2.GL_UNPACK_SKIP_ROWS;

public class Lab4Scene extends Scene {
    protected final static int screenSize = 600;
    protected static FloodFiller floodFiller = new FloodFiller();
    protected static float[] screenBuffer = new float[screenSize * screenSize * 3];
    protected List<FigureDecorator> allFigure = new ArrayList<>();
    protected List<Labyrinth> labyrinths = new ArrayList<>();
    protected List<ClipBox> clipBosex = new ArrayList<>();
    protected Matrix3f transformMatrix = new Matrix3f();
    protected Quad tempResult = new Quad();
    protected Vertex3f tempVertexResult = new Vertex3f();
    protected GlobalParameters globalParameters = new GlobalParameters();
    protected Predicate<Float> inBound = value -> (value > -1 && value < 1);
    float color[] = {0f, 0f, 0f};
    FloatBuffer buffer = FloatBuffer.allocate(4);
    List<ru.justnero.study.s6.cg.e2.main.figures.Vertex3f> list = new ArrayList<>();
    private float coordinateSize;

    public Lab4Scene() {
        transformMatrix.setIdentity();
    }

    public void setCoordinateSize(float coordinateSize) {
        this.coordinateSize = coordinateSize;
    }

    public void clear() {
        allFigure.clear();
        labyrinths.clear();
        clipBosex.clear();
    }

    public void setAnimation(boolean animation) {
        globalParameters.animation = animation;
    }

    public void addClipBox(ClipBox quad) {
        quad.updateBounds();
        quad.setColor(Color.BLUE);
        clipBosex.add(quad);
    }

    public void addLabyrinth(Labyrinth quad) {
        quad.updateBounds();
        quad.setColor(Color.RED);
        labyrinths.add(quad);
    }

    public void addQuad(Quad quad, Vector2D directionAndSpeed) {
        FigureDecorator instance =
                new GlobalSpeed(
                        new InBoundMovement(
                                new AutoApplyTransform(quad),
                                directionAndSpeed,
                                inBound,
                                inBound
                        ),
                        globalParameters
                );

        quad.setResult(tempResult);
        allFigure.add(instance);
    }

    public GlobalParameters getGlobalParameters() {
        return globalParameters;
    }

    private void update() {
        labyrinths.forEach(labyrinth -> allFigure.removeIf(labyrinth::hit));

        allFigure.forEach(figure -> figure.transform(transformMatrix));
    }

    @Override
    public synchronized void display(GLAutoDrawable drawable) {
        update();
        final GL2 gl = drawable.getGL().getGL2();
        allFigure
                .forEach(quad -> quad.display(drawable, coordinateSize, globalParameters.fill));

//        if (globalParameters.fill) {
//            allFigure
//                    .forEach(figure -> fill(gl, figure));
//        }

        clipBosex.stream()
                .peek(clipBox -> clipBox.display(drawable, coordinateSize, false))
                .forEach(clipBox -> clipBox.display(drawable, coordinateSize,
                        allFigure));

        labyrinths
                .forEach(labyrinth -> labyrinth.display(drawable, coordinateSize, false));
    }

    private void fill(GL2 gl, Complex figure) {
        gl.glReadBuffer(GL_FRONT);
        gl.glReadPixels(0, 0, screenSize, screenSize, GL_RGB, GL_FLOAT, FloatBuffer.wrap(screenBuffer));
        floodFiller.setScreenBuffer(screenBuffer);

        list.clear();
        figure.getVertexes(list);

        floodFiller.floodFillScanline(
                (int) ((list.get(0).x + 1) * screenSize / 2 + 10),
                (int) ((list.get(0).y + 1) * screenSize / 2),
                figure.getColor().getRGBColorComponents(null));

        gl.glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        gl.glPixelStorei(GL_UNPACK_SKIP_PIXELS, 0);
        gl.glPixelStorei(GL_UNPACK_SKIP_ROWS, 0);

        gl.glDrawPixels(screenSize, screenSize,
                GL_RGB, GL_FLOAT,
                FloatBuffer.wrap(screenBuffer));
    }

    public static class GlobalParameters {

        public float value = 1 / 100f;
        public boolean fill = false;
        public boolean animation = true;
    }
}
