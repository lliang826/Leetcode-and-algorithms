public class P_695_MaxAreaOfIsland {
    /*
    DFS with boolean 2D array to track visited nodes. This problem is very similar to 200. Number of Islands.
    For this problem, the input is a matrix, so we can iterate through it to find any nodes with a value of 
    1. If we do, we perform DFS using the 4 predetermined directions: up, down, left, right. 
    We also need a helper function to determine if a node is valid since we can potentially move to a node 
    that is outside the boundaries of the m x n input matrix.
    While we perform a DFS on each "island" (connected component), we can also track its "area" (# of nodes).
    So every time we find a connected component, we can find the largest area so far using the max() method.
    
    Time: O(m * n), where m and n are the dimensions of the input matrix - we traverse all nodes
    Space: O(m * n + m * n) => O(m * n)
      - we use a 2D boolean array (m x n) to track the visited nodes
      - the stack in DFS is also m * n because in the worst case scenario, the entire grid is an island
    */
    class Solution {
        boolean[][] visited;

        public int maxAreaOfIsland(int[][] grid) {
            visited = new boolean[grid.length][grid[0].length];
            int maxArea = 0;

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 1 && !visited[i][j]) {
                        int area = dfs(i, j, grid);
                        maxArea = Math.max(maxArea, area);
                    }
                }
            }

            return maxArea;
        }

        private int dfs(int row, int col, int[][] grid) {
            visited[row][col] = true;
            int area = 1;

            if (isValid(row - 1, col, grid) && grid[row - 1][col] == 1 && !visited[row - 1][col]) {
                area += dfs(row - 1, col, grid);
            }
            if (isValid(row + 1, col, grid) && grid[row + 1][col] == 1 && !visited[row + 1][col]) {
                area += dfs(row + 1, col, grid);
            }
            if (isValid(row, col - 1, grid) && grid[row][col - 1] == 1 && !visited[row][col - 1]) {
                area += dfs(row, col - 1, grid);
            }
            if (isValid(row, col + 1, grid) && grid[row][col + 1] == 1 && !visited[row][col + 1]) {
                area += dfs(row, col + 1, grid);
            }

            return area;
        }

        private boolean isValid(int row, int col, int[][] grid) {
            int m = grid.length;
            int n = grid[0].length;

            return row >= 0 && row <= m - 1 && col >= 0 && col <= n - 1;
        }
    }

    /*
    Identical to the solution above, except that we use a "directions" constant to track the 4 pre-determined
    directions: up, down, left, right. Then, we can simply iterate through all the directions using a for
    loop, which makes the code cleaner.
    
    Same time and space complexities.
    */
    class Solution2 {
        boolean[][] visited;
        int[][] directions = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

        public int maxAreaOfIsland(int[][] grid) {
            visited = new boolean[grid.length][grid[0].length];
            int maxArea = 0;

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 1 && !visited[i][j]) {
                        int area = dfs(i, j, grid);
                        maxArea = Math.max(maxArea, area);
                    }
                }
            }

            return maxArea;
        }

        private int dfs(int row, int col, int[][] grid) {
            visited[row][col] = true;
            int area = 1;

            for (int[] d : directions) {
                int x = row + d[0];
                int y = col + d[1];
                if (isValid(x, y, grid) && grid[x][y] == 1 && !visited[x][y]) {
                    area += dfs(x, y, grid);
                }
            }
            return area;
        }

        private boolean isValid(int row, int col, int[][] grid) {
            int m = grid.length;
            int n = grid[0].length;

            return row >= 0 && row <= m - 1 && col >= 0 && col <= n - 1;
        }
    }

    private static int[][] copyGrid(int[][] grid) {
        int[][] copy = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = grid[i].clone();
        }
        return copy;
    }

    public static void main(String[] args) {
        int[][][] grids = {
                // Test 1: LeetCode example 1 — max island area is 6
                {
                        { 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0 },
                        { 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0 },
                        { 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 }
                },
                // Test 2: LeetCode example 2 — all water
                {
                        { 0, 0, 0, 0, 0, 0, 0, 0 }
                },
                // Test 3: single cell land
                {
                        { 1 }
                },
                // Test 4: single cell water
                {
                        { 0 }
                },
                // Test 5: all water
                {
                        { 0, 0, 0 },
                        { 0, 0, 0 },
                        { 0, 0, 0 }
                },
                // Test 6: all land — one big island
                {
                        { 1, 1, 1 },
                        { 1, 1, 1 },
                        { 1, 1, 1 }
                },
                // Test 7: diagonal cells — no diagonal adjacency, max area is 1
                {
                        { 1, 0, 1 },
                        { 0, 1, 0 },
                        { 1, 0, 1 }
                },
                // Test 8: two islands, different sizes — max is 4
                {
                        { 1, 1, 0, 0 },
                        { 1, 1, 0, 0 },
                        { 0, 0, 1, 0 },
                        { 0, 0, 0, 0 }
                },
                // Test 9: single row with islands
                {
                        { 1, 0, 1, 1, 0, 1 }
                },
                // Test 10: single column
                {
                        { 1 },
                        { 0 },
                        { 1 },
                        { 1 },
                        { 1 },
                        { 0 }
                }
        };
        int[] expected = { 6, 0, 1, 0, 0, 9, 1, 4, 2, 3 };

        System.out.println("Running tests for P_695_MaxAreaOfIsland — Solution (explicit if-blocks)\n");
        int pass1 = 0;
        for (int i = 0; i < grids.length; i++) {
            P_695_MaxAreaOfIsland outer = new P_695_MaxAreaOfIsland();
            Solution solver = outer.new Solution();
            int actual = solver.maxAreaOfIsland(copyGrid(grids[i]));
            boolean ok = actual == expected[i];
            if (ok) pass1++;
            System.out.printf("Test %d: expected=%d, actual=%d => %s%n",
                    i + 1, expected[i], actual, ok ? "PASS" : "FAIL");
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_695_MaxAreaOfIsland — Solution2 (directions array)\n");
        int pass2 = 0;
        for (int i = 0; i < grids.length; i++) {
            P_695_MaxAreaOfIsland outer = new P_695_MaxAreaOfIsland();
            Solution2 solver = outer.new Solution2();
            int actual = solver.maxAreaOfIsland(copyGrid(grids[i]));
            boolean ok = actual == expected[i];
            if (ok) pass2++;
            System.out.printf("Test %d: expected=%d, actual=%d => %s%n",
                    i + 1, expected[i], actual, ok ? "PASS" : "FAIL");
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:%n");
        System.out.printf("Solution:  %d/%d tests passed%n", pass1, grids.length);
        System.out.printf("Solution2: %d/%d tests passed%n", pass2, grids.length);
    }
}
