import data_structures.TreeNode;

public class P_543_DiameterOfBinaryTree {
    /*
     * DFS approach. We traverse the tree and calculate the depth of each subtree.
     * The diameter at any node is the sum of the depths of its left and right
     * subtrees. The key is to keep track of the maximum diameter found during the
     * traversal using a class variable.
     * 
     * Time: O(n), where n is the number of nodes in the tree, as we need to visit
     * each node once
     * Space: O(h), where h is the height of the tree. In the worst case, the tree
     * is a vertical straight line, which means all the nodes are stored in the call
     * stack. Therefore, the space complexity would be O(n) in the worst case. In
     * the best case, the tree is balanced, and the height would be log(n), leading
     * to a space complexity of O(log n).
     */
    class Solution {
        int diameter = 0;

        public int diameterOfBinaryTree(TreeNode root) {
            dfs(root);
            return diameter;
        }

        private int dfs(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int left = dfs(root.left);
            int right = dfs(root.right);
            diameter = Math.max(diameter, left + right);
            return 1 + Math.max(left, right);
        }
    }

    public static void main(String[] args) {
        P_543_DiameterOfBinaryTree outer = new P_543_DiameterOfBinaryTree();

        // Helper to build trees
        TreeNode t1 = new TreeNode(1);
        t1.left = new TreeNode(2);
        t1.right = new TreeNode(3);
        t1.left.left = new TreeNode(4);
        t1.left.right = new TreeNode(5);

        TreeNode t2 = new TreeNode(1);
        t2.left = new TreeNode(2);

        TreeNode t3 = new TreeNode(1);
        t3.right = new TreeNode(2);

        TreeNode t4 = new TreeNode(1);

        TreeNode t5 = null;

        TreeNode t6 = new TreeNode(1);
        t6.left = new TreeNode(2);
        t6.left.left = new TreeNode(3);
        t6.left.left.left = new TreeNode(4);

        TreeNode t7 = new TreeNode(1);
        t7.left = new TreeNode(2);
        t7.right = new TreeNode(3);
        t7.right.right = new TreeNode(4);
        t7.right.right.right = new TreeNode(5);

        Object[][] tests = new Object[][] {
            // Format: {tree, expected diameter}
            { t1, 3 }, // [4,2,1,3] or [5,2,1,3]
            { t2, 1 }, // [2,1]
            { t3, 1 }, // [1,2]
            { t4, 0 }, // single node
            { t5, 0 }, // null tree
            { t6, 3 }, // straight line left
            { t7, 4 }, // straight line right
        };

        System.out.println("Running tests for P_543_DiameterOfBinaryTree.diameterOfBinaryTree\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            Solution s = outer.new Solution(); // reset diameter for each test
            TreeNode input = (TreeNode) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = s.diameterOfBinaryTree(input);
            boolean ok = expected == actual;
            if (ok) pass++;
            System.out.printf("Test %d: tree=%s => expected=%d, actual=%d => %s\n",
                i + 1, TreeNode.treeToString(input), expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("diameterOfBinaryTree: %d/%d tests passed\n", pass, tests.length);
    }
}
