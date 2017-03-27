package ru.justnero.study.s6.cg.e4.util.vector2DDecorators;

import com.sun.javafx.geom.Vec2f;

import ru.justnero.study.s6.cg.e4.util.Vector2D;

public class VectorDecorator extends Vector2D {
    protected Vector2D vector2D;

    public VectorDecorator(Vector2D vector2D) {
        this.vector2D = vector2D;
    }

    @Override
    public void normalize() {
        vector2D.normalize();
    }

    @Override
    public void multiply(float value) {
        vector2D.multiply(value);
    }

    @Override
    public void set(Vec2f v) {
        vector2D.set(v);
    }

    @Override
    public void set(float x, float y) {
        vector2D.set(x, y);
    }

    @Override
    public float distanceSq(float vx, float vy) {
        return vector2D.distanceSq(vx, vy);
    }

    @Override
    public float distanceSq(Vec2f v) {
        return vector2D.distanceSq(v);
    }

    @Override
    public float distance(float vx, float vy) {
        return vector2D.distance(vx, vy);
    }

    @Override
    public float distance(Vec2f v) {
        return vector2D.distance(v);
    }

    @Override
    public int hashCode() {
        return vector2D.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return vector2D.equals(obj);
    }

    @Override
    public String toString() {
        return vector2D.toString();
    }
}
