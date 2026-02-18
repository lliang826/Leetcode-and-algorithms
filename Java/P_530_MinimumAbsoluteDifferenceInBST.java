import java.util.ArrayList;

import data_structures.TreeNode;

public class P_530_MinimumAbsoluteDifferenceInBST {
    /*
    DFS approach. Since this is a binary search tree (all node values are ordered), we perform in-order
    traversal of the tree and store all node values in a list. Then we iterate through the list and
    calculate the minimum absolute difference by comparing adjacent values in the list.
    
    Time: O(n + n) => O(n), we visit all nodes in the tree and then iterate through the list of node values
    Space: O(h + n) => O(n), DFS requires O(h) space for the call stack, where h is the height of the tree.
    We also have O(n) space for the list of node values, where n is the number of nodes in the tree. 
    */
    class Solution {
        public int getMinimumDifference(TreeNode root) {
            ArrayList<Integer> list = new ArrayList<>();
            dfs(root, list);
            int min = Integer.MAX_VALUE;

            for (int i = 0; i < list.size() - 1; i++) {
                min = Math.min(min, list.get(i + 1) - list.get(i));
            }
            return min;
        }

        private void dfs(TreeNode root, ArrayList<Integer> list) {
            if (root == null) {
                return;
            }

            dfs(root.left, list);
            list.add(root.val);
            dfs(root.right, list);
        }
    }

    /*
    Optimized DFS approach. Since this is a BST, we can perform the adjacent value comparison while we perform
    the in-order traversal on the tree. This means we don't need the additional loop through the node values,
    we only have to iterate through the tree once.
    We can keep track of the previous node value during the in-order traversal, and calculate the minimum
    absolute difference by comparing the current node value with the previous node value.
    
    Time: O(n), we visit all nodes in the tree once
    Space: O(h), DFS requires O(h) space for the call stack, where h is the height of the tree. In the worst
    case, the tree is a vertical straight line, which means all the nodes are stored in the call stack.
    Therefore, the space complexity would be O(n) in the worst case. In the best case, the tree is balanced,
    and the height would be log(n), leading to a space complexity of O(log n).
    */
    class Solution2 {
        private TreeNode prev = null;
        private int min = Integer.MAX_VALUE;

        public int getMinimumDifference(TreeNode root) {
            dfs(root);
            return min;
        }

        private void dfs(TreeNode root) {
            if (root == null) {
                return;
            }

            dfs(root.left);
            if (prev != null) {
                min = Math.min(min, root.val - prev.val);
            }
            prev = root;
            dfs(root.right);
        }
    }

    public static void main(String[] args) {
        P_530_MinimumAbsoluteDifferenceInBST solver = new P_530_MinimumAbsoluteDifferenceInBST();

        Object[][] tests = new Object[][] {
                { new Integer[] { 4, 2, 6, 1, 3 }, 1 },
                { new Integer[] { 1, 0, 48, null, null, 12, 49 }, 1 },
                { new Integer[] { 5, 3, 7, 2, 4, 6, 8 }, 1 },
                { new Integer[] { 10, 5, 15, null, null, 13, 20 }, 2 },
                { new Integer[] { 1, null, 3, 2 }, 1 },
                { new Integer[] { 5, 3, 8, 1, 4, 7, 10 }, 1 },
                { new Integer[] { 100, 50, 150, 25, 75, 125, 175 }, 25 },
                { new Integer[] { 1, null, 5, 3 }, 2 },
                { new Integer[] { 27, null, 34, null, 58, 50 }, 7 },
                { new Integer[] { 90, 69, null, 49, 89, null, 52 }, 1 }
        };

        System.out.println("Running tests for P_530_MinimumAbsoluteDifferenceInBST\n");

        int pass1 = 0;
        int pass2 = 0;

        for (int i = 0; i < tests.length; i++) {
            Integer[] values = (Integer[]) tests[i][0];
            int expected = (int) tests[i][1];

            TreeNode root1 = TreeNode.buildTree(values);
            TreeNode root2 = TreeNode.buildTree(values);

            Solution sol1 = solver.new Solution();
            Solution2 sol2 = solver.new Solution2();

            int actual1 = sol1.getMinimumDifference(root1);
            int actual2 = sol2.getMinimumDifference(root2);

            boolean ok1 = expected == actual1;
            boolean ok2 = expected == actual2;

            if (ok1)
                pass1++;
            if (ok2)
                pass2++;

            System.out.printf("Test %d: expected=%d => method1=%d (%s), method2=%d (%s)\n",
                    i + 1, expected, actual1, (ok1 ? "PASS" : "FAIL"), actual2, (ok2 ? "PASS" : "FAIL"));
        }

        System.out.printf("\nSummary: method1=%d/%d, method2=%d/%d tests passed.\n",
                pass1, tests.length, pass2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Overall Summary:");
        System.out.printf("Solution: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("Solution2: %d/%d tests passed\n", pass2, tests.length);
    }
}
