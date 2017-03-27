package ru.justnero.study.s6.cg.e4.util.algorithm.clipping;

public final class LineClipping {
    int xmax = 90, ymax = 80, xmin = 40, ymin = 40;

    private LineClipping() {
    }

    public int[] set(int x, int y) {
        int a[] = new int[4];
        if (x < xmin)
            a[3] = 1;
        else
            a[3] = 0;
        if (x > xmax)
            a[2] = 1;
        else
            a[2] = 0;
        if (y < ymin)
            a[0] = 1;
        else
            a[0] = 0;
        if (y > ymax)
            a[1] = 1;
        else
            a[1] = 0;
        return a;
    }

    boolean check(int a[]) {
        for (int i = 0; i < a.length; i++)
            if (a[i] == 1)
                return false;
        return true;
    }

    int[] produceXY(int i, int x1, int y1, float m) {
        int a[] = new int[2];
        float x = 0, y = 0;

        switch (i) {
            case 0:
                x = xmin;
                y = y1 + m * (x - x1);
                break;
            case 1:
                x = xmax;
                y = y1 + m * (x - x1);
                break;
            case 2:
                y = ymax;
                x = x1 + (y - y1) / m;
                break;
            case 3:
                y = ymin;
                x = x1 + (y - y1) / m;
                break;
        }
        a[0] = (int) x;
        a[1] = (int) y;
        return a;
    }

    boolean doAnd(int a[], int b[]) {
        for (int i = 0; i < a.length; i++) {
            int k = a[i] & b[i];
            if (k == 1)
                return false;
        }
        return true;
    }

//    static void Otsechenie(float xn, float yn, float xk, float yk) {
//        int visible = 0;
//        int codN, codK, ii, s;        // Рабочие переменные
//        float dx, dy,       // Приращения координат
//                dxdy, dydx,    // Наклоны отрезка к сторонам
//                r;            // Рабочая переменная
//
//        float x0, y0, x1, y1;
//
//        xL = mOk[0][0];
//        xR = mOk[1][0];
//        yT = mOk[0][1];
//        yB = mOk[1][1];
//
//        x1 = xk;
//        y1 = yk;
//        x0 = xk;
//        y0 = yk;
//        codK = CodeVer(x0, y0);
//        x0 = xn;
//        y0 = yn;
//        codN = CodeVer(x0, y0);
//
//        dx = x1 - x0;
//        dy = y1 - y0;
//        if (dx != 0) dydx = dy / dx;
//        else if (dy == 0)
//            if ((codN == 0) && (codK == 0)) {
//                xnOts = x0;
//                ynOts = y0;
//                xkOts = x1;
//                ykOts = y1;
//                return;
//            } else return;
//
//        if (dy != 0) dxdy = dx / dy;
//        visible = 0;
//        ii = 4;
//        do {
//            if (codN & codK) break;         // Целиком вне окна
//            if ((codN == 0) && (codK == 0)) {
//                ++visible;
//                break;
//            }         // Целиком внутри окна
//
//            if (!codN)                      // Если Pn внутри окна, то
//            {
//                s = codN;
//                codN = codK;
//                codK = s;  // перестить точки Pn,Pk и
//                r = x0;
//                x0 = x1;
//                x1 = r;          // их коды, чтобы Pn
//                r = y0;
//                y0 = y1;
//                y1 = r;
//            }         // оказалась вне окна
//
//            // Теперь отрезок разделяется. Pn помещается в точку
//            //  пересечения отрезка со стороной окна.
//
//            if (codN & 1)                   // Пересечение с левой стороной
//            {
//                y0 = y0 + dydx * (xL - x0);
//                x0 = xL;
//            } else if (codN & 2)                 // Пересечение с правой стороной
//            {
//                y0 = y0 + dydx * (xR - x0);
//                x0 = xR;
//            } else if (codN & 4)               // Пересечение в нижней стороной
//            {
//                x0 = x0 + dxdy * (yB - y0);
//                y0 = yB;
//            } else if (codN & 8)            // Пересечение с верхней стороной
//            {
//                x0 = x0 + dxdy * (yT - y0);
//                y0 = yT;
//            }
//            codN = CodeVer(x0, y0);        // Перевычисление кода точки Pn
//        }
//        while (--ii >= 0);
//        if (visible) {
//            xnOts = x0;
//            ynOts = y0;
//            xkOts = x1;
//            ykOts = y1;
//        }
//    }
//
//    static int CodeVer(float xVer, float yVer) {
//        int cod = 0;
//        if (xVer < xL) ++cod;
//        else if (xVer > xR) cod += 2;
//        if (yVer > yB) cod += 4;
//        else if (yVer < yT) cod += 8;
//        return cod;
//    }
}
