import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class P_909_SnakesAndLadders {
    /*
    BFS approach with boolean array to track seen nodes/squares.
    This is a pretty tricky problem because it isn't your typical matrix problem where you 
    move in 4 directions (up, down, left, right). Instead, we simulate a 6-sided dice roll,
    which allows us to move 1-6 squares. 
    Given this different movement, and the fact that the matrix/board is structured weirdly 
    (1-indexed, bottom left starting point instead of top left, alternating column order 
    every row), we store the square value in the queue (and in 'seen') instead of the 
    matrix coordinates (row/column as int[]).
    The super tricky part of this problem is converting the square value into its matrix
    coordinates (e.g., what is the row/col of square 2?). This is important because we have
    to check the matrix for snakes/ladders.
    Aside from the helper function which converts a square value to the matrix coordinates,
    everything else is just straightforward BFS stuff.
    
    Time: O(n^2), we visit all squares/nodes in the graph at most once in BFS
    Space: O(n^2), the boolean seen array and the queue
    */
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

    /*
    Similar to the solution above, but instead of dealing with the confusing logic of converting
    a square value to its matrix coordinates, we can instead flatten the 2D matrix input into a
    1D array.
    We can do this because the movement/square values in this problem are all moving in the
    same ascending order. 
    For the 1D preprocessing array, each square is represented by the array index, so we can check
    the value at the index for snakes/ladders.
    This approach is more intuitive.
    
    Time: O(n^2), same time complexity as above
    Space: O(n^2 + 1 + n^2 + 1) => O(n^2), for the preprocessing 1D array,the boolean seen array,
            and the queue
    */
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

    private static int[][] copyBoard(int[][] board) {
        int[][] copy = new int[board.length][];
        for (int i = 0; i < board.length; i++) {
            copy[i] = board[i].clone();
        }
        return copy;
    }

    public static void main(String[] args) {
        P_909_SnakesAndLadders outer = new P_909_SnakesAndLadders();
        Solution solver1 = outer.new Solution();
        Solution2 solver2 = outer.new Solution2();

        Object[][] tests = new Object[][] {
                // LeetCode example 1: mix of snake/ladder jumps on a 6x6 board
                { new int[][] {
                        { -1, -1, -1, -1, -1, -1 },
                        { -1, -1, -1, -1, -1, -1 },
                        { -1, -1, -1, -1, -1, -1 },
                        { -1, 35, -1, -1, 13, -1 },
                        { -1, -1, -1, -1, -1, -1 },
                        { -1, 15, -1, -1, -1, -1 } }, 4 },
                // LeetCode example 2: can finish in one roll by landing on square 3, which redirects to 9
                { new int[][] {
                        { -1, -1, -1 },
                        { -1, 9, 8 },
                        { -1, 8, 9 } }, 1 },
                // Smallest board shape: can roll directly from square 1 to square 4
                { new int[][] {
                        { -1, -1 },
                        { -1, 3 } }, 1 },
                // No snakes or ladders: plain dice movement across a 3x3 board
                { new int[][] {
                        { -1, -1, -1 },
                        { -1, -1, -1 },
                        { -1, -1, -1 } }, 2 },
                // Odd-sized board with an immediate ladder from square 2 to the finish
                { new int[][] {
                        { -1, -1, -1 },
                        { -1, -1, -1 },
                        { -1, 9, -1 } }, 1 },
                // Snake near the start: landing on square 2 sends us back, so BFS must try other rolls
                { new int[][] {
                        { -1, -1, -1 },
                        { -1, -1, -1 },
                        { -1, 1, -1 } }, 2 },
                // Ladder is useful but does not finish immediately
                { new int[][] {
                        { -1, -1, -1, -1 },
                        { -1, -1, -1, -1 },
                        { -1, -1, -1, -1 },
                        { -1, -1, 12, -1 } }, 2 },
                // Avoids revisiting destinations when a snake creates a backward move
                { new int[][] {
                        { -1, -1, -1, -1 },
                        { -1, -1, -1, -1 },
                        { -1, 2, -1, -1 },
                        { -1, -1, -1, -1 } }, 3 },
        };

        System.out.println("Running tests for P_909_SnakesAndLadders.Solution.snakesAndLadders\n");
        int pass1 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[][] board = (int[][]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver1.snakesAndLadders(copyBoard(board));

            boolean ok = expected == actual;
            if (ok) {
                pass1++;
            }
            System.out.printf("Test %d: board=%s => expected=%d, actual=%d => %s%n",
                    i + 1, Arrays.deepToString(board), expected, actual, ok ? "PASS" : "FAIL");
        }

        System.out.println("\n" + "=".repeat(50));

        System.out.println("\nRunning tests for P_909_SnakesAndLadders.Solution2.snakesAndLadders\n");
        int pass2 = 0;
        for (int i = 0; i < tests.length; i++) {
            int[][] board = (int[][]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver2.snakesAndLadders(copyBoard(board));

            boolean ok = expected == actual;
            if (ok) {
                pass2++;
            }
            System.out.printf("Test %d: board=%s => expected=%d, actual=%d => %s%n",
                    i + 1, Arrays.deepToString(board), expected, actual, ok ? "PASS" : "FAIL");
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Overall Summary:");
        System.out.printf("Solution (square-to-matrix helper): %d/%d tests passed%n", pass1, tests.length);
        System.out.printf("Solution2 (flattened cells array): %d/%d tests passed%n", pass2, tests.length);
    }
}
