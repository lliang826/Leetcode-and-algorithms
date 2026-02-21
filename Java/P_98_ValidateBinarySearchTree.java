import java.util.ArrayList;

import data_structures.TreeNode;

public class P_98_ValidateBinarySearchTree {
    /*
    DFS approach. Since this is a BST, we can perform an in-order traversal and store all the node values
    into a list. If the list values are not in ascending order, then the tree is not a valid BST.
    
    Time: O(n), where n is the number of nodes in the tree, we visit every node once.
    Space: O(n), we store all node values in a list
    */
    class Solution {
        public boolean isValidBST(TreeNode root) {
            ArrayList<Integer> list = new ArrayList<>();
            this.dfs(root, list);

            for (int i = 1; i < list.size(); i++) {
                if (list.get(i) <= list.get(i - 1)) {
                    return false;
                }
            }

            return true;
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
    DFS approach. In a BST definition, any values in the left subtree of a node must be less than the node's
    value, and any values in the right subtree of a node must be greater than the node's value. So, we can
    use two variables to keep track of the minimum and maximum values for each subtree.
    Since node values can be Integer.MIN_VALUE and Integer.MAX_VALUE, we can use long data type's minimum and
    maximum values to avoid overflow issues.
    
    Time: O(n), where n is the number of nodes in the tree, we visit every node once. More efficient than the
    first solution since we don't need a second pass to check the values in the list.
    Space: O(h), where h is the height of the tree, we use space for the recursion stack. In the worst case,
    when the tree is skewed, the height of the tree can be n, so the space complexity can be O(n). In the best
    case scenario, when the tree is balanced, the height of the tree can be log(n), so the space complexity
    can be O(log(n)).
    */
    class Solution2 {
        public boolean isValidBST(TreeNode root) {
            return this.dfs(root, Long.MIN_VALUE, Long.MAX_VALUE);
        }

        private boolean dfs(TreeNode root, long min, long max) {
            if (root == null) {
                return true;
            }

            if (root.val <= min || root.val >= max) {
                return false;
            }

            boolean left = dfs(root.left, min, root.val);
            boolean right = dfs(root.right, root.val, max);

            return left && right;
        }
    }

    public static void main(String[] args) {
        P_98_ValidateBinarySearchTree outer = new P_98_ValidateBinarySearchTree();
        Solution sol1 = outer.new Solution();
        Solution2 sol2 = outer.new Solution2();

        Object[][] tests = new Object[][] {
                // Basic valid BSTs
                { new Integer[] { 2, 1, 3 }, true, "Simple valid BST" },
                { new Integer[] { 5, 1, 7, null, null, 6, 8 }, true, "Right subtree all greater" },
                { new Integer[] { 1, null, 2, null, 3 }, true, "Right-skewed increasing" },
                { new Integer[] { -2147483648, null, 2147483647 }, true, "Handles int min/max" },

                // Invalid BSTs (subtree violations)
                { new Integer[] { 5, 1, 4, null, null, 3, 6 }, false, "Right subtree has smaller value" },
                { new Integer[] { 5, 4, 6, null, null, 3, 7 }, false, "Deep violation in right subtree" },
                { new Integer[] { 32, 26, 47, 19, null, null, 56, null, 27 }, false,
                        "Classic LC counterexample (27 in left subtree of 32 via 26->19->27)" },
                { new Integer[] { 1, 1 }, false, "Duplicate on left" },
                { new Integer[] { 2, 2, 2 }, false, "All duplicates" },
                { new Integer[] { 10, 5, 15, null, null, 6, 20 }, false, "6 in right subtree but < 10" },

                // Edge / empty
                { new Integer[] {}, true, "Empty tree" },
                { new Integer[] { 42 }, true, "Single node" },
        };

        System.out.println("Running tests for P_98_ValidateBinarySearchTree.Solution (in-order list)\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            Integer[] arr = (Integer[]) tests[i][0];
            boolean expected = (boolean) tests[i][1];
            String label = (String) tests[i][2];
            TreeNode root = TreeNode.buildTree(arr);
            boolean actual = sol1.isValidBST(root);
            boolean ok = expected == actual;
            if (ok)
                pass1++;
            System.out.printf("Test %d: %-60s input=%s expected=%s actual=%s => %s\n", i + 1,
                    label,
                    java.util.Arrays.toString(arr), expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_98_ValidateBinarySearchTree.Solution2 (min/max bounds)\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            Integer[] arr = (Integer[]) tests[i][0];
            boolean expected = (boolean) tests[i][1];
            String label = (String) tests[i][2];
            TreeNode root = TreeNode.buildTree(arr);
            boolean actual = sol2.isValidBST(root);
            boolean ok = expected == actual;
            if (ok)
                pass2++;
            System.out.printf("Test %d: %-60s input=%s expected=%s actual=%s => %s\n", i + 1,
                    label,
                    java.util.Arrays.toString(arr), expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("Solution (in-order list): %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("Solution2 (min/max bounds): %d/%d tests passed\n", pass2, tests.length);
    }
}

