package ru.justnero.sevsu.s6.toi.e1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String args[]) {
        try {
            String input = new BufferedReader(new FileReader("input.txt"))
                    .lines()
                    .reduce("", (s1, s2) -> s1 + "\n" + s2)
                    .trim();

            BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"));
            input.chars().forEach(c -> {
                try {
                    bw.write(Character.isLowerCase(c) ?
                            Character.toUpperCase(c) :
                            Character.toLowerCase(c));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
