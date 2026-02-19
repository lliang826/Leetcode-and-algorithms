import java.util.ArrayList;

import data_structures.TreeNode;

public class P_98_ValidateBinarySearchTree {
    /*
    DFS approach. Since this is a BST, we can perform an in-order traversal and store all the node values
    into a list. If the list values are not in ascending order, then the tree is not a valid BST.
    
    Time: O(n), where n is the number of nodes in the tree, we visit every node once.
    Space: O(n), we store all node values in a list
    */
    class Solution {
        public boolean isValidBST(TreeNode root) {
            ArrayList<Integer> list = new ArrayList<>();
            this.dfs(root, list);

            for (int i = 1; i < list.size(); i++) {
                if (list.get(i) <= list.get(i - 1)) {
                    return false;
                }
            }

            return true;
        }

        private void dfs(TreeNode root, ArrayList<Integer> list) {
            if (root == null) {
                return;
            }

            dfs(root.left, list);
            list.add(root.val);
            dfs(root.right, list);
        }
    }
}
