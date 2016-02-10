package ru.justnero.study.sevsu.java.lab02;

import java.io.*;
import java.util.Random;

public class CFloatBuffer extends CBuffer implements IBufferComputable, IBufferSortable, IBufferStorable, IBufferPrintable {

    protected float buf[];

    public CFloatBuffer(int count) {
        super(count);
        buf = new float[count];
        generate();
    }

    @Override
    public void generate() {
        Random rnd = new Random();
        for(int i=0;i<bufSize;i++) {
            buf[i] = rnd.nextFloat();
        }
    }

    @Override
    public void max() {
        float max = buf[0];
        for(int i=1;i<bufSize;i++) {
            if(buf[i] > max) {
                max = buf[i];
            }
        }
        _log("Max: ",max,'\n');
    }

    @Override
    public void min() {
        float min = buf[0];
        for(int i=1;i<bufSize;i++) {
            if(buf[i] < min) {
                min = buf[i];
            }
        }
        _log("Min: ",min,'\n');
    }

    @Override
    public void sum() {
        float sum = 0;
        for(int i=0;i<bufSize;i++) {
            sum += buf[i];
        }
        _log("Sum: ",sum,'\n');
    }

    @Override
    public void sort() {
        int m;
        float tmp;
        for(int i=0;i<bufSize-1;i++) {
            m = i;
            for(int j=i+1;j<bufSize;j++) {
                if(buf[j] < buf[m]) {
                    m = j;
                }
            }
            if(m != i) {
                tmp = buf[i];
                buf[i] = buf[m];
                buf[m] = tmp;
            }
        }
    }

    @Override
    public void printOneLine(String filename) {
        printToFile(filename,' ');
    }

    @Override
    public void printSeparateLines(String filename) {
        printToFile(filename,'\n');
    }

    private void printToFile(String filename, char separator) {
        try(PrintWriter out = new PrintWriter(new FileOutputStream(filename,true))) {
            for (int i = 0; i < bufSize; i++) {
                out.print(buf[i]);
                out.print(separator);
            }
            out.print('\n');
            out.close();
        } catch (FileNotFoundException e) {
            _log("File not found: ",filename);
            e.printStackTrace();
        }
    }

    @Override
    public void printInfo() {
        _log('\t',"Buffer #",bufID," size: ",bufSize,'\n');
    }

    @Override
    public void print() {
        for(int i=0;i<bufSize;i++) {
            _log(buf[i],' ');
        }
        _log('\n');
    }

    @Override
    public void printFirst(int n) {
        for(int i=0;i<Math.min(bufSize,n);i++) {
            _log(buf[i],' ');
        }
        _log('\n');
    }

    @Override
    public void printLast(int n) {
        for (int i = Math.max(0, bufSize - n); i < bufSize; i++) {
            _log(buf[i], ' ');
        }
        _log('\n');
    }

    private static void _log(Object... args) {
        StringBuilder sb = new StringBuilder();
        for(Object str : args) {
            sb.append(str);
        }
        System.out.print(sb.toString());
    }

}
