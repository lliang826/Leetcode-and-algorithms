import java.util.ArrayDeque;
import java.util.Queue;

public class P_1293_ShortestPathInAGridWithObstaclesElimination {
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
                    if (node[0] == m - 1 && node[1] == n - 1) {
                        return steps - 1;
                    }

                    for (int[] d : directions) {
                        int x = node[0] + d[0];
                        int y = node[1] + d[1];
                        if (!isWithinGrid(x, y, m, n)) {
                            continue;
                        }

                        int newRemain = node[2] - grid[x][y];
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
}
