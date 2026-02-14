import data_structures.TreeNode;

public class P_236_LowestCommonAncestorOfABinaryTree {
    /*   
    To find the lowest common ancestor of a binary tree (not a binary search tree, which has ordered values),
    we can use a depth-first search (DFS) approach. We will traverse the tree and check for the presence of
    the nodes p and q. If we find either p or q, we return that node. If we find both p and q in different
    subtrees of the same node, that node is the lowest common ancestor. If we find both p and q in the same
    subtree, we continue searching in that subtree. If we reach a null node, we return null.
    
    This can be summarized into three scenarios + base case:
    Base case: empty tree => null
    
    When we are at a node N:
    1. If p and q are in left and right subtrees respectively, the LCA is the node N
    2. If p and q are both in the same subtree, node N is not the answer - LCA is in the same subtree
    3. If either p or q is the node N, the LCA is the node N
    
    Time: O(n), where n is the number of nodes in the tree - we visit all nodes in the tree in the worst case
    Space: O(h), where h is the height of the tree. In the worst case, the tree is a vertical straight line,
    which means all the nodes are stored in the call stack. Therefore, the space complexity would be O(n) in
    the worst case. In the best case, the tree is balanced, and the height would be log(n), leading to a
    space complexity of O(log n).
    */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // Base case
        if (root == null) {
            return null;
        }

        // Scenario 3
        if (root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // Scenario 1
        if (left != null && right != null) {
            return root;
        }

        // Scenario 2
        if (left != null && right == null) {
            return left;
        }

        return right;
    }

    /*
    Cleaner/more concise implementation of the solution above. We can combine the base case and scenario 3 into
    a single case, and we transform scenario 2 into a single ternary operator. The logic is the same as the first
    solution, but it is more concise and easier to read.
    
    Same time and space complexities as the first solution above.
    */
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        // Base case + scenario 3
        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestor2(root.left, p, q);
        TreeNode right = lowestCommonAncestor2(root.right, p, q);

        // Scenario 1
        if (left != null && right != null) {
            return root;
        }

        // Scenario 2
        return left != null ? left : right;
    }


    public static void main(String[] args) {
        P_236_LowestCommonAncestorOfABinaryTree solver = new P_236_LowestCommonAncestorOfABinaryTree();

        Object[][] tests = new Object[][] {
                { new Integer[] { 3, 5, 1, 6, 2, 0, 8, null, null, 7, 4 }, 5, 1, 3 },
                { new Integer[] { 3, 5, 1, 6, 2, 0, 8, null, null, 7, 4 }, 5, 4, 5 },
                { new Integer[] { 1, 2, 3 }, 2, 3, 1 },
                { new Integer[] { 1, 2, null, 3, null, 4 }, 3, 4, 3 },
                { new Integer[] { 1 }, 1, 1, 1 }
        };

        System.out.println("Running tests for P_236_LowestCommonAncestorOfABinaryTree\n");

        int pass1 = 0;
        int pass2 = 0;

        for (int i = 0; i < tests.length; i++) {
            Integer[] values = (Integer[]) tests[i][0];
            int pVal = (int) tests[i][1];
            int qVal = (int) tests[i][2];
            int expected = (int) tests[i][3];

            TreeNode root = TreeNode.buildTree(values);
            TreeNode p = TreeNode.findNodeByValue(root, pVal);
            TreeNode q = TreeNode.findNodeByValue(root, qVal);

            TreeNode actual1 = solver.lowestCommonAncestor(root, p, q);
            TreeNode actual2 = solver.lowestCommonAncestor2(root, p, q);

            boolean ok1 = actual1 != null && actual1.val == expected;
            boolean ok2 = actual2 != null && actual2.val == expected;

            if (ok1) {
                pass1++;
            }
            if (ok2) {
                pass2++;
            }

            System.out.printf(
                    "Test %d: p=%d, q=%d, expected=%d => method1=%s, method2=%s\n",
                    i + 1, pVal, qVal, expected,
                    ok1 ? "PASS" : "FAIL",
                    ok2 ? "PASS" : "FAIL");
        }

        System.out.printf("\nSummary: method1=%d/%d, method2=%d/%d tests passed.\n",
                pass1, tests.length, pass2, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Overall Summary:");
        System.out.printf("lowestCommonAncestor: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("lowestCommonAncestor2: %d/%d tests passed\n", pass2, tests.length);
    }
}
