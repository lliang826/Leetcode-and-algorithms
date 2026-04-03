import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import data_structures.TreeNode;

public class P_863_AllNodesDistanceKInBinaryTree {
    class Solution {
        private Map<TreeNode, List<TreeNode>> map = new HashMap<>();

        public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
            this.dfs(root);

            Queue<TreeNode> queue = new ArrayDeque<>();
            Set<TreeNode> visited = new HashSet<>();
            List<Integer> nodes = new ArrayList<>();
            int level = 0;

            queue.offer(target);
            visited.add(target);

            while (!queue.isEmpty()) {
                int rowWidth = queue.size();
                if (level == k + 1) {
                    return nodes;
                }

                for (int i = 0; i < rowWidth; i++) {
                    TreeNode node = queue.poll();
                    if (level == k) {
                        nodes.add(node.val);
                    }

                    for (TreeNode t : map.get(node)) {
                        if (!visited.contains(t)) {
                            queue.offer(t);
                            visited.add(t);
                        }
                    }
                }
                level++;
            }

            return nodes;
        }

        private void dfs(TreeNode root) {
            if (root == null) {
                return;
            }

            map.putIfAbsent(root, new ArrayList<>());

            if (root.left != null) {
                map.get(root).add(root.left);
                map.putIfAbsent(root.left, new ArrayList<>());
                map.get(root.left).add(root);
                dfs(root.left);
            }
            if (root.right != null) {
                map.get(root).add(root.right);
                map.putIfAbsent(root.right, new ArrayList<>());
                map.get(root.right).add(root);
                dfs(root.right);
            }
        }
    }
}
