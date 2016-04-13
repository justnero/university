package ru.justnero.study.sevsu.pptc;

import java.util.ArrayList;
import java.util.List;

public class Report {

    public final String name;
    public final List<String> lines;

    public Report(String name) {
        this.name = name;
        this.lines = new ArrayList<>();
    }

    public Report addLine(final Object... elements) {
        StringBuilder sb = new StringBuilder();
        for(Object el : elements) {
            sb.append(el);
        }
        lines.add(sb.toString());
        return this;
    }

    public Report removeItem(final int index) {
        lines.remove(index);
        return this;
    }

    public String getLine(final int index) {
        return lines.get(index);
    }

    public int getSize() {
        return lines.size();
    }

    public void print() {
        System.out.println("\t\t"+name);
        for(final String line : lines) {
            System.out.println(line);
        }
    }

}
