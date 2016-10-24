package ru.justnero.study.dsmnm.lab04;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.control.TreeItem;

class HashCollectionNode<Data extends Comparable<Data>> implements ICollectionNode<Data>, IConvertible {

    private final List<Data> list = new LinkedList<>();

    @Override
    public int add(Data val) {
        for(Data data : list) {
            if(data.compareTo(val) == 0) {
                return ICollection.DUPLICATE;
            }
        }
        list.add(val);
        return ICollection.SUCCESS;
    }

    @Override
    public Data find(Data key) {
        for(Data data : list) {
            if(data.compareTo(key) == 0) {
                return data;
            }
        }
        return null;
    }

    @Override
    public int remove(Data key) {
        for(Data data : list) {
            if(data.compareTo(key) == 0) {
                list.remove(data);
                return ICollection.SUCCESS;
            }
        }
        return ICollection.NOT_FOUND;
    }

    public TreeItem<String> convert() {
        TreeItem<String> root = new TreeItem<>("");
        for(Data data : list) {
             root.getChildren().add(new TreeItem<>(data.toString()));
        }
        return root;
    }
}
