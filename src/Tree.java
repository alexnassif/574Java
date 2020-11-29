import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class Tree {
    private Tree left;
    private Tree right;
    private int value;

    public Tree(int value) {
        this.value = value;
    }

    public Tree insert(Tree tree, int value) {

        Tree current = tree;
        Tree previous = null;
        while (current != null) {
            previous = current;
            if (value < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        current = new Tree(value);
        if (value > previous.value) {
            previous.right = current;
        } else {
            previous.left = current;
        }
        return tree;
    }

    public static Tree createTree() {
        Tree tree = new Tree(0);

        for (int i = 1; i < 100000; i++) {
            tree = tree.insert(tree, i);
        }

        return tree;
    }

    private void inorder(Tree tree, List<Integer> list) {
        if (tree == null)
            return;


        Stack<Tree> s = new Stack<Tree>();
        Tree curr = tree;
        while (curr != null || s.size() > 0) {
            while (curr != null) {
                s.push(curr);
                curr = curr.left;
            }
            curr = s.pop();

            list.add(curr.value);

            curr = curr.right;
        }
    }

    public static void main(String[] args) throws InterruptedException {


        Tree tree = Tree.createTree();
        Tree tree1 = Tree.createTree();
        List<Integer> ch = new ArrayList<>();
        List<Integer> ch1 = new ArrayList<>();
        final long start = System.currentTimeMillis();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                tree.inorder(tree, ch);
            }
        });

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                tree1.inorder(tree1, ch1);
            }
        });

        t.start();
        t1.start();
        t.join();
        t1.join();

        for (Integer num : ch)
            System.out.println(num);
        for (Integer num : ch1)
            System.out.println(num);

        final long end = System.currentTimeMillis();
        long finalTime = end - start;

        System.out.println(finalTime / 1000.0);
    }
}
