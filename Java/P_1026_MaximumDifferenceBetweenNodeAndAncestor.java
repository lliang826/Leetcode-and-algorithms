import data_structures.TreeNode;

public class P_1026_MaximumDifferenceBetweenNodeAndAncestor {
    /*
    Initial solution using recursive DFS approach. To calculate the maximum difference, we need to track both 
    the minimum and maximum values of a subtree, which are passed down the child nodes via recursive calls.
    We also need to calculate the maximum difference at the current node, which is the difference between
    the current max and min values. After the recursive calls, we return the maximum difference from
    the current node and its children.
    The first solution isn't the cleanest because it performs the difference calculation at each node,
    which is unnecessary. See the second solution for a cleaner implementation.
    
    Time: O(n), where n is the number of nodes in the tree. We visit each node once.
    
    Space: O(h), where h is the height of the tree. In the worst case, the tree is a vertical straight line, which 
    means all the nodes are stored in the call stack. Therefore, the space complexity would be O(n) in the worst
    case. In the best case, the tree is balanced, and the height would be log(n), leading to a space complexity of
    O(log n).
    */
    public int maxAncestorDiff(TreeNode root) {
        return dfs(root, Integer.MAX_VALUE, Integer.MIN_VALUE);
    }

    private int dfs(TreeNode root, int min, int max) {
        if (root == null) {
            return 0;
        }

        min = Math.min(min, root.val);
        max = Math.max(max, root.val);
        int diff = max - min;

        int left = dfs(root.left, min, max);
        int right = dfs(root.right, min, max);

        int maxChildDiff = Math.max(left, right);
        return Math.max(diff, maxChildDiff);
    }

    /*
    Cleaner implementation of the solution above. We can calculate the difference at the leaf nodes instead of at
    every node, which is unnecessary. The maximum difference will be the same at the leaf nodes as it is at the 
    parent nodes, since the min and max values are passed down the child nodes via recursive calls.
    
    Same time and space complexities as the first solution.
    */
    public int v2(TreeNode root) {
        return dfs2(root, Integer.MAX_VALUE, Integer.MIN_VALUE);
    }

    private int dfs2(TreeNode root, int min, int max) {
        if (root == null) {
            return max - min;
        }

        min = Math.min(min, root.val);
        max = Math.max(max, root.val);

        int left = dfs2(root.left, min, max);
        int right = dfs2(root.right, min, max);

        return Math.max(left, right);
    }

    public static void main(String[] args) {
        P_1026_MaximumDifferenceBetweenNodeAndAncestor solver = new P_1026_MaximumDifferenceBetweenNodeAndAncestor();

        // Test cases: each test is {tree array representation, expected result}
        Object[][] testSets = new Object[][] {
            // Test 1: Example from LeetCode - [8,3,10,1,6,null,14,null,null,4,7,13]
            {new Integer[]{8, 3, 10, 1, 6, null, 14, null, null, 4, 7, 13}, 7},
            
            // Test 2: Example from LeetCode - [1,null,2,null,0,3]
            {new Integer[]{1, null, 2, null, 0, 3}, 3},
            
            // Test 3: Single node
            {new Integer[]{1}, 0},
            
            // Test 4: Two nodes - parent and left child
            {new Integer[]{5, 2}, 3},
            
            // Test 5: Two nodes - parent and right child
            {new Integer[]{5, null, 8}, 3},
            
            // Test 6: Balanced tree with same values
            {new Integer[]{5, 5, 5}, 0},
            
            // Test 7: Linear tree increasing
            {new Integer[]{1, null, 2, null, 3, null, 4}, 3},
            
            // Test 8: Linear tree decreasing
            {new Integer[]{10, 8, null, 6, null, 4}, 6},
            
            // Test 9: Complete binary tree - path from 50 to 20 or 80
            {new Integer[]{50, 30, 70, 20, 40, 60, 80}, 30},
            
            // Test 10: Left-skewed tree
            {new Integer[]{100, 80, null, 60, null, 40}, 60},
            
            // Test 11: Two levels with max difference on left
            {new Integer[]{10, 2, 15}, 8},
            
            // Test 12: Two levels with max difference on right
            {new Integer[]{10, 5, 20}, 10},
            
            // Test 13: Deep tree - path from 20 to 1
            {new Integer[]{20, 10, 30, 5, 15, 25, 35, 1}, 19},
            
            // Test 14: Tree with value 0
            {new Integer[]{0, null, 5, null, 10}, 10},
            
            // Test 15: Larger values
            {new Integer[]{100000, 50000, 150000}, 50000}
        };

        System.out.println("Running tests for P_1026_MaximumDifferenceBetweenNodeAndAncestor.maxAncestorDiff\n");
        int pass1 = 0;
        int totalTests1 = 0;
        
        for (int i = 0; i < testSets.length; i++) {
            Integer[] treeArray = (Integer[]) testSets[i][0];
            int expected = (int) testSets[i][1];
            TreeNode root = TreeNode.buildTree(treeArray);
            int actual = solver.maxAncestorDiff(root);
            
            boolean ok = expected == actual;
            totalTests1++;
            if (ok) pass1++;
            
            System.out.printf("  Test %d: maxAncestorDiff(%s) => expected=%d, actual=%d => %s\n",
                    i + 1,
                    java.util.Arrays.toString(treeArray),
                    expected,
                    actual,
                    (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass1, totalTests1);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running tests for P_1026_MaximumDifferenceBetweenNodeAndAncestor.v2\n");
        int pass2 = 0;
        int totalTests2 = 0;
        
        for (int i = 0; i < testSets.length; i++) {
            Integer[] treeArray = (Integer[]) testSets[i][0];
            int expected = (int) testSets[i][1];
            TreeNode root = TreeNode.buildTree(treeArray);
            int actual = solver.v2(root);
            
            boolean ok = expected == actual;
            totalTests2++;
            if (ok) pass2++;
            
            System.out.printf("  Test %d: v2(%s) => expected=%d, actual=%d => %s\n",
                    i + 1,
                    java.util.Arrays.toString(treeArray),
                    expected,
                    actual,
                    (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass2, totalTests2);

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("maxAncestorDiff: %d/%d tests passed\n", pass1, totalTests1);
        System.out.printf("v2: %d/%d tests passed\n", pass2, totalTests2);
    }
}
