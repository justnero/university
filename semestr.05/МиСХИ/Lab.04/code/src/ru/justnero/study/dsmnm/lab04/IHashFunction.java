package ru.justnero.study.dsmnm.lab04;

interface IHashFunction<Data extends Comparable<Data>> {

    int max();

    int get(Data data);

}
