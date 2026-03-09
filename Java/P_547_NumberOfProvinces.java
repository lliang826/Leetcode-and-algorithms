import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class P_547_NumberOfProvinces {
    /*
    DFS + adjacency list approach. To make this graph problem more intuitive, we first convert the matrix input
    into an adjacency list. Then, we iterate through the nodes in order (n x n matrix, so we loop from 0 to n - 1).
    If we find a node that hasn't been seen yet, we increment our result counter and we visit any connected nodes
    by DFS. Visited nodes will be marked as seen.
    The result counter tracks the number of connected components, which is different from the number of total
    nodes - if a node is already seen (it's connected to another node), it won't be included in the count.

    Note: the nested loop iterates over all (i, j) pairs, which adds redundant duplicate edges (both i->j and
    j->i are added twice) and self-loops (i == j, since the diagonal is always 1). This doesn't affect
    correctness but is inefficient — Solution2 fixes this by starting j at i + 1.
    
    Time: O(n + e), where n is the # of nodes and e is the # of edges 
        - but in the worst case, if every node is connected with every other node, e = n^2 => O(n^2)
        - for this problem, time complexity is O(n^2) since we have a nested loop to build the adjacency list
    Space: O(n + e), worst case O(n^2)
    */
    class Solution {
        private Set<Integer> seen = new HashSet<>();
        private HashMap<Integer, List<Integer>> graph = new HashMap<>();

        public int findCircleNum(int[][] isConnected) {
            int length = isConnected.length;

            for (int i = 0; i < length; i++) {
                if (!graph.containsKey(i)) {
                    graph.put(i, new ArrayList<>());
                }
                for (int j = 0; j < length; j++) {
                    if (!graph.containsKey(j)) {
                        graph.put(j, new ArrayList<>());
                    }
                    if (isConnected[i][j] == 1) {
                        graph.get(i).add(j);
                        graph.get(j).add(i);
                    }
                }
            }

            int res = 0;
            for (int i = 0; i < length; i++) {
                if (!seen.contains(i)) {
                    res++;
                    seen.add(i);
                    dfs(i);
                }
            }

            return res;
        }

        private void dfs(int node) {
            for (int neighbor : graph.get(node)) {
                if (!seen.contains(neighbor)) {
                    seen.add(neighbor);
                    dfs(neighbor);
                }
            }
        }
    }

    /*
    Almost identical to the solution above, but when we build the adjacency list, the nested for loop starts
    at i + 1 so we can avoid redundant duplicate edges - more efficient. This only works for undirected
    graphs.
    
    Same time and space complexities, but slightly more efficient since we avoid redundant edges.
    */
    class Solution2 {
        private Set<Integer> seen = new HashSet<>();
        private HashMap<Integer, List<Integer>> graph = new HashMap<>();

        public int findCircleNum(int[][] isConnected) {
            int length = isConnected.length;

            for (int i = 0; i < length; i++) {
                if (!graph.containsKey(i)) {
                    graph.put(i, new ArrayList<>());
                }
                for (int j = i + 1; j < length; j++) {
                    if (!graph.containsKey(j)) {
                        graph.put(j, new ArrayList<>());
                    }
                    if (isConnected[i][j] == 1) {
                        graph.get(i).add(j);
                        graph.get(j).add(i);
                    }
                }
            }

            int res = 0;
            for (int i = 0; i < length; i++) {
                if (!seen.contains(i)) {
                    res++;
                    seen.add(i);
                    dfs(i);
                }
            }

            return res;
        }

        private void dfs(int node) {
            for (int neighbor : graph.get(node)) {
                if (!seen.contains(neighbor)) {
                    seen.add(neighbor);
                    dfs(neighbor);
                }
            }
        }
    }

    /*
    Same as the first 2 solutions above, but cleaner and slightly more intuitive. 
    Changes:
    1. Created a helper method to build the adjacency list 
    2. The buildGraph() helper method has a separate for loop to initialize the arraylists, which is cleaner.
    3. Changed for loop in the main method: after building the adjacency list, we iterate through the keys
    in the hashmap (the keys are all the nodes in the graph).
    4. We mark nodes as seen in the dfs() helper method, this is cleaner than marking them in both the main
    method and the dfs() method
    
    Same time and space complexities as solution 2.
    */
    class Solution3 {
        private Set<Integer> seen = new HashSet<>();
        private HashMap<Integer, List<Integer>> graph = new HashMap<>();

        public int findCircleNum(int[][] isConnected) {
            this.buildGraph(isConnected);

            int res = 0;
            for (int node : graph.keySet()) {
                if (!seen.contains(node)) {
                    res++;
                    dfs(node);
                }
            }

            return res;
        }

        private void dfs(int node) {
            seen.add(node);
            for (int neighbor : graph.get(node)) {
                if (!seen.contains(neighbor)) {
                    dfs(neighbor);
                }
            }
        }

        private void buildGraph(int[][] isConnected) {
            int n = isConnected.length;

            for (int i = 0; i < n; i++) {
                graph.put(i, new ArrayList<>());
            }

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (isConnected[i][j] == 1) {
                        graph.get(i).add(j);
                        graph.get(j).add(i);
                    }
                }
            }
        }
    }

    /*
    Most efficient solution to this problem. Instead of building the adjacency list, which takes additional 
    time and space, we can iterate on the matrix directly.
    
    Time: O(n^2), since dfs scans all n columns for each node (not just neighbors)
    Space: O(n), for the seen set and recursion stack (no adjacency list built)
    */
    class Solution4 {
        private Set<Integer> seen = new HashSet<>();

        public int findCircleNum(int[][] isConnected) {
            int n = isConnected.length;
            int res = 0;

            for (int i = 0; i < n; i++) {
                if (!seen.contains(i)) {
                    res += 1;
                    dfs(isConnected, i);
                }
            }

            return res;
        }

        private void dfs(int[][] isConnected, int node) {
            seen.add(node);
            for (int j = 0; j < isConnected.length; j++) {
                if (isConnected[node][j] == 1 && !seen.contains(j)) {
                    dfs(isConnected, j);
                }
            }
        }
    }

    public static void main(String[] args) {
        P_547_NumberOfProvinces outer = new P_547_NumberOfProvinces();

        int[][][] inputs = {
                {{1, 1, 0}, {1, 1, 0}, {0, 0, 1}},
                {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}},
                {{1}},
                {{1, 1}, {1, 1}},
                {{1, 0, 0, 1}, {0, 1, 1, 0}, {0, 1, 1, 1}, {1, 0, 1, 1}},
                {{1, 1, 0, 0}, {1, 1, 0, 0}, {0, 0, 1, 1}, {0, 0, 1, 1}},
                {{1, 0, 1, 0}, {0, 1, 0, 1}, {1, 0, 1, 0}, {0, 1, 0, 1}},
                {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}},
                {{1, 0, 0, 0, 0}, {0, 1, 0, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 0, 1}},
                {{1, 1, 1, 0, 0}, {1, 1, 0, 0, 0}, {1, 0, 1, 0, 0}, {0, 0, 0, 1, 1}, {0, 0, 0, 1, 1}},
        };
        int[] expected = {2, 3, 1, 1, 1, 2, 2, 1, 5, 2};

        String[] names = {"Solution", "Solution2", "Solution3", "Solution4"};
        int[] passCounts = new int[names.length];

        for (int s = 0; s < names.length; s++) {
            System.out.printf("Running tests for P_547_NumberOfProvinces.%s.findCircleNum\n\n", names[s]);
            for (int i = 0; i < inputs.length; i++) {
                int actual;
                switch (s) {
                    case 0: actual = outer.new Solution().findCircleNum(inputs[i]); break;
                    case 1: actual = outer.new Solution2().findCircleNum(inputs[i]); break;
                    case 2: actual = outer.new Solution3().findCircleNum(inputs[i]); break;
                    default: actual = outer.new Solution4().findCircleNum(inputs[i]); break;
                }
                boolean ok = actual == expected[i];
                if (ok) passCounts[s]++;
                System.out.printf("Test %d: input=%s => expected=%d, actual=%d => %s\n",
                        i + 1, Arrays.deepToString(inputs[i]), expected[i], actual, ok ? "PASS" : "FAIL");
            }
            if (s < names.length - 1) System.out.println("\n" + "=".repeat(50) + "\n");
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Overall Summary:");
        for (int s = 0; s < names.length; s++) {
            System.out.printf("%s.findCircleNum: %d/%d tests passed\n", names[s], passCounts[s], inputs.length);
        }
    }
}
