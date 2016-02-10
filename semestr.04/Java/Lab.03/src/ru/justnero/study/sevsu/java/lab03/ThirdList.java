package ru.justnero.study.sevsu.java.lab03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class ThirdList extends LinkedHashMap<String,Book> {

    public void read(String filename) {
        this.clear();
        Book b;
        try(Scanner scn = new Scanner(new FileInputStream(filename))) {
            while(scn.hasNext()) {
                b = Book.read(scn);
                put(b.getAuthor(),b);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    public void print() {
        for(Book b : this.values()) {
            System.out.println(b.toKeyValueString());
        }
    }

    public void print(String author) {
        Book b = get(author);
        if(b != null) {
            System.out.println(b.toKeyValueString());
        } else {
            System.out.println("No such book");
        }
    }

}
