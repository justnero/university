package ru.justnero.sevsu.s6.toi.e2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static int strSearch(char[] haystack, char[] needle) {
        int i, j, k, haystack_length, needle_length;
        int[] shift;
        haystack_length = haystack.length;
        needle_length = needle.length;
        shift = new int[needle_length];

        j = 0;
        k = -1;
        shift[0] = -1;
        while (j < needle_length - 1) {
            while (k >= 0 && needle[j] != needle[k]) {
                k = shift[k];
            }
            j++;
            k++;
            if (needle[j] == needle[k]) {
                shift[j] = shift[k];
            } else {
                shift[j] = k;
            }
        }
        i = 0;
        j = 0;
        while (j < needle_length && i < haystack_length) {
            while (j >= 0 && haystack[i] != needle[j]) {
                j = shift[j];
            }
            i++;
            j++;
        }
        if (j == needle_length) {
            return i - j;
        } else {
            return -1;
        }
    }

    public static void main(String args[]) {
        Scanner scn = new Scanner(System.in);
        task1(scn);
        task2(scn);
        task3();
    }

    public static void task1(Scanner scn) {
        int[] list = new int[1000];
        Random rnd = new Random();
        for (int i = 0; i < 1000; i++) {
            list[i] = (int) (1000 * rnd.nextDouble());
        }
        Arrays.sort(list);

        int n = scn.nextInt();
        System.out.println(Algorithm.interpolationSearch(list, n));
    }

    public static void task2(Scanner scn) {
        try {
            String input = new BufferedReader(new FileReader("input.txt"))
                    .lines()
                    .reduce("", (s1, s2) -> s1 + "\n" + s2)
                    .trim();

            String needle = scn.next();
            System.out.println(Algorithm.rabinSearch(input, needle));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void task3() {
        try {
            final Map<String, List<Integer>> glossary = new HashMap<>();
            int index[] = {0};
            new BufferedReader(new FileReader("input.txt"))
                    .lines().forEachOrdered(l -> {
                index[0]++;
                Arrays.stream(l.split("[\\s,.!?\\-–()]+")).forEach(w -> {
                    if (!glossary.containsKey(w.toLowerCase())) {
                        glossary.put(w.toLowerCase(), new ArrayList<>());
                    }
                    glossary.get(w.toLowerCase()).add(index[0]);
                });
            });

            BufferedWriter bw = new BufferedWriter(new FileWriter("task3.txt"));
            glossary.forEach((s, l) -> {
                try {
                    bw.write(String.format("%s\t[%s]\n", s, l.stream().map(Object::toString)
                            .collect(Collectors.joining(", "))));
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
