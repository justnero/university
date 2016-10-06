package ru.justnero.study.dsmnm.lab02;

import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.paint.Color;

class AVLTree implements ITree {

    private AVLTreeNode root = null;

    @Override
    public int add(TData data) {
        try {
            root = add(root, data);
            return SUCCESS;
        } catch (Exception ex) {
            return DUPLICATE;
        }
    }

    private AVLTreeNode add(AVLTreeNode tree, TData data) throws Exception {
        if (tree == null) {
            tree = new AVLTreeNode(data);
        } else if (data.compareTo(tree.getData()) < 0) {
            tree.setLeft(add(tree.getLeft(), data));
            if (height(tree.getLeft()) - height(tree.getRight()) == 2) {
                if (data.compareTo(tree.getLeft().getData()) < 0) {
                    tree = rotateWithLeftChild(tree);
                } else {
                    tree = doubleWithLeftChild(tree);
                }
            }
        } else if (data.compareTo(tree.getData()) > 0) {
            tree.setRight(add(tree.getRight(), data));

            if (height(tree.getRight()) - height(tree.getLeft()) == 2)
                if (data.compareTo(tree.getRight().getData()) > 0) {
                    tree = rotateWithRightChild(tree);
                } else {
                    tree = doubleWithRightChild(tree);
                }
        } else {
            throw new Exception("Attempting to insert duplicate value");
        }

        tree.setHeight(Math.max(height(tree.getLeft()), height(tree.getRight())) + 1);
        return tree;
    }

    private AVLTreeNode rotateWithLeftChild(AVLTreeNode k2) {
        AVLTreeNode k1 = k2.getLeft();

        k2.setLeft(k1.getRight());
        k1.setRight(k2);

        k2.setHeight(Math.max(height(k2.getLeft()), height(k2.getRight())) + 1);
        k1.setHeight(Math.max(height(k1.getLeft()), k2.getHeight()) + 1);

        return (k1);
    }

    private AVLTreeNode doubleWithLeftChild(AVLTreeNode k3) {
        k3.setLeft(rotateWithRightChild(k3.getLeft()));
        return rotateWithLeftChild(k3);
    }

    private AVLTreeNode rotateWithRightChild(AVLTreeNode k1) {
        AVLTreeNode k2 = k1.getRight();

        k1.setRight(k2.getLeft());
        k2.setLeft(k1);

        k1.setHeight(Math.max(height(k1.getLeft()), height(k1.getRight())) + 1);
        k2.setHeight(Math.max(height(k2.getRight()), k1.getHeight()) + 1);

        return k2;
    }

    private AVLTreeNode doubleWithRightChild(AVLTreeNode k1) {
        k1.setRight(rotateWithLeftChild(k1.getRight()));
        return rotateWithRightChild(k1);
    }

    private int height(AVLTreeNode tree) {
        return tree == null ? -1 : tree.getHeight();
    }

    @Override
    public TreeNode find(TData data) {
        AVLTreeNode cur = root;
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

    private AVLTreeNode remove(AVLTreeNode tree, TData data) {
        if (tree == null) {
            return null;
        }

        if (data.compareTo(tree.getData()) < 0) {
            tree.setLeft(remove(tree.getLeft(), data));
            int l = tree.getLeft() != null ? tree.getLeft().getHeight() : 0;

            if ((tree.getRight() != null) && (tree.getRight().getHeight() - l >= 2)) {
                int rightHeight = tree.getRight().getRight() != null ? tree.getRight().getRight().getHeight() : 0;
                int leftHeight = tree.getRight().getLeft() != null ? tree.getRight().getLeft().getHeight() : 0;

                if (rightHeight >= leftHeight)
                    tree = rotateWithLeftChild(tree);
                else
                    tree = doubleWithRightChild(tree);
            }
        } else if (data.compareTo(tree.getData()) > 0) {
            tree.setRight(remove(tree.getRight(), data));
            int r = tree.getRight() != null ? tree.getRight().getHeight() : 0;
            if ((tree.getLeft() != null) && (tree.getLeft().getHeight() - r >= 2)) {
                int leftHeight = tree.getLeft().getLeft() != null ? tree.getLeft().getLeft().getHeight() : 0;
                int rightHeight = tree.getLeft().getRight() != null ? tree.getLeft().getRight().getHeight() : 0;
                if (leftHeight >= rightHeight)
                    tree = rotateWithRightChild(tree);
                else
                    tree = doubleWithLeftChild(tree);
            }
        } else if (tree.getLeft() != null) {
            tree.setData(findMax(tree.getLeft()).getData());
            remove(tree.getLeft(), tree.getData());

            if ((tree.right != null) && (tree.getRight().getHeight() - tree.getLeft().getHeight() >= 2)) {
                int rightHeight = tree.getRight().getRight() != null ? tree.getRight().getRight().getHeight() : 0;
                int leftHeight = tree.getRight().getLeft() != null ? tree.getRight().getLeft().getHeight() : 0;

                if (rightHeight >= leftHeight)
                    tree = rotateWithLeftChild(tree);
                else
                    tree = doubleWithRightChild(tree);
            }
        } else
            tree = (tree.getLeft() != null) ? tree.getLeft() : tree.getRight();

        if (tree != null) {
            int leftHeight = tree.getLeft() != null ? tree.getLeft().getHeight() : 0;
            int rightHeight = tree.getRight() != null ? tree.getRight().getHeight() : 0;
            tree.height = Math.max(leftHeight, rightHeight) + 1;
        }
        return tree;
    }

    private AVLTreeNode findMax(AVLTreeNode tree) {
        if (tree == null) {
            return null;
        }

        while (tree.getRight() != null) {
            tree = tree.getRight();
        }
        return tree;
    }


    @Override
    public void clear() {
        root = null;
    }

    @Override
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

    private class AVLTreeNode extends TreeNode {

        int height = 0;

        AVLTreeNode(TData data) {
            this(data, 0);
        }

        AVLTreeNode(TData data, int height) {
            super(data);
            this.height = height;
        }

        int getHeight() {
            return height;
        }

        void setHeight(int height) {
            this.height = height;
        }

        @Override
        public AVLTreeNode getLeft() {
            return (AVLTreeNode) super.getLeft();
        }

        @Override
        public AVLTreeNode getRight() {
            return (AVLTreeNode) super.getRight();
        }

        @Override
        public String toString() {
            return String.valueOf(height) + " " + super.toString();
        }
    }

}
