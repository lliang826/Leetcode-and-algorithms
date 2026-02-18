import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import data_structures.TreeNode;

public class P_103_BinaryTreeZigzagLevelOrderTraversal {
    /*
     * BFS approach. We can use a queue to perform a level-order traversal of the
     * tree. At each level, we collect the node values in a list. If the current
     * level should be traversed from right to left, we reverse the list before
     * adding it to the result.
     * 
     * Time: O(n), where n is the number of nodes in the tree, as we need to visit
     * each node once
     * Space: O(w), where w is the maximum width of the tree. In the worst case,
     * the tree is a complete binary tree, and the maximum width is n/2, leading
     * to a space complexity of O(n).
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        List<List<Integer>> list = new ArrayList<>();
        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            int rowWidth = queue.size();
            List<Integer> rowValues = new ArrayList<>();

            for (int i = 0; i < rowWidth; i++) {
                TreeNode node = queue.poll();
                rowValues.add(node.val);

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            if (!leftToRight) {
                Collections.reverse(rowValues);
            }
            list.add(rowValues);
            leftToRight = !leftToRight;
        }

        return list;
    }

    /*
     * Alternative version of the solution above. Still BFS, but instead of reversing
     * the list at the end of each level, we create the level list in either
     * left-to-right or right-to-left order as we go by adding elements at the
     * beginning of the row list for reverse order.
     * 
     * However, this is actually LESS efficient than the first solution because
     * ArrayList.add(0, element) is O(k) where k is the current list size (requires
     * shifting all elements right), making each reverse-order level O(w²) where w is
     * the level width. The first solution's Collections.reverse() is O(w) per level.
     * 
     * Time: O(n²) in worst case due to repeated insertions at index 0
     * Space: Same O(w) as first solution
     */
    public List<List<Integer>> zigzagLevelOrder2(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        List<List<Integer>> list = new ArrayList<>();
        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            int rowWidth = queue.size();
            List<Integer> rowValues = new ArrayList<>();

            for (int i = 0; i < rowWidth; i++) {
                TreeNode node = queue.poll();

                if (leftToRight) {
                    rowValues.add(node.val);
                } else {
                    rowValues.add(0, node.val);
                }

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            list.add(rowValues);
            leftToRight = !leftToRight;
        }

        return list;
    }

    /*
     * Most efficient version using Deque. Instead of using ArrayList which has O(k)
     * insertion cost at the beginning, we use a Deque (implemented as LinkedList)
     * which provides O(1) insertion at both ends.
     * For left-to-right levels, we add to the end (addLast).
     * For right-to-left levels, we add to the beginning (addFirst).
     * This avoids both the reversal overhead of method 1 and the expensive
     * insertions of method 2.
     * 
     * Time: O(n), where n is the number of nodes - each node processed once with O(1) operations
     * Space: O(w), where w is the maximum width of the tree
     */
    public List<List<Integer>> zigzagLevelOrder3(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        List<List<Integer>> list = new ArrayList<>();
        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            int rowWidth = queue.size();
            java.util.Deque<Integer> rowValues = new java.util.LinkedList<>();

            for (int i = 0; i < rowWidth; i++) {
                TreeNode node = queue.poll();

                if (leftToRight) {
                    rowValues.addLast(node.val);
                } else {
                    rowValues.addFirst(node.val);
                }

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            list.add(new ArrayList<>(rowValues));
            leftToRight = !leftToRight;
        }

        return list;
    }

    public static void main(String[] args) {
        P_103_BinaryTreeZigzagLevelOrderTraversal solver = new P_103_BinaryTreeZigzagLevelOrderTraversal();

        // Test case 1: [3,9,20,null,null,15,7]
        TreeNode t1 = new TreeNode(3);
        t1.left = new TreeNode(9);
        t1.right = new TreeNode(20);
        t1.right.left = new TreeNode(15);
        t1.right.right = new TreeNode(7);

        // Test case 2: Single node
        TreeNode t2 = new TreeNode(1);

        // Test case 3: Null tree
        TreeNode t3 = null;

        // Test case 4: Complete binary tree
        TreeNode t4 = new TreeNode(1);
        t4.left = new TreeNode(2);
        t4.right = new TreeNode(3);
        t4.left.left = new TreeNode(4);
        t4.left.right = new TreeNode(5);
        t4.right.left = new TreeNode(6);
        t4.right.right = new TreeNode(7);

        // Test case 5: Left skewed
        TreeNode t5 = new TreeNode(1);
        t5.left = new TreeNode(2);
        t5.left.left = new TreeNode(3);

        // Test case 6: Right skewed
        TreeNode t6 = new TreeNode(1);
        t6.right = new TreeNode(2);
        t6.right.right = new TreeNode(3);

        // Test case 7: Two levels
        TreeNode t7 = new TreeNode(1);
        t7.left = new TreeNode(2);
        t7.right = new TreeNode(3);

        Object[][] tests = new Object[][] {
            { t1, List.of(List.of(3), List.of(20, 9), List.of(15, 7)) },
            { t2, List.of(List.of(1)) },
            { t3, List.of() },
            { t4, List.of(List.of(1), List.of(3, 2), List.of(4, 5, 6, 7)) },
            { t5, List.of(List.of(1), List.of(2), List.of(3)) },
            { t6, List.of(List.of(1), List.of(2), List.of(3)) },
            { t7, List.of(List.of(1), List.of(3, 2)) },
        };

        System.out.println("Running tests for P_103_BinaryTreeZigzagLevelOrderTraversal\n");
        int pass1 = 0;
        int pass2 = 0;
        int pass3 = 0;

        for (int i = 0; i < tests.length; i++) {
            TreeNode input = (TreeNode) tests[i][0];
            @SuppressWarnings("unchecked")
            List<List<Integer>> expected = (List<List<Integer>>) tests[i][1];
            
            List<List<Integer>> actual1 = solver.zigzagLevelOrder(input);
            List<List<Integer>> actual2 = solver.zigzagLevelOrder2(input);
            List<List<Integer>> actual3 = solver.zigzagLevelOrder3(input);

            boolean ok1 = actual1.equals(expected);
            boolean ok2 = actual2.equals(expected);
            boolean ok3 = actual3.equals(expected);

            if (ok1) pass1++;
            if (ok2) pass2++;
            if (ok3) pass3++;

            System.out.printf("Test %d: tree=%s => method1=%s, method2=%s, method3=%s\n",
                i + 1, TreeNode.treeToString(input),
                ok1 ? "PASS" : "FAIL",
                ok2 ? "PASS" : "FAIL",
                ok3 ? "PASS" : "FAIL");
        }

        System.out.printf("\nSummary: method1=%d/%d, method2=%d/%d, method3=%d/%d tests passed.\n",
            pass1, tests.length, pass2, tests.length, pass3, tests.length);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Overall Summary:");
        System.out.printf("zigzagLevelOrder: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("zigzagLevelOrder2: %d/%d tests passed\n", pass2, tests.length);
        System.out.printf("zigzagLevelOrder3: %d/%d tests passed\n", pass3, tests.length);
    }
}
