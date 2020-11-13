package com.romarjozeka;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class BST {


    private Node root;

    BST() {
        this.root = null;
    }


    public Node getRoot() {
        return root;
    }


    public void insert(int value) {

        root = insert(root, value);
    }


    private Node insert(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }
        if (value < current.value) {
            current.left = insert(current.left, value);
        } else if (value > current.value) {
            current.right = insert(current.right, value);
        }
        return current;
    }

    public void inOrderTraversal(Node current) {


        if (current != null) {


            inOrderTraversal(current.left);
            System.out.println(current.value);
            inOrderTraversal(current.right);
        }


    }

    public void preOrderTraversal(Node current) {

        if (current != null) {
            System.out.println(current.value);
            postOrderTraversal(current.left);
            postOrderTraversal(current.right);

        }
    }

    public void postOrderTraversal(Node current) {

        if (current != null) {
            postOrderTraversal(current.left);
            postOrderTraversal(current.right);
            System.out.println(current.value);
        }
    }

    public Node find(int value) {
        return find(root, value);
    }

    private Node find(Node current, int value) {

        if (current.value == value) {
            return current;
        }

        if (value < current.value) {
            return current.left == null ? null : find(current.left, value);
        } else {
            return current.right == null ? null : find(current.right, value);
        }

    }

    public void levelOrderTraversal(Node current) {

        if (current == null) {
            return;
        }

        List<ArrayList<Integer>> list = new ArrayList<>();

        Deque<Node> queue = new ArrayDeque<>();

        queue.offer(current);

        while (!queue.isEmpty()) {
            int size = queue.size();
            ArrayList<Integer> tempList = new ArrayList<>();
            //traverse the current elements in the queue
            for (int i = 0; i < size; i++) {

                Node parent = queue.poll();

                tempList.add(parent.value);


                if (parent.left != null) {
                    queue.offer(parent.left);
                }
                if (parent.right != null) {
                    queue.offer(parent.right);
                }
            }

            list.add(tempList);
        }

        System.out.println(list.toString());
    }

    public int getSize(Node current) {
        if (current == null) {
            return 0;
        }

        return 1 + getSize(current.left) + getSize(current.right);
    }

    public void reverseBST(Node current) {
        //recursive way

        if (current != null) {

            //do swaping
            Node temp = current.right;
            current.right = current.left;
            current.left = temp;

            //repeat with left and right children
            reverseBST(current.left);
            reverseBST(current.right);
        }


        //iterative way

        Deque<Node> queue = new ArrayDeque<>();
        queue.offer(current);

        while (!queue.isEmpty()) {

            Node parent = queue.pop();

            if (parent.left != null) {
                queue.offer(parent.left);
            }
            if (parent.right != null) {
                queue.offer(parent.right);
            }

            Node temp = parent.right;
            parent.right = parent.left;
            parent.left = temp;

        }
    }

    public Integer findMax(Node current) {


        return current.right == null ? current.value : findMax(current.right);

    }

    public Integer findMin(Node current) {


        return current.left == null ? current.value : findMin(current.left);
    }


    public Node delete(Node current, int value) {

        if (current == null) {
            System.out.println("BST is empty");
        } else if (value < current.value) {
            current.left = delete(current.left, value);
        } else if (value > current.value) {
            current.right = delete(current.right, value);

        } else {
            //we found it
            if (current.left == null && current.right == null) {   //check if its a leaf
                current = null;
            } else if (current.left == null) {  //check if right is present
                Node temp = current.right;
                current.value = temp.value;
                current.right = null;
            } else if (current.right == null) { //check if left is present
                Node temp = current.left;
                current.value = temp.value;
                current.left = null;

            } else { //both left and right nodes are present, find the leftmost in the right subtree

                Node temp = current.right;

                while (temp.left != null) {
                    temp = temp.left;
                }

                current.value = temp.value; //replace node to be deleted with leftmost node
                current.right = delete(current.right, temp.value); //delete the leftmost node
            }

        }

        return current;
    }


    public int maxDepth(Node current) {

        //base case
        if (current == null) {
            return 0;
        }

        //step 1
        int l = maxDepth(current.left);
        //step 2
        int r = maxDepth(current.right);

        //step 3
        return Math.max(l, r) + 1;

    }

    public List<Integer> printLeftSide(Node current) {
        List<Integer> list = new ArrayList<>();
        printLeftSide(current, 0, list);

        return list;
    }

    private void printLeftSide(Node current, int treeLevel, List<Integer> list) {

        if (current == null) {
            return;
        }

        if (treeLevel == list.size()) {
            list.add(current.value);
        }

        printLeftSide(current.left, treeLevel + 1, list);
        printLeftSide(current.left, treeLevel + 1, list);


    }

    //Recursive way
    public int minDepthRecursive(Node current) {

        if (current == null) {
            return 0;
        }

        //if one child exists, find Max, if two children, find min between them.
        if (current.left == null || current.right == null) {

            return Math.max(minDepthRecursive(current.left), minDepthRecursive(current.right)) + 1;

        } else {

            return Math.min(minDepthRecursive(current.left), minDepthRecursive(current.right)) + 1;
        }
    }

    public int minDepthLevelOrder(Node root) {

        Deque<Node> queue = new ArrayDeque<>();
        queue.offer(root);
        int level = 1;
        while (!queue.isEmpty()) {

            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node current = queue.poll();

                //nor left or right
                if (current.left == null && current.right == null) {
                    return level;
                }
                if (current.left != null) {

                    queue.offer(current.left);
                }
                if (current.right != null) {

                    queue.offer(current.right);
                }
            }
            level++;


        }
        return level;
    }

    public boolean isBSTValid(Node root) {
        return isBSTValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isBSTValid(Node current, Long min, Long max) {

        if (current == null) {
            return true;
        }

        if (current.value < min || current.value > max) {
            return false;
        }
        return isBSTValid(current.left, min, Long.valueOf(current.value)) && isBSTValid(current.right, Long.valueOf(current.value), max);
    }

    public boolean isBSTBalanced(Node current) {

        if (current == null) {
            return true;
        }

        int l = maxDepth(current.left);
        int r = maxDepth(current.right);


        return (Math.abs(l - r) <= 1) && isBSTBalanced(current.left) && isBSTBalanced(current.right);


    }

    public void constructByPreOrder(int[] preOrderArr) {
        root = constructByPreOrder(preOrderArr, new int[]{preOrderArr.length - 1}, Integer.MAX_VALUE);
    }

    private Node constructByPreOrder(int[] preOrderArr, int[] idx, int max) {

        if (idx[0] == -1 || preOrderArr[idx[0]] > max) return null;

        Node root = new Node(preOrderArr[idx[0]--]);

        root.right = constructByPreOrder(preOrderArr, idx, max);
        root.left = constructByPreOrder(preOrderArr, idx, root.value);


        return root;
    }


    public Node duplicate() {

        return duplicate(root);
    }

    private Node duplicate(Node current) {

        if (current == null) return null;

        Node node = new Node(current.value);

        node.left = duplicate(current.left);
        node.right = duplicate(current.right);

        return node;
    }

    public void sortedArrayToBST(int[] arr) {
        if (arr == null || arr.length == 0) return;
        root = sortedArrayToBST(arr, 0, arr.length - 1);
    }

    private Node sortedArrayToBST(int[] arr, int low, int high) {

        if (low < 0 || high < 0 || low > high ||
                low >= arr.length || high >= arr.length) return null;

        int middle = low + (high - low) / 2;

        Node current = new Node(arr[middle]);

        current.left = sortedArrayToBST(arr, low, middle - 1);

        current.right = sortedArrayToBST(arr, middle + 1, high);

        return current;
    }


    private class Node {
        Node left;
        Node right;
        int value;

        public Node(int value) {
            this.value = value;

        }
    }
}