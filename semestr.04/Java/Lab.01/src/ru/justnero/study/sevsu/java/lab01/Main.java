package ru.justnero.study.sevsu.java.lab01;

import java.io.*;
import java.util.Scanner;

public class Main {

    private static InputStream  is = System.in;
    private static OutputStream os = System.out;
    private static Scanner in = new Scanner(is);
    private static PrintStream out = new PrintStream(os);

    public static void main(String[] args) {
        init(args);
        run(args);
        in.close();
        out.close();
    }

    private static void run(String[] args) {
        char start = 'A';
        String inStr;
        char end = 'A';
        boolean isCharGood = false;
        while(!isCharGood) {
            inStr = in.next();
            end = inStr.charAt(0);
            if(end < 'A' || end > 'Z') {
                _log("This char (",String.valueOf(end),") is not allowed here, try again...\n");
            } else {
                isCharGood = true;
            }
        }
        _log("┌────┬───┬───┬───┬───────┐\n");
        _log("│char│hex│dec│oct│  bin  │\n");
        for(int i=start;i<=end;i++) {
            _log("├────┼───┼───┼───┼───────┤\n");
            _log(String.format("│%3c │%3h│%3d│%3o│%7s│\n",i,i,i,i,dec2bin(i)));
        }
        _log("└────┴───┴───┴───┴───────┘\n");
    }

    private static String dec2bin(int i) {
        StringBuilder sb = new StringBuilder();
        while(i != 0) {
            sb.append(i%2);
            i /= 2;
        }
        return sb.reverse().toString();
    }

    private static void init(String[] args) {
        String  inFile = "";
        String outFile = "";
        int i = 0;
        while(i < args.length) {
            if(args[i].equalsIgnoreCase("-i")) {
                inFile  = args[++i];
            } else if(args[i].equalsIgnoreCase("-o")) {
                outFile = args[++i];
            } else {
                _log("Unknown argument: ",args[i]);
            }
            i++;
        }
        if(!inFile.isEmpty()) {
            try {
                is = new FileInputStream(inFile);
            } catch (FileNotFoundException e) {
                is = System.in;
                _log("Input file ('",inFile,"') not found");
                e.printStackTrace();
            } finally {
                in = new Scanner(is);
            }
        }
        if(!outFile.isEmpty()) {
            try {
                os = new FileOutputStream(outFile);
            } catch (FileNotFoundException e) {
                os = System.out;
                _log("Output file('",outFile,"') not found");
                e.printStackTrace();
            } finally {
                out = new PrintStream(os);
            }
        }
    }

    private static void _log(String... args) {
        StringBuilder sb = new StringBuilder();
        for(String str : args) {
            sb.append(str);
        }
        out.print(sb.toString());
    }
}
