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
    /*
    DFS + BFS + hashset to store visited nodes.
    This question is a bit tricky because it gives us the root of a binary tree as the input, but for trees, we
    can only traverse downwards from parent to child nodes. This makes it difficult to find nodes that have a
    distance k from the target node, but are in a different subtree.
    To get around this issue, we first convert the tree into an undirected graph - with graphs, we can start at
    any node and traverse in any direction. We can do this by performing a DFS and building the undirected graph
    as a hashmap.
    Once we have the adjacency list hashmap, the rest of the problem becomes simpler - we perform a BFS starting
    with the target node. BFS allows us to visit nodes according to their distance from the target, so once we 
    find nodes with the same level/distance as k, we add them to the list.
    Finally, we return the list of values. We can also return the list of values early if we reach level k + 1.

    Time: O(n + e) => O(n + n - 1) => O(n), where n is the number of nodes in the binary tree
      - We have to visit all nodes in the tree using DFS to build the adjacency list
      - In the worst case scenario, if the target node is the root and all the nodes at level k are leaves, we
      also traverse all nodes in the tree using BFS
    Space: O(n + e) => O(n + n - 1) => O(n), we store all nodes in an adjacency list hashmap 
      - we also store all nodes in the 'visited' hashset in the worst case scenario
    */
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

    /*
    Almost the same as the solution above, but instead of building an adjacency list map, we only store the child =>
    parent edge in a hashmap. Earlier, we mentioned that the tricky part of this problem is finding nodes in other
    subtrees, which involves traversing upwards, from child node to parent node. 
    In a binary tree, a node can have at most 3 edges: left, right, and parent (we can already traverse to left and
    right). We use the hashmap to traverse from child to parent.
    
    Same time and space complexities as above, but slightly less space since we only store the child - parent edge
    instead of all edges.
    */
    class Solution2 {
        Map<TreeNode, TreeNode> parents = new HashMap<>();

        public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
            this.dfs(root);

            Queue<TreeNode> queue = new ArrayDeque<>();
            Set<TreeNode> visited = new HashSet<>();
            List<Integer> res = new ArrayList<>();
            int level = 0;

            queue.offer(target);
            visited.add(target);

            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                if (level == k + 1) {
                    return res;
                }

                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    if (level == k) {
                        res.add(node.val);
                    }

                    if (node.left != null && !visited.contains(node.left)) {
                        queue.offer(node.left);
                        visited.add(node.left);
                    }
                    if (node.right != null && !visited.contains(node.right)) {
                        queue.offer(node.right);
                        visited.add(node.right);
                    }
                    if (parents.containsKey(node) && !visited.contains(parents.get(node))) {
                        queue.offer(parents.get(node));
                        visited.add(parents.get(node));
                    }
                }

                level++;
            }

            return res;
        }

        private void dfs(TreeNode root) {
            if (root == null) {
                return;
            }

            if (root.left != null) {
                parents.put(root.left, root);
                dfs(root.left);
            }
            if (root.right != null) {
                parents.put(root.right, root);
                dfs(root.right);
            }
        }
    }

    /*
    Almost identical to solution2 above, except that we iterate over an array instead of having multiple if
    statements. 
    
    Cleaner solution, but same time and space complexities.
    */
    class Solution3 {
        Map<TreeNode, TreeNode> parents = new HashMap<>();

        public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
            this.dfs(root);

            Queue<TreeNode> queue = new ArrayDeque<>();
            Set<TreeNode> visited = new HashSet<>();
            List<Integer> res = new ArrayList<>();
            int level = 0;

            queue.offer(target);
            visited.add(target);

            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                if (level == k + 1) {
                    return res;
                }

                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    if (level == k) {
                        res.add(node.val);
                    }

                    for (TreeNode n : new TreeNode[] { node.left, node.right, parents.get(node) }) {
                        if (n != null && !visited.contains(n)) {
                            queue.offer(n);
                            visited.add(n);
                        }
                    }
                }

                level++;
            }

            return res;
        }

        private void dfs(TreeNode root) {
            if (root == null) {
                return;
            }

            if (root.left != null) {
                parents.put(root.left, root);
                dfs(root.left);
            }
            if (root.right != null) {
                parents.put(root.right, root);
                dfs(root.right);
            }
        }
    }

    public static void main(String[] args) {
        P_863_AllNodesDistanceKInBinaryTree outer = new P_863_AllNodesDistanceKInBinaryTree();

        // Test cases: {tree level-order array, target value, k, expected sorted result}
        Object[][] tests = new Object[][] {
                { new Integer[] { 3, 5, 1, 6, 2, 0, 8, null, null, 7, 4 }, 5, 2,
                        List.of(1, 4, 7) },
                { new Integer[] { 1 }, 1, 3, List.of() },
                { new Integer[] { 1 }, 1, 0, List.of(1) },
                { new Integer[] { 3, 5, 1, 6, 2, 0, 8, null, null, 7, 4 }, 3, 0,
                        List.of(3) },
                { new Integer[] { 3, 5, 1, 6, 2, 0, 8, null, null, 7, 4 }, 3, 1,
                        List.of(1, 5) },
                { new Integer[] { 3, 5, 1, 6, 2, 0, 8, null, null, 7, 4 }, 7, 1,
                        List.of(2) },
                { new Integer[] { 3, 5, 1, 6, 2, 0, 8, null, null, 7, 4 }, 6, 3,
                        List.of(1, 4, 7) },
                { new Integer[] { 0, 1, null, 3, 2 }, 2, 1, List.of(1) },
                { new Integer[] { 0, 1, null, 3, 2 }, 2, 3, List.of() },
                { new Integer[] { 1, 2, 3 }, 2, 1, List.of(1) },
        };

        System.out.println("Running tests for P_863_AllNodesDistanceKInBinaryTree.Solution.distanceK\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            Integer[] treeArr = (Integer[]) tests[i][0];
            int targetVal = (int) tests[i][1];
            int k = (int) tests[i][2];
            @SuppressWarnings("unchecked")
            List<Integer> expected = (List<Integer>) tests[i][3];

            TreeNode root = TreeNode.buildTree(treeArr);
            TreeNode target = TreeNode.findNodeByValue(root, targetVal);

            // New Solution per test to avoid stale map state
            Solution solver = outer.new Solution();
            List<Integer> actual = solver.distanceK(root, target, k);

            List<Integer> sortedActual = new ArrayList<>(actual);
            List<Integer> sortedExpected = new ArrayList<>(expected);
            java.util.Collections.sort(sortedActual);
            java.util.Collections.sort(sortedExpected);

            boolean ok = sortedExpected.equals(sortedActual);
            if (ok)
                pass1++;
            System.out.printf("Test %d: tree=%s, target=%d, k=%d => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(treeArr), targetVal, k,
                    sortedExpected, sortedActual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_863_AllNodesDistanceKInBinaryTree.Solution2.distanceK\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            Integer[] treeArr = (Integer[]) tests[i][0];
            int targetVal = (int) tests[i][1];
            int k = (int) tests[i][2];
            @SuppressWarnings("unchecked")
            List<Integer> expected = (List<Integer>) tests[i][3];

            TreeNode root = TreeNode.buildTree(treeArr);
            TreeNode target = TreeNode.findNodeByValue(root, targetVal);

            Solution2 solver = outer.new Solution2();
            List<Integer> actual = solver.distanceK(root, target, k);

            List<Integer> sortedActual = new ArrayList<>(actual);
            List<Integer> sortedExpected = new ArrayList<>(expected);
            java.util.Collections.sort(sortedActual);
            java.util.Collections.sort(sortedExpected);

            boolean ok = sortedExpected.equals(sortedActual);
            if (ok)
                pass2++;
            System.out.printf("Test %d: tree=%s, target=%d, k=%d => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(treeArr), targetVal, k,
                    sortedExpected, sortedActual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_863_AllNodesDistanceKInBinaryTree.Solution3.distanceK\n");
        int pass3 = 0;
        for (int i = 0; i < tests.length; i++) {
            Integer[] treeArr = (Integer[]) tests[i][0];
            int targetVal = (int) tests[i][1];
            int k = (int) tests[i][2];
            @SuppressWarnings("unchecked")
            List<Integer> expected = (List<Integer>) tests[i][3];

            TreeNode root = TreeNode.buildTree(treeArr);
            TreeNode target = TreeNode.findNodeByValue(root, targetVal);

            Solution3 solver = outer.new Solution3();
            List<Integer> actual = solver.distanceK(root, target, k);

            List<Integer> sortedActual = new ArrayList<>(actual);
            List<Integer> sortedExpected = new ArrayList<>(expected);
            java.util.Collections.sort(sortedActual);
            java.util.Collections.sort(sortedExpected);

            boolean ok = sortedExpected.equals(sortedActual);
            if (ok)
                pass3++;
            System.out.printf("Test %d: tree=%s, target=%d, k=%d => expected=%s, actual=%s => %s\n",
                    i + 1, java.util.Arrays.toString(treeArr), targetVal, k,
                    sortedExpected, sortedActual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("Solution.distanceK:  %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("Solution2.distanceK: %d/%d tests passed\n", pass2, tests.length);
        System.out.printf("Solution3.distanceK: %d/%d tests passed\n", pass3, tests.length);
    }
}
