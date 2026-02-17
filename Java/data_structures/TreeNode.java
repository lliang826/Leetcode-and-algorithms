package data_structures;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    // Helper method to build tree from level-order array (null represents no node)
    public static TreeNode buildTree(Integer[] values) {
        if (values == null || values.length == 0 || values[0] == null) {
            return null;
        }
        
        TreeNode root = new TreeNode(values[0]);
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.offer(root);
        int i = 1;
        
        while (!queue.isEmpty() && i < values.length) {
            TreeNode node = queue.poll();
            
            // Left child
            if (i < values.length && values[i] != null) {
                node.left = new TreeNode(values[i]);
                queue.offer(node.left);
            }
            i++;
            
            // Right child
            if (i < values.length && values[i] != null) {
                node.right = new TreeNode(values[i]);
                queue.offer(node.right);
            }
            i++;
        }
        
        return root;
    }

    // Helper method to find a node by value (first match in level order; assumes values are unique)
    public static TreeNode findNodeByValue(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.val == val) {
                return node;
            }
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return null;
    }

    // Helper method to print tree structure (level order, up to 7 nodes)
    public static String treeToString(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int count = 0;
        java.util.Queue<TreeNode> q = new java.util.LinkedList<>();
        q.add(root);
        while (!q.isEmpty() && count < 7) {
            TreeNode node = q.poll();
            if (node == null) {
                sb.append("null");
            } else {
                sb.append(node.val);
                q.add(node.left);
                q.add(node.right);
            }
            if (count < 6) sb.append(",");
            count++;
        }
        if (count == 7 && !q.isEmpty()) sb.append("...");
        sb.append("]");
        return sb.toString();
    }
}
