package ru.justnero.study.sevsu.java.lab03;

import java.util.Scanner;

public class Book implements Comparable<Book> {

    protected final String author;
    protected final int publishYear;
    protected final int pageCount;
    protected final String publisher;

    public Book(String author, int publishYear, int pageCount, String publisher) {
        this.author = author;
        this.publishYear = publishYear;
        this.pageCount = pageCount;
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getPublisher() {
        return publisher;
    }

    public static Book read(Scanner scn) {
        return new Book(scn.next(),scn.nextInt(),scn.nextInt(),scn.next());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Book author: ");
        sb.append(author);
        sb.append("; year: ");
        sb.append(publishYear);
        sb.append("; pages: ");
        sb.append(pageCount);
        sb.append("; publisher: ");
        sb.append(publisher);
        sb.append(";");
        return sb.toString();
    }

    public String toKeyValueString() {
        StringBuilder sb = new StringBuilder();
        sb.append(author);
        sb.append(" -> ");
        sb.append(publishYear);
        sb.append("; ");
        sb.append(pageCount);
        sb.append("; ");
        sb.append(publisher);
        sb.append(";");
        return sb.toString();
    }

    public String toRawString() {
        StringBuilder sb = new StringBuilder();
        sb.append(author);
        sb.append(" ");
        sb.append(publishYear);
        sb.append(" ");
        sb.append(pageCount);
        sb.append(" ");
        sb.append(publisher);
        sb.append(" ");
        return sb.toString();
    }

    @Override
    public int compareTo(Book o) {
        return author.compareTo(o.author);
    }
}
