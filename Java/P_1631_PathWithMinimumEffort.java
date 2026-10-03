import java.util.ArrayDeque;
import java.util.Queue;

public class P_1631_PathWithMinimumEffort {
    class Solution {
        public int minimumEffortPath(int[][] heights) {
            int left = 0;
            int right = (int) (Math.pow(10, 6)) - 1;

            while (left <= right) {
                int mid = (right - left) / 2 + left;
                if (check(heights, mid)) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            return left;
        }

        private boolean check(int[][] heights, int mid) {
            int rows = heights.length;
            int cols = heights[0].length;
            int[][] directions = new int[][] { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

            Queue<int[]> queue = new ArrayDeque<>();
            boolean[][] seen = new boolean[rows][cols];

            queue.offer(new int[] { 0, 0 });
            seen[0][0] = true;

            while (!queue.isEmpty()) {
                int level = queue.size();

                for (int i = 0; i < level; i++) {
                    int[] node = queue.poll();

                    if (node[0] == rows - 1 && node[1] == cols - 1) {
                        return true;
                    }

                    for (int[] d : directions) {
                        int row = node[0] + d[0];
                        int col = node[1] + d[1];

                        if (isValid(rows, cols, row, col) && !seen[row][col]
                                && Math.abs(heights[row][col] - heights[node[0]][node[1]]) <= mid) {
                            queue.offer(new int[] { row, col });
                            seen[row][col] = true;
                        }
                    }
                }
            }

            return false;
        }

        private boolean isValid(int rows, int cols, int row, int col) {
            return 0 <= row && row <= rows - 1 && 0 <= col && col <= cols - 1;
        }
    }

    private static int[][] gradientGrid(int rows, int cols) {
        int[][] grid = new int[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = r * cols + c + 1;
            }
        }
        return grid;
    }

    private static int[][] checkerboardGrid(int rows, int cols, int low, int high) {
        int[][] grid = new int[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = ((r + c) % 2 == 0) ? low : high;
            }
        }
        return grid;
    }

    private static int[][] filledGrid(int rows, int cols, int value) {
        int[][] grid = new int[rows][cols];
        for (int[] row : grid) {
            java.util.Arrays.fill(row, value);
        }
        return grid;
    }

    private static String formatGrid(int[][] grid) {
        if (grid.length * grid[0].length <= 25) {
            return java.util.Arrays.deepToString(grid);
        }
        return "[" + grid.length + "x" + grid[0].length + " grid]";
    }

    public static void main(String[] args) {
        P_1631_PathWithMinimumEffort outer = new P_1631_PathWithMinimumEffort();

        // Test cases: {heights, expected}
        Object[][] tests = new Object[][] {
                // LeetCode examples
                { new int[][] { { 1, 2, 2 }, { 3, 8, 2 }, { 5, 3, 5 } }, 2 },
                { new int[][] { { 1, 2, 3 }, { 3, 8, 4 }, { 5, 3, 5 } }, 1 },
                { new int[][] { { 1, 2, 1, 1, 1 }, { 1, 2, 1, 2, 1 }, { 1, 2, 1, 2, 1 }, { 1, 2, 1, 2, 1 },
                        { 1, 1, 1, 2, 1 } }, 0 },
                // Single cell: start is the target, no steps taken
                { new int[][] { { 5 } }, 0 },
                // Single row / single column: only one path, answer is the max adjacent difference
                { new int[][] { { 1, 10, 6, 7, 9, 10, 4, 9 } }, 9 },
                { new int[][] { { 1 }, { 100 }, { 50 } }, 99 },
                // Answer at the upper bound of the search range (10^6 - 1)
                { new int[][] { { 1, 1000000 } }, 999999 },
                // Only zero-effort path requires moving up and left (snake through the 1s)
                { new int[][] { { 1, 100, 1, 1, 1 }, { 1, 100, 1, 100, 1 }, { 1, 1, 1, 100, 1 } }, 0 },
                // Max size, all equal heights
                { filledGrid(100, 100, 7), 0 },
                // Max size, every step is 1 -> 10^6 or vice versa
                { checkerboardGrid(100, 100, 1, 1000000), 999999 },
                // Max size, right steps cost 1, down steps cost 100; any path must go down 99 times
                { gradientGrid(100, 100), 100 }
        };

        System.out.println("Running tests for P_1631_PathWithMinimumEffort.minimumEffortPath\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[][] heights = (int[][]) tests[i][0];
            int expected = (int) tests[i][1];
            Solution solver = outer.new Solution();
            int actual = solver.minimumEffortPath(heights);
            boolean ok = (expected == actual);
            if (ok)
                pass1++;
            System.out.printf("Test %d: heights=%s => expected=%d, actual=%d => %s\n",
                    i + 1, formatGrid(heights), expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("minimumEffortPath: %d/%d tests passed\n", pass1, tests.length);
    }
}
