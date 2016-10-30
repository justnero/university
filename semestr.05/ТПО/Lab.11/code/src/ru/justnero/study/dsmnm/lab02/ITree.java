package ru.justnero.study.dsmnm.lab02;

public interface ITree extends IConvertable {

    int NOT_FOUND = 1;
    int DUPLICATE = 2;
    int SUCCESS = 4;

    int add(TData data);

    TreeNode find(TData val);

    int remove(TData val);

    void clear();

}