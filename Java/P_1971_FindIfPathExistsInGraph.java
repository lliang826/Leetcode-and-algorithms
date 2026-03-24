import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class P_1971_FindIfPathExistsInGraph {
    /*
    DFS approach: build an adjacency list from the edges and then run DFS from the source node,
    marking every reachable node in a seen set. After the traversal completes, check whether the
    destination was visited.

    Time: O(V + E), where V = number of nodes, E = number of edges
    Space: O(V + E) for the adjacency list and the visited set
    */
    class Solution {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Set<Integer> seen = new HashSet<>();

        public boolean validPath(int n, int[][] edges, int source, int destination) {
            this.buildGraph(n, edges);
            this.dfs(source);
            return seen.contains(destination);
        }

        private void dfs(int i) {
            seen.add(i);
            for (int n : graph.get(i)) {
                if (!seen.contains(n)) {
                    dfs(n);
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

    /*
    DFS with early termination: same adjacency list approach, but dfs returns a boolean so we can
    stop exploring as soon as the destination is found. This avoids traversing the entire connected
    component when the destination is reachable early in the search.

    Time: O(V + E), where V = number of nodes, E = number of edges (worst case unchanged)
    Space: O(V + E) for the adjacency list and the visited set
    */
    class Solution2 {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Set<Integer> seen = new HashSet<>();

        public boolean validPath(int n, int[][] edges, int source, int destination) {
            this.buildGraph(n, edges);
            return this.dfs(source, destination);
        }

        private boolean dfs(int i, int destination) {
            seen.add(i);
            if (i == destination) {
                return true;
            }
            for (int n : graph.get(i)) {
                if (!seen.contains(n)) {
                    if (dfs(n, destination)) {
                        return true;
                    }
                }
            }
            return false;
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
        P_1971_FindIfPathExistsInGraph outer = new P_1971_FindIfPathExistsInGraph();

        // {n, edges, source, destination, expected}
        Object[][] tests = new Object[][] {
                { 3, new int[][] { { 0, 1 }, { 1, 2 }, { 2, 0 } }, 0, 2, true },
                { 6, new int[][] { { 0, 1 }, { 0, 2 }, { 3, 5 }, { 5, 4 }, { 4, 3 } }, 0, 5, false },
                { 1, new int[][] {}, 0, 0, true },                                          // single node, source == destination
                { 2, new int[][] { { 0, 1 } }, 0, 1, true },                                // two connected nodes
                { 2, new int[][] {}, 0, 1, false },                                         // two disconnected nodes
                { 5, new int[][] { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 3, 4 } }, 0, 4, true }, // linear chain
                { 4, new int[][] { { 0, 1 }, { 2, 3 } }, 0, 3, false },                    // two separate components
                { 4, new int[][] { { 0, 1 }, { 1, 2 }, { 2, 3 } }, 3, 0, true },           // reverse direction
                { 5, new int[][] { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 0, 4 } }, 1, 4, true }, // star graph
                { 7, new int[][] { { 0, 1 }, { 1, 2 }, { 3, 4 }, { 4, 5 }, { 5, 6 } }, 0, 6, false }, // two disconnected chains
        };

        System.out.println("Running tests for Solution (DFS)\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int n = (int) tests[i][0];
            int[][] edges = (int[][]) tests[i][1];
            int source = (int) tests[i][2];
            int destination = (int) tests[i][3];
            boolean expected = (boolean) tests[i][4];
            // fresh instance each time since graph/seen are instance fields
            Solution s1 = outer.new Solution();
            boolean actual = s1.validPath(n, edges, source, destination);
            boolean ok = expected == actual;
            if (ok) pass1++;
            System.out.printf("Test %d: n=%d, source=%d, dest=%d => expected=%b, actual=%b => %s\n",
                    i + 1, n, source, destination, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for Solution2 (DFS with early termination)\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int n = (int) tests[i][0];
            int[][] edges = (int[][]) tests[i][1];
            int source = (int) tests[i][2];
            int destination = (int) tests[i][3];
            boolean expected = (boolean) tests[i][4];
            Solution2 s2 = outer.new Solution2();
            boolean actual = s2.validPath(n, edges, source, destination);
            boolean ok = expected == actual;
            if (ok) pass2++;
            System.out.printf("Test %d: n=%d, source=%d, dest=%d => expected=%b, actual=%b => %s\n",
                    i + 1, n, source, destination, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("Solution (DFS): %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("Solution2 (DFS early termination): %d/%d tests passed\n", pass2, tests.length);
    }
}
