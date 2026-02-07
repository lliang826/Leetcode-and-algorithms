import data_structures.TreeNode;

public class P_104_MaximumDepthOfBinaryTree {
    /*
    Recursive DFS approach: we traverse the tree and calculate the depth of each subtree. The maximum depth 
    of the tree is the maximum depth between the left and right subtrees plus one for the current node.
    
    Time: O(n), where n is the number of nodes in the tree. We visit each node once.
    
    Space: O(h), where h is the height of the tree. In the worst case, the tree is a vertical straight
    line, which means all the nodes are stored in the call stack. Therefore, the space complexity would
    be O(n) in the worst case. In the best case, the tree is balanced, and the height would be log(n),
    leading to a space complexity of O(log n).
    */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return Math.max(left, right) + 1;
    }

    public static void main(String[] args) {
        P_104_MaximumDepthOfBinaryTree solver = new P_104_MaximumDepthOfBinaryTree();

        Object[][] tests = new Object[][] {
                // Empty tree
                { new Integer[] {}, 0 },
                
                // Single node
                { new Integer[] { 1 }, 1 },
                
                // Balanced tree - depth 2
                { new Integer[] { 1, 2, 3 }, 2 },
                
                // Balanced tree - depth 3
                { new Integer[] { 3, 9, 20, null, null, 15, 7 }, 3 },
                
                // Left-skewed tree
                { new Integer[] { 1, 2, null, 3, null, 4 }, 4 },
                
                // Right-skewed tree
                { new Integer[] { 1, null, 2, null, 3, null, 4 }, 4 },
                
                // Complete binary tree
                { new Integer[] { 1, 2, 3, 4, 5, 6, 7 }, 3 },
                
                // Unbalanced tree - left deeper
                { new Integer[] { 1, 2, 3, 4, 5 }, 3 },
                
                // Unbalanced tree - right deeper
                { new Integer[] { 1, 2, 3, null, null, 4, 5 }, 3 },
                
                // Tree with many nulls
                { new Integer[] { 1, null, 2, null, 3, null, 4, null, 5 }, 5 },
                
                // Larger balanced tree
                { new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 }, 4 },
                
                // Tree with only left children
                { new Integer[] { 5, 4, null, 3, null, 2, null, 1 }, 5 },
                
                // Tree with only right children
                { new Integer[] { 1, null, 2, null, 3, null, 4, null, 5 }, 5 },
                
                // Two nodes - left child
                { new Integer[] { 1, 2 }, 2 },
                
                // Two nodes - right child
                { new Integer[] { 1, null, 2 }, 2 }
        };

        System.out.println("Running tests for P_104_MaximumDepthOfBinaryTree.maxDepth\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            Integer[] treeArray = (Integer[]) tests[i][0];
            int expected = (int) tests[i][1];
            
            TreeNode root = TreeNode.buildTree(treeArray);
            int actual = solver.maxDepth(root);

            boolean ok = expected == actual;
            if (ok) pass++;
            
            System.out.printf("Test %d: tree=%s => expected=%d, actual=%d => %s\n",
                    i + 1, arrayToString(treeArray), expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("maxDepth: %d/%d tests passed\n", pass, tests.length);
    }
    
    private static String arrayToString(Integer[] arr) {
        if (arr == null || arr.length == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < Math.min(arr.length, 7); i++) {
            if (arr[i] == null) {
                sb.append("null");
            } else {
                sb.append(arr[i]);
            }
            if (i < Math.min(arr.length, 7) - 1) {
                sb.append(",");
            }
        }
        if (arr.length > 7) {
            sb.append("...");
        }
        sb.append("]");
        return sb.toString();
    }
}
