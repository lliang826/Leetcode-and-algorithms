import data_structures.TreeNode;

public class P_236_LowestCommonAncestorOfABinaryTree {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // Base case
        if (root == null) {
            return null;
        }

        // Scenario 3 (example 3)
        if (root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // Scenario 1 (example 1)
        if (left != null && right != null) {
            return root;
        }

        // Scenario 2 (example 2)
        if (left != null && right == null) {
            return left;
        }

        return right;
    }
}
