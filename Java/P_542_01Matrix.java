import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class P_542_01Matrix {
    /*
    Brute-force BFS approach. For each cell in the matrix, run a separate BFS to find
    the nearest 0. This works correctly but is inefficient because neighboring 1-cells
    end up exploring much of the same territory redundantly.

    Time: O(m^2 * n^2) — each of the m*n cells performs a BFS that can visit up to m*n cells
    Space: O(m * n) — visited array and queue per BFS call
    */
    class Solution {
        public int[][] updateMatrix(int[][] mat) {
            int m = mat.length;
            int n = mat[0].length;
            int[][] res = new int[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    res[i][j] = bfs(i, j, mat, 0, m, n);
                }
            }

            return res;
        }

        private int bfs(int row, int col, int[][] mat, int distance, int m, int n) {
            if (mat[row][col] == 0) {
                return distance;
            }

            Queue<int[]> queue = new ArrayDeque<>();
            boolean[][] visited = new boolean[m][n];
            int[][] directions = new int[][] { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
            int level = 0;

            queue.offer(new int[] { row, col });
            visited[row][col] = true;

            while (!queue.isEmpty()) {
                int levelSize = queue.size();

                for (int i = 0; i < levelSize; i++) {
                    int[] node = queue.poll();
                    if (mat[node[0]][node[1]] == 0) {
                        return level;
                    }

                    for (int[] d : directions) {
                        int x = node[0] + d[0];
                        int y = node[1] + d[1];
                        if (isValid(x, y, m, n) && !visited[x][y]) {
                            queue.offer(new int[] { x, y });
                            visited[x][y] = true;
                        }
                    }
                }

                level++;
            }

            return -1;
        }

        private boolean isValid(int row, int col, int m, int n) {
            return row >= 0 && row < m && col >= 0 && col < n;
        }
    }

    /*
    Multi-source BFS approach. Instead of searching from each 1-cell independently,
    we flip the perspective: seed the queue with ALL 0-cells at once and expand outward.
    The first time a 1-cell is reached, the current BFS level is its shortest distance
    to any 0. Every cell is visited exactly once.
    
    When we start BFS from all the 0s, we can easily calculate the level/distance when
    we reach a 1 (analogy: ripple effect). This means we only need a single BFS queue.
    This approach is called multi-source BFS.
    
    The reverse doesn't work; we can't use a single BFS queue when we start from the 1s
    because when we reach a 0, we don't know which 1 was the starting point. We have to
    perform BFS for every 1, which was the solution above, but that is inefficient.
    
    Time: O(m * n) — single BFS pass visiting each cell once
    Space: O(m * n) — visited array, result array, and queue
    */
    class Solution2 {
        public int[][] updateMatrix(int[][] mat) {
            int m = mat.length;
            int n = mat[0].length;
            Queue<int[]> queue = new ArrayDeque<>();
            boolean[][] visited = new boolean[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (mat[i][j] == 0) {
                        queue.offer(new int[] { i, j });
                        visited[i][j] = true;
                    }
                }
            }

            return bfs(queue, visited, mat, m, n);
        }

        private int[][] bfs(Queue<int[]> queue, boolean[][] visited, int[][] mat, int m, int n) {
            int[][] res = new int[m][n];
            int[][] directions = new int[][] { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
            int level = 0;

            while (!queue.isEmpty()) {
                int levelSize = queue.size();

                for (int i = 0; i < levelSize; i++) {
                    int[] node = queue.poll();
                    int row = node[0];
                    int col = node[1];

                    if (mat[row][col] == 1) {
                        res[row][col] = level;
                    }

                    for (int[] d : directions) {
                        int x = row + d[0];
                        int y = col + d[1];
                        if (isValid(x, y, m, n) && !visited[x][y]) {
                            queue.offer(new int[] { x, y });
                            visited[x][y] = true;
                        }
                    }
                }

                level++;
            }

            return res;
        }

        private boolean isValid(int row, int col, int m, int n) {
            return row >= 0 && row < m && col >= 0 && col < n;
        }
    }

    private static String matToString(int[][] mat) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < mat.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(Arrays.toString(mat[i]));
        }
        sb.append("]");
        return sb.toString();
    }

    private static boolean matEquals(int[][] a, int[][] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (!Arrays.equals(a[i], b[i])) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        // Test cases: {input mat, expected output}
        int[][][] inputs = {
                // Test 1: LeetCode example 1
                {{ 0, 0, 0 }, { 0, 1, 0 }, { 0, 0, 0 }},
                // Test 2: LeetCode example 2
                {{ 0, 0, 0 }, { 0, 1, 0 }, { 1, 1, 1 }},
                // Test 3: all zeros — distances are all 0
                {{ 0, 0 }, { 0, 0 }},
                // Test 4: single cell 0
                {{ 0 }},
                // Test 5: single cell 1 adjacent to 0
                {{ 0, 1 }},
                // Test 6: single row with 1s at edges
                {{ 1, 1, 0, 1, 1 }},
                // Test 7: single column
                {{ 1 }, { 1 }, { 0 }, { 1 }, { 1 }},
                // Test 8: corner 0, rest 1s — distances increase diagonally
                {{ 0, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 }},
                // Test 9: 0s on opposite corners
                {{ 0, 1, 1 }, { 1, 1, 1 }, { 1, 1, 0 }},
                // Test 10: checkerboard — all 1s are adjacent to a 0
                {{ 0, 1, 0 }, { 1, 0, 1 }, { 0, 1, 0 }},
        };
        int[][][] expected = {
                {{ 0, 0, 0 }, { 0, 1, 0 }, { 0, 0, 0 }},
                {{ 0, 0, 0 }, { 0, 1, 0 }, { 1, 2, 1 }},
                {{ 0, 0 }, { 0, 0 }},
                {{ 0 }},
                {{ 0, 1 }},
                {{ 2, 1, 0, 1, 2 }},
                {{ 2 }, { 1 }, { 0 }, { 1 }, { 2 }},
                {{ 0, 1, 2 }, { 1, 2, 3 }, { 2, 3, 4 }},
                {{ 0, 1, 2 }, { 1, 2, 1 }, { 2, 1, 0 }},
                {{ 0, 1, 0 }, { 1, 0, 1 }, { 0, 1, 0 }},
        };

        P_542_01Matrix outer = new P_542_01Matrix();

        // --- Test Solution (brute-force BFS per cell) ---
        Solution solver1 = outer.new Solution();
        System.out.println("Running tests for P_542_01Matrix.Solution.updateMatrix\n");
        int pass1 = 0;
        for (int i = 0; i < inputs.length; i++) {
            int[][] actual = solver1.updateMatrix(inputs[i]);
            boolean ok = matEquals(expected[i], actual);
            if (ok) pass1++;
            System.out.printf("Test %d: expected=%s, actual=%s => %s%n",
                    i + 1, matToString(expected[i]), matToString(actual), ok ? "PASS" : "FAIL");
        }

        System.out.println("\n" + "=".repeat(50));

        // --- Test Solution2 (multi-source BFS) ---
        Solution2 solver2 = outer.new Solution2();
        System.out.println("\nRunning tests for P_542_01Matrix.Solution2.updateMatrix\n");
        int pass2 = 0;
        for (int i = 0; i < inputs.length; i++) {
            int[][] actual = solver2.updateMatrix(inputs[i]);
            boolean ok = matEquals(expected[i], actual);
            if (ok) pass2++;
            System.out.printf("Test %d: expected=%s, actual=%s => %s%n",
                    i + 1, matToString(expected[i]), matToString(actual), ok ? "PASS" : "FAIL");
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:%n");
        System.out.printf("Solution (BFS per cell): %d/%d tests passed%n", pass1, inputs.length);
        System.out.printf("Solution2 (multi-source BFS): %d/%d tests passed%n", pass2, inputs.length);
    }
}
