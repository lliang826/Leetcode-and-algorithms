import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class P_547_NumberOfProvinces {
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
}
