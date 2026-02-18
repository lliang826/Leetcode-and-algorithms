public class P_938_RangeSumOfBST {
    public int rangeSumBST(TreeNode root, int low, int high) {
        if (root == null) {
            return 0;
        }

        int left = rangeSumBST(root.left, low, high);
        int right = rangeSumBST(root.right, low, high);

        boolean withinRange = root.val >= low && root.val <= high;
        int value = withinRange ? root.val : 0;
        return left + right + value;
    }

    public int rangeSumBST(TreeNode root, int low, int high) {
        if (root == null) {
            return 0;
        }

        int left = 0;
        if (root.val > low) {
            left = rangeSumBST(root.left, low, high);
        }

        int right = 0;
        if (root.val < high) {
            right = rangeSumBST(root.right, low, high);
        }

        boolean withinRange = root.val >= low && root.val <= high;
        int value = withinRange ? root.val : 0;
        return left + right + value;
    }
}