import data_structures.TreeNode;

public class P_111_MinimumDepthOfBinaryTree {
    /*
    Recursive DFS approach. We traverse the tree and calculate the depth of each subtree. The minimum depth of the 
    tree is the minimum depth between the left and right subtrees plus one for the current node. We need to handle
    the case where one of the subtrees is null, in which case we should not consider it for the minimum depth
    calculation; we return the depth of the other subtree plus one for the current node. If both subtrees have a 
    depth greater than zero, we return the minimum of the two depths plus one for the current node.
    
    Time: O(n), where n is the number of nodes in the tree. We visit each node once.

    Space: O(h), where h is the height of the tree. In the worst case, the tree is a vertical straight line, which 
    means all the nodes are stored in the call stack. Therefore, the space complexity would be O(n) in the worst
    case. In the best case, the tree is balanced, and the height would be log(n), leading to a space complexity of
    O(log n).
    */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = minDepth(root.left);
        int right = minDepth(root.right);

        if (left == 0) {
            return right + 1;
        }
        if (right == 0) {
            return left + 1;
        }
        return Math.min(left, right) + 1;
    }
    
    /*
    0 0 => 0 + 1
    1 0 => 1 + 1
    0 1 => 1 + 1
    1 1 => min(1, 1) + 1
    
    Slightly different implemention of the solution above. If either the left or right subtree is null, we can return
    the depth of the left subtree plus the depth of the right subtree plus one for the current node. This is similar
    to the solution above, but it combines the two cases where one of the subtrees is null into a single case.
    
    Same time and space complexities as the first solution.
    */
    public int minDepth2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = minDepth2(root.left);
        int right = minDepth2(root.right);

        if (left == 0 || right == 0) {
            return left + right + 1;
        }
        return Math.min(left, right) + 1;
    }
    
    // Helper method to build tree from level-order array (null represents no node)
    private static TreeNode buildTree(Integer[] values) {
        if (values == null || values.length == 0 || values[0] == null) {
            return null;
        }
        
        TreeNode root = new TreeNode(values[0]);
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.offer(root);
        int i = 1;
        
        while (!queue.isEmpty() && i < values.length) {
            TreeNode node = queue.poll();
            
            // Left child
            if (i < values.length && values[i] != null) {
                node.left = new TreeNode(values[i]);
                queue.offer(node.left);
            }
            i++;
            
            // Right child
            if (i < values.length && values[i] != null) {
                node.right = new TreeNode(values[i]);
                queue.offer(node.right);
            }
            i++;
        }
        
        return root;
    }
    
    public static void main(String[] args) {
        P_111_MinimumDepthOfBinaryTree solver = new P_111_MinimumDepthOfBinaryTree();

        // Test cases: each test is {tree array representation, expected result}
        Object[][] testSets = new Object[][] {
            // Test 1: Example from LeetCode - [3,9,20,null,null,15,7]
            {new Integer[]{3, 9, 20, null, null, 15, 7}, 2},
            
            // Test 2: Example from LeetCode - [2,null,3,null,4,null,5,null,6]
            {new Integer[]{2, null, 3, null, 4, null, 5, null, 6}, 5},
            
            // Test 3: Single node
            {new Integer[]{1}, 1},
            
            // Test 4: Only left child
            {new Integer[]{1, 2}, 2},
            
            // Test 5: Only right child
            {new Integer[]{1, null, 2}, 2},
            
            // Test 6: Balanced tree with depth 3
            {new Integer[]{1, 2, 3, 4, 5, 6, 7}, 3},
            
            // Test 7: Left-heavy tree
            {new Integer[]{1, 2, 3, 4, null, null, null}, 2},
            
            // Test 8: Right-heavy tree
            {new Integer[]{1, 2, 3, null, null, null, 4}, 2},
            
            // Test 9: Deep left path
            {new Integer[]{1, 2, null, 3, null, 4}, 4},
            
            // Test 10: Null root
            {null, 0},
            
            // Test 11: Two levels, left leaf at level 2
            {new Integer[]{1, 2, 3, 4}, 2},
            
            // Test 12: Two levels, right leaf at level 2
            {new Integer[]{1, 2, 3, null, null, null, 4}, 2},
            
            // Test 13: Complete binary tree depth 4
            {new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, 4},
            
            // Test 14: Skewed left tree
            {new Integer[]{5, 4, null, 3, null, 2, null, 1}, 5},
            
            // Test 15: Two nodes
            {new Integer[]{1, 2, 3}, 2}
        };

        System.out.println("Running tests for P_111_MinimumDepthOfBinaryTree.minDepth\n");
        int pass1 = 0;
        int totalTests1 = 0;
        
        for (int i = 0; i < testSets.length; i++) {
            Integer[] treeArray = (Integer[]) testSets[i][0];
            int expected = (int) testSets[i][1];
            TreeNode root = buildTree(treeArray);
            int actual = solver.minDepth(root);
            
            boolean ok = expected == actual;
            totalTests1++;
            if (ok) pass1++;
            
            System.out.printf("  Test %d: minDepth(%s) => expected=%d, actual=%d => %s\n",
                    i + 1,
                    java.util.Arrays.toString(treeArray),
                    expected,
                    actual,
                    (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass1, totalTests1);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_111_MinimumDepthOfBinaryTree.minDepth2\n");
        int pass2 = 0;
        int totalTests2 = 0;
        
        for (int i = 0; i < testSets.length; i++) {
            Integer[] treeArray = (Integer[]) testSets[i][0];
            int expected = (int) testSets[i][1];
            TreeNode root = buildTree(treeArray);
            int actual = solver.minDepth2(root);
            
            boolean ok = expected == actual;
            totalTests2++;
            if (ok) pass2++;
            
            System.out.printf("  Test %d: minDepth2(%s) => expected=%d, actual=%d => %s\n",
                    i + 1,
                    java.util.Arrays.toString(treeArray),
                    expected,
                    actual,
                    (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass2, totalTests2);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("minDepth: %d/%d tests passed\n", pass1, totalTests1);
        System.out.printf("minDepth2: %d/%d tests passed\n", pass2, totalTests2);
    }
}
