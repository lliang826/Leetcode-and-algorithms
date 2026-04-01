import java.util.ArrayDeque;
import java.util.Queue;

public class P_1091_ShortestPathInBinaryMatrix {
    class Solution {
        public int shortestPathBinaryMatrix(int[][] grid) {
            boolean[][] visited = new boolean[grid.length][grid.length];
            int pathLength = 0;
            Queue<int[]> queue = new ArrayDeque<>();
            int[][] directions = new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 }, { 1, -1 },
                    { -1, -1 }, { -1, 1 } };
            int n = grid.length;

            if (grid[0][0] != 0) {
                return -1;
            }

            queue.offer(new int[] { 0, 0 });
            visited[0][0] = true;

            while (!queue.isEmpty()) {
                int rowWidth = queue.size();
                pathLength++;

                for (int i = 0; i < rowWidth; i++) {
                    int[] node = queue.poll();
                    if (node[0] == n - 1 && node[1] == n - 1) {
                        return pathLength;
                    }

                    for (int[] d : directions) {
                        int x = node[0] + d[0];
                        int y = node[1] + d[1];
                        if (isValid(x, y, n) && !visited[x][y] && grid[x][y] == 0) {
                            queue.offer(new int[] { x, y });
                            visited[x][y] = true;
                        }
                    }
                }
            }

            return -1;
        }

        private boolean isValid(int row, int col, int n) {
            return row >= 0 && row < n && col >= 0 && col < n;
        }
    }
}