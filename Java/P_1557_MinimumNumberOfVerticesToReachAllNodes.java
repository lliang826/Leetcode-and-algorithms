import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class P_1557_MinimumNumberOfVerticesToReachAllNodes {
    /*
    Initial solution: build an adjacency list and run DFS from every node to find all nodes that have
    incoming edges. Nodes with no incoming edges must be in the result since they are unreachable from
    any other node. Correct but over-engineered — the DFS and graph construction are unnecessary.

    Time: O(n + e), where n is the number of nodes and e is the number of edges
    Space: O(n + e), for the adjacency list, seen set, and incoming-edges set
    */
    class Solution {
        private Map<Integer, List<Integer>> graph = new HashMap<>();
        private Set<Integer> nodesWithIncomingEdges = new HashSet<>();
        private Set<Integer> seen = new HashSet<>();

        public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges) {
            buildGraph(n, edges);

            for (int i = 0; i < n; i++) {
                dfs(i);
            }

            List<Integer> res = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (!nodesWithIncomingEdges.contains(i)) {
                    res.add(i);
                }
            }
            return res;
        }

        private void dfs(int i) {
            seen.add(i);
            for (int n : graph.get(i)) {
                nodesWithIncomingEdges.add(n);
                if (!seen.contains(n)) {
                    dfs(n);
                }
            }
        }

        private void buildGraph(int n, List<List<Integer>> edges) {
            for (int i = 0; i < n; i++) {
                graph.put(i, new ArrayList<>());
            }

            for (int i = 0; i < edges.size(); i++) {
                int from = edges.get(i).get(0);
                int to = edges.get(i).get(1);
                graph.get(from).add(to);
            }
        }
    }

    /*
    Optimized solution: in a DAG, any node with zero in-degree is unreachable from other nodes and must
    be a starting vertex. We simply collect all edge destinations into a set (these have in-degree > 0),
    then return every node NOT in that set. No graph construction or traversal needed.

    Time: O(n + e), where n is the number of nodes and e is the number of edges
    Space: O(n), for the set of destination nodes
    */
    public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges) {
        Set<Integer> nodesWithIncomingEdges = new HashSet<>();
        for (List<Integer> list : edges) {
            int to = list.get(1);
            nodesWithIncomingEdges.add(to);
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!nodesWithIncomingEdges.contains(i)) {
                res.add(i);
            }
        }

        return res;
    }

    // Helper to build edge list from a 2D int array
    private static List<List<Integer>> toEdgeList(int[][] edges) {
        List<List<Integer>> list = new ArrayList<>();
        for (int[] e : edges) {
            list.add(Arrays.asList(e[0], e[1]));
        }
        return list;
    }

    public static void main(String[] args) {
        P_1557_MinimumNumberOfVerticesToReachAllNodes solver = new P_1557_MinimumNumberOfVerticesToReachAllNodes();

        // Each test: {n, edges[][], expected sorted result}
        Object[][] tests = {
            // Example 1: two disconnected source nodes
            { 6, new int[][]{{0,1},{0,2},{2,5},{3,4},{4,2}}, new int[]{0,3} },
            // Example 2: three source nodes feeding into shared sinks
            { 5, new int[][]{{0,1},{2,1},{3,1},{1,4},{2,4}}, new int[]{0,2,3} },
            // Minimum graph: 2 nodes, 1 edge — only one source
            { 2, new int[][]{{0,1}}, new int[]{0} },
            // No edges at all: every node is a source
            { 3, new int[][]{}, new int[]{0,1,2} },
            // Linear chain: only node 0 has no incoming edge
            { 4, new int[][]{{0,1},{1,2},{2,3}}, new int[]{0} },
            // Star graph: single source fans out to all others
            { 5, new int[][]{{0,1},{0,2},{0,3},{0,4}}, new int[]{0} },
            // Diamond DAG: 0 -> 1,2 -> 3; only node 0 is a source
            { 4, new int[][]{{0,1},{0,2},{1,3},{2,3}}, new int[]{0} },
            // Two isolated chains: 0->1->2 and 3->4->5
            { 6, new int[][]{{0,1},{1,2},{3,4},{4,5}}, new int[]{0,3} },
            // All nodes are sources except one: multiple nodes feed into node 4
            { 5, new int[][]{{0,4},{1,4},{2,4},{3,4}}, new int[]{0,1,2,3} },
            // Complex DAG with multiple sources and shared downstream nodes
            { 8, new int[][]{{0,2},{1,2},{2,3},{3,4},{5,6},{6,7},{5,7}}, new int[]{0,1,5} },
        };

        // --- Test DFS solution (inner class) ---
        System.out.println("Running tests for Solution.findSmallestSetOfVertices (DFS)\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int n = (int) tests[i][0];
            int[][] edgeArr = (int[][]) tests[i][1];
            int[] expected = (int[]) tests[i][2];

            // Inner class needs a fresh instance each time (stateful fields)
            Solution sol = solver.new Solution();
            List<Integer> result = sol.findSmallestSetOfVertices(n, toEdgeList(edgeArr));
            Collections.sort(result);
            int[] actual = result.stream().mapToInt(Integer::intValue).toArray();

            boolean ok = Arrays.equals(expected, actual);
            if (ok) pass1++;
            System.out.printf("Test %d: n=%d, edges=%s => expected=%s, actual=%s => %s%n",
                    i + 1, n, Arrays.deepToString(edgeArr),
                    Arrays.toString(expected), Arrays.toString(actual),
                    ok ? "PASS" : "FAIL");
        }

        System.out.println("\n" + "=".repeat(50));

        // --- Test optimized solution ---
        System.out.println("\nRunning tests for findSmallestSetOfVertices (optimized)\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int n = (int) tests[i][0];
            int[][] edgeArr = (int[][]) tests[i][1];
            int[] expected = (int[]) tests[i][2];

            List<Integer> result = solver.findSmallestSetOfVertices(n, toEdgeList(edgeArr));
            Collections.sort(result);
            int[] actual = result.stream().mapToInt(Integer::intValue).toArray();

            boolean ok = Arrays.equals(expected, actual);
            if (ok) pass2++;
            System.out.printf("Test %d: n=%d, edges=%s => expected=%s, actual=%s => %s%n",
                    i + 1, n, Arrays.deepToString(edgeArr),
                    Arrays.toString(expected), Arrays.toString(actual),
                    ok ? "PASS" : "FAIL");
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:%n");
        System.out.printf("Solution (DFS): %d/%d tests passed%n", pass1, tests.length);
        System.out.printf("findSmallestSetOfVertices (optimized): %d/%d tests passed%n", pass2, tests.length);
    }
}
