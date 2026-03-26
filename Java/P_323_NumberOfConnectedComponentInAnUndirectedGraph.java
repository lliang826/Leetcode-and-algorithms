import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class P_323_NumberOfConnectedComponentInAnUndirectedGraph {
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
}
