import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class P_1129_ShortestPathWithAlternatingColors {
    class Solution {
        Map<Integer, List<Integer>> redMap = new HashMap<>();
        Map<Integer, List<Integer>> blueMap = new HashMap<>();

        public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
            this.buildGraph(n, redEdges, blueEdges);

            int[] res = new int[n];
            for (int i = 0; i < n; i++) {
                res[i] = -1;
            }
            Queue<int[]> queue = new ArrayDeque<>();
            boolean[][] visited = new boolean[n][2];
            int level = 0;

            queue.offer(new int[] { 0, 0 }); // 0 is red
            visited[0][0] = true;
            res[0] = 0;

            queue.offer(new int[] { 0, 1 }); // 1 is blue
            visited[0][1] = true;
            res[0] = 0;

            while (!queue.isEmpty()) {
                int levelSize = queue.size();

                for (int i = 0; i < levelSize; i++) {
                    int[] node = queue.poll();
                    if (res[node[0]] == -1) {
                        res[node[0]] = level;
                    }

                    if (node[1] == 0) {
                        for (int neighbor : blueMap.get(node[0])) {
                            if (!visited[neighbor][1]) {
                                queue.offer(new int[] { neighbor, 1 });
                                visited[neighbor][1] = true;
                            }
                        }
                    } else {
                        for (int neighbor : redMap.get(node[0])) {
                            if (!visited[neighbor][0]) {
                                queue.offer(new int[] { neighbor, 0 });
                                visited[neighbor][0] = true;
                            }
                        }
                    }
                }

                level++;
            }

            return res;
        }

        private void buildGraph(int n, int[][] redEdges, int[][] blueEdges) {
            for (int i = 0; i < n; i++) {
                redMap.put(i, new ArrayList<>());
                blueMap.put(i, new ArrayList<>());
            }

            for (int i = 0; i < redEdges.length; i++) {
                redMap.get(redEdges[i][0]).add(redEdges[i][1]);
            }

            for (int i = 0; i < blueEdges.length; i++) {
                blueMap.get(blueEdges[i][0]).add(blueEdges[i][1]);
            }
        }
    }

    class Solution2 {
        int RED = 0;
        int BLUE = 1;
        Map<Integer, List<Integer>> redGraph = new HashMap<>();
        Map<Integer, List<Integer>> blueGraph = new HashMap<>();

        public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
            this.buildGraphs(n, redEdges, blueEdges);

            int[] res = new int[n];
            for (int i = 0; i < n; i++) {
                res[i] = -1;
            }

            Queue<int[]> queue = new ArrayDeque<>();
            boolean[][] visited = new boolean[n][2];
            int level = 0;

            queue.offer(new int[] { 0, RED });
            visited[0][RED] = true;

            queue.offer(new int[] { 0, BLUE });
            visited[0][BLUE] = true;
            res[0] = 0;

            while (!queue.isEmpty()) {
                int levelSize = queue.size();

                for (int i = 0; i < levelSize; i++) {
                    int[] node = queue.poll();
                    if (res[node[0]] == -1) {
                        res[node[0]] = level;
                    }

                    if (node[1] == RED) {
                        for (int neighbor : blueGraph.get(node[0])) {
                            if (!visited[neighbor][BLUE]) {
                                queue.offer(new int[] { neighbor, BLUE });
                                visited[neighbor][BLUE] = true;
                            }
                        }
                    } else {
                        for (int neighbor : redGraph.get(node[0])) {
                            if (!visited[neighbor][RED]) {
                                queue.offer(new int[] { neighbor, RED });
                                visited[neighbor][RED] = true;
                            }
                        }
                    }
                }

                level++;
            }

            return res;
        }

        private void buildGraphs(int n, int[][] redEdges, int[][] blueEdges) {
            for (int i = 0; i < n; i++) {
                redGraph.put(i, new ArrayList<>());
                blueGraph.put(i, new ArrayList<>());
            }

            for (int[] edge : redEdges) {
                redGraph.get(edge[0]).add(edge[1]);
            }

            for (int[] edge : blueEdges) {
                blueGraph.get(edge[0]).add(edge[1]);
            }
        }
    }
}
