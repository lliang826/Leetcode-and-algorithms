import data_structures.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class P_701_InsertIntoABinarySearchTree {
    /*
    Recursive DFS approach. If the given value is smaller or equal to the node's value, we look at the left 
    subtree. If it's larger, we look at the right subtree.
    
    Time: O(h), where h is the height of the tree. O(n) worst case if the tree is skewed. O(log n) best case
    if the tree is balanced
    Space: Also O(h)
    */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        if (val <= root.val) {
            root.left = insertIntoBST(root.left, val);
        }
        if (val > root.val) {
            root.right = insertIntoBST(root.right, val);
        }

        return root;
    }
    
    /*
    Similar to the first solution above, but iterative. We track both the current node and its parent until
    we find an empty leaf to insert the value.
    
    Time: O(h)
    Space: O(1), no additional data structures
    */
    public TreeNode insertIntoBST2(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        TreeNode curr = root;
        TreeNode parent = null;
        boolean isLeft = true;

        while (curr != null) {
            parent = curr;
            if (val <= curr.val) {
                curr = curr.left;
                isLeft = true;
            } else {
                curr = curr.right;
                isLeft = false;
            }
        }

        if (isLeft) {
            parent.left = new TreeNode(val);
        } else {
            parent.right = new TreeNode(val);
        }
        return root;
    }
    
    /*
    Almost identical to the solution above. Still iterative, but instead of having the additional variables
    to track the parent, we only track the current node.
    
    Same time and space complexities.
    */
    public TreeNode insertIntoBST3(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        
        TreeNode curr = root;
        
        while (curr != null) {
            if (val <= curr.val) {
                if (curr.left == null) {
                    curr.left = new TreeNode(val);
                    break;
                } else {
                    curr = curr.left;
                }
            } else {
                if (curr.right == null) {
                    curr.right = new TreeNode(val);
                    break;
                } else {
                    curr = curr.right;
                }
            }
        }
        
        return root;
    }

    // Helper: in-order traversal to sorted list
    private static List<Integer> inorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private static void inorderHelper(TreeNode node, List<Integer> list) {
        if (node == null) return;
        inorderHelper(node.left, list);
        list.add(node.val);
        inorderHelper(node.right, list);
    }

    // Helper: check if tree is a valid BST
    private static boolean isValidBST(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return isValidBST(node.left, min, node.val) && isValidBST(node.right, node.val, max);
    }

    public static void main(String[] args) {
        P_701_InsertIntoABinarySearchTree solver = new P_701_InsertIntoABinarySearchTree();

        // Test cases: {tree as Integer[], val to insert, expected inorder after insert}
        Object[][] tests = new Object[][] {
            // LeetCode Example 1: [4,2,7,1,3] insert 5 => inorder [1,2,3,4,5,7]
            { new Integer[]{4, 2, 7, 1, 3}, 5, new int[]{1, 2, 3, 4, 5, 7} },
            // LeetCode Example 2: [40,20,60,10,30,50,70] insert 25 => inorder [10,20,25,30,40,50,60,70]
            { new Integer[]{40, 20, 60, 10, 30, 50, 70}, 25, new int[]{10, 20, 25, 30, 40, 50, 60, 70} },
            // LeetCode Example 3: [4,2,7,1,3,null,null,null,null] insert 5 => same as example 1
            { new Integer[]{4, 2, 7, 1, 3, null, null, null, null}, 5, new int[]{1, 2, 3, 4, 5, 7} },
            // Insert into null tree
            { new Integer[]{}, 1, new int[]{1} },
            // Single node, insert smaller
            { new Integer[]{5}, 3, new int[]{3, 5} },
            // Single node, insert larger
            { new Integer[]{5}, 8, new int[]{5, 8} },
            // Insert smallest value (goes all the way left)
            { new Integer[]{10, 5, 15, 3, 7}, 1, new int[]{1, 3, 5, 7, 10, 15} },
            // Insert largest value (goes all the way right)
            { new Integer[]{10, 5, 15, 3, 7}, 20, new int[]{3, 5, 7, 10, 15, 20} },
            // Insert into middle
            { new Integer[]{10, 5, 15}, 12, new int[]{5, 10, 12, 15} },
            // Larger tree, insert deep
            { new Integer[]{50, 30, 70, 20, 40, 60, 80}, 35, new int[]{20, 30, 35, 40, 50, 60, 70, 80} },
        };

        String[] methodNames = {"insertIntoBST", "insertIntoBST2", "insertIntoBST3"};
        int[] passCounts = new int[3];

        for (int m = 0; m < 3; m++) {
            System.out.println("Running tests for P_701_InsertIntoABinarySearchTree." + methodNames[m] + "\n");
            for (int i = 0; i < tests.length; i++) {
                Integer[] treeArr = (Integer[]) tests[i][0];
                int val = (int) tests[i][1];
                int[] expected = (int[]) tests[i][2];

                // Build a fresh tree for each test
                TreeNode root = (treeArr.length == 0) ? null : TreeNode.buildTree(treeArr);

                TreeNode result;
                if (m == 0) result = solver.insertIntoBST(root, val);
                else if (m == 1) result = solver.insertIntoBST2(root, val);
                else result = solver.insertIntoBST3(root, val);

                List<Integer> actualList = inorder(result);
                int[] actual = actualList.stream().mapToInt(Integer::intValue).toArray();
                boolean validBST = isValidBST(result, Long.MIN_VALUE, Long.MAX_VALUE);

                boolean ok = java.util.Arrays.equals(expected, actual) && validBST;
                if (ok) passCounts[m]++;
                System.out.printf("Test %2d: tree=%-35s val=%2d => expected=%-30s actual=%-30s bst=%s => %s\n",
                        i + 1, java.util.Arrays.toString(treeArr), val,
                        java.util.Arrays.toString(expected), java.util.Arrays.toString(actual),
                        validBST, (ok ? "PASS" : "FAIL"));
            }
            System.out.println("\n" + "=".repeat(50));
        }

        System.out.println("\nOverall Summary:");
        for (int m = 0; m < 3; m++) {
            System.out.printf("%s: %d/%d tests passed\n", methodNames[m], passCounts[m], tests.length);
        }
    }
}
