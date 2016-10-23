package ru.justnero.study.dsmnm.lab03;

import java.util.Arrays;

import javafx.scene.control.TreeItem;

public class BTreeImpl<T extends Comparable<T>> {

    private int minKeySize = 2;
    private int minChildrenSize = minKeySize + 1;
    private int maxKeySize = 2 * minKeySize;
    private int maxChildrenSize = maxKeySize + 1;

    private TreeNode<T> root = null;
    private int size = 0;

    public BTreeImpl() {
    }

    public BTreeImpl(int order) {
        this.minKeySize = order;
        this.minChildrenSize = minKeySize + 1;
        this.maxKeySize = 2 * minKeySize;
        this.maxChildrenSize = maxKeySize + 1;
    }

    public boolean add(T value) {
        if (root == null) {
            root = new TreeNode<T>(null, maxKeySize, maxChildrenSize);
            root.addKey(value);
        } else {
            TreeNode<T> treeNode = root;
            while (treeNode != null) {
                if (treeNode.numberOfChildren() == 0) {
                    treeNode.addKey(value);
                    if (treeNode.numberOfKeys() <= maxKeySize) {
                        break;
                    }
                    split(treeNode);
                    break;
                }

                T lesser = treeNode.getKey(0);
                if (value.compareTo(lesser) <= 0) {
                    treeNode = treeNode.getChild(0);
                    continue;
                }

                int numberOfKeys = treeNode.numberOfKeys();
                int last = numberOfKeys - 1;
                T greater = treeNode.getKey(last);
                if (value.compareTo(greater) > 0) {
                    treeNode = treeNode.getChild(numberOfKeys);
                    continue;
                }

                for (int i = 1; i < treeNode.numberOfKeys(); i++) {
                    T prev = treeNode.getKey(i - 1);
                    T next = treeNode.getKey(i);
                    if (value.compareTo(prev) > 0 && value.compareTo(next) <= 0) {
                        treeNode = treeNode.getChild(i);
                        break;
                    }
                }
            }
        }

        size++;

        return true;
    }

    private void split(TreeNode<T> treeNodeToSplit) {
        TreeNode<T> treeNode = treeNodeToSplit;
        int numberOfKeys = treeNode.numberOfKeys();
        int medianIndex = numberOfKeys / 2;
        T medianValue = treeNode.getKey(medianIndex);

        TreeNode<T> left = new TreeNode<T>(null, maxKeySize, maxChildrenSize);
        for (int i = 0; i < medianIndex; i++) {
            left.addKey(treeNode.getKey(i));
        }
        if (treeNode.numberOfChildren() > 0) {
            for (int j = 0; j <= medianIndex; j++) {
                TreeNode<T> c = treeNode.getChild(j);
                left.addChild(c);
            }
        }

        TreeNode<T> right = new TreeNode<T>(null, maxKeySize, maxChildrenSize);
        for (int i = medianIndex + 1; i < numberOfKeys; i++) {
            right.addKey(treeNode.getKey(i));
        }
        if (treeNode.numberOfChildren() > 0) {
            for (int j = medianIndex + 1; j < treeNode.numberOfChildren(); j++) {
                TreeNode<T> c = treeNode.getChild(j);
                right.addChild(c);
            }
        }

        if (treeNode.parent == null) {
            TreeNode<T> newRoot = new TreeNode<T>(null, maxKeySize, maxChildrenSize);
            newRoot.addKey(medianValue);
            treeNode.parent = newRoot;
            root = newRoot;
            treeNode = root;
            treeNode.addChild(left);
            treeNode.addChild(right);
        } else {
            TreeNode<T> parent = treeNode.parent;
            parent.addKey(medianValue);
            parent.removeChild(treeNode);
            parent.addChild(left);
            parent.addChild(right);

            if (parent.numberOfKeys() > maxKeySize) split(parent);
        }
    }

    public T remove(T value) {
        T removed = null;
        TreeNode<T> treeNode = this.find(value);
        removed = remove(value, treeNode);
        return removed;
    }

    private T remove(T value, TreeNode<T> treeNode) {
        if (treeNode == null) return null;

        T removed = null;
        int index = treeNode.indexOf(value);
        removed = treeNode.removeKey(value);
        if (treeNode.numberOfChildren() == 0) {
            if (treeNode.parent != null && treeNode.numberOfKeys() < minKeySize) {
                this.combined(treeNode);
            } else if (treeNode.parent == null && treeNode.numberOfKeys() == 0) {
                root = null;
            }
        } else {
            TreeNode<T> lesser = treeNode.getChild(index);
            TreeNode<T> greatest = this.getGreatestNode(lesser);
            T replaceValue = this.removeGreatestValue(greatest);
            treeNode.addKey(replaceValue);
            if (greatest.parent != null && greatest.numberOfKeys() < minKeySize) {
                this.combined(greatest);
            }
            if (greatest.numberOfChildren() > maxChildrenSize) {
                this.split(greatest);
            }
        }

        size--;

        return removed;
    }

