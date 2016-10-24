package ru.justnero.study.dsmnm.lab04;

interface ICollectionNode<Data extends Comparable<Data>> {

    int add(Data val);

    Data find(Data key);

    int remove(Data key);

}
