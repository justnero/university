package ru.justnero.study.s6.cg.e4.main;

public class Trapeze extends Quad {
    public Trapeze() {
        this(new Vertex3f[]{
                new Vertex3f("A", 0, 0),
                new Vertex3f("D", 5, 0),
                new Vertex3f("C", 3, 2),
                new Vertex3f("B", 1, 2)
        });
    }

    public Trapeze(Vertex3f[] vertices) {
        super(vertices);
    }
}