    private T removeGreatestValue(TreeNode<T> treeNode) {
        T value = null;
        if (treeNode.numberOfKeys() > 0) {
            value = treeNode.removeKey(treeNode.numberOfKeys() - 1);
        }
        return value;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public boolean contains(T value) {
        TreeNode<T> treeNode = find(value);
        return (treeNode != null);
    }

    public TreeNode<T> find(T value) {
        TreeNode<T> treeNode = root;
        while (treeNode != null) {
            T lesser = treeNode.getKey(0);
            if (value.compareTo(lesser) < 0) {
                if (treeNode.numberOfChildren() > 0)
                    treeNode = treeNode.getChild(0);
                else
                    treeNode = null;
                continue;
            }

            int numberOfKeys = treeNode.numberOfKeys();
            int last = numberOfKeys - 1;
            T greater = treeNode.getKey(last);
            if (value.compareTo(greater) > 0) {
                if (treeNode.numberOfChildren() > numberOfKeys)
                    treeNode = treeNode.getChild(numberOfKeys);
                else
                    treeNode = null;
                continue;
            }

            for (int i = 0; i < numberOfKeys; i++) {
                T currentValue = treeNode.getKey(i);
                if (currentValue.compareTo(value) == 0) {
                    return treeNode;
                }

                int next = i + 1;
                if (next <= last) {
                    T nextValue = treeNode.getKey(next);
                    if (currentValue.compareTo(value) < 0 && nextValue.compareTo(value) > 0) {
                        if (next < treeNode.numberOfChildren()) {
                            treeNode = treeNode.getChild(next);
                            break;
                        }
                        return null;
                    }
                }
            }
        }
        return null;
    }

    private TreeNode<T> getGreatestNode(TreeNode<T> treeNodeToGet) {
        TreeNode<T> treeNode = treeNodeToGet;
        while (treeNode.numberOfChildren() > 0) {
            treeNode = treeNode.getChild(treeNode.numberOfChildren() - 1);
        }
        return treeNode;
    }

    private boolean combined(TreeNode<T> treeNode) {
        TreeNode<T> parent = treeNode.parent;
        int index = parent.indexOf(treeNode);
        int indexOfLeftNeighbor = index - 1;
        int indexOfRightNeighbor = index + 1;

        TreeNode<T> rightNeighbor = null;
        int rightNeighborSize = -minChildrenSize;
        if (indexOfRightNeighbor < parent.numberOfChildren()) {
            rightNeighbor = parent.getChild(indexOfRightNeighbor);
            rightNeighborSize = rightNeighbor.numberOfKeys();
        }

        if (rightNeighbor != null && rightNeighborSize > minKeySize) {
            T removeValue = rightNeighbor.getKey(0);
            int prev = getIndexOfPreviousValue(parent, removeValue);
            T parentValue = parent.removeKey(prev);
            T neighborValue = rightNeighbor.removeKey(0);
            treeNode.addKey(parentValue);
            parent.addKey(neighborValue);
            if (rightNeighbor.numberOfChildren() > 0) {
                treeNode.addChild(rightNeighbor.removeChild(0));
            }
        } else {
            TreeNode<T> leftNeighbor = null;
            int leftNeighborSize = -minChildrenSize;
            if (indexOfLeftNeighbor >= 0) {
                leftNeighbor = parent.getChild(indexOfLeftNeighbor);
                leftNeighborSize = leftNeighbor.numberOfKeys();
            }

            if (leftNeighbor != null && leftNeighborSize > minKeySize) {
                T removeValue = leftNeighbor.getKey(leftNeighbor.numberOfKeys() - 1);
                int prev = getIndexOfNextValue(parent, removeValue);
                T parentValue = parent.removeKey(prev);
                T neighborValue = leftNeighbor.removeKey(leftNeighbor.numberOfKeys() - 1);
                treeNode.addKey(parentValue);
                parent.addKey(neighborValue);
                if (leftNeighbor.numberOfChildren() > 0) {
                    treeNode.addChild(leftNeighbor.removeChild(leftNeighbor.numberOfChildren() - 1));
                }
            } else if (rightNeighbor != null && parent.numberOfKeys() > 0) {
                T removeValue = rightNeighbor.getKey(0);
                int prev = getIndexOfPreviousValue(parent, removeValue);
                T parentValue = parent.removeKey(prev);
                parent.removeChild(rightNeighbor);
                treeNode.addKey(parentValue);
                for (int i = 0; i < rightNeighbor.keysSize; i++) {
                    T v = rightNeighbor.getKey(i);
                    treeNode.addKey(v);
                }
                for (int i = 0; i < rightNeighbor.childrenSize; i++) {
                    TreeNode<T> c = rightNeighbor.getChild(i);
                    treeNode.addChild(c);
                }

                if (parent.parent != null && parent.numberOfKeys() < minKeySize) {
                    this.combined(parent);
                } else if (parent.numberOfKeys() == 0) {
                    treeNode.parent = null;
                    root = treeNode;
                }
            } else if (leftNeighbor != null && parent.numberOfKeys() > 0) {
                T removeValue = leftNeighbor.getKey(leftNeighbor.numberOfKeys() - 1);
                int prev = getIndexOfNextValue(parent, removeValue);
                T parentValue = parent.removeKey(prev);
                parent.removeChild(leftNeighbor);
                treeNode.addKey(parentValue);
                for (int i = 0; i < leftNeighbor.keysSize; i++) {
                    T v = leftNeighbor.getKey(i);
                    treeNode.addKey(v);
                }
                for (int i = 0; i < leftNeighbor.childrenSize; i++) {
                    TreeNode<T> c = leftNeighbor.getChild(i);
                    treeNode.addChild(c);
                }

                if (parent.parent != null && parent.numberOfKeys() < minKeySize) {
                    this.combined(parent);
                } else if (parent.numberOfKeys() == 0) {
                    treeNode.parent = null;
                    root = treeNode;
                }
            }
        }

        return true;
    }

    private int getIndexOfPreviousValue(TreeNode<T> treeNode, T value) {
        for (int i = 1; i < treeNode.numberOfKeys(); i++) {
            T t = treeNode.getKey(i);
            if (t.compareTo(value) >= 0)
                return i - 1;
        }
        return treeNode.numberOfKeys() - 1;
    }

    private int getIndexOfNextValue(TreeNode<T> treeNode, T value) {
        for (int i = 0; i < treeNode.numberOfKeys(); i++) {
            T t = treeNode.getKey(i);
            if (t.compareTo(value) >= 0)
                return i;
        }
        return treeNode.numberOfKeys() - 1;
    }


    public int size() {
        return size;
    }


    public boolean validate() {
        if (root == null) return true;
        return validateNode(root);
    }

    private boolean validateNode(TreeNode<T> treeNode) {
        int keySize = treeNode.numberOfKeys();
        if (keySize > 1) {
            for (int i = 1; i < keySize; i++) {
                T p = treeNode.getKey(i - 1);
                T n = treeNode.getKey(i);
                if (p.compareTo(n) > 0)
                    return false;
            }
        }
        int childrenSize = treeNode.numberOfChildren();
        if (treeNode.parent == null) {
            if (keySize > maxKeySize) {
                return false;
            } else if (childrenSize == 0) {
                return true;
            } else if (childrenSize < 2) {
                return false;
            } else if (childrenSize > maxChildrenSize) {
                return false;
            }
        } else {
            if (keySize < minKeySize) {
                return false;
            } else if (keySize > maxKeySize) {
                return false;
            } else if (childrenSize == 0) {
                return true;
            } else if (keySize != (childrenSize - 1)) {
                return false;
            } else if (childrenSize < minChildrenSize) {
                return false;
            } else if (childrenSize > maxChildrenSize) {
                return false;
            }
        }

        TreeNode<T> first = treeNode.getChild(0);
        if (first.getKey(first.numberOfKeys() - 1).compareTo(treeNode.getKey(0)) > 0)
            return false;

        TreeNode<T> last = treeNode.getChild(treeNode.numberOfChildren() - 1);
        if (last.getKey(0).compareTo(treeNode.getKey(treeNode.numberOfKeys() - 1)) < 0)
            return false;

        for (int i = 1; i < treeNode.numberOfKeys(); i++) {
            T p = treeNode.getKey(i - 1);
            T n = treeNode.getKey(i);
            TreeNode<T> c = treeNode.getChild(i);
            if (p.compareTo(c.getKey(0)) > 0)
                return false;
            if (n.compareTo(c.getKey(c.numberOfKeys() - 1)) < 0)
                return false;
        }

        for (int i = 0; i < treeNode.childrenSize; i++) {
            TreeNode<T> c = treeNode.getChild(i);
            boolean valid = this.validateNode(c);
            if (!valid)
                return false;
        }

        return true;
    }

    public TreeItem<String> convert() {
        if (root != null) {
            TreeItem<String> tmp = convert(root);
            return tmp;
        }
        return null;
    }

    private TreeItem<String> convert(TreeNode<T> cur) {
        if (cur == null) {
            return null;
        }
        TreeItem<String> treeItem = new TreeItem<>(Arrays.toString(cur.keys));

        for (TreeNode<T> item : cur.children) {
            treeItem.getChildren().add(convert(item));
        }
        return treeItem;
    }
}