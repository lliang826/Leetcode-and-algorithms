import java.util.ArrayList;

import data_structures.TreeNode;

public class P_98_ValidateBinarySearchTree {
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
