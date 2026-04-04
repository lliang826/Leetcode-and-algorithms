import java.util.ArrayDeque;
import java.util.Queue;

public class P_542_01Matrix {
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
}
