import data_structures.TreeNode;

public class P_1026_MaximumDifferenceBetweenNodeAndAncestor {
    class Solution {
        public int maxAncestorDiff(TreeNode root) {
            return dfs(root, Integer.MAX_VALUE, Integer.MIN_VALUE);
        }

        public int dfs(TreeNode root, int min, int max) {
            if (root == null) {
                return 0;
            }

            min = Math.min(min, root.val);
            max = Math.max(max, root.val);
            int diff = min == Integer.MAX_VALUE || max == Integer.MIN_VALUE ? 0 : Math.abs(max - min);

            int left = dfs(root.left, min, max);
            int right = dfs(root.right, min, max);

            int maxChildDiff = Math.max(left, right);
            return Math.max(diff, maxChildDiff);
        }
    }
}
