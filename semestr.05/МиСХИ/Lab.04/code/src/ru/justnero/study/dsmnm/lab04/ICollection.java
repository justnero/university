package ru.justnero.study.dsmnm.lab04;

interface ICollection<Data extends Comparable<Data>> extends IConvertible {

    int NOT_FOUND = 1;
    int DUPLICATE = 2;
    int SUCCESS = 4;

    int add(Data data);

    Data find(Data val);

    int remove(Data val);

    void clear();

}