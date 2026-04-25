import java.util.ArrayDeque;
import java.util.Queue;

public class P_1926_NearestExitFromEntranceInMaze {
    class Solution {
        public int nearestExit(char[][] maze, int[] entrance) {
            Queue<int[]> queue = new ArrayDeque<>();
            boolean[][] seen;
            int[][] directions = new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

            int m = maze.length;
            int n = maze[0].length;
            seen = new boolean[m][n];

            queue.offer(entrance);
            seen[entrance[0]][entrance[1]] = true;
            int steps = 0;

            while (!queue.isEmpty()) {
                int levelSize = queue.size();

                for (int i = 0; i < levelSize; i++) {
                    int[] node = queue.poll();

                    boolean isEntrance = node[0] == entrance[0] && node[1] == entrance[1];
                    boolean isBorderExit = node[0] == 0 || node[0] == m - 1 || node[1] == 0 || node[1] == n - 1;

                    if (!isEntrance && isBorderExit) {
                        return steps;
                    }

                    for (int[] d : directions) {
                        int x = node[0] + d[0];
                        int y = node[1] + d[1];
                        if (isValid(x, y, maze, m, n) && !seen[x][y]) {
                            queue.offer(new int[] { x, y });
                            seen[x][y] = true;
                        }
                    }
                }

                steps++;
            }

            return -1;
        }

        private boolean isValid(int row, int col, char[][] maze, int m, int n) {
            return row >= 0 && row < m && col >= 0 && col < n && maze[row][col] == '.';
        }
    }

    class Solution2 {
        public int nearestExit(char[][] maze, int[] entrance) {
            Queue<int[]> queue = new ArrayDeque<>();
            int[][] directions = new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
            int m = maze.length;
            int n = maze[0].length;

            queue.offer(entrance);
            maze[entrance[0]][entrance[1]] = '+';
            int steps = 0;

            while (!queue.isEmpty()) {
                int levelSize = queue.size();

                for (int i = 0; i < levelSize; i++) {
                    int[] node = queue.poll();

                    if (!isEntrance(entrance, node[0], node[1]) && isBorderExit(m, n, node[0], node[1])) {
                        return steps;
                    }

                    for (int[] d : directions) {
                        int x = node[0] + d[0];
                        int y = node[1] + d[1];
                        if (isValid(x, y, maze, m, n)) {
                            queue.offer(new int[] { x, y });
                            maze[x][y] = '+';
                        }
                    }
                }

                steps++;
            }

            return -1;
        }

        private boolean isValid(int row, int col, char[][] maze, int m, int n) {
            return row >= 0 && row < m && col >= 0 && col < n && maze[row][col] == '.';
        }

        private boolean isBorderExit(int m, int n, int row, int col) {
            return row == 0 || row == m - 1 || col == 0 || col == n - 1;
        }

        private boolean isEntrance(int[] entrance, int row, int col) {
            return row == entrance[0] && col == entrance[1];
        }
    }

}
