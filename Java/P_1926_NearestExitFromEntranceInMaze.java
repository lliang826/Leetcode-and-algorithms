import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class P_1926_NearestExitFromEntranceInMaze {
    /*
    BFS approach with a 2D boolean array for seen nodes.
    We are given a maze and an entrance. Because each move has the same cost, we can use BFS to find
    the shortest path from the entrance to the nearest valid exit. An exit is an empty cell on the
    border of the maze, but the entrance itself does not count as an exit even if it is on the border.

    We treat each maze cell as a graph node. From each node, we check up to 4 neighboring cells
    using the directions array. In graph terms, V = m * n cells and E is bounded by 4 * m * n neighbor
    checks, so O(V + E) simplifies to O(m * n).

    Time: O(m * n), where m is the number of rows and n is the number of columns
    Space: O(m * n), for the queue and seen array
    */
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

    /*
    BFS approach that marks visited cells in-place by changing open cells from '.' to '+'. This avoids
    a separate seen array, but it also means this method mutates the input maze. The BFS logic is the
    same: process cells level by level, and the first non-entrance border cell we reach is the nearest
    exit.

    Time: O(m * n), where m is the number of rows and n is the number of columns
    Space: O(m * n), for the queue; visited state is stored in-place in the maze
    */
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

    public static void main(String[] args) {
        P_1926_NearestExitFromEntranceInMaze outer = new P_1926_NearestExitFromEntranceInMaze();

        // Test cases: {maze, entrance, expected output}
        Object[][] tests = new Object[][] {
                { new char[][] { { '+', '+', '.', '+' }, { '.', '.', '.', '+' }, { '+', '+', '+', '.' } },
                        new int[] { 1, 2 }, 1 },
                { new char[][] { { '+', '+', '+' }, { '.', '.', '.' }, { '+', '+', '+' } },
                        new int[] { 1, 0 }, 2 },
                { new char[][] { { '.', '+' } }, new int[] { 0, 0 }, -1 },
                { new char[][] { { '+', '.', '+' }, { '.', '.', '.' }, { '+', '.', '+' } },
                        new int[] { 1, 1 }, 1 },
                { new char[][] { { '+', '+', '+' }, { '+', '.', '+' }, { '+', '+', '+' } },
                        new int[] { 1, 1 }, -1 },
                { new char[][] { { '.', '.', '.', '.', '.' } }, new int[] { 0, 2 }, 1 },
                { new char[][] { { '.', '+', '.' }, { '.', '+', '.' }, { '.', '.', '.' } },
                    new int[] { 0, 0 }, 1 },
                { new char[][] { { '+', '+', '+', '+', '+' }, { '+', '.', '.', '.', '+' },
                        { '+', '.', '+', '.', '+' }, { '+', '.', '+', '.', '.' }, { '+', '+', '+', '+', '+' } },
                        new int[] { 1, 1 }, 5 }
        };

        System.out.println("Running tests for P_1926_NearestExitFromEntranceInMaze.Solution\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            char[][] maze = deepCopy((char[][]) tests[i][0]);
            int[] entrance = Arrays.copyOf((int[]) tests[i][1], ((int[]) tests[i][1]).length);
            int expected = (int) tests[i][2];
            Solution solver = outer.new Solution();
            int actual = solver.nearestExit(maze, entrance);

            boolean ok = expected == actual;
            if (ok)
                pass1++;
            System.out.printf("Test %d: maze=%s, entrance=%s => expected=%d, actual=%d => %s\n",
                    i + 1, Arrays.deepToString((char[][]) tests[i][0]), Arrays.toString((int[]) tests[i][1]),
                    expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_1926_NearestExitFromEntranceInMaze.Solution2\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            char[][] maze = deepCopy((char[][]) tests[i][0]);
            int[] entrance = Arrays.copyOf((int[]) tests[i][1], ((int[]) tests[i][1]).length);
            int expected = (int) tests[i][2];
            Solution2 solver = outer.new Solution2();
            int actual = solver.nearestExit(maze, entrance);

            boolean ok = expected == actual;
            if (ok)
                pass2++;
            System.out.printf("Test %d: maze=%s, entrance=%s => expected=%d, actual=%d => %s\n",
                    i + 1, Arrays.deepToString((char[][]) tests[i][0]), Arrays.toString((int[]) tests[i][1]),
                    expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("Solution: %d/%d tests passed\n", pass1, tests.length);
        System.out.printf("Solution2: %d/%d tests passed\n", pass2, tests.length);
    }

    private static char[][] deepCopy(char[][] maze) {
        char[][] copy = new char[maze.length][];

        for (int i = 0; i < maze.length; i++) {
            copy[i] = Arrays.copyOf(maze[i], maze[i].length);
        }

        return copy;
    }

}
