public class P_695_MaxAreaOfIsland {
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
}
