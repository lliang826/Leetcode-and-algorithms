import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class P_2368_ReachableNodesWithRestrictions {
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

    class Solution3 {
        public int reachableNodes(int n, int[][] edges, int[] restricted) {
            List<List<Integer>> graph = buildGraph(n, edges, restricted);
            Set<Integer> visited = new HashSet<>();
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
}
