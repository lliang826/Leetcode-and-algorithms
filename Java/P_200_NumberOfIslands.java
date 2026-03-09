public class P_200_NumberOfIslands {
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
}
