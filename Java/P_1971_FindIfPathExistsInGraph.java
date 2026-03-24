import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class P_1971_FindIfPathExistsInGraph {
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
}
