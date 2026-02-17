import data_structures.TreeNode;

public class P_1448_CountGoodNodesInBinaryTree {
    /*
     * DFS approach. We can keep track of the maximum value seen so far on the path
     * from the root to the current node. If the current node's value is greater
     * than or equal to this maximum, then it is a "good" node. We can then update
     * the maximum value and continue the DFS traversal for the left and right
     * subtrees.
     * 
     * Time: O(n), where n is the number of nodes in the tree, as we need to visit
     * each node once
     * Space: O(h), where h is the height of the tree. In the worst case, the tree
     * is a vertical straight line, which means all the nodes are stored in the call
     * stack. Therefore, the space complexity would be O(n) in the worst case. In
     * the best case, the tree is balanced, and the height would be log(n), leading
     * to a space complexity of O(log n).
     */
    public int goodNodes(TreeNode root) {
        return dfs(root, Integer.MIN_VALUE);
    }

    public int dfs(TreeNode root, int max) {
        if (root == null) {
            return 0;
        }

        int curr = 0;
        if (root.val >= max) {
            curr = 1;
        }

        max = Math.max(root.val, max);
        int left = dfs(root.left, max);
        int right = dfs(root.right, max);
        return left + right + curr;
    }

    public static void main(String[] args) {
        P_1448_CountGoodNodesInBinaryTree solver = new P_1448_CountGoodNodesInBinaryTree();

        // Test case 1: [3,1,4,3,null,1,5]
        TreeNode t1 = new TreeNode(3);
        t1.left = new TreeNode(1);
        t1.right = new TreeNode(4);
        t1.left.left = new TreeNode(3);
        t1.right.left = new TreeNode(1);
        t1.right.right = new TreeNode(5);

        // Test case 2: [3,3,null,4,2]
        TreeNode t2 = new TreeNode(3);
        t2.left = new TreeNode(3);
        t2.left.left = new TreeNode(4);
        t2.left.right = new TreeNode(2);

        // Test case 3: Single node
        TreeNode t3 = new TreeNode(1);

        // Test case 4: Null tree
        TreeNode t4 = null;

        // Test case 5: All increasing values (all good)
        TreeNode t5 = new TreeNode(1);
        t5.left = new TreeNode(2);
        t5.right = new TreeNode(3);
        t5.left.left = new TreeNode(4);
        t5.left.right = new TreeNode(5);

        // Test case 6: All decreasing values (only root is good)
        TreeNode t6 = new TreeNode(5);
        t6.left = new TreeNode(4);
        t6.right = new TreeNode(3);
        t6.left.left = new TreeNode(2);
        t6.left.right = new TreeNode(1);

        // Test case 7: Left skewed tree with increasing values
        TreeNode t7 = new TreeNode(1);
        t7.left = new TreeNode(2);
        t7.left.left = new TreeNode(3);
        t7.left.left.left = new TreeNode(4);

        // Test case 8: Two nodes
        TreeNode t8 = new TreeNode(5);
        t8.left = new TreeNode(3);

        // Test case 9: Two nodes with equal values
        TreeNode t9 = new TreeNode(5);
        t9.left = new TreeNode(5);

        Object[][] tests = new Object[][] {
            { t1, 4 }, // nodes 3,3,4,5 are good
            { t2, 3 }, // nodes 3,3,4 are good
            { t3, 1 }, // single node is good
            { t4, 0 }, // null tree
            { t5, 5 }, // all nodes are good
            { t6, 1 }, // only root is good
            { t7, 4 }, // all nodes are good
            { t8, 1 }, // only root is good
            { t9, 2 }, // both nodes are good (equal to max)
        };

        System.out.println("Running tests for P_1448_CountGoodNodesInBinaryTree.goodNodes\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            TreeNode input = (TreeNode) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.goodNodes(input);
            boolean ok = expected == actual;
            if (ok) pass++;
            System.out.printf("Test %d: tree=%s => expected=%d, actual=%d => %s\n",
                i + 1, TreeNode.treeToString(input), expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("goodNodes: %d/%d tests passed\n", pass, tests.length);
    }
}
