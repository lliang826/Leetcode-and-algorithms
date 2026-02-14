import data_structures.TreeNode;

public class P_236_LowestCommonAncestorOfABinaryTree {
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
}
