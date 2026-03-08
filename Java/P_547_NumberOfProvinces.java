import java.util.ArrayList;
import java.util.Arrays;
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

    class Solution3 {
        private Set<Integer> seen = new HashSet<>();
        private HashMap<Integer, List<Integer>> graph = new HashMap<>();

        public int findCircleNum(int[][] isConnected) {
            this.buildGraph(isConnected);

            int res = 0;
            for (int i = 0; i < graph.size(); i++) {
                if (!seen.contains(i)) {
                    res++;
                    dfs(i);
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
}
