package ru.justnero.study.dsmnm.lab03;

import java.util.Arrays;
import java.util.Comparator;

class TreeNode<Data extends Comparable<Data>> {

    TreeNode<Data> parent = null;
    Data[] keys = null;
    int keysSize = 0;
    TreeNode<Data>[] children = null;
    int childrenSize = 0;
    private Comparator<TreeNode<Data>> comparator = (a, b) -> a.getKey(0).compareTo(b.getKey(0));

    TreeNode(TreeNode<Data> parent, int maxKeySize, int maxChildrenSize) {
        this.parent = parent;
        this.keys = (Data[]) new Comparable[maxKeySize + 1];
        this.keysSize = 0;
        this.children = new TreeNode[maxChildrenSize + 1];
        this.childrenSize = 0;
    }

    Data getKey(int index) {
        return keys[index];
    }

    int indexOf(Data value) {
        for (int i = 0; i < keysSize; i++) {
            if (keys[i].equals(value)) return i;
        }
        return -1;
    }

    void addKey(Data value) {
        keys[keysSize++] = value;
        Arrays.sort(keys, 0, keysSize);
    }

    Data removeKey(Data value) {
        Data removed = null;
        boolean found = false;
        if (keysSize == 0) return null;
        for (int i = 0; i < keysSize; i++) {
            if (keys[i].equals(value)) {
                found = true;
                removed = keys[i];
            } else if (found) {
                keys[i - 1] = keys[i];
            }
        }
        if (found) {
            keysSize--;
            keys[keysSize] = null;
        }
        return removed;
    }

    Data removeKey(int index) {
        if (index >= keysSize)
            return null;
        Data value = keys[index];
        System.arraycopy(keys, index + 1, keys, index + 1 - 1, keysSize - (index + 1));
        keysSize--;
        keys[keysSize] = null;
        return value;
    }

    int numberOfKeys() {
        return keysSize;
    }

    TreeNode<Data> getChild(int index) {
        if (index >= childrenSize)
            return null;
        return children[index];
    }

    int indexOf(TreeNode<Data> child) {
        for (int i = 0; i < childrenSize; i++) {
            if (children[i].equals(child))
                return i;
        }
        return -1;
    }

    boolean addChild(TreeNode<Data> child) {
        child.parent = this;
        children[childrenSize++] = child;
        Arrays.sort(children, 0, childrenSize, comparator);
        return true;
    }

    boolean removeChild(TreeNode<Data> child) {
        boolean found = false;
        if (childrenSize == 0)
            return false;
        for (int i = 0; i < childrenSize; i++) {
            if (children[i].equals(child)) {
                found = true;
            } else if (found) {
                children[i - 1] = children[i];
            }
        }
        if (found) {
            childrenSize--;
            children[childrenSize] = null;
        }
        return found;
    }

    TreeNode<Data> removeChild(int index) {
        if (index >= childrenSize)
            return null;
        TreeNode<Data> value = children[index];
        children[index] = null;
        System.arraycopy(children, index + 1, children, index + 1 - 1, childrenSize - (index + 1));
        childrenSize--;
        children[childrenSize] = null;
        return value;
    }

    int numberOfChildren() {
        return childrenSize;
    }
}
