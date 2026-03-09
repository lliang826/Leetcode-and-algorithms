public class P_200_NumberOfIslands {
    /*
    DFS approach. For this problem, building an adjacency list isn't needed - we can find the edges to a node
    by looking at the nodes adjacent to it (right, down, left, up).
    Like all other graph problems, we need to track the nodes that we have already seen/visited - we do this
    using a 2D array of booleans (the indices correspond with the input 2D array).
    
    The problem tells us that it's a m x n 2D binary grid, so we can calculate m and n easily.
    We iterate through the matrix and whenever we find a value of '1' that isn't visited yet, we can increment
    our island counter and perform DFS to mark the adjacent nodes as visited.
    
    The tricky part of this problem is knowing to create a predefined array for the 4 adjacent directions. We
    also need a helper function to determine if the new node in that direction is valid/within bounds.
    
    Time: O(nodes + edges) => O(nodes) => O(m * n), where m and n are the dimensions of the input matrix
        - # of edges is fixed at 4, which is a constant and can therefore be omitted
    Space: O(m * n + m * n) => O(m * n)
        - 2D boolean array has m * n space
        - stack also requires m * n space
    */
    class Solution {
        boolean[][] seen;
        int[][] directions = new int[][] { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

        public int numIslands(char[][] grid) {
            int m = grid.length;
            int n = grid[0].length;
            this.seen = new boolean[m][n];
            int res = 0;

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == '1' && !seen[i][j]) {
                        res += 1;
                        dfs(i, j, grid);
                    }
                }
            }

            return res;
        }

        private void dfs(int row, int col, char[][] grid) {
            seen[row][col] = true;

            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];
                if (isValid(newRow, newCol, grid) && grid[newRow][newCol] == '1' && !seen[newRow][newCol]) {
                    dfs(newRow, newCol, grid);
                }
            }
        }

        private boolean isValid(int row, int col, char[][] grid) {
            return 0 <= row && row < grid.length && 0 <= col && col < grid[0].length;
        }
    }

    private static char[][] copyGrid(char[][] grid) {
        char[][] copy = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = grid[i].clone();
        }
        return copy;
    }

    public static void main(String[] args) {
        // Each test: {grid, expected number of islands}
        char[][][] grids = {
                // Test 1: LeetCode example 1 — 1 island
                {
                        { '1', '1', '1', '1', '0' },
                        { '1', '1', '0', '1', '0' },
                        { '1', '1', '0', '0', '0' },
                        { '0', '0', '0', '0', '0' }
                },
                // Test 2: LeetCode example 2 — 3 islands
                {
                        { '1', '1', '0', '0', '0' },
                        { '1', '1', '0', '0', '0' },
                        { '0', '0', '1', '0', '0' },
                        { '0', '0', '0', '1', '1' }
                },
                // Test 3: single cell land
                {
                        { '1' }
                },
                // Test 4: single cell water
                {
                        { '0' }
                },
                // Test 5: all water
                {
                        { '0', '0', '0' },
                        { '0', '0', '0' },
                        { '0', '0', '0' }
                },
                // Test 6: all land — 1 island
                {
                        { '1', '1', '1' },
                        { '1', '1', '1' },
                        { '1', '1', '1' }
                },
                // Test 7: diagonal cells — 5 separate islands (no diagonal adjacency)
                {
                        { '1', '0', '1' },
                        { '0', '1', '0' },
                        { '1', '0', '1' }
                },
                // Test 8: two rows, alternating
                {
                        { '1', '0', '1', '0', '1' },
                        { '0', '1', '0', '1', '0' }
                },
                // Test 9: single row
                {
                        { '1', '0', '1', '1', '0', '1' }
                },
                // Test 10: single column
                {
                        { '1' },
                        { '0' },
                        { '1' },
                        { '1' },
                        { '0' },
                        { '1' }
                }
        };
        int[] expected = { 1, 3, 1, 0, 0, 1, 5, 5, 3, 3 };

        System.out.println("Running tests for P_200_NumberOfIslands.numIslands\n");
        int pass = 0;
        for (int i = 0; i < grids.length; i++) {
            P_200_NumberOfIslands outer = new P_200_NumberOfIslands();
            Solution solver = outer.new Solution();
            char[][] gridCopy = copyGrid(grids[i]);
            int actual = solver.numIslands(gridCopy);
            boolean ok = actual == expected[i];
            if (ok)
                pass++;
            System.out.printf("Test %d: expected=%d, actual=%d => %s%n",
                    i + 1, expected[i], actual, ok ? "PASS" : "FAIL");
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:%n");
        System.out.printf("numIslands: %d/%d tests passed%n", pass, grids.length);
    }
}
