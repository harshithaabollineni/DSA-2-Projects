class Node {
    int key, height;
    Node left, right;
    Node(int k) { key = k; height = 1; }
}

class AVL {
    Node root;

    int h(Node n) { return n == null ? 0 : n.height; }
    int bal(Node n) { return n == null ? 0 : h(n.left) - h(n.right); }

    Node rRotate(Node y) {
        Node x = y.left, t = x.right;
        x.right = y; y.left = t;
        y.height = 1 + Math.max(h(y.left), h(y.right));
        x.height = 1 + Math.max(h(x.left), h(x.right));
        return x;
    }

    Node lRotate(Node x) {
        Node y = x.right, t = y.left;
        y.left = x; x.right = t;
        x.height = 1 + Math.max(h(x.left), h(x.right));
        y.height = 1 + Math.max(h(y.left), h(y.right));
        return y;
    }

    Node insert(Node n, int k) {
        if (n == null) return new Node(k);
        if (k < n.key) n.left = insert(n.left, k);
        else if (k > n.key) n.right = insert(n.right, k);
        else return n;

        n.height = 1 + Math.max(h(n.left), h(n.right));
        int b = bal(n);

        if (b > 1 && k < n.left.key) return rRotate(n);
        if (b < -1 && k > n.right.key) return lRotate(n);
        if (b > 1 && k > n.left.key) { n.left = lRotate(n.left); return rRotate(n); }
        if (b < -1 && k < n.right.key) { n.right = rRotate(n.right); return lRotate(n); }

        return n;
    }

    Node min(Node n) {
        while (n.left != null) n = n.left;
        return n;
    }

    Node delete(Node n, int k) {
        if (n == null) return n;
        if (k < n.key) n.left = delete(n.left, k);
        else if (k > n.key) n.right = delete(n.right, k);
        else {
            if (n.left == null || n.right == null)
                n = (n.left != null) ? n.left : n.right;
            else {
                Node t = min(n.right);
                n.key = t.key;
                n.right = delete(n.right, t.key);
            }
        }
        if (n == null) return n;

        n.height = 1 + Math.max(h(n.left), h(n.right));
        int b = bal(n);

        if (b > 1 && bal(n.left) >= 0) return rRotate(n);
        if (b > 1 && bal(n.left) < 0) { n.left = lRotate(n.left); return rRotate(n); }
        if (b < -1 && bal(n.right) <= 0) return lRotate(n);
        if (b < -1 && bal(n.right) > 0) { n.right = rRotate(n.right); return lRotate(n); }

        return n;
    }

    boolean search(Node n, int k) {
        if (n == null) return false;
        if (k == n.key) return true;
        return k < n.key ? search(n.left, k) : search(n.right, k);
    }

    void inorder(Node n) {
        if (n != null) {
            inorder(n.left);
            System.out.print(n.key + " ");
            inorder(n.right);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        AVL t = new AVL();
        int[] arr = {50, 20, 70, 10, 30, 60, 80};

        for (int x : arr) t.root = t.insert(t.root, x);

        System.out.print("Inorder: ");
        t.inorder(t.root);

        System.out.println("\nSearch 30: " + t.search(t.root, 30));

        t.root = t.delete(t.root, 20);

        System.out.print("After Delete: ");
        t.inorder(t.root);
    }
}