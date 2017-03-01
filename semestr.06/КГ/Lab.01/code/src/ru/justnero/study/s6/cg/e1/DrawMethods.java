package ru.justnero.study.s6.cg.e1;

import java.awt.*;
import java.util.Vector;

public final class DrawMethods {

    private DrawMethods() {
    }

    public static Vector<Point> symmetricCirclePoint(int xc, int yc, int x, int y) {
        Vector<Point> vector = new Vector<>(8);
        vector.add(new Point(xc + x, yc + y));
        vector.add(new Point(xc + y, yc + x));
        vector.add(new Point(xc + y, yc - x));
        vector.add(new Point(xc + x, yc - y));
        vector.add(new Point(xc - x, yc - y));
        vector.add(new Point(xc - y, yc - x));
        vector.add(new Point(xc - y, yc + x));
        vector.add(new Point(xc - x, yc + y));
        return vector;
    }

    public static Vector<Point> circle(int xc, int yc, int r) {
        Vector<Point> vector = new Vector<>();
        int x, y, z, Dd;
        x = 0;
        y = r;
        Dd = 2 * (1 - r);
        while (x < y) {
            vector.addAll(symmetricCirclePoint(xc, yc, x, y));
            String direction = "horizontal";
            if (Dd == 0) {
                direction = "diagonal";
            }
            z = 2 * Dd - 1;
            if (Dd > 0) {
                if (z + 2 * x <= 0) {
                    direction = "diagonal";
                } else {
                    direction = "vertical";
                }
            }
            if (z + 2 * y > 0) {
                direction = "diagonal";
            }
            switch (direction) {
                case "horizontal":
                    ++x;
                    Dd = Dd + 2 * x + 1;
                    break;
                case "vertical":
                    --y;
                    Dd = Dd - 2 * y + 1;
                    break;
                case "diagonal":
                    ++x;
                    --y;
                    Dd = Dd + 2 * (x - y + 1);
                    break;
            }
        }
        if (x == y) {
            vector.addAll(symmetricCirclePoint(xc, yc, x, y));
        }
        return vector;
    }

    public static Vector<Point> asymmetricDDA(int x1, int y1, int x2, int y2) {
        Vector<Point> vector;
        int n, px, py;
        float dx, dy;
        float x1f = x1, y1f = y1;

        px = x2 - x1;
        py = y2 - y1;

        if (Math.abs(px) > Math.abs(py)) {
            n = Math.abs(px);

            dx = (px > 0 ? 1f : -1f);
            dy = (float) py / n;
        } else {
            n = Math.abs(py);

            dx = (float) px / n;
            dy = (py > 0 ? 1f : -1f);
        }

        vector = new Vector<>(2 * n);
        vector.add(new Point(x1, y1));

        while (x1 != x2 || y1 != y2) {
            x1f += dx;
            y1f += dy;

            x1 = Math.round(x1f);
            y1 = Math.round(y1f);
            vector.add(new Point(x1, y1));
        }

        return vector;
    }

    public static Vector<Point> rectangular(int x0, int y0, int width, int height) {
        Vector<Point> result = asymmetricDDA(x0, y0, x0 + width, y0);
        result.addAll(asymmetricDDA(x0, y0, x0, y0 + height));
        result.addAll(asymmetricDDA(x0, y0 + height, x0 + width, y0 + height));
        result.addAll(asymmetricDDA(x0 + width, y0, x0 + width, y0 + height));

        return result;
    }

    public static Vector<Point> line(int x1, int y1, int x2, int y2) {
        return asymmetricDDA(x1, y1, x2, y2);
    }

    public static Vector<Point> resistor(int x1, int y1, int width, int height, int wire) {
        Vector<Point> result = new Vector<>(4);

        result.addAll(rectangular(x1, y1, width, height));

        result.addAll(line(x1, y1 + height / 2, x1 - wire, y1 + height / 2));
        result.addAll(line(x1 + width, y1 + height / 2, x1 + width + wire, y1 + height / 2));

        return result;
    }

    public static Vector<Point> key(int x1, int y1, int width, int height, int wire) {
        Vector<Point> result = new Vector<>(12);

        result.addAll(line(x1 - width / 2, y1, x1 - width, y1 + height));

        result.addAll(line(x1 - width / 2, y1, x1 - width / 2, y1 - wire));
        result.addAll(line(x1 - width / 2, y1 + height, x1 - width / 2, y1 + height + wire));

        return result;
    }

    public static final class StandardMethod {

        private StandardMethod() {
        }

        public static Vector<Point> circle(int xc, int yc, int r) {
            return DrawMethods.circle(xc, yc, r);
        }

        public static Vector<Point> rectangular(int x0, int y0, int width, int height) {
            Vector<Point> result = new Vector<>(4);
            result.add(new Point(x0, y0));
            result.add(new Point(x0 + width, y0));

            result.add(new Point(x0 + width, y0));
            result.add(new Point(x0 + width, y0 + height));

            result.add(new Point(x0 + width, y0 + height));
            result.add(new Point(x0, y0 + height));

            result.add(new Point(x0, y0 + height));
            result.add(new Point(x0, y0));

            return result;
        }

        public static Vector<Point> line(int x1, int y1, int x2, int y2) {
            Vector<Point> result = new Vector<>(2);
            result.add(new Point(x1, y2));
            result.add(new Point(x2, y2));

            return result;
        }

        public static Vector<Point> resistor(int x1, int y1, int width, int height, int wire) {
            Vector<Point> result = new Vector<>(12);

            result.addAll(rectangular(x1, y1, width, height));

            result.addAll(line(x1, y1 + height / 2, x1 - wire, y1 + height / 2));
            result.addAll(line(x1 + width, y1 + height / 2, x1 + width + wire, y1 + height / 2));

            return result;
        }

        public static Vector<Point> key(int x1, int y1, int width, int height, int wire) {
            Vector<Point> result = new Vector<>(12);

            result.addAll(line(x1 - width / 2, y1, x1 - width, y1 + height));

            result.addAll(line(x1 - width / 2, y1, x1 - width / 2, y1 - wire));
            result.addAll(line(x1 - width / 2, y1 + height, x1 - width / 2, y1 + height + wire));

            return result;
        }

    }
}
