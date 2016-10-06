package ru.justnero.study.dsmnm.lab02;

import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.paint.Color;

class BinTree implements ITree {

    private TreeNode root;

    BinTree() {
    }

    @Override
    public int add(TData data) {
        if (root == null) {
            root = new TreeNode(data);
            return SUCCESS;
        } else {
            TreeNode cur = root;
            while (true) {
                int comp = data.compareTo(cur.getData());
                if (comp == 0) {
                    return DUPLICATE;
                }
                if (comp < 0) {
                    if (cur.getLeft() == null) {
                        cur.setLeft(new TreeNode(data));
                        return SUCCESS;
                    } else {
                        cur = cur.getLeft();
                    }
                } else {
                    if (cur.getRight() == null) {
                        cur.setRight(new TreeNode(data));
                        return SUCCESS;
                    } else {
                        cur = cur.getRight();
                    }
                }
            }
        }
    }

    @Override
    public TreeNode find(TData data) {
        TreeNode cur = root;
        while (cur != null) {
            if (data.compareTo(cur.getData()) == 0) {
                return cur;
            }
            if (data.compareTo(cur.getData()) < 0) {
                cur = cur.getLeft();
            } else {
                cur = cur.getRight();
            }
        }
        return null;
    }

    @Override
    public int remove(TData val) {
        if (val == null) {
            return NOT_FOUND;
        }
        root = remove(root, val);
        return SUCCESS;
    }

    private TreeNode remove(TreeNode tree, TData data) {
        if (tree == null) {
            return null;
        }

        if (data.compareTo(tree.getData()) < 0) {
            tree.setLeft(remove(tree.getLeft(), data));
        } else if (data.compareTo(tree.getData()) > 0) {
            tree.setRight(remove(tree.getRight(), data));
        } else if (tree.getLeft() != null) {
            tree.setData(findMax(tree.getLeft()).getData());
            remove(tree.getLeft(), tree.getData());
        } else {
            tree = (tree.getLeft() != null) ? tree.getLeft() : tree.getRight();
        }

        return tree;
    }

    private TreeNode findMax(TreeNode tree) {
        if (tree == null) {
            return null;
        }

        while (tree.getRight() != null) {
            tree = tree.getRight();
        }
        return tree;
    }

    public void clear() {
        root = null;
    }

    public TreeItem<TreeNode> convert() {
        if (root != null) {
            TreeItem<TreeNode> tmp = convert(root);
            tmp.setGraphic(getIcon("ROOT"));
            return tmp;
        }
        return null;
    }

    private TreeItem<TreeNode> convert(TreeNode cur) {
        if (cur == null) {
            return null;
        }
        TreeItem<TreeNode> treeItem = new TreeItem<>(cur),
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
}
