package ru.justnero.sevsu.s6.toi.e3.main;

public enum SplitScale {
    BY_STRINGS("\n"),
    BY_SENTENCES("[.;!?\n]"),
    BY_WORDS("[^\\wА-Яа-яґєҐЄ´ІіЇї]|\\d"),
    BY_CHARACTERS("");

    private String splitRegex;

    SplitScale(String splitRegex) {
        this.splitRegex = splitRegex;
    }

    public String getSplitRegex() {
        return splitRegex;
    }
}
