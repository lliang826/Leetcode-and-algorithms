import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class P_1466_ReorderRoutesToMakeAllPathsLeadToCityZero {
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
}
