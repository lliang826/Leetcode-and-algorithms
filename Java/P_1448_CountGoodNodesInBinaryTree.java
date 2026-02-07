import data_structures.TreeNode;

public class P_1448_CountGoodNodesInBinaryTree {
    public int goodNodes(TreeNode root) {
        return dfs(root, Integer.MIN_VALUE);
    }

    public int dfs(TreeNode root, int max) {
        if (root == null) {
            return 0;
        }

        int curr = 0;
        if (root.val >= max) {
            curr = 1;
        }

        max = Math.max(root.val, max);
        int left = dfs(root.left, max);
        int right = dfs(root.right, max);
        return left + right + curr;
    }
}
