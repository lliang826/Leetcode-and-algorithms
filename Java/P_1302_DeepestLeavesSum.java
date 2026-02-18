import java.util.ArrayDeque;
import java.util.Queue;

import data_structures.TreeNode;

public class P_1302_DeepestLeavesSum {
    /*
     * BFS approach. We can use a queue to perform a level-order traversal of the
     * tree. At each level, we calculate the sum of the node values. The sum of the
     * last level (deepest leaves) will be our result.
     * The tricky part is figuring out when we are at the last level: if we finish
     * iterating through a level and the queue is empty, it means that we have just
     * finished processing the deepest level, so we can return the sum for that row
     * as the result.
     * 
     * Time: O(n), where n is the number of nodes in the tree, as we need to visit
     * each node once
     * Space: O(w), where w is the maximum width of the tree. In the worst case,
     * the tree is a complete binary tree, and the maximum width is n/2, leading
     * to a space complexity of O(n).
     */
    public int deepestLeavesSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int res = 0;
        
        while (!queue.isEmpty()) {
            int rowWidth = queue.size();
            int rowSum = 0;
            
            for (int i = 0; i < rowWidth; i++) {
                TreeNode node = queue.poll();
                rowSum += node.val;
                
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            
            if (queue.isEmpty()) {
                res = rowSum;
            }
        }
        
        return res;
    }

    public static void main(String[] args) {
        P_1302_DeepestLeavesSum solver = new P_1302_DeepestLeavesSum();

        // Test case 1: [1,2,3,4,5,null,6,7,null,null,null,null,8]
        TreeNode t1 = new TreeNode(1);
        t1.left = new TreeNode(2);
        t1.right = new TreeNode(3);
        t1.left.left = new TreeNode(4);
        t1.left.right = new TreeNode(5);
        t1.right.right = new TreeNode(6);
        t1.left.left.left = new TreeNode(7);
        t1.right.right.right = new TreeNode(8);

        // Test case 2: [6,7,8,2,7,1,3,9,null,1,4,null,null,null,5]
        TreeNode t2 = new TreeNode(6);
        t2.left = new TreeNode(7);
        t2.right = new TreeNode(8);
        t2.left.left = new TreeNode(2);
        t2.left.right = new TreeNode(7);
        t2.right.left = new TreeNode(1);
        t2.right.right = new TreeNode(3);
        t2.left.left.left = new TreeNode(9);
        t2.left.right.left = new TreeNode(1);
        t2.left.right.right = new TreeNode(4);
        t2.right.right.right = new TreeNode(5);

        // Test case 3: Single node
        TreeNode t3 = new TreeNode(1);

        // Test case 4: Two levels
        TreeNode t4 = new TreeNode(1);
        t4.left = new TreeNode(2);
        t4.right = new TreeNode(3);

        // Test case 5: Left skewed tree
        TreeNode t5 = new TreeNode(1);
        t5.left = new TreeNode(2);
        t5.left.left = new TreeNode(3);
        t5.left.left.left = new TreeNode(4);

        // Test case 6: Right skewed tree
        TreeNode t6 = new TreeNode(1);
        t6.right = new TreeNode(2);
        t6.right.right = new TreeNode(3);
        t6.right.right.right = new TreeNode(4);

        // Test case 7: Complete binary tree
        TreeNode t7 = new TreeNode(1);
        t7.left = new TreeNode(2);
        t7.right = new TreeNode(3);
        t7.left.left = new TreeNode(4);
        t7.left.right = new TreeNode(5);
        t7.right.left = new TreeNode(6);
        t7.right.right = new TreeNode(7);

        // Test case 8: Null tree
        TreeNode t8 = null;

        Object[][] tests = new Object[][] {
            { t1, 15 },  // 7 + 8 = 15
            { t2, 19 },  // 9 + 1 + 4 + 5 = 19
            { t3, 1 },   // single node
            { t4, 5 },   // 2 + 3 = 5
            { t5, 4 },   // only 4 at deepest level
            { t6, 4 },   // only 4 at deepest level
            { t7, 22 },  // 4 + 5 + 6 + 7 = 22
            { t8, 0 },   // null tree
        };

        System.out.println("Running tests for P_1302_DeepestLeavesSum.deepestLeavesSum\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            TreeNode input = (TreeNode) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.deepestLeavesSum(input);
            boolean ok = expected == actual;
            if (ok) pass++;
            System.out.printf("Test %d: tree=%s => expected=%d, actual=%d => %s\n",
                i + 1, TreeNode.treeToString(input), expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("deepestLeavesSum: %d/%d tests passed\n", pass, tests.length);
    }
}
