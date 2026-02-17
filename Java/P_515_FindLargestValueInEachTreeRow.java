import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import data_structures.TreeNode;

public class P_515_FindLargestValueInEachTreeRow {
    public List<Integer> largestValues(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        ArrayList<Integer> res = new ArrayList<>();

        while (!queue.isEmpty()) {
            int rowWidth = queue.size();
            int rowMax = Integer.MIN_VALUE;

            for (int i = 0; i < rowWidth; i++) {
                TreeNode node = queue.poll();
                rowMax = Math.max(rowMax, node.val);

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            res.add(rowMax);
        }

        return res;
    }
}
