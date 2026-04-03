import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class P_1091_ShortestPathInBinaryMatrix {
    /*
    BFS approach with 2D boolean array to track visited nodes. 
    Since the problem asks for the shortest path, we use BFS since it visits nodes based on their distance to the
    root. We also create a 2D array for the 8 directions (4 edges + 4 corners) so the code is cleaner.
    We begin by adding the top left corner (0, 0) to the queue. BFS in graphs is basically the same as BFS in trees,
    so we can use the same placeholder algorithm. 
    Whenever we add a node to the queue, we mark it as visited. We can't do the reverse, where we mark a node as 
    visited when it is dequeued, because if a node has multiple neighbors, it can potentially be enqueued multiple 
    times.
    We increment the pathLength counter every time we visit a new level, and we return this value if we reach the
    bottom right corner (n - 1, n - 1). If we don't reach the bottom right corner after visiting all possible
    neighbors, we return -1.
    For each node, its neighbors are the 8 directions mentioned earlier; we also need a helper method to validate
    if the neighboring node is within the n x n matrix.
    
    Time: O(n^2) because the matrix dimensions are n x n
      - in the worst case scenario, we visit all nodes in the graph if they're all 0s
    Space: O(n^2) because of the 2D boolean array
    */
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

    public static void main(String[] args) {
        P_1091_ShortestPathInBinaryMatrix outer = new P_1091_ShortestPathInBinaryMatrix();
        Solution solver = outer.new Solution();

        // Test cases: {grid, expected shortest path length}
        Object[][] tests = new Object[][] {
                // LeetCode examples
                { new int[][] { { 0, 1 }, { 1, 0 } }, 2 },
                { new int[][] { { 0, 0, 0 }, { 1, 1, 0 }, { 1, 1, 0 } }, 4 },
                { new int[][] { { 1, 0, 0 }, { 1, 1, 0 }, { 1, 1, 0 } }, -1 },
                // Single cell
                { new int[][] { { 0 } }, 1 },
                { new int[][] { { 1 } }, -1 },
                // End cell blocked
                { new int[][] { { 0, 0 }, { 0, 1 } }, -1 },
                // All clear 3x3 — diagonal path length 3
                { new int[][] { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } }, 3 },
                // Start surrounded by walls
                { new int[][] { { 0, 1, 0 }, { 1, 1, 0 }, { 0, 0, 0 } }, -1 },
                // Forced longer path
                { new int[][] { { 0, 0, 0 }, { 1, 1, 0 }, { 0, 0, 0 } }, 4 },
                // Larger 4x4 grid
                { new int[][] {
                        { 0, 0, 1, 0 },
                        { 1, 0, 1, 0 },
                        { 1, 0, 0, 0 },
                        { 0, 0, 0, 0 } }, 4 },
        };

        System.out.println("Running tests for P_1091_ShortestPathInBinaryMatrix.shortestPathBinaryMatrix\n");
        int passed = 0;
        for (int i = 0; i < tests.length; i++) {
            int[][] grid = (int[][]) tests[i][0];
            int expected = (int) tests[i][1];
            // Deep-copy grid since it's not mutated, but keep habit
            int[][] copy = Arrays.stream(grid).map(int[]::clone).toArray(int[][]::new);
            int actual = solver.shortestPathBinaryMatrix(copy);

            boolean ok = expected == actual;
            if (ok) passed++;
            String gridStr = Arrays.deepToString(grid);
            System.out.printf("Test %d: grid=%s => expected=%d, actual=%d => %s\n",
                    i + 1, gridStr, expected, actual, (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("shortestPathBinaryMatrix: %d/%d tests passed\n", passed, tests.length);
    }
}