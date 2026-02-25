import java.util.ArrayDeque;
import java.util.Queue;

import data_structures.TreeNode;

public class P_270_ClosestBinarySearchTreeValue {

    /*
    BFS approach. We iterate through all the nodes in the tree: if a node's value is closer to the target than
    the current value, or if both values are equally close but the node value is smaller, we replace the current
    value with the node's value.
    
    Time: O(n), we iterate through all nodes in the tree
    Space: O(w), where w is the longest width of the tree. In the worst case, this is O(n) 
    */
    public int closestValue(TreeNode root, double target) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int res = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            int rowWidth = queue.size();

            for (int i = 0; i < rowWidth; i++) {
                TreeNode node = queue.poll();

                double currDiff = Math.abs(target - node.val);
                double resDiff = Math.abs(target - (double) res);
                if (currDiff < resDiff) {
                    res = node.val;
                } else if (currDiff == resDiff && node.val < res) {
                    res = node.val;
                }

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }

        return res;
    }
    
    /*
    Almost identical approach to the one above, except that we track both the closest value and its difference
    to avoid recalculations. We track these 2 values using a double[] array of size 2, where the first index is
    the value and the second index is the difference. 
    Also, we combine both if statements since they both update the value and its difference.
    
    Same time and space complexities as the solution above.
    */
    public int closestValue2(TreeNode root, double target) {
        if (root == null) {
            return 0;
        }
        
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        double[] res = new double[]{Double.MAX_VALUE, Double.MAX_VALUE};
        
        while (!queue.isEmpty()) {
            int rowWidth = queue.size();
            
            for (int i = 0; i < rowWidth; i++) {
                TreeNode node = queue.poll();
                
                double currDiff = Math.abs(target - node.val);
                if (currDiff < res[1] || currDiff == res[1] && node.val < res[0]) {
                    res[0] = node.val;
                    res[1] = currDiff;
                }
                
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        
        return (int) res[0];
    }

    /*
    Recursive DFS approach. Identical to the first solution, but recursive instead of iterative.
    
    Time: O(n), we iterate through all nodes in the tree
    Space: O(h), where h is the height of the tree. O(n) in the worst case, which is if the tree is skewed in
    a single direction. O(log n) best case, which is when the tree is balanced
    */
    class Solution {
        private int res = Integer.MAX_VALUE;
        private double resDiff = Double.MAX_VALUE;

        public int closestValue(TreeNode root, double target) {
            if (root == null) {
                return 0;
            }
            dfs(root, target);
            return res;
        }

        private void dfs(TreeNode root, double target) {
            if (root == null) {
                return;
            }

            double currDiff = Math.abs(target - (double) root.val);
            if (currDiff < resDiff || currDiff == resDiff && root.val < res) {
                this.res = root.val;
                this.resDiff = currDiff;
            }

            dfs(root.left, target);
            dfs(root.right, target);
        }
    }

    /*
    Recursive DFS approach like the solution above, but we take advantage of the fact that the tree is a BST.
    If the difference between the target and the current node's value is negative, it means that the value is
    too big, so we need to look at the left subtree for a smaller value. If the difference is positive, it's
    the opposite: the value is too small, so we look at the right subtree for a bigger value.
    
    Time: O(h), where h is the height of the tree. O(n) in the worst case, which is if the tree is skewed in
    a single direction. O(log n) best case, which is when the tree is balanced
    Space: Also O(h)
    */
    class Solution2 {
        private int res = Integer.MAX_VALUE;
        private double resDiff = Double.MAX_VALUE;

        public int closestValue(TreeNode root, double target) {
            if (root == null) {
                return 0;
            }
            dfs(root, target);
            return res;
        }

        private void dfs(TreeNode root, double target) {
            if (root == null) {
                return;
            }

            double currDiff = target - (double) root.val;
            double absValueCurrDiff = Math.abs(currDiff);
            if (absValueCurrDiff < resDiff || absValueCurrDiff == resDiff && root.val < res) {
                this.res = root.val;
                this.resDiff = absValueCurrDiff;
            }

            if (currDiff < 0) {
                dfs(root.left, target);
            } else if (currDiff > 0) {
                dfs(root.right, target);
            }
        }
    }

    public static void main(String[] args) {
        P_270_ClosestBinarySearchTreeValue solver = new P_270_ClosestBinarySearchTreeValue();

        Object[][] tests = new Object[][] {
                // Classic example from LeetCode
                { new Integer[] { 4, 2, 5, 1, 3 }, 3.714286, 4 },
                // Exact match exists
                { new Integer[] { 4, 2, 5, 1, 3 }, 3.0, 3 },
                // Single node tree
                { new Integer[] { 1 }, 4.428571, 1 },
                // Tie between 2 and 4 for target=3 -> pick smaller (2)
                { new Integer[] { 3, 2, 4 }, 3.0, 3 }, // direct match
                { new Integer[] { 3, 2, 4 }, 3.5, 3 }, // 3.5 is 0.5 away from both 3 and 4 => pick 3 (smaller)
                { new Integer[] { 3, 2, 4 }, 2.4, 2 }, // closer to 2
                // Negative values
                { new Integer[] { -10, -20, -3, null, null, -5, 0 }, -4.0, -5 },
                // Larger values
                { new Integer[] { 1000000000, 500000000, 1500000000 }, 1499999999.6, 1500000000 },
                // Empty tree -> as per implementation returns 0
                { new Integer[] {}, 7.5, 0 },
                // Degenerate (linked-list-like) BST
                { new Integer[] { 1, null, 2, null, 3, null, 4 }, 2.9, 3 },
                // Tie-break logic (no duplicates; valid BST): target 6 => tie between 5 and 7 => pick smaller (5)
                { new Integer[] { 6, 5, 7, 4, null, null, 8 }, 6.0, 6 }, // exact hit
                { new Integer[] { 6, 5, 7, 4, null, null, 8 }, 6.5, 6 }, // tie between 6 and 7? closer to 7 by 0.5; ensure logic
                { new Integer[] { 6, 5, 7, 4, null, null, 8 }, 5.5, 5 }, // tie between 5 and 6 -> pick 5 (smaller)
                // Target exactly between two nodes -> pick smaller
                { new Integer[] { 8, 3, 10, 1, 6, null, 14 }, 9.0, 8 },
                { new Integer[] { 8, 3, 10, 1, 6, null, 14 }, 9.5, 10 }, // closer to 10
                { new Integer[] { 8, 3, 10, 1, 6, null, 14 }, 7.0, 6 },  // closer to 6
                { new Integer[] { 8, 3, 10, 1, 6, null, 14 }, -100.0, 1 }, // below min
                { new Integer[] { 8, 3, 10, 1, 6, null, 14 }, 100.0, 14 }, // above max
                // Deep skewed tree (right-leaning)
                { new Integer[] { 1, null, 4, null, 9, null, 16, null, 25 }, 15.0, 16 },
                // Deep skewed tree (left-leaning)
                { new Integer[] { 25, 16, null, 9, null, 4, null, 1 }, 3.9, 4 },
                // Floating target near integer boundary
                { new Integer[] { 2, 1, 3 }, 2.499999, 2 },
                { new Integer[] { 2, 1, 3 }, 2.500001, 3 },
                // Stress case: wide range values (valid BST ordering)
                { new Integer[] { 0, Integer.MIN_VALUE, Integer.MAX_VALUE }, 0.1, 0 },
                // Case to validate absolute-difference logic even when target < node and node negative
                { new Integer[] { -8, -10, -3 }, -9.7, -10 },
                // Case where signed diff can be misleading if abs not used properly
                { new Integer[] { 7, 5, 9, 4, 6, 8, 10 }, 8.4, 8 },
                { new Integer[] { 7, 5, 9, 4, 6, 8, 10 }, 8.6, 9 },
        };

        int pass1 = 0;
        int pass2 = 0;
        int pass3 = 0;
        int pass4 = 0;

        for (int i = 0; i < tests.length; i++) {
            Integer[] values = (Integer[]) tests[i][0];
            double target = (double) tests[i][1];
            int expected = (int) tests[i][2];

            TreeNode root1 = TreeNode.buildTree(values);
            TreeNode root2 = TreeNode.buildTree(values);
            TreeNode root3 = TreeNode.buildTree(values);
            TreeNode root4 = TreeNode.buildTree(values);

            int actual1 = solver.closestValue(root1, target);
            int actual2 = solver.closestValue2(root2, target);
            // instantiate per test to avoid stale state in res/resDiff
            Solution dfsSolver = solver.new Solution();
            Solution2 dfsSolver2 = solver.new Solution2();
            int actual3 = dfsSolver.closestValue(root3, target);
            int actual4 = dfsSolver2.closestValue(root4, target);

            boolean ok1 = expected == actual1;
            boolean ok2 = expected == actual2;
            boolean ok3 = expected == actual3;
            boolean ok4 = expected == actual4;
            if (ok1) pass1++;
            if (ok2) pass2++;
            if (ok3) pass3++;
            if (ok4) pass4++;

            System.out.printf(
                    "Test %d: tree=%s, target=%.6f => expected=%d | closestValue=%d (%s), closestValue2=%d (%s), closestValue3=%d (%s), closestValue4=%d (%s)\n",
                    i + 1,
                    TreeNode.treeToString(root1),
                    target,
                    expected,
                    actual1,
                    ok1 ? "PASS" : "FAIL",
                    actual2,
                    ok2 ? "PASS" : "FAIL",
                    actual3,
                    ok3 ? "PASS" : "FAIL",
                    actual4,
                    ok4 ? "PASS" : "FAIL");
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Overall Summary:");
        System.out.printf("closestValue: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("closestValue2: %d/%d tests passed\n", pass2, tests.length);
        System.out.printf("closestValue3: %d/%d tests passed\n", pass3, tests.length);
        System.out.printf("closestValue4: %d/%d tests passed\n", pass4, tests.length);
    }
}
