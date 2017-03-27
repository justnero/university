package ru.justnero.study.s6.cg.e4.util.decorators;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import ru.justnero.study.s6.cg.e2.main.Matrix3f;
import ru.justnero.study.s6.cg.e2.main.figures.Vertex3f;
import ru.justnero.study.s6.cg.e4.util.Complex;
import ru.justnero.study.s6.cg.e4.util.Vector2D;

public class InBoundMovement<T extends Complex<T>>
        extends TransformByVector<T> {

    protected List<Vertex3f> vertex3fs = new ArrayList<>();
    protected boolean xDirectionChanged = false;
    protected boolean yDirectionChanged = false;

    protected Predicate<Float> xInBound;
    protected Predicate<Float> yInBound;

    public InBoundMovement(TransformByVector<T> figure,
                           Predicate<Float> xInBound,
                           Predicate<Float> yInBound) {
        this((T) figure, figure.getVelocity(), xInBound, yInBound);
    }

    public InBoundMovement(T figure, Vector2D vector2D,
                           Predicate<Float> xInBound,
                           Predicate<Float> yInBound) {
        super(figure, vector2D);
        this.xInBound = xInBound;
        this.yInBound = yInBound;
    }

    @Override
    public void transform(Matrix3f translateMatrix, T result) {
        vertex3fs.removeAll(vertex3fs);
        xDirectionChanged = false;
        yDirectionChanged = false;

        getVertexes(vertex3fs);
        for (Vertex3f vertex3f : vertex3fs) {

            if (!xInBound.test(vertex3f.x)) {
                if (!xDirectionChanged) {
                    velocity.x = -velocity.x;
                    xDirectionChanged = true;
                }
            }

            if (!yInBound.test(vertex3f.y)) {
                if (!yDirectionChanged) {
                    velocity.y = -velocity.y;
                    yDirectionChanged = true;
                }
            }
        }

        super.transform(translateMatrix, result);
    }

}
