package ru.justnero.study.sevsu.java.lab03;

import java.util.Comparator;

public class BookComparator implements Comparator<Book> {

    @Override
    public int compare(Book b1, Book b2) {
        return b1.getPublishYear() - b2.getPublishYear();
    }

}
