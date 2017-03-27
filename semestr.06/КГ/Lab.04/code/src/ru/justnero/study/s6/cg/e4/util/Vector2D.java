package ru.justnero.study.s6.cg.e4.util;

import com.sun.javafx.geom.Vec2f;

public class Vector2D extends Vec2f {
    public Vector2D() {
    }

    public Vector2D(float x, float y) {
        super(x, y);
    }

    public Vector2D(Vector2D v) {
        super(v);
    }

    public Vector2D(Vec2f v) {
        super(v);
    }

    public void normalize() {
        float distance = distance(this);
        x = x / distance;
        y = y / distance;
    }

    public void multiply(float value) {
        x *= value;
        y *= value;
    }
}
