package ru.justnero.study.s6.cg.e4.main;

public class Parallelogram extends Quad {
    public Parallelogram() {
        this(new Vertex3f[]{
                new Vertex3f("A", 1, 0),
                new Vertex3f("B", 2, 2),
                new Vertex3f("C", 5, 2),
                new Vertex3f("D", 4, 0),
        });
    }

    public Parallelogram(ru.justnero.study.s6.cg.e2.main.figures.Vertex3f[] vertices) {
        super(vertices);
    }
}
