package ru.justnero.study.dsmnm.lab03;

public interface ITree extends IConvertible {

    int NOT_FOUND = 1;
    int DUPLICATE = 2;
    int SUCCESS = 4;

    int add(TData data);

    TreeNode<TData> find(TData val);

    int remove(TData val);

    void clear();

}