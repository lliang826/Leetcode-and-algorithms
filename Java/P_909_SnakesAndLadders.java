import java.util.ArrayDeque;
import java.util.Queue;

public class P_909_SnakesAndLadders {
    class Solution {
        public int snakesAndLadders(int[][] board) {
            int n = board.length;
            Queue<Integer> queue = new ArrayDeque<>();
            boolean[] seen = new boolean[n * n + 1];
            int rolls = 0;

            queue.offer(1);
            seen[1] = true;

            while (!queue.isEmpty()) {
                int levelSize = queue.size();

                for (int i = 0; i < levelSize; i++) {
                    int node = queue.poll();

                    if (node == n * n) {
                        return rolls;
                    }

                    for (int next = node + 1; next <= node + 6 && next <= n * n; next++) {
                        int[] c = boardSquareToMatrix(next, n);
                        int dest = next;

                        if (board[c[0]][c[1]] != -1) {
                            dest = board[c[0]][c[1]];
                        }

                        if (!seen[dest]) {
                            queue.offer(dest);
                            seen[dest] = true;
                        }
                    }
                }

                rolls++;
            }

            return -1;
        }

        private int[] boardSquareToMatrix(int square, int n) {
            int index = square - 1;
            int rowFromBottom = index / n;
            int row = n - 1 - rowFromBottom;
            int col = index % n;

            if (rowFromBottom % 2 == 1) {
                col = n - 1 - col;
            }

            return new int[] { row, col };
        }
    }

    class Solution2 {
        public int snakesAndLadders(int[][] board) {
            int n = board.length;
            Queue<Integer> queue = new ArrayDeque<>();
            boolean[] seen = new boolean[n * n + 1];
            int rolls = 0;

            queue.offer(1);
            seen[1] = true;

            int[] cells = this.buildCells(board, n);

            while (!queue.isEmpty()) {
                int levelSize = queue.size();

                for (int i = 0; i < levelSize; i++) {
                    int square = queue.poll();

                    if (square == n * n) {
                        return rolls;
                    }

                    for (int next = square + 1; next <= square + 6 && next <= n * n; next++) {
                        int dest = cells[next] != -1 ? cells[next] : next;

                        if (!seen[dest]) {
                            queue.offer(dest);
                            seen[dest] = true;
                        }
                    }
                }

                rolls++;
            }

            return -1;
        }

        private int[] buildCells(int[][] board, int n) {
            int[] cells = new int[n * n + 1];
            int index = 1;

            for (int i = 1; i <= n; i++) {
                if (i % 2 == 0) {
                    for (int j = n - 1; j >= 0; j--) {
                        cells[index] = board[n - i][j];
                        index++;
                    }
                } else {
                    for (int j = 0; j < n; j++) {
                        cells[index] = board[n - i][j];
                        index++;
                    }
                }
            }

            return cells;
        }
    }
}
