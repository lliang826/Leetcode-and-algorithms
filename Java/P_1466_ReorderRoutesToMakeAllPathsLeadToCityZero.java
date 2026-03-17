import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class P_1466_ReorderRoutesToMakeAllPathsLeadToCityZero {
    /*
    DFS + adjacency list approach. For this problem, we have to return the minimum number of edges changed
    so that all cities are directed towards city 0. We can also think of this as counting the number of 
    edges pointing AWAY from node 0.
    We can do this by pretending that the input is an undirected graph. Since we can travel in both directions
    between nodes, if we perform a DFS on node 0 and we mark all visited nodes as seen, we will eventually
    reach the leaf nodes. As we travel outwards, if we find any edges from the input that use the same
    outward direction, we know that they should be reversed, and therefore we can add them to our counter.
    
    Time: O(n + e), where n is the # of nodes and e is the # of edges
     - For this problem, we know that there are n - 1 roads/edges, so O(n + n - 1) => O(n)
    Space: O(n + e) => O(n)
    */
    class Solution {
        private Map<Integer, List<Integer>> graph = new HashMap<>();
        private Set<String> set = new HashSet<>();
        private int edges = 0;
        private Set<Integer> seen = new HashSet<>();

        public int minReorder(int n, int[][] connections) {
            this.buildGraph(n, connections);

            for (int i = 0; i < n; i++) {
                if (!seen.contains(i)) {
                    dfs(i);
                }
            }

            return edges;
        }

        private void dfs(int i) {
            seen.add(i);
            for (int city : graph.get(i)) {
                if (!seen.contains(city)) {
                    if (set.contains(i + "," + city)) {
                        edges++;
                    }
                    dfs(city);
                }
            }
        }

        private void buildGraph(int n, int[][] connections) {
            for (int i = 0; i < n; i++) {
                graph.put(i, new ArrayList<>());
            }

            for (int[] edge : connections) {
                graph.get(edge[0]).add(edge[1]);
                graph.get(edge[1]).add(edge[0]);
                set.add(edge[0] + "," + edge[1]);
            }
        }
    }

    public static void main(String[] args) {
        // Each test: { n, connections[][], expected }
        Object[][] tests = {
                // LeetCode Example 1: three roads point away from 0
                { 6, new int[][] { { 0, 1 }, { 1, 3 }, { 2, 3 }, { 4, 0 }, { 4, 5 } }, 3 },
                // LeetCode Example 2: two roads need reversing
                { 5, new int[][] { { 1, 0 }, { 1, 2 }, { 3, 2 }, { 3, 4 } }, 2 },
                // LeetCode Example 3: all roads already point to 0
                { 3, new int[][] { { 1, 0 }, { 2, 0 } }, 0 },
                // Two cities: road away from 0
                { 2, new int[][] { { 0, 1 } }, 1 },
                // Two cities: road toward 0
                { 2, new int[][] { { 1, 0 } }, 0 },
                // Linear chain all pointing away from 0
                { 4, new int[][] { { 0, 1 }, { 1, 2 }, { 2, 3 } }, 3 },
                // Linear chain all pointing toward 0
                { 4, new int[][] { { 3, 2 }, { 2, 1 }, { 1, 0 } }, 0 },
                // Star: all roads from 0 outward (all need reversing)
                { 4, new int[][] { { 0, 1 }, { 0, 2 }, { 0, 3 } }, 3 },
                // Star: all roads toward 0 (none need reversing)
                { 4, new int[][] { { 1, 0 }, { 2, 0 }, { 3, 0 } }, 0 },
                // Mixed directions in a longer chain
                { 5, new int[][] { { 0, 1 }, { 2, 1 }, { 3, 2 }, { 3, 4 } }, 2 },
        };

        System.out.println("Running tests for P_1466 minReorder\n");
        int passed = 0;
        for (int i = 0; i < tests.length; i++) {
            int n = (int) tests[i][0];
            int[][] connections = (int[][]) tests[i][1];
            int expected = (int) tests[i][2];

            // New instance per test to reset state (graph, set, edges, seen)
            P_1466_ReorderRoutesToMakeAllPathsLeadToCityZero outer =
                    new P_1466_ReorderRoutesToMakeAllPathsLeadToCityZero();
            int actual = outer.new Solution().minReorder(n, connections);

            boolean ok = actual == expected;
            if (ok) passed++;
            System.out.printf("Test %d: n=%d, connections=%s => expected=%d, actual=%d => %s\n",
                    i + 1, n, java.util.Arrays.deepToString(connections),
                    expected, actual, ok ? "PASS" : "FAIL");
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("minReorder: %d/%d tests passed\n", passed, tests.length);
    }
}
