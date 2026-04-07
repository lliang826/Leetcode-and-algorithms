import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class P_1293_ShortestPathInAGridWithObstaclesElimination {
    /*
    We use augmented-state BFS because this problem has an obstacle elimination counter k,
    which means that we can reach the same node with different paths and different k values.
    The counter k determines which future nodes are reachable, so both the queue and the 
    visited array are 3D.

    BFS over the state space (row, col, remainingEliminations). Each cell can be visited
    with different numbers of remaining eliminations, so the visited array is 3D:
    visited[row][col][remaining]. When we move into an obstacle cell, we decrement
    the remaining eliminations. BFS guarantees the first time we reach (m-1, n-1)
    is via the shortest path.

    Time: O(m * n * k), each state (row, col, remaining) is visited at most once
    Space: O(m * n * k), for the visited array and queue
    */
    class Solution {
        public int shortestPath(int[][] grid, int k) {
            int m = grid.length;
            int n = grid[0].length;

            Queue<int[]> queue = new ArrayDeque<>();
            boolean[][][] visited = new boolean[m][n][k + 1];
            int[][] directions = new int[][] { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
            int steps = 0;

            queue.add(new int[] { 0, 0, k });
            visited[0][0][k] = true;

            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                steps++;

                for (int i = 0; i < levelSize; i++) {
                    int[] node = queue.poll();
                    int row = node[0];
                    int col = node[1];
                    int remain = node[2];

                    if (row == m - 1 && col == n - 1) {
                        return steps - 1;
                    }

                    for (int[] d : directions) {
                        int x = row + d[0];
                        int y = col + d[1];
                        if (!isWithinGrid(x, y, m, n)) {
                            continue;
                        }

                        int newRemain = remain - grid[x][y];
                        if (newRemain >= 0 && !visited[x][y][newRemain]) {
                            visited[x][y][newRemain] = true;
                            queue.offer(new int[] { x, y, newRemain });
                        }
                    }
                }
            }

            return -1;
        }

        private boolean isWithinGrid(int row, int col, int m, int n) {
            return row >= 0 && row < m && col >= 0 && col < n;
        }
    }

    public static void main(String[] args) {
        P_1293_ShortestPathInAGridWithObstaclesElimination outer = new P_1293_ShortestPathInAGridWithObstaclesElimination();
        Solution solver = outer.new Solution();

        // Test cases: {grid, k, expected}
        int[][][] grids = {
                // 1. LeetCode Example 1: eliminate 1 obstacle for optimal path of length 6
                { { 0, 0, 0 }, { 1, 1, 0 }, { 0, 0, 0 }, { 0, 1, 1 }, { 0, 0, 0 } },
                // 2. LeetCode Example 2: not enough eliminations to reach end
                { { 0, 1, 1 }, { 1, 1, 1 }, { 1, 0, 0 } },
                // 3. 1x1 grid: already at destination
                { { 0 } },
                // 4. No obstacles, basic shortest path
                { { 0, 0, 0 }, { 0, 0, 0 } },
                // 5. 2x2 with diagonal obstacles, enough k to pass through
                { { 0, 1 }, { 1, 0 } },
                // 6. 2x2 with diagonal obstacles, k=0 so no path
                { { 0, 1 }, { 1, 0 } },
                // 7. Large k allows Manhattan-distance shortcut through obstacles
                { { 0, 1, 1 }, { 1, 1, 1 }, { 1, 1, 0 } },
                // 8. Single row with one obstacle
                { { 0, 0, 1, 0, 0 } },
                // 9. Single column with obstacles
                { { 0 }, { 1 }, { 0 }, { 1 }, { 0 } },
                // 10. All zeros, no elimination needed
                { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } },
        };
        int[] ks = { 1, 1, 0, 0, 2, 0, 4, 1, 2, 0 };
        int[] expected = { 6, -1, 0, 3, 2, -1, 4, 4, 4, 4 };

        System.out.println("Running tests for P_1293 shortestPath\n");
        int pass = 0;
        for (int i = 0; i < grids.length; i++) {
            int actual = solver.shortestPath(grids[i], ks[i]);
            boolean ok = actual == expected[i];
            if (ok)
                pass++;
            String gridStr = Arrays.deepToString(grids[i]);
            if (gridStr.length() > 40)
                gridStr = gridStr.substring(0, 37) + "...";
            System.out.printf("Test %d: grid=%s, k=%d => expected=%d, actual=%d => %s\n",
                    i + 1, gridStr, ks[i], expected[i], actual, ok ? "PASS" : "FAIL");
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("shortestPath: %d/%d tests passed\n", pass, grids.length);
    }
}
