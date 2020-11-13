package com.romarjozeka;

public class Main {
    public static void main(String[] args) {

        BST tree = new BST();

        int arr[] = {-10, -3, 0, 5, 9};

        tree.sortedArrayToBST(arr);

        tree.levelOrderTraversal(tree.getRoot());



    }
}
