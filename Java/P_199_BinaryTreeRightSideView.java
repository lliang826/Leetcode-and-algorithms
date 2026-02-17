import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import data_structures.TreeNode;

public class P_199_BinaryTreeRightSideView {
    /*
     * This method returns the values of the nodes that are visible from the right side of a binary tree.
     * It uses a level-order traversal (BFS) to process each row and keeps track of the last node in each row,
     * which represents the rightmost node visible from that level.
     * 
     * Time: O(n), where n is the number of nodes in the tree, each node must be visited once
     * Space: O(m), where m is the maximum number of nodes in the widest level of the tree (the maximum width of the tree)
     */
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

    public static void main(String[] args) {
        P_199_BinaryTreeRightSideView solver = new P_199_BinaryTreeRightSideView();

        // Tree: [1, 2, 3, null, 5, null, 4]
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.right = new TreeNode(5);
        root1.right.right = new TreeNode(4);

        // Tree: [1, 2, 3]
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(3);

        // Tree: [1]
        TreeNode root3 = new TreeNode(1);

        // Tree: null
        TreeNode root4 = null;

        // Tree: [1, 2, null, 3]
        TreeNode root5 = new TreeNode(1);
        root5.left = new TreeNode(2);
        root5.left.left = new TreeNode(3);

        Object[][] tests = new Object[][] {
            { root1, List.of(1, 3, 4) },
            { root2, List.of(1, 3) },
            { root3, List.of(1) },
            { root4, List.of() },
            { root5, List.of(1, 2, 3) }
        };

        System.out.println("Running tests for P_199_BinaryTreeRightSideView.rightSideView\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            TreeNode input = (TreeNode) tests[i][0];
            List<Integer> expected = (List<Integer>) tests[i][1];
            List<Integer> actual = solver.rightSideView(input);
            boolean ok = expected.equals(actual);
            if (ok) pass++;
            System.out.printf("Test %d: expected=%s, actual=%s => %s\n",
                i + 1, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
    }
}
