package ru.justnero.study.sevsu.pptc;

import java.util.LinkedList;
import java.util.List;

public class Report {

    public final String name;
    public final List<Object> elements;

    public Report(String name) {
        this.name = name;
        this.elements = new LinkedList<>();
    }

    public Report addLine(final Object... parts) {
        for (Object part : parts) {
            elements.add(part);
        }
        elements.add("\n");
        return this;
    }

    public int getSize() {
        return elements.size();
    }

    public void print() {
        System.out.println("\t\t" + name);
        elements.forEach(System.out::print);
    }

}
