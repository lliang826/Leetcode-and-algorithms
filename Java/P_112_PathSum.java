import data_structures.TreeNode;

public class P_112_PathSum {
    /*
     * DFS approach. We can use a recursive helper function that takes the current
     * node and the remaining target sum as arguments. At each step, we check if the
     * current node is a leaf node (i.e., it has no left or right children) and if
     * its value equals the remaining target sum. If both conditions are true, we
     * have found a valid path and can return true. Otherwise, we recursively call
     * the helper function on the left and right children of the current node,
     * updating the remaining target sum by subtracting the value of the current
     * node.
     * 
     * Time: O(n), where n is the number of nodes in the tree, as we need to visit
     * each node once
     * Space: O(h), where h is the height of the tree. In the worst case, the tree
     * is a vertical straight line, which means all the nodes are stored in the call
     * stack. Therefore, the space complexity would be O(n) in the worst case. In
     * the best case, the tree is balanced, and the height would be log(n), leading
     * to a space complexity of O(log n).
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }

        if (root.left == null && root.right == null) {
            return targetSum - root.val == 0;
        }

        boolean left = hasPathSum(root.left, targetSum - root.val);
        boolean right = hasPathSum(root.right, targetSum - root.val);
        return left || right;
    }

    public static void main(String[] args) {
        P_112_PathSum solver = new P_112_PathSum();

        // Test case 1: [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
        TreeNode t1 = new TreeNode(5);
        t1.left = new TreeNode(4);
        t1.right = new TreeNode(8);
        t1.left.left = new TreeNode(11);
        t1.left.left.left = new TreeNode(7);
        t1.left.left.right = new TreeNode(2);
        t1.right.left = new TreeNode(13);
        t1.right.right = new TreeNode(4);
        t1.right.right.right = new TreeNode(1);

        // Test case 2: [1,2,3], targetSum = 5
        TreeNode t2 = new TreeNode(1);
        t2.left = new TreeNode(2);
        t2.right = new TreeNode(3);

        // Test case 3: Empty tree
        TreeNode t3 = null;

        // Test case 4: Single node matching
        TreeNode t4 = new TreeNode(1);

        // Test case 5: Single node not matching
        TreeNode t5 = new TreeNode(1);

        // Test case 6: Left path only
        TreeNode t6 = new TreeNode(1);
        t6.left = new TreeNode(2);
        t6.left.left = new TreeNode(3);

        // Test case 7: Right path only
        TreeNode t7 = new TreeNode(1);
        t7.right = new TreeNode(2);
        t7.right.right = new TreeNode(3);

        // Test case 8: Negative values
        TreeNode t8 = new TreeNode(-2);
        t8.right = new TreeNode(-3);

        // Test case 9: Zero sum
        TreeNode t9 = new TreeNode(1);
        t9.left = new TreeNode(-1);

        Object[][] tests = new Object[][] {
            { t1, 22, true },  // path exists: 5->4->11->2
            { t2, 5, false },  // no path sums to 5
            { t3, 0, false },  // empty tree
            { t4, 1, true },   // single node matches
            { t5, 2, false },  // single node doesn't match
            { t6, 6, true },   // left path: 1->2->3
            { t7, 6, true },   // right path: 1->2->3
            { t8, -5, true },  // negative values: -2->-3
            { t9, 0, true },   // zero sum: 1->-1
        };

        System.out.println("Running tests for P_112_PathSum.hasPathSum\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            TreeNode input = (TreeNode) tests[i][0];
            int targetSum = (int) tests[i][1];
            boolean expected = (boolean) tests[i][2];
            boolean actual = solver.hasPathSum(input, targetSum);
            boolean ok = expected == actual;
            if (ok) pass++;
            System.out.printf("Test %d: tree=%s, targetSum=%d => expected=%s, actual=%s => %s\n",
                i + 1, TreeNode.treeToString(input), targetSum, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("hasPathSum: %d/%d tests passed\n", pass, tests.length);
    }
}
