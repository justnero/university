package ru.justnero.study.s6.cg.e4.main;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ru.justnero.study.s6.cg.e2.main.figures.Vertex3f;
import ru.justnero.study.s6.cg.e4.util.Complex;

public class ClipBox extends Labyrinth {
    public static final int INSIDE = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 4;
    public static final int TOP = 8;

    public static final int COHEN_SUTHERLAND = 0;
    public static final int LIANG_BARSKY = 1;

    private CohenSutherland clipper = new CohenSutherland();

    public ClipBox() {
        super();
        setColor(Color.blue);
    }

    @Override
    protected void displayQuad(GL2 gl, boolean fill) {
        super.displayQuad(gl, fill);
    }

    @Override
    protected void floodFill(GL2 gl) {
        gl.glColor3fv(Color.white.getRGBColorComponents(null), 0);
        gl.glBegin(GL2.GL_QUADS);
        Arrays.stream(vertices).forEach(vertex3f -> gl.glVertex2f(vertex3f.x, vertex3f.y));
        gl.glEnd();
    }


    public void display(GLAutoDrawable drawable, float coordinateSize, List<? extends Complex> figures) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glEnable(GL.GL_LINE_WIDTH);
        gl.glLineWidth(3f);
        gl.glBegin(GL2.GL_LINES);
//        gl.glBegin(GL2.GL_LINES);
        gl.glColor3fv(getColor().getRGBColorComponents(null), 0);

        figures.stream()
                .map(clipper::clip)
                .forEach(vertex3fs -> vertex3fs.forEach(vertex3f -> gl.glVertex2f(vertex3f.x, vertex3f.y)));

        gl.glEnd();

        gl.glEnable(GL.GL_POINT_SIZE);
        gl.glPointSize(5f);
        gl.glBegin(GL2.GL_POINTS);

        figures.stream()
                .map(clipper::clip)
                .forEach(vertex3fs -> vertex3fs.forEach(vertex3f -> gl.glVertex2f(vertex3f.x, vertex3f.y)));

        gl.glEnd();
    }

    public interface LineClipper {
        public LineSegment clip(LineSegment clip);
    }

    private class LineSegment {
        public float x0;
        public float y0;
        public float x1;
        public float y1;

        public LineSegment(float x0, float y0, float x1, float y1) {
            this.x0 = x0;
            this.y0 = y0;
            this.x1 = x1;
            this.y1 = y1;
        }

        @Override
        public String toString() {
            return "LineSegment(x0: " + x0 + ", y0: " + y0 + "; x1: " + x1 + ", y1: " + y1 + ")";
        }
    }

    public class CohenSutherland implements LineClipper {

        /**
         * Computes OutCode for given point (x,y)
         *
         * @param x Horizontal coordinate
         * @param y Vertical coordinate
         * @return Computed OutCode
         */
        private int computeOutCode(double x, double y) {
            int code = INSIDE;

            if (x < xMin) {
                code |= LEFT;
            } else if (x > xMax) {
                code |= RIGHT;
            }
            if (y < yMin) {
                code |= BOTTOM;
            } else if (y > yMax) {
                code |= TOP;
            }

            return code;
        }

        public List<Vertex3f> clip(Complex figure) {
            List<LineSegment> segments = new ArrayList<>();
            List<Vertex3f> list = new ArrayList<>();
            figure.getVertexes(list);


            for (int i = 1; i < list.size() + 1; i++) {
                segments.add(clip(list.get(i - 1), list.get(i % list.size())));
            }


            return segments.stream()
                    .filter(Objects::nonNull)
                    .flatMap(segment -> Stream.of(
                            new Vertex3f(segment.x0, segment.y0),
                            new Vertex3f(segment.x1, segment.y1)))
                    .collect(Collectors.toList());
        }

        public LineSegment clip(ru.justnero.study.s6.cg.e2.main.figures.Vertex3f begin, ru.justnero.study.s6.cg.e2.main.figures.Vertex3f end) {
            return clip(new LineSegment(begin.x, begin.y, end.x, end.y));
        }

        /**
         * Execute line clipping using Cohen-Sutherland
         * Taken from: http://en.wikipedia.org/wiki/Cohen-Sutherland
         *
         * @param line LineSegment to work with
         * @return Clipped line
         */
        public LineSegment clip(LineSegment line) {
//            System.out.println("\nExecuting Cohen-Sutherland...");
            float x0 = line.x0, x1 = line.x1, y0 = line.y0, y1 = line.y1;
            int outCode0 = computeOutCode(x0, y0);
            int outCode1 = computeOutCode(x1, y1);
//            System.out.println("OutCode0: " + outCode0 + ", OutCode1: " + outCode1);
            boolean accept = false;

            while (true) {
                if ((outCode0 | outCode1) == 0) { // Bitwise OR is 0. Trivially accept
                    accept = true;
                    break;
                } else if ((outCode0 & outCode1) != 0) { // Bitwise AND is not 0. Trivially reject
                    break;
                } else {
                    float x, y;

                    // Pick at least one point outside rectangle
                    int outCodeOut = (outCode0 != 0) ? outCode0 : outCode1;

                    // Now find the intersection point;
                    // use formulas y = y0 + slope * (x - x0), x = x0 + (1 / slope) * (y - y0)
                    if ((outCodeOut & TOP) != 0) {
                        x = x0 + (x1 - x0) * (yMax - y0) / (y1 - y0);
                        y = yMax;
                    } else if ((outCodeOut & BOTTOM) != 0) {
                        x = x0 + (x1 - x0) * (yMin - y0) / (y1 - y0);
                        y = yMin;
                    } else if ((outCodeOut & RIGHT) != 0) {
                        y = y0 + (y1 - y0) * (xMax - x0) / (x1 - x0);
                        x = xMax;
                    } else {
                        y = y0 + (y1 - y0) * (xMin - x0) / (x1 - x0);
                        x = xMin;
                    }

                    // Now we move outside point to intersection point to clip
                    if (outCodeOut == outCode0) {
                        x0 = x;
                        y0 = y;
                        outCode0 = computeOutCode(x0, y0);
                    } else {
                        x1 = x;
                        y1 = y;
                        outCode1 = computeOutCode(x1, y1);
                    }
                }
            }

            if (accept) {
                return new LineSegment(x0, y0, x1, y1);
            }
            return null;
        }

    }
}
