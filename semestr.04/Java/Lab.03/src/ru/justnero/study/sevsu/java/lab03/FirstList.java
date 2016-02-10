package ru.justnero.study.sevsu.java.lab03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class FirstList extends LinkedHashSet<Book> {

    public void read(String filename) {
        this.clear();
        try(Scanner scn = new Scanner(new FileInputStream(filename))) {
            while(scn.hasNext()) {
                add(Book.read(scn));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    public void print() {
        for(Book b : this) {
            System.out.println(b.toString());
        }
    }

    public boolean contains(String author) {
        for(Book b : this) {
            if(b.getAuthor().equalsIgnoreCase(author)) {
                return true;
            }
        }
        return false;
    }

}
