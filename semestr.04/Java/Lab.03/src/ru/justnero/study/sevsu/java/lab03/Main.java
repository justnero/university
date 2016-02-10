package ru.justnero.study.sevsu.java.lab03;

import java.io.File;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    protected static String inFile  = "";
    protected static String outFile = "";

    public static void main(String[] args) {
        if(!init(args)) {
            return;
        }
        Scanner scn = new Scanner(System.in);

        FirstList list1 = new FirstList();
        list1.read(inFile);
        _log("\tFirst list:\n");
        list1.print();
        _log("Input book author to be found\n");
        if(list1.contains(scn.next())) {
            _log("Book found\n");
        } else {
            _log("No such book\n");
        }
        _log("\n");

        SecondList list2 = new SecondList();
        list2.read(inFile);
        _log("\tSecond list unsorted:\n");
        list2.print();
        _log("\tSecond list sorted by 1st field:\n");
        Collections.sort(list2);
        list2.print();
        _log("\tSecond list sorted by 2nd field:\n");
        Collections.sort(list2,new BookComparator());
        list2.print();
        list2.write(outFile);
        _log("\n");

        ThirdList list3 = new ThirdList();
        list3.read(inFile);
        _log("\tThird list:\n");
        list3.print();
        _log("Input book author to be found\n");
        list3.print(scn.next());
        _log("\n");

        scn.close();
    }

    private static boolean init(String[] args) {
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
        if(inFile.isEmpty()) {
            _log("Input file is unset, use -i argument");
            return false;
        } else if(!(new File(inFile)).exists()) {
            _log("Input file does not exists");
            return false;
        }
        if(outFile.isEmpty()) {
            _log("Output file is unset, use -o argument");
            return false;
        }
        return true;
    }

    private static void _log(String... args) {
        StringBuilder sb = new StringBuilder();
        for(String str : args) {
            sb.append(str);
        }
        System.out.print(sb.toString());
    }

}
