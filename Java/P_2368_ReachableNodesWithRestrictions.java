import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class P_2368_ReachableNodesWithRestrictions {
    /*
    DFS + hashset to track visited nodes.
    Since the input is a 2D array of edges, we first transform this input into an adjacency list hashmap. The problem
    description tells us that the input is an UNDIRECTED tree, so for each edge, we have to update the adjacency list
    twice (one for each direction).
    The problem description tells us to return the "maximum number of nodes you can reach FROM NODE 0", so we only
    perform DFS from node 0.
    As we traverse in the graph in DFS, we track all visited nodes using a set. If the current node is in the list
    of restricted nodes, we don't do anything. Otherwise, we can increment our counter and continue the DFS.
    
    Time: O(n + e) => O(n + n - 1) => O(n), where n is the # of nodes and e is the # of edges
    Space: O(n)
      - O(n + e) but since e = n - 1, O(n + e) => O(n + n - 1) => O(n)
    */
    class Solution {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        int max = 0;

        public int reachableNodes(int n, int[][] edges, int[] restricted) {
            Set<Integer> restrict = new HashSet<>();
            for (int i : restricted) {
                restrict.add(i);
            }

            this.buildGraph(n, edges);
            this.dfs(0, restrict);
            return max;
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

        private void dfs(int node, Set<Integer> restrict) {
            visited.add(node);
            if (restrict.contains(node)) {
                return;
            }
            max++;
            for (int n : graph.get(node)) {
                if (!visited.contains(n)) {
                    dfs(n, restrict);
                }
            }
        }
    }

    /*
    Cleaner version: uses a List<List<>> instead of HashMap since nodes are 0..n-1, and pre-seeds the
    visited set with restricted nodes so they are never entered during DFS. This eliminates the need for a
    separate restrict set and the if-check inside DFS. DFS returns the count directly instead of mutating
    an instance field, making the solution stateless and safe to call repeatedly.

    Time: O(n), where n is the # of nodes (a tree has n - 1 edges)
    Space: O(n)
      - Adjacency list: O(n) since e = n - 1
      - visited set: O(n)
      - Recursion stack: O(n) worst case (straight-line tree)
    */
    class Solution2 {
        public int reachableNodes(int n, int[][] edges, int[] restricted) {
            List<List<Integer>> graph = buildGraph(n, edges);

            Set<Integer> visited = new HashSet<>();
            for (int r : restricted) {
                visited.add(r);
            }

            return dfs(0, graph, visited);
        }

        private int dfs(int node, List<List<Integer>> graph, Set<Integer> visited) {
            visited.add(node);
            int count = 1;
            for (int neighbor : graph.get(node)) {
                if (!visited.contains(neighbor)) {
                    count += dfs(neighbor, graph, visited);
                }
            }
            return count;
        }

        private List<List<Integer>> buildGraph(int n, int[][] edges) {
            List<List<Integer>> graph = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                graph.add(new ArrayList<>());
            }
            for (int[] e : edges) {
                graph.get(e[0]).add(e[1]);
                graph.get(e[1]).add(e[0]);
            }
            return graph;
        }
    }

    /*
    Instead of filtering out the restricted nodes when we perform the DFS traversal, we can also filter when we build
    the adjacency list. Restricted nodes aren't added, which means they won't be visited. Then, we simply have to
    return the count of the visited nodes.
    
    Same time and space complexities.
    */
    class Solution3 {
        public int reachableNodes(int n, int[][] edges, int[] restricted) {
            List<List<Integer>> graph = buildGraph(n, edges, restricted);
            Set<Integer> visited = new HashSet<>();
            dfs(0, graph, visited);
            return visited.size();
        }

        private void dfs(int node, List<List<Integer>> graph, Set<Integer> visited) {
            visited.add(node);
            for (int neighbor : graph.get(node)) {
                if (!visited.contains(neighbor)) {
                    dfs(neighbor, graph, visited);
                }
            }
        }

        private List<List<Integer>> buildGraph(int n, int[][] edges, int[] restricted) {
            Set<Integer> restrict = new HashSet<>();
            for (int r : restricted) {
                restrict.add(r);
            } 
            List<List<Integer>> graph = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                graph.add(new ArrayList<>());
            }
            for (int[] e : edges) {
                if (!restrict.contains(e[0]) && !restrict.contains(e[1])) {
                    graph.get(e[0]).add(e[1]);
                    graph.get(e[1]).add(e[0]);
                }
            }
            return graph;
        }
    }

    public static void main(String[] args) {
        P_2368_ReachableNodesWithRestrictions outer = new P_2368_ReachableNodesWithRestrictions();
        Solution2 s2 = outer.new Solution2();
        Solution3 s3 = outer.new Solution3();

        // Test cases: {n, edges, restricted, expected}
        Object[][] tests = new Object[][] {
                { 7, new int[][] { { 0, 1 }, { 1, 2 }, { 3, 1 }, { 4, 0 }, { 0, 5 }, { 5, 6 } },
                        new int[] { 4, 5 }, 4 },
                { 7, new int[][] { { 0, 1 }, { 0, 2 }, { 0, 5 }, { 0, 4 }, { 3, 2 }, { 6, 5 } },
                        new int[] { 4, 2, 1 }, 3 },
                { 2, new int[][] { { 0, 1 } }, new int[] { 1 }, 1 },
                { 1, new int[][] {}, new int[] {}, 1 },
                { 4, new int[][] { { 0, 1 }, { 1, 2 }, { 2, 3 } }, new int[] { 1 }, 1 },
                { 5, new int[][] { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 0, 4 } }, new int[] {}, 5 },
                { 6, new int[][] { { 0, 1 }, { 0, 2 }, { 1, 3 }, { 1, 4 }, { 2, 5 } },
                        new int[] { 2 }, 4 },
        };

        String[] methodNames = { "reachableNodes (Solution)", "reachableNodes (Solution2)", "reachableNodes (Solution3)" };
        int[] passCounts = new int[3];

        for (int m = 0; m < 3; m++) {
            System.out.printf("Running tests for P_2368_ReachableNodesWithRestrictions.%s\n\n", methodNames[m]);
            for (int i = 0; i < tests.length; i++) {
                int n = (int) tests[i][0];
                int[][] edges = (int[][]) tests[i][1];
                int[] restricted = (int[]) tests[i][2];
                int expected = (int) tests[i][3];

                int actual;
                if (m == 0) {
                    // Solution uses instance fields — need a fresh instance each time
                    Solution fresh = outer.new Solution();
                    actual = fresh.reachableNodes(n, edges, restricted);
                } else if (m == 1) {
                    actual = s2.reachableNodes(n, edges, restricted);
                } else {
                    actual = s3.reachableNodes(n, edges, restricted);
                }

                boolean ok = actual == expected;
                if (ok) passCounts[m]++;
                System.out.printf("Test %d: n=%d, restricted=%s => expected=%d, actual=%d => %s\n",
                        i + 1, n, java.util.Arrays.toString(restricted), expected, actual,
                        (ok ? "PASS" : "FAIL"));
            }
            System.out.println("\n" + "=".repeat(50));
            if (m < 2) System.out.println();
        }

        System.out.printf("\nOverall Summary:\n");
        for (int m = 0; m < 3; m++) {
            System.out.printf("%s: %d/%d tests passed\n", methodNames[m], passCounts[m], tests.length);
        }
    }
}
