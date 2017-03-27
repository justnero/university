package ru.justnero.study.s6.cg.e4.util.decorators;

import com.jogamp.opengl.GLAutoDrawable;

import java.awt.*;
import java.util.List;

import ru.justnero.study.s6.cg.e2.main.Matrix3f;
import ru.justnero.study.s6.cg.e2.main.figures.Vertex3f;
import ru.justnero.study.s6.cg.e4.util.Complex;

public class FigureDecorator<T extends Complex<T>>
        implements Complex<T> {
    protected T figure;

    public FigureDecorator(T figure) {
        this.figure = figure;
    }

    @Override
    public void setResult(T result) {
        figure.setResult(result);
    }

    @Override
    public void copyTo(T result) {
        figure.copyTo(result);
    }

    @Override
    public T getResultMatrix() {
        return figure.getResultMatrix();
    }

    @Override
    public void normalize() {
        figure.normalize();
    }

    @Override
    public void transform(Matrix3f translateMatrix, T result) {
        figure.transform(translateMatrix, result);
    }

    @Override
    public void applyChange() {
        if (!(figure instanceof FigureDecorator)) {
            getResultMatrix().copyTo(figure);
        } else {
            ((FigureDecorator) figure).figure.applyChange();
        }
    }

    @Override
    public void redoChange() {
        figure.copyTo(getResultMatrix());
    }

    @Override
    public void display(GLAutoDrawable drawable, float coordinateSize, boolean fill) {
        figure.display(drawable, coordinateSize, fill);
    }

    @Override
    public void getVertexes(List<Vertex3f> result) {
        figure.getVertexes(result);
    }

    @Override
    public Color getColor() {
        return figure.getColor();
    }

    public T getFigure() {
        return figure;
    }

    public void setFigure(T figure) {
        this.figure = figure;
    }
}
