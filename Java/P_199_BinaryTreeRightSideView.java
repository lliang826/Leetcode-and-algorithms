import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import data_structures.TreeNode;

public class P_199_BinaryTreeRightSideView {
    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        ArrayList<Integer> list = new ArrayList<>();

        while (!queue.isEmpty()) {
            int levelWidth = queue.size();

            for (int i = 0; i < levelWidth; i++) {
                TreeNode node = queue.poll();
                if (i == levelWidth - 1) {
                    list.add(node.val);
                }

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }

        return list;
    }
}
