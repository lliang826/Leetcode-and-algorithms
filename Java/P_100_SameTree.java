import data_structures.TreeNode;

public class P_100_SameTree {
    /*
    DFS approach. There are three conditions to check:
    1. Both nodes are null -> return true
    2. One node is null and the other is not -> return false
    3. Both nodes are not null -> check if their values are equal and recursively check their left and right subtrees
    
    Time: O(n), where n is the number of nodes in the tree, as we need to visit each node once
    Space: Space: O(h), where h is the height of the tree. In the worst case, the tree is a vertical straight
    line, which means all the nodes are stored in the call stack. Therefore, the space complexity would
    be O(n) in the worst case. In the best case, the tree is balanced, and the height would be log(n),
    leading to a space complexity of O(log n).
    */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        boolean left = isSameTree(p.left, q.left);
        boolean right = isSameTree(p.right, q.right);
        return p.val == q.val && left && right;
    }

    public static void main(String[] args) {
        P_100_SameTree solver = new P_100_SameTree();

        // Helper to build trees
        TreeNode t1 = new TreeNode(1);
        t1.left = new TreeNode(2);
        t1.right = new TreeNode(3);

        TreeNode t2 = new TreeNode(1);
        t2.left = new TreeNode(2);
        t2.right = new TreeNode(3);

        TreeNode t3 = new TreeNode(1);
        t3.left = new TreeNode(2);

        TreeNode t4 = new TreeNode(1);
        t4.right = new TreeNode(2);

        TreeNode t5 = new TreeNode(1);
        t5.left = new TreeNode(2);
        t5.right = new TreeNode(1);

        TreeNode t6 = new TreeNode(1);
        t6.left = new TreeNode(1);
        t6.right = new TreeNode(2);

        TreeNode t7 = null;
        TreeNode t8 = null;

        TreeNode t9 = new TreeNode(1);
        t9.left = new TreeNode(2);
        t9.right = new TreeNode(3);
        t9.left.left = new TreeNode(4);

        TreeNode t10 = new TreeNode(1);
        t10.left = new TreeNode(2);
        t10.right = new TreeNode(3);
        t10.left.left = new TreeNode(4);

        TreeNode t11 = new TreeNode(1);
        t11.left = new TreeNode(2);
        t11.right = new TreeNode(3);
        t11.left.left = new TreeNode(5);

        Object[][] tests = new Object[][] {
            // Format: {tree1, tree2, expected}
            { t1, t2, true }, // identical trees
            { t1, t3, false }, // one missing right child
            { t3, t4, false }, // left vs right child
            { t5, t6, false }, // different values
            { t7, t8, true }, // both null
            { t1, t7, false }, // one null, one not
            { t9, t10, true }, // larger identical trees
            { t9, t11, false }, // larger, one node differs
            { new TreeNode(0), new TreeNode(0), true }, // single node, same value
            { new TreeNode(0), new TreeNode(1), false }, // single node, different value
        };

        System.out.println("Running tests for P_100_SameTree.isSameTree\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            TreeNode a = (TreeNode) tests[i][0];
            TreeNode b = (TreeNode) tests[i][1];
            boolean expected = (boolean) tests[i][2];
            boolean actual = solver.isSameTree(a, b);
            boolean ok = expected == actual;
            if (ok) pass++;
            System.out.printf("Test %d: expected=%s, actual=%s => %s\n", i + 1, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
    }
}
