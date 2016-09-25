package ru.justnero.study.dsmnm.lab01;

import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.paint.Color;

public class BinTree {

    protected Node root;


    public BinTree() {
    }

    public boolean add(TData data) {
        if (root == null) {
            root = new Node(data, null);
            return true;
        } else {
            Node cur = root;
            while (true) {
                int comp = cur.getData().toString().compareTo(data.toString());
                if (comp == 0) {
                    return false;
                }
                if (comp < 0) {
                    if (cur.getLeft() == null) {
                        cur.setLeft(new Node(data, cur));
                        return true;
                    } else {
                        cur = cur.getLeft();
                    }
                } else {
                    if (cur.getRight() == null) {
                        cur.setRight(new Node(data, cur));
                        return true;
                    } else {
                        cur = cur.getRight();
                    }
                }
            }
        }
    }

    public Node find(String name) {
        Node cur = root;
        while (cur != null) {
            if (cur.getData().toString().equalsIgnoreCase(name)) {
                return cur;
            }
            if (cur.getData().toString().compareTo(name) < 0) {
                cur = cur.getLeft();
            } else {
                cur = cur.getRight();
            }
        }
        return null;
    }

    public void remove(TData target) {
        Node el = find(target.getName());
        Node parent = el.getParent();
        boolean wasRoot = false;
        if (parent == null) {
            wasRoot = true;
            parent = new Node();
            parent.setLeft(el);
            el.setParent(parent);
        }
        if (el.getLeft() == null && el.getRight() == null) {
            if (parent.getLeft() == el) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        } else if (el.getLeft() != null && el.getRight() == null) {
            el.setData(el.getLeft().getData());
            el.getLeft().setParent(null);
            el.setLeft(null);
        } else if (el.getLeft() == null && el.getRight() != null) {
            el.setData(el.getRight().getData());
            el.getRight().setParent(null);
            el.setRight(null);
        } else {
            Node rl = el.getRight();
            while (rl.getLeft() != null) {
                rl = rl.getLeft();
            }
            el.setData(rl.getData());
            Node tmp = rl.getParent();
            if (tmp == el) {
                tmp.setRight(rl.getRight());
            } else {
                tmp.setLeft(rl.getRight());
            }
            rl.setParent(null);
            if (rl.getRight() != null) {
                rl.setParent(tmp);
            }
        }
        if (wasRoot) {
            root = parent.getLeft();
            root.setParent(null);
        }
    }

    public void clear() {
        root = null;
    }

    public TreeItem<TData> convert() {
        if (root != null) {
            TreeItem<TData> tmp = convert(root);
            tmp.setGraphic(getIcon("ROOT"));
            return tmp;
        }
        return null;
    }

    public TreeItem<TData> convert(Node cur) {
        if (cur == null) {
            return null;
        }
        TreeItem<TData> treeItem = new TreeItem<>(cur.data),
                tmp;

        if (cur.getLeft() != null) {
            tmp = convert(cur.getLeft());
            tmp.setGraphic(getIcon("L"));
            treeItem.getChildren().add(tmp);
        }
        if (cur.getRight() != null) {
            tmp = convert(cur.getRight());
            tmp.setGraphic(getIcon("R"));
            treeItem.getChildren().add(tmp);
        }
        return treeItem;
    }

    private javafx.scene.Node getIcon(String dir) {
        Label label = new Label(dir);
        label.setTextFill(Color.RED);
        return label;
    }

    public class Node {
        private Node left = null;
        private Node right = null;
        private Node parent;
        private TData data;

        private Node() {

        }

        public Node(TData data, Node parent) {
            this.data = data;
            this.parent = parent;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public TData getData() {
            return data;
        }

        public void setData(TData data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return data != null ? data.toString() : "null";
        }
    }
}
