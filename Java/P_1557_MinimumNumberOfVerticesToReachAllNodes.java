import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class P_1557_MinimumNumberOfVerticesToReachAllNodes {
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
}
