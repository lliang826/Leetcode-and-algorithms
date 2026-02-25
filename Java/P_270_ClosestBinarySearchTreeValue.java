import java.util.ArrayDeque;
import java.util.Queue;

import data_structures.TreeNode;

public class P_270_ClosestBinarySearchTreeValue {

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
    
    public int closestValue2(TreeNode root, double target) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        double[] res = new double[] { Double.MAX_VALUE, Double.MAX_VALUE };

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
}
