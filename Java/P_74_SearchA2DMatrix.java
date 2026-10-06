public class P_74_SearchA2DMatrix {
    /*
    Binary search approach.

    Since the problem requires us to write a solution with O(log (m * n)) time
    complexity, we must use binary search. The range is the indices of the matrix,
    starting from the top left cell (index 0), going left to right to the bottom
    right cell (index m * n - 1). We must use the indices because that allows us to
    find the middle index in every iteration.

    The tricky part is finding an index's value: each row has n cells, so if we
    divide the index by n, the floor gives us the row. The remainder (or modulo) of
    that division gives us the column.

    Time: O(log (m * n))
    - m is the number of rows and n is the number of columns
    - m * n gives the total number of cells

    Space: O(1), no data structures
    */
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int left = 0;
        int right = m * n - 1;

        while (left <= right) {
            int mid = (right - left) / 2 + left;
            int row = mid / n;
            int col = mid % n;

            if (target == matrix[row][col]) {
                return true;
            } else if (target < matrix[row][col]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        P_74_SearchA2DMatrix solver = new P_74_SearchA2DMatrix();

        int[][] example = {
                { 1, 3, 5, 7 },
                { 10, 11, 16, 20 },
                { 23, 30, 34, 60 }
        };

        // Test cases: {matrix, target, expected}
        Object[][] tests = new Object[][] {
                { example, 3, true },                                       // LeetCode example 1
                { example, 13, false },                                     // LeetCode example 2
                { example, 1, true },                                       // first cell (index 0)
                { example, 60, true },                                      // last cell (index m*n - 1)
                { example, 10, true },                                      // first cell of a middle row (col = 0)
                { example, 7, true },                                       // last cell of a row (col = n - 1)
                { example, 0, false },                                      // smaller than every value
                { example, 61, false },                                     // larger than every value
                { example, 8, false },                                      // falls in the gap between rows
                { new int[][] { { 5 } }, 5, true },                         // 1x1 found
                { new int[][] { { 5 } }, 4, false },                        // 1x1 not found
                { new int[][] { { 1, 3, 5, 7, 9 } }, 9, true },             // single row (m = 1)
                { new int[][] { { 1 }, { 3 }, { 5 }, { 7 } }, 5, true },    // single column (n = 1)
                { new int[][] { { 1 }, { 3 }, { 5 }, { 7 } }, 4, false },   // single column, missing
                { new int[][] { { -10, -5 }, { -1, 0 } }, -5, true },       // negative values
                { new int[][] { { -10000, 0 }, { 1, 10000 } }, 10000, true } // constraint bounds
        };

        System.out.println("Running tests for P_74_SearchA2DMatrix.searchMatrix\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[][] matrix = (int[][]) tests[i][0];
            int target = (int) tests[i][1];
            boolean expected = (boolean) tests[i][2];
            boolean actual = solver.searchMatrix(matrix, target);

            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: matrix=%s, target=%d => expected=%b, actual=%b => %s\n",
                    i + 1, java.util.Arrays.deepToString(matrix), target, expected, actual,
                    (ok ? "PASS" : "FAIL"));
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("Overall Summary:\n");
        System.out.printf("searchMatrix: %d/%d tests passed\n", pass, tests.length);
    }
}
