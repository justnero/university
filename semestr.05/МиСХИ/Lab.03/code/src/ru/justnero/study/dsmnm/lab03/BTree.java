package ru.justnero.study.dsmnm.lab03;

import javafx.scene.control.TreeItem;

public class BTree implements ITree {

    private final BTreeImpl<TData> impl = new BTreeImpl<>();

    @Override
    public int add(TData data) {
        return impl.add(data) ? SUCCESS : DUPLICATE;
    }

    @Override
    public TreeNode<TData> find(TData val) {
        return impl.find(val);
    }

    @Override
    public int remove(TData val) {
        return impl.remove(val) == null ? SUCCESS : NOT_FOUND;
    }

    @Override
    public void clear() {
        impl.clear();
    }

    @Override
    public TreeItem<String> convert() {
        return impl.convert();
    }

}
