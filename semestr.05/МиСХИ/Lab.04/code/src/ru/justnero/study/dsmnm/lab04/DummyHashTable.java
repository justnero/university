package ru.justnero.study.dsmnm.lab04;

import javafx.scene.control.TreeItem;

class DummyHashTable<Data extends Comparable<Data>> implements ICollection<Data> {

    private final IHashFunction<Data> hashFunction;
    private HashCollectionNode<Data>[] map;

    DummyHashTable(IHashFunction<Data> hashFunction) {
        this.hashFunction = hashFunction;
        clear();
    }

    @Override
    public int add(Data data) {
        return map[hashFunction.get(data)].add(data);
    }

    @Override
    public Data find(Data key) {
        return map[hashFunction.get(key)].find(key);
    }

    @Override
    public int remove(Data key) {
        return map[hashFunction.get(key)].remove(key);
    }

    @Override
    public void clear() {
        @SuppressWarnings("unchecked")
        HashCollectionNode<Data>[] map = new HashCollectionNode[hashFunction.max() + 1];
        this.map = map;
        for(int i=0;i<=hashFunction.max();i++) {
            map[i] = new HashCollectionNode<>();
        }
    }

    @Override
    public TreeItem<String> convert() {
        TreeItem<String> root = new TreeItem<>("ROOT");
        TreeItem<String> cur;
        for(int i=0;i<=hashFunction.max();i++) {
            cur = map[i].convert();
            if(cur.getChildren().size() == 0) {
                continue;
            }
            cur.setValue(String.valueOf(i));
            root.getChildren().add(cur);
        }
        return root;
    }

}
