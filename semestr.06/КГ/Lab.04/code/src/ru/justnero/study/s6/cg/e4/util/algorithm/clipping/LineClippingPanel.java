package ru.justnero.study.s6.cg.e4.util.algorithm.clipping;

import javax.swing.*;

/**
 * @author Enrique Arango Lyons
 */
public class LineClippingPanel extends JPanel {

    public static final int INSIDE = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 4;
    public static final int TOP = 8;

    public static final int COHEN_SUTHERLAND = 0;
    public static final int LIANG_BARSKY = 1;

    private float xMin;
    private float xMax;
    private float yMin;
    private float yMax;

    private LineClipper clipper;

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

        /**
         * Execute line clipping using Cohen-Sutherland
         * Taken from: http://en.wikipedia.org/wiki/Cohen-Sutherland
         *
         * @param line LineSegment to work with
         * @return Clipped line
         */
        public LineSegment clip(LineSegment line) {
            System.out.println("\nExecuting Cohen-Sutherland...");
            float x0 = line.x0, x1 = line.x1, y0 = line.y0, y1 = line.y1;
            int outCode0 = computeOutCode(x0, y0);
            int outCode1 = computeOutCode(x1, y1);
            System.out.println("OutCode0: " + outCode0 + ", OutCode1: " + outCode1);
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

//    public class LiangBarsky implements LineClipper {
//
//        /**
//         * Execute line clipping using Liang-Barsky
//         * @param line Line segment to work with
//         * @return Clipped line
//         */
//        public LineSegment clip(LineSegment line) {
//            System.out.println("\nExecuting Liang-Barsky...");
//            double u1 = 0, u2 = 1;
//            int x0 = line.x0, y0 = line.y0, x1 = line.x1, y1 = line.y1;
//            int dx = x1 - x0, dy = y1 - y0;
//            int p[] = {-dx, dx, -dy, dy};
//            int q[] = {x0 - xMin, xMax - x0, y0 - yMin, yMax - y0};
//            for (int i = 0; i < 4; i++) {
//                if (p[i] == 0) {
//                    if (q[i] < 0) {
//                        return null;
//                    }
//                } else {
//                    double u = (double) q[i] / p[i];
//                    if (p[i] < 0) {
//                        u1 = Math.max(u, u1);
//                    } else {
//                        u2 = Math.min(u, u2);
//                    }
//                }
//            }
//            System.out.println("u1: " + u1 + ", u2: " + u2);
//            if (u1 > u2) {
//                return null;
//            }
//            int nx0, ny0, nx1, ny1;
//            nx0 = (int) (x0 + u1 * dx);
//            ny0 = (int) (y0 + u1 * dy);
//            nx1 = (int) (x0 + u2 * dx);
//            ny1 = (int) (y0 + u2 * dy);
//            return new LineSegment(nx0, ny0, nx1, ny1);
//        }
//    }

//    /**
//     * Constructor
//     * @param xMin Bottom side of rectangle
//     * @param yMin Left side of rectangle
//     * @param xMax Top side of rectangle
//     * @param yMax Right side of rectangle
//     * @param clipperOption Code for LineClipper algorithm to use (0: Cohen-Sutherland, 1: Liang-Barsky)
//     */
//    public LineClippingPanel(int xMin, int yMin, int xMax, int yMax, int clipperOption) {
//        this.xMin = xMin;
//        this.yMin = yMin;
//        this.xMax = xMax;
//        this.yMax = yMax;
//        switch(clipperOption) {
//            case COHEN_SUTHERLAND:
//                clipper = new CohenSutherland();
//                break;
//            case LIANG_BARSKY:
//                clipper = new LiangBarsky();
//                break;
//        }
//    }

//    @Override
//    public void paintComponent(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//
//        g2d.setColor(Color.black);
//        g2d.fillRect(0, 0, getWidth(), getHeight());
//
//        g2d.setColor(Color.blue);
//        drawLine(g2d, xMin, 0, xMin, getHeight());
//        drawLine(g2d, xMax, 0, xMax, getHeight());
//        drawLine(g2d, 0, yMin, getWidth(), yMin);
//        drawLine(g2d, 0, yMax, getWidth(), yMax);
//
//        int x0, y0, x1, y1;
//        LineSegment line, clippedLine;
//        for (int i = 0; i < 10; i++) {
//            x0 = (int)(Math.random() * getWidth());
//            x1 = (int)(Math.random() * getWidth());
//            y0 = (int)(Math.random() * getHeight());
//            y1 = (int)(Math.random() * getHeight());
//            line = new LineSegment(x0, y0, x1, y1);
//            clippedLine = clipper.clip(line);
//
//            System.out.println("Original: " + line);
//            System.out.println("Clipped: " + clippedLine);
//
//            if (clippedLine == null) {
//                g2d.setColor(Color.red);
//                drawLine(g2d, line.x0, line.y0, line.x1, line.y1);
//            } else {
//                g2d.setColor(Color.red);
//                drawLine(g2d, line.x0, line.y0, clippedLine.x0, clippedLine.y0);
//                drawLine(g2d, clippedLine.x1, clippedLine.y1, line.x1, line.y1);
//                g2d.setColor(Color.green);
//                drawLine(g2d, clippedLine.x0, clippedLine.y0, clippedLine.x1, clippedLine.y1);
//            }
//        }
//    }

//    private void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
//        g.drawLine(x1, getHeight() - y1, x2, getHeight() - y2);
//    }

//    public static void main(String[] args) {
//
//
//        JFrame mainFrame = new JFrame("Line Clipping");
//        mainFrame.setSize(800, 600);
//        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        int x0, y0, x1, y1;
//        x0 = y0 = x1 = y1 = -1;
//
//        do {
//            String response = JOptionPane.showInputDialog(mainFrame, "Please insert the coordinates of the lower left and upper right points\nof the rectangle separated by commas.\n"
//                            + "Format: \"xMin,yMin,xMax,yMax\"; xMin < xMax and yMin < yMax\n"
//                            + "(0 <= x <= 800 and 0 <= y <= 600)",
//                    "100,100,700,500");
//            if (response == null) System.exit(0);
//            String[] coordinates = response.split(",");
//            try {
//                x0 = Integer.parseInt(coordinates[0]);
//                y0 = Integer.parseInt(coordinates[1]);
//                x1 = Integer.parseInt(coordinates[2]);
//                y1 = Integer.parseInt(coordinates[3]);
//            } catch (NumberFormatException ne) {
//                JOptionPane.showMessageDialog(mainFrame, "All values must be integers");
//            } finally {
//            }
//        } while (0 > x0 || x1 > 800 || 0 > y0 || y1 > 600 || x0 >= x1 || y0 >= y1);
//
//
//        String algorithms[] = {"Cohen-Sutherland", "Liam-Barsky"};
//        int choice = JOptionPane.showOptionDialog(mainFrame, "Choose the algorithm to use", "Algoritm selection",
//                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
//                null, algorithms, algorithms[0]);
//
//        if (choice == JOptionPane.CLOSED_OPTION) {
//            System.exit(0);
//        }
//
//        mainFrame.add(new LineClippingPanel(x0, y0, x1, y1, choice));
//        mainFrame.setLocationRelativeTo(null);
//        mainFrame.setVisible(true);
//    }

}