package ru.justnero.study.dsmnm.lab02;

public class TreeNode {

    protected TreeNode left = null;
    protected TreeNode right = null;
    protected TData data;

    protected TreeNode() {

    }

    public TreeNode(TData data) {
        this.data = data;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
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
