package ru.justnero.study.sevsu.java.lab02;

public class Main {

    public static void main(String[] args) {
        int n = 3;
        int l = 80;
        CFloatBuffer bufs[] = new CFloatBuffer[n];
        for(int i=0;i<n;i++) {
            bufs[i] = new CFloatBuffer(l);
            bufs[i].printInfo();
            bufs[i].sum();
            System.out.println("First 10:");
            System.out.print("Before: ");
            bufs[i].printFirst(10);
            bufs[i].sort();
            System.out.print("After: ");
            bufs[i].printFirst(10);
            bufs[i].printSeparateLines("buffer"+String.valueOf(bufs[i].getBufID())+".txt");
        }
    }
}
