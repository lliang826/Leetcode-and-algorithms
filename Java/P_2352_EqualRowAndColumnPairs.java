import java.util.HashMap;
import java.util.Map;

public class P_2352_EqualRowAndColumnPairs {
    /*
    Hash map approach. Bit of a tricky problem: 
    - Iterating through a 2D to get the columns (invert the loops and the indices)
    - Multiply the matching rows and columns to get the number of pairs
    - Using a string for the hash map key, which means using StringBuilder to convert the
    array to a string
    
    First, we iterate through grid[i] to get all the rows. Since we are looking for matching
    rows and columns, each row/column must be the key in the hash map. However, arrays are
    mutable, which means we must convert the array to a string before putting it into the 
    hash map. We have to also use a delimiter to separate values in each row, or else 
    [11, 1, 1] looks the same as [1, 11, 1]
    Without delimiter: '1111'
    With delimiter: '11#1#1#' and '1#11#1#'
    
    Then, we iterate through the input 2D array again to get the columns. We can do this by
    inverting the loops and the indices. Again, we use a delimiter to separate values. The
    columns must be put in a separate hash map or else we cannot calculate the number of
    pairs.
    
    Finally, we calculate the number of pairs by finding the same rows and columns, and
    multiplying their occurrences.
    E.g., 3 rows and 2 columns with the same string array => 3 * 2 = 6 pairs
    
    Time: O(n^2 + n^2) => O(n^2), where n is the number of subarrays and the number of 
    elements in each subarray (input is a n x n 2D array). We have to iterate through all 
    elements in the input 2D array twice to get the rows and columns
    
    Space: O(n * n) => O(n^2), we store all rows/columns into 2 separate hash maps, so there
    are n rows/columns composed of n integers (and delimiters) 
    */
    public int equalPairs(int[][] grid) {
        Map<String, Integer> rows = new HashMap<>();

        for (int i = 0; i < grid.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < grid[i].length; j++) {
                sb.append(grid[i][j]).append('#');
            }
            rows.put(sb.toString(), rows.getOrDefault(sb.toString(), 0) + 1);
        }

        Map<String, Integer> columns = new HashMap<>();
        for (int i = 0; i < grid[0].length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < grid.length; j++) {
                sb.append(grid[j][i]).append('#');
            }
            columns.put(sb.toString(), columns.getOrDefault(sb.toString(), 0) + 1);
        }

        int output = 0;
        for (String key : rows.keySet()) {
            if (columns.containsKey(key)) {
                output += rows.get(key) * columns.get(key);
            }
        }

        return output;
    }

    public static void main(String[] args) {
        P_2352_EqualRowAndColumnPairs solver = new P_2352_EqualRowAndColumnPairs();

        Object[][] tests = new Object[][] {
                { new int[][] { { 3, 2, 1 }, { 1, 7, 6 }, { 2, 7, 7 } }, 1 },
                { new int[][] { { 3, 1, 2, 2 }, { 1, 4, 4, 5 }, { 2, 4, 2, 2 }, { 2, 4, 2, 2 } }, 3 },
                { new int[][] { { 1 } }, 1 },
                { new int[][] { { 1, 2 }, { 3, 4 } }, 0 },
                { new int[][] { { 1, 2 }, { 2, 1 } }, 2 },
                { new int[][] { { 11, 1 }, { 1, 11 } }, 2 },
                { new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }, 9 },
                { new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } }, 0 },
                { new int[][] { { 13, 13 }, { 13, 13 } }, 4 }
        };

        System.out.println("Running tests for P_2352_EqualRowAndColumnPairs.equalPairs\n");
        int pass = 0;
        for (int i = 0; i < tests.length; i++) {
            int[][] input = (int[][]) tests[i][0];
            int expected = (int) tests[i][1];
            int actual = solver.equalPairs(input);

            boolean ok = expected == actual;
            if (ok)
                pass++;
            System.out.printf("Test %d: expected=%d, actual=%d => %s\n",
                    i + 1, expected, actual, (ok ? "PASS" : "FAIL"));
        }
        System.out.printf("\nSummary: %d/%d tests passed.\n", pass, tests.length);
    }
}
