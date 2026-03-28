import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class P_323_NumberOfConnectedComponentInAnUndirectedGraph {
    /*
    DFS with "seen" hashset approach. This problem is basically identical to 547 Number of Provinces.
    In both problems, we are looking for the number of provinces/connected components. This can be found
    by using a DFS on the graph and using a hashset to track the seen/visited nodes. We attempt a DFS on
    every node from 0 to n - 1, and if we are able to call DFS on a node that hasn't been seen before,
    we know that it's a new connected component, and we increment the counter.
    
    Time: O(n + e), where n is the # of nodes and e is the # of edges
    Space: O(n + e), stored in the adjacency list hashmap
    */
    class Solution {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Set<Integer> seen = new HashSet<>();

        public int countComponents(int n, int[][] edges) {
            int numOfConnected = 0;
            this.buildGraph(n, edges);

            for (int i = 0; i < n; i++) {
                if (!seen.contains(i)) {
                    numOfConnected++;
                    this.dfs(i);
                }
            }
            return numOfConnected;
        }

        private void dfs(int i) {
            seen.add(i);
            for (int destination : graph.get(i)) {
                if (!seen.contains(destination)) {
                    dfs(destination);
                }
            }
        }

        private void buildGraph(int n, int[][] edges) {
            for (int i = 0; i < n; i++) {
                graph.put(i, new ArrayList<>());
            }

            for (int[] edge : edges) {
                graph.get(edge[0]).add(edge[1]);
                graph.get(edge[1]).add(edge[0]);
            }
        }
    }

    public static void main(String[] args) {
        P_323_NumberOfConnectedComponentInAnUndirectedGraph outer =
                new P_323_NumberOfConnectedComponentInAnUndirectedGraph();

        // {n, edges, expected}
        Object[][] tests = new Object[][] {
                { 5, new int[][] { { 0, 1 }, { 1, 2 }, { 3, 4 } }, 2 },                             // example 1: two components
                { 5, new int[][] { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 3, 4 } }, 1 },                   // example 2: all connected
                { 1, new int[][] {}, 1 },                                                             // single node, no edges
                { 4, new int[][] {}, 4 },                                                             // all isolated nodes
                { 3, new int[][] { { 0, 1 }, { 1, 2 }, { 0, 2 } }, 1 },                              // fully connected (cycle)
                { 6, new int[][] { { 0, 1 }, { 2, 3 }, { 4, 5 } }, 3 },                              // three separate pairs
                { 2, new int[][] { { 0, 1 } }, 1 },                                                  // two nodes, one edge
                { 2, new int[][] {}, 2 },                                                             // two disconnected nodes
                { 7, new int[][] { { 0, 1 }, { 1, 2 }, { 3, 4 }, { 4, 5 }, { 5, 6 } }, 2 },         // two chains
                { 5, new int[][] { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 0, 4 } }, 1 },                   // star graph
        };

        System.out.println("Running tests for Solution (DFS)\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int n = (int) tests[i][0];
            int[][] edges = (int[][]) tests[i][1];
            int expected = (int) tests[i][2];
            // fresh instance each time since graph/seen are instance fields
            Solution s = outer.new Solution();
            int actual = s.countComponents(n, edges);
            boolean ok = expected == actual;
            if (ok) pass1++;
            String edgeStr = Arrays.deepToString(edges);
            System.out.printf("Test %d: n=%d, edges=%s => expected=%d, actual=%d => %s\n",
                    i + 1, n, edgeStr, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("Solution (DFS): %d/%d tests passed\n", pass1, tests.length);
    }
}
