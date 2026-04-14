import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class P_1129_ShortestPathWithAlternatingColors {
    /*
    Augmented-state BFS. Since each node can be reached with edges of different color, we need an extra
    state/dimension; node + color. Both the queue and the visited array are 2D.
    Breadth-first search over states of the form (node, last color used). We start from node 0 in both
    color states so the first real edge can be either red or blue. Because a node can be reached with two
    different last-edge colors, we track visited[node][color] instead of just visited[node].

    Time: O(n + r + b), where r is redEdges.length and b is blueEdges.length
    Space: O(n + r + b), for the adjacency lists, queue, and visited table
    */
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

    /*
    Almost identical to the solution above, but we use constants for the Blue and Red colors to
    improve readability, and we also use for each loops in the buildGraphs() helper function.
    */
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

    public static void main(String[] args) {
        P_1129_ShortestPathWithAlternatingColors outer = new P_1129_ShortestPathWithAlternatingColors();

        // Test cases: {n, red edges, blue edges, expected output}
        Object[][] tests = new Object[][] {
                { 1, new int[][] {}, new int[][] {}, new int[] { 0 } },
                { 3, new int[][] { { 0, 1 }, { 1, 2 } }, new int[][] {}, new int[] { 0, 1, -1 } },
                { 3, new int[][] { { 0, 1 } }, new int[][] { { 1, 2 } }, new int[] { 0, 1, 2 } },
                { 3, new int[][] {}, new int[][] { { 0, 1 }, { 1, 2 } }, new int[] { 0, 1, -1 } },
                { 3, new int[][] { { 0, 1 } }, new int[][] { { 2, 1 } }, new int[] { 0, 1, -1 } },
                { 5, new int[][] { { 0, 1 }, { 0, 2 } }, new int[][] { { 1, 3 }, { 2, 3 }, { 3, 4 } },
                        new int[] { 0, 1, 1, 2, -1 } },
                { 5, new int[][] { { 0, 1 }, { 2, 3 }, { 3, 4 } }, new int[][] { { 1, 2 }, { 2, 1 } },
                        new int[] { 0, 1, 2, 3, -1 } },
                { 4, new int[][] { { 0, 1 }, { 2, 2 } }, new int[][] { { 1, 2 }, { 2, 3 }, { 3, 1 } },
                        new int[] { 0, 1, 2, 4 } }
        };

        System.out.println("Running tests for P_1129_ShortestPathWithAlternatingColors.Solution\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int n = (int) tests[i][0];
            int[][] redEdges = deepCopy((int[][]) tests[i][1]);
            int[][] blueEdges = deepCopy((int[][]) tests[i][2]);
            int[] expected = (int[]) tests[i][3];
            Solution solver = outer.new Solution();
            int[] actual = solver.shortestAlternatingPaths(n, redEdges, blueEdges);

            boolean ok = Arrays.equals(expected, actual);
            if (ok)
                pass1++;
            System.out.printf("Test %d: n=%d, red=%s, blue=%s => expected=%s, actual=%s => %s\n",
                    i + 1, n, Arrays.deepToString((int[][]) tests[i][1]), Arrays.deepToString((int[][]) tests[i][2]),
                    Arrays.toString(expected), Arrays.toString(actual), (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_1129_ShortestPathWithAlternatingColors.Solution2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int n = (int) tests[i][0];
            int[][] redEdges = deepCopy((int[][]) tests[i][1]);
            int[][] blueEdges = deepCopy((int[][]) tests[i][2]);
            int[] expected = (int[]) tests[i][3];
            Solution2 solver = outer.new Solution2();
            int[] actual = solver.shortestAlternatingPaths(n, redEdges, blueEdges);

            boolean ok = Arrays.equals(expected, actual);
            if (ok)
                pass2++;
            System.out.printf("Test %d: n=%d, red=%s, blue=%s => expected=%s, actual=%s => %s\n",
                    i + 1, n, Arrays.deepToString((int[][]) tests[i][1]), Arrays.deepToString((int[][]) tests[i][2]),
                    Arrays.toString(expected), Arrays.toString(actual), (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("Solution: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("Solution2: %d/%d tests passed\n", pass2, tests.length);
    }

    private static int[][] deepCopy(int[][] edges) {
        int[][] copy = new int[edges.length][];

        for (int i = 0; i < edges.length; i++) {
            copy[i] = Arrays.copyOf(edges[i], edges[i].length);
        }

        return copy;
    }
}
