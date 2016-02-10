package ru.justnero.study.sevsu.java.lab03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class SecondList extends LinkedList<Book> {

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

    public void write(String filename) {
        try(PrintWriter wrt = new PrintWriter(new FileOutputStream(filename))) {
            for(Book b : this) {
                wrt.write(b.toRawString());
                wrt.write('\n');
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

}
